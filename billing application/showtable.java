package billingapp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

///////
//////     THIS IS PART OF ADMIN INTERFACE FUNCTIONALITY
///////
public class showtable {
    @FXML
    public TableView tableviewforshowingtables;
    @FXML
    public TableColumn first, second, third, fourth, fifth, sixth;
    @FXML
    private AnchorPane mainpane;

    //TO GO FROM SHOW TABLE INTERFACE TO ADMIN MAIN INTERFACE
    @FXML
    public void backfromshowtables(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("admininterface.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        mainpane.getChildren().setAll(root);
    }

    //FOR REMOVING ALL COLUMN VALUE WHEN ANOTHER TABLE IS CALLED
    @FXML
    private void removelasttable() {
        first.getColumns().removeAll();
        second.getColumns().removeAll();
        third.getColumns().removeAll();
        fourth.getColumns().removeAll();
        fifth.getColumns().removeAll();
        sixth.getColumns().removeAll();
    }

    //FOR FUNCTION IS TO INITIALIZE THE HEADER OF THE TABLE VIEW ACCORDING TO ADMIN TABLE VALUES
    @FXML
    public void showadmintable(ActionEvent event) {
        removelasttable();
        first.setText("EMAIL ID");
        first.setCellValueFactory(new PropertyValueFactory<adminDetails, String>("email"));
        second.setText("PASSWORD");
        third.setText("C3");
        fourth.setText("C4");
        fifth.setText("C5");
        sixth.setText("C6");
        second.setCellValueFactory(new PropertyValueFactory<adminDetails, String>("password"));
        Task<ObservableList<adminDetails>> task = new getadmintable();
        tableviewforshowingtables.itemsProperty().bind(task.valueProperty());
        tableviewforshowingtables.setEditable(false);
        new Thread(task).start();
    }

    //FOR FUNCTION IS TO INITIALIZE THE HEADER OF THE TABLE VIEW ACCORDING TO EMPLOYEE TABLE VALUES
    @FXML
    public void showempployeetable(ActionEvent event) {
        removelasttable();
        first.setText("EMAIL ID");
        first.setCellValueFactory(new PropertyValueFactory<empDetails, String>("email"));
        second.setText("PASSWORD");
        second.setCellValueFactory(new PropertyValueFactory<empDetails, String>("password"));
        third.setText("NAME");
        third.setCellValueFactory(new PropertyValueFactory<empDetails, String>("name"));
        fourth.setText("PHONE");
        fourth.setCellValueFactory(new PropertyValueFactory<empDetails, Integer>("phone"));
        fifth.setText("ADDED BY");
        sixth.setText("C6");
        fifth.setCellValueFactory(new PropertyValueFactory<empDetails, String>("addedbyadmin"));
        Task<ObservableList<empDetails>> task = new getemptable();
        tableviewforshowingtables.itemsProperty().bind(task.valueProperty());
        tableviewforshowingtables.setEditable(false);
        new Thread(task).start();
    }

    //FOR FUNCTION IS TO INITIALIZE THE HEADER OF THE TABLE VIEW ACCORDING TO PRODUCT TABLE VALUES
    @FXML
    public void showproducttable(ActionEvent event) {
        removelasttable();
        first.setText("ID");
        first.setCellValueFactory(new PropertyValueFactory<productDetails, Integer>("productID"));
        second.setText("NAME");
        second.setCellValueFactory(new PropertyValueFactory<productDetails, String>("productname"));
        third.setText("PRICE");
        third.setCellValueFactory(new PropertyValueFactory<productDetails, Double>("productprice"));
        fourth.setText("COMPANY ID");
        fourth.setCellValueFactory(new PropertyValueFactory<productDetails, Integer>("companyID"));
        fifth.setText("WEIGHT");
        fifth.setCellValueFactory(new PropertyValueFactory<productDetails, String>("productweight"));
        sixth.setText("CATEGORY");
        sixth.setCellValueFactory(new PropertyValueFactory<productDetails, String>("typeofproduct"));
        Task<ObservableList<productDetails>> task = new getproducttable();
        tableviewforshowingtables.itemsProperty().bind(task.valueProperty());
        tableviewforshowingtables.setEditable(false);
        new Thread(task).start();
    }

    //FOR FUNCTION IS TO INITIALIZE THE HEADER OF THE TABLE VIEW ACCORDING TO COMPANY TABLE VALUES
    @FXML
    public void showcompanytable(ActionEvent event) {
        removelasttable();
        first.setText("ID");
        first.setCellValueFactory(new PropertyValueFactory<companyDetails, Integer>("companyid"));
        second.setText("NAME");
        second.setCellValueFactory(new PropertyValueFactory<companyDetails, String>("companyname"));
        third.setText("NUMBER OF PRODUCTS");
        third.setCellValueFactory(new PropertyValueFactory<companyDetails, Integer>("companynumberofproduct"));
        fourth.setText("ADDED BY");
        fifth.setText("C5");
        sixth.setText("C6");
        fourth.setCellValueFactory(new PropertyValueFactory<companyDetails, String>("companyadmin"));
        Task<ObservableList<companyDetails>> task = new getcompanytable();
        tableviewforshowingtables.itemsProperty().bind(task.valueProperty());
        tableviewforshowingtables.setEditable(false);
        new Thread(task).start();
    }
    //FOR FUNCTION IS TO INITIALIZE THE HEADER OF THE TABLE VIEW ACCORDING TO CUSTOMER TABLE VALUES
    @FXML
    public void showcustomertable(ActionEvent event) {
        removelasttable();
        first.setText("PHONE NO.");
        first.setCellValueFactory(new PropertyValueFactory<customerDetails, Integer>("customerphone"));
        second.setText("NAME");
        second.setCellValueFactory(new PropertyValueFactory<customerDetails, String>("customername"));
        third.setText("AMOUNT(in Rs)");
        third.setCellValueFactory(new PropertyValueFactory<customerDetails, Double>("amountpaid"));
        fourth.setText("PAYMENT MODE");
        fourth.setCellValueFactory(new PropertyValueFactory<customerDetails, String>("paymentmode"));
        fifth.setText("RECEIVER'S EMAIL");
        sixth.setText("C6");
        fifth.setCellValueFactory(new PropertyValueFactory<customerDetails, String>("empemail"));
        Task<ObservableList<customerDetails>> task = new getcustomertable();
        tableviewforshowingtables.itemsProperty().bind(task.valueProperty());
        tableviewforshowingtables.setEditable(false);
        new Thread(task).start();
    }

    //FOR FUNCTION IS TO INITIALIZE THE HEADER OF THE TABLE VIEW ACCORDING TO PURCHASE TABLE VALUES
    @FXML
    public void showpurchasetable(ActionEvent event) {
        removelasttable();
        first.setText("PHONE");
        first.setCellValueFactory(new PropertyValueFactory<purchaseDetails, Integer>("customerphone"));
        second.setText("PRODUCTS NAME");
        second.setCellValueFactory(new PropertyValueFactory<purchaseDetails, String>("productnames"));
        third.setText("AMOUNT");
        fourth.setText("C4");
        fifth.setText("C5");
        sixth.setText("C6");
        third.setCellValueFactory(new PropertyValueFactory<purchaseDetails, Double>("totalprice"));
        Task<ObservableList<purchaseDetails>> task = new getpurchasetable();
        tableviewforshowingtables.itemsProperty().bind(task.valueProperty());
        tableviewforshowingtables.setEditable(false);
        new Thread(task).start();
    }

    // FUNCTION FOR REMOVING ADMIN ROW ON MOUSE CLICK
    @FXML
    public void onmouseclickremoverow(MouseEvent mouseEvent) {
        ObservableList<adminDetails> selectedRows = tableviewforshowingtables.getSelectionModel().getSelectedItems();
        ArrayList<adminDetails> rows = new ArrayList<>(selectedRows);
        try {
            dbconnection.removeadminrow(rows.get(0).getEmail());
            rows.forEach(row -> tableviewforshowingtables.getItems().remove(row));
        } catch (Exception e) {
            try2();
        }
    }
    // FUNCTION FOR REMOVING EMPLOYEE ROW ON MOUSE CLICK
    @FXML
    private void try2() {
        ObservableList<empDetails> selectedRows = tableviewforshowingtables.getSelectionModel().getSelectedItems();
        ArrayList<empDetails> rows = new ArrayList<>(selectedRows);
        try {
            dbconnection.removeemployeerow(rows.get(0).getEmail());
            rows.forEach(row->tableviewforshowingtables.getItems().remove(row));
        } catch (Exception e) {
            try3();
        }
    }
    // FUNCTION FOR REMOVING PRODUCT ROW ON MOUSE CLICK
    @FXML
    private void try3() {
        ObservableList<productDetails> selectedRows = tableviewforshowingtables.getSelectionModel().getSelectedItems();
        ArrayList<productDetails> rows = new ArrayList<>(selectedRows);
        try {
            dbconnection.removeproductrow(rows.get(0).getProductID());
            rows.forEach(row->tableviewforshowingtables.getItems().remove(row));
        } catch (Exception e) {
            try4();
        }
    }
    // FUNCTION FOR REMOVING COMPANY ROW ON MOUSE CLICK
    @FXML
    private void try4() {
        ObservableList<companyDetails> selectedRows = tableviewforshowingtables.getSelectionModel().getSelectedItems();
        ArrayList<companyDetails> rows = new ArrayList<>(selectedRows);
        try {
            dbconnection.removecompanyrow(rows.get(0).getCompanyid());
            rows.forEach(row->tableviewforshowingtables.getItems().remove(row));
        } catch (Exception e) {
            try5();
        }
    }
    // FUNCTION FOR REMOVING CUSTOMER ROW ON MOUSE CLICK
    @FXML
    private void try5() {
        ObservableList<customerDetails> selectedRows = tableviewforshowingtables.getSelectionModel().getSelectedItems();
        ArrayList<customerDetails> rows = new ArrayList<>(selectedRows);
        try {
            dbconnection.removecustomerrow(rows.get(0).getCustomerphone());
            rows.forEach(row->tableviewforshowingtables.getItems().remove(row));
        } catch (Exception e) {
            try6();
        }
    }
    // FUNCTION FOR REMOVING PURCHASE ROW ON MOUSE CLICK
    @FXML
    private void try6() {
        ObservableList<purchaseDetails> selectedRows = tableviewforshowingtables.getSelectionModel().getSelectedItems();
        ArrayList<purchaseDetails> rows = new ArrayList<>(selectedRows);
        try {
            dbconnection.removepurchaserow(rows.get(0).getCustomerphone());
            System.out.println(rows.get(0).getProductnames());
            System.out.println(rows.get(0).getTotalprice());
            rows.forEach(row->tableviewforshowingtables.getItems().remove(row));
        } catch (Exception e) {
            System.out.println("TRY6 EXECUTED");
        }
    }
}
//FOR RETURNING OBSERVABLE ARRAY LIST OF ADMIN TABLE
class getadmintable extends Task<ObservableList<adminDetails>> {
    @Override
    public ObservableList<adminDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.loadtableadmin());
    }
}
//FOR RETURNING OBSERVABLE ARRAY LIST OF EMPLOYEE TABLE
class getemptable extends Task<ObservableList<empDetails>> {
    @Override
    public ObservableList<empDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.loadtableemp());
    }
}
//FOR RETURNING OBSERVABLE ARRAY LIST OF PRODUCT TABLE
class getproducttable extends Task<ObservableList<productDetails>> {
    @Override
    public ObservableList<productDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.loadtableproduct());
    }
}
//FOR RETURNING OBSERVABLE ARRAY LIST OF COMPANY TABLE
class getcompanytable extends Task<ObservableList<companyDetails>> {
    @Override
    public ObservableList<companyDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.loadtablecompany());
    }
}
//FOR RETURNING OBSERVABLE ARRAY LIST OF CUSTOMER TABLE
class getcustomertable extends Task<ObservableList<customerDetails>> {
    @Override
    public ObservableList<customerDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.loadtablecustomer());
    }
}
// RETURNING OBSERVABLE ARRAY LIST OF PURCHASE TABLE
class getpurchasetable extends Task<ObservableList<purchaseDetails>> {
    @Override
    public ObservableList<purchaseDetails> call() throws SQLException {
        return FXCollections.observableArrayList
                (dbconnection.loadtablepurchase());
    }
}