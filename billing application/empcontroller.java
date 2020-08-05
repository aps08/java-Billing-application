package billingapp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class empcontroller implements Initializable {
    @FXML
    private TextField customernumber,customername;
    @FXML
    private int orderno=0;
    @FXML
    private double amount=0;
    @FXML
    public Label pricevaluelabel,totallabel;
    @FXML
    private Button billproduct,okbuttonid;
    @FXML
    private TableColumn productprice,productname;
    @FXML
    private AnchorPane mainpane;
    @FXML
    public TableView<productDetails> tableview,tableview2;
    @FXML
    ObservableList<productDetails> observableList=FXCollections.observableArrayList();
    //THE INITIALIZER WILL LOAD ONCE THE EMPLOYEE INTERFACE IS LOADED
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getall();
        visibility("false");
    }
    //IT WILL GET ALL PRODUCT AND LOAD IN THE TABLE VIEW
    @FXML
    private void getall(){
        Task<ObservableList<productDetails>> task = new getAllIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //FUNCTION TO PUT PRODUCT FROM ONE TABLE VIEW TO ANOTHER ON ONE CLICK
    @FXML
    private void putincartonclick(MouseEvent e) {
        productname.setCellValueFactory(new PropertyValueFactory<productDetails, String>("productname"));
        productprice.setCellValueFactory(new PropertyValueFactory<productDetails, Double>("productprice"));
        productDetails productDetails=new productDetails();
        productDetails.setProductname(tableview.selectionModelProperty().get().getSelectedItem().getProductname());
        productDetails.setProductprice(tableview.selectionModelProperty().get().getSelectedItem().getProductprice());
        amount=amount+tableview.selectionModelProperty().get().getSelectedItem().getProductprice();
        observableList.add(productDetails);
        tableview2.setItems(observableList);
        StringBuilder  sb=new StringBuilder();
        sb.append(amount);
        pricevaluelabel.setText(sb.toString());
        visibility("true");
    }
    //FUNCTION FOR VISIBILITY OF SOME BUTTON
    @FXML
    private void visibility(String string){
        tableview2.setVisible(Boolean.parseBoolean(string));
        totallabel.setVisible(Boolean.parseBoolean(string));
        pricevaluelabel.setVisible(Boolean.parseBoolean(string));
        billproduct.setVisible(Boolean.parseBoolean(string));
    }
    //FUNCTION FOR REMOVING PRODUCTS FROM THE CART TABLE VIEW
    @FXML
    private void removefromcartonclick(MouseEvent e) {
        StringBuilder  sb=new StringBuilder();
        ObservableList<productDetails> selectedRows = tableview2.getSelectionModel().getSelectedItems();
        amount=amount-tableview2.selectionModelProperty().get().getSelectedItem().getProductprice();
        sb.append(amount);
        ArrayList<productDetails> rows = new ArrayList<>(selectedRows);
        rows.forEach(row -> tableview2.getItems().remove(row));
        if (tableview2.getSelectionModel().getSelectedItems().isEmpty()){
            visibility("false");amount=0;
        }
        pricevaluelabel.setText(sb.toString());
    }
    // WHEN BILL BUTTON IS CLICKED
    // THIS FUNCTION DOES 4 WORKS
    // 1st INCREASE ORDER NUMBER
    // 2nd CONFIRMS THE PAYMENT
    // 3rd TAKES VALUES LIKE CUSTOMER NAME AND CUSTOMER NUMBER AND EMPLOYEE NAME
    // AND IF THE CONFIRMATION IS CANCELLED THE ALERT BOX BOX IS SHOWN
    @FXML
    private void billpayment(ActionEvent e) {
        double paid;
        orderno++;
        StringBuilder sb=new StringBuilder();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("YOUR BILL");
        alert.setResizable(false);
        alert.setWidth(200);
        alert.setHeight(500);
        sb.append("ORDER NO : ");
        sb.append(orderno);
        sb.append("\n");
        sb.append("Total amount : ");
        sb.append(amount);
        sb.append("\n");
        sb.append("Quantity :  ");
        sb.append(tableview2.getItems().size());
        sb.append("\n");
        alert.setContentText(sb.toString());
        int i=0;
        while (i <tableview2.getItems().size()){
            sb.append(tableview2.getItems().get(i).getProductname());
            sb.append(" : ");
            sb.append("Rs. ");
            sb.append(tableview2.getItems().get(i).getProductprice());
            sb.append("\n");
            tableview2.getItems().remove(0);
        }
        visibility("false");
        paid=amount;
        amount=0;
        alert.setContentText(sb.toString());
        ButtonType printButton = new ButtonType("PRINT");
        ButtonType noButton = new ButtonType("CANCEL");
        alert.getButtonTypes().setAll(printButton, noButton);
        Optional<ButtonType> result1 = alert.showAndWait();
        if(result1.get() == printButton)
        {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Purchase Details");
            dialog.setHeaderText("Customer Information");
            dialog.setContentText("Customer name");
            Optional<String> result = dialog.showAndWait();
            dialog.setContentText("Customer Phone(7 digits)");
            Optional<String> result5=dialog.showAndWait();
            dialog.setContentText("CASH/CREDIT CARD/DEBIT CARD/ONLINE BANKING ");
            Optional<String> re=dialog.showAndWait();
            dialog.setContentText("RECEIVER'S NAME(in lower case)(.emp@supermart.com will be added)");
            Optional<String> result3=dialog.showAndWait();
            if (result.isPresent()&&result3.isPresent()&&result5.isPresent()&&re.isPresent()) {
                try{
                    String name=result.get();int phone=Integer.parseInt(result5.get());
                    String mode=re.get(); String receiver=result3.get()+".emp@supermart.com";
                    dbconnection.updatepctable(sb.toString(),paid,phone,name,mode,receiver);
                }catch (Exception e1){
                    e1.printStackTrace();
                    emptyfield();
                }
            }
            else {
                emptyfield();
            }
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("TAKE THE PAYMENT");
            alert.setResizable(false);
            alert1.setHeaderText("NOTICE");
            alert1.setContentText("THIS BILL BE PRINTED AND GIVEN TO THE CUSTOMER");
            alert1.showAndWait();
            alert.close();
        }
        else if(result1.get() == noButton)
        {
            orderno--;
            alert.close();
        }
    }
    // ALERT WHEN EMPTY FIELD IS DETECTED
    @FXML
    private void emptyfield(){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Input field error.");
        alert.setContentText("Looks like one of field was missing or invalid entry is detected, please retry. Exiting the application.");
        alert.showAndWait();
        System.exit(0);
    }
    //TO LOAD CHOCOLATE CATEGORY PRODUCTS
    @FXML
    private void categorychocolate(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getchocolateIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD BABY CARE CATEGORY PRODUCTS
    @FXML
    private void categorybabycare(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getbabycareIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD SNACKS CATEGORY PRODUCTS
    @FXML
    private void categorysnacks(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getsnacksIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD NAMKEEN CATEGORY PRODUCTS
    @FXML
    private void categorynamkeen(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getnamkeenIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD ELECTRONICS CATEGORY PRODUCTS
    @FXML
    private void categoryelectronics(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getelectronicsIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD DAIRY CATEGORY PRODUCTS
    @FXML
    private void categorydairy(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getdairyIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD PERSONAL CARE CATEGORY PRODUCTS
    @FXML
    private void categorypersonalcare(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getpersonalcareIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD SPICES CATEGORY PRODUCTS
    @FXML
    private void categoryspices(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getspicesIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD TOAST CATEGORY PRODUCTS
    @FXML
    private void categorytoast(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new gettoastIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    //TO LOAD VEGETABLES CATEGORY PRODUCTS
    @FXML
    private void categoryvegetables(ActionEvent e) {
        Task<ObservableList<productDetails>> task = new getvegetablesIDNAME();
        tableview.itemsProperty().bind(task.valueProperty());
        tableview.setEditable(false);
        new Thread(task).start();
    }
    // //TO LOAD NONE CATEGORY PRODUCTS
    @FXML
    private void categorynone(ActionEvent e) {
        getall();
    }
    // FOR LOG OUT CONFIRMATION
    @FXML
    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRM");
        alert.setHeaderText("ARE YOU SURE. YOU WANT TO SIGN OUT ?");
        alert.setContentText("PLEASE CHOOSE AN OPTION.");
        alert.setWidth(120);
        alert.setHeight(100);
        alert.setResizable(false);
        ButtonType yesButton = new ButtonType("YES");
        ButtonType noButton = new ButtonType("CANCEL");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == yesButton)
        {
            Parent root = null;
            try {
                root= FXMLLoader.load(getClass().getResource("employeelogin.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mainpane.getChildren().setAll(root);
        }
        else if(result.get() == noButton)
        {
            alert.close();
        }
    }
}
// RETURNS AN OBSERVABLE ARRAY LIST OF PRODUCT ID AND NAME
class getAllIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load());
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF CHOCOLATE CATEGORY PRODUCT
class getchocolateIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Chocolate"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF NAMKEEN CATEGORY PRODUCTS
class getnamkeenIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Namkeen"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF ELECTRONICS CATEGORY PRODUCTS
class getelectronicsIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Electronics"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF DAIRY CATEGORY PRODUCTS
class getdairyIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Dairy"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF SPICES CATEGORY PRODUCTS
class getspicesIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Spices"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF TOAST CATEGORY PRODUCTS
class gettoastIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Toast"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF SNACKS CATEGORY PRODUCTS
class getsnacksIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Snacks"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF VEGETABLES CATEGORY PRODUCTS
class getvegetablesIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Vegetables"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF PERSONAL CARE CATEGORY PRODUCTS
class getpersonalcareIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Personal care"));
    }
}
//RETURNS AN OBSERVABLE ARRAY LIST OF BABY CARE CATEGORY PRODUCTS
class getbabycareIDNAME extends Task {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.load_by_category("Baby care"));
    }
}

