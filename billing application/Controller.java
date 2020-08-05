package billingapp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    dbconnection db=new dbconnection();
    @FXML
    public Button addbuttonforproduct;
    @FXML
    private TextField textfield2,textfield1,textfield3,textfield4,textfield5,textfield6,textfield7;
    @FXML
    private PasswordField passwordfield1;
    @FXML
    private PasswordField USERPASSWORD,ADMINPASSWORD;
    @FXML
    private TextField USERID,ADMINID;
    @FXML
    private AnchorPane mainpane;
    @FXML
    private Button showtablebuttonid,addadminbutton,addbuttonforadmin, logoutbutton,backbuttonfromchoiceid,empbuttonid,adminbuttonid;
    @FXML
    private Button   addempployeebutton,aboutbuttonid,cancelbuttonid,loginbuttonid,addproductbutton,addbuttonforemp;
    @FXML
    public Label label1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            if(db.isDbconnected()){
                System.out.println("Connection");
            }
            else
                System.out.println("Not connected");
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    // WHEN LOGIN BUTTON IS CLICKED ON FIRST INTERFACE
    @FXML
    public void onloginbuttonclick(ActionEvent event) {
        label1.setText("LOGIN AS ?");
        adminbuttonid.setVisible(true);
        empbuttonid.setVisible(true);
        backbuttonfromchoiceid.setVisible(true);
        loginbuttonid.setVisible(false);
        cancelbuttonid.setVisible(false);
        aboutbuttonid.setVisible(false);
    }
    //EXITING APPLICATION ON CANCEL BUTTON CLICK
    @FXML
    public void oncancelbuttonclick(ActionEvent event) {
        System.exit(0);
    }
    //LOADING DETAILS OF PROJECT WHEN HELP BUTTON IS CLICKED
    @FXML
    public void onaboutbuttonclick(ActionEvent event) {
        loadscene("about");
    }
    // THIS FUNCTION WILL HELP US TO LOAD DIFFERENT XML FILES
    @FXML
    private void loadscene(String UI) {
        Parent root = null;
        try {
            root= FXMLLoader.load(getClass().getResource(UI+".fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        mainpane.getChildren().setAll(root);
    }
    //FOR LOADING ADMIN LOGIN PAGE WHEN USER CLICK LOGIN AS ADMIN
    @FXML
    public void onadminbuttonclick(ActionEvent event) {
        loadscene("adminlogin");
    }
    //FOR LOADING ADMIN LOGIN PAGE WHEN USER CLICK LOGIN AS EMPLOYEE
    @FXML
    public void onemployeebuttonclick(ActionEvent event) {
        loadscene("employeelogin");
    }
    //WHEN LOGIN BUTTON ON FIRST INTERFACE IS CLICKED
    @FXML
    public void onbackbuttonfromchoice(ActionEvent event) {
        label1.setText("");
        adminbuttonid.setVisible(false);
        empbuttonid.setVisible(false);
        backbuttonfromchoiceid.setVisible(false);
        loginbuttonid.setVisible(true);
        cancelbuttonid.setVisible(true);
        aboutbuttonid.setVisible(true);
    }
    //TO BACK TO FIRST PAGE FROM SECOND INTERFACE
    @FXML
    public void backtoFirstpage(ActionEvent event) {
        loadscene("sample");
    }
    //ON CLICKING FORGGOT PASSWORD IN ADMIN LOGIN INTERFACE
    @FXML
    public void forgotpasswordbuttonclick(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ALERT");
        alert.setWidth(500.0);
        alert.setHeight(300.0);
        alert.setHeaderText("Forgot Password ?");
        alert.setContentText("Only existing admin has the right to add new admin or users. Login using admin and add a user or change user/email/password. ");
        alert.setResizable(false); alert.showAndWait();
    }
    // TO LOAD SCREEN ACORDING TO RESULT OF THE LOGIN CREDENTIALS
    @FXML
    private void loginresult(String UI,int n,String ID)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ALERT");
        alert.setWidth(150.0);
        alert.setHeight(170.0);
        if(n==1)
        {
            alert.setHeaderText("MATCH FOUND.");
            alert.setContentText("SUCCESSFULLY LOGGED IN AS"+" "+ID);
        }
        else {
            alert.setHeaderText("DID NOT FOUND A MATCH.");
            alert.setContentText("FAILED TO LOG IN AS"+" "+ID);
        }
        alert.setResizable(false);
        alert.showAndWait();
        loadscene(UI);
    }
    //WHEN NO ENTRY IS DETECTED BY THE INTERFACE ON TEXTFIELDS
    @FXML
    private void emptyfield(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ALERT");
        alert.setWidth(30.0);
        alert.setHeight(50.0);
        alert.setHeaderText("INVALID OPERATION");
        alert.setContentText("NO ENTRY DETECTED. EXITING THE APPLICATION");
        alert.setResizable(false);
        alert.showAndWait();
        System.exit(0);
    }
    // LOGIN CHECK FOR EMPLOYEE
    @FXML
    public void onloginbuttonclickforemp(ActionEvent event) {
        String ID= USERID.getText();
        String pass= USERPASSWORD.getText();
        if(ID.isEmpty()||pass.isEmpty())
            emptyfield();
        try {
            if (db.logincheckforemployee(ID,pass))
                loginresult("employeeinterface",1,ID);
            else
                loginresult("employeelogin",2,ID);
        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    // FOR COMING BACK FROM THIRD INTERFACE
    @FXML
    public void backbuttonfromemployeelogin(ActionEvent event) {
        loadscene("sample");
    }
    //FOR CHECKING LOGIN CREDENTIALS OF ADMIN
    @FXML
    public void onloginbuttonclickforadmin(ActionEvent event) {
        String ID= ADMINID.getText();
        String pass= ADMINPASSWORD.getText();
        if(ID.isBlank()||pass.isBlank())
            emptyfield();
        try {
            if (db.logincheckforadmin(ID,pass))
                loginresult("admininterface",1,ID);
            else
                loginresult("adminlogin",2,ID);
        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    //FOR COMING BACK TO FIRST INTERFACE
    @FXML
    public void backbuttonclickfromadminlogin(ActionEvent event) {
        loadscene("sample");
    }
    //FOR LOGGING OUT OF ADMIN INTERFACE
    @FXML
    public void logoutfromadmininterface(ActionEvent event) {
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
           loadscene("adminlogin");
        }
        else if(result.get() == noButton)
        {
            alert.close();
        }
    }
   // WHEN ADD BUTTON IN ADMIN INTERFACE IS CLICKED
    @FXML
    public void onaddadminbuttonclick(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(false);
        alert.setHeaderText("BE CAREFUL");
        alert.setTitle("FOLLOW");
        alert.setContentText("EMAIL'S FORMAT: name@supermart.com"+"\n"+"PASSWORD : MORE THAN 7 CHARACTERS");
        alert.showAndWait();
        textfield1.promptTextProperty().setValue("NEW ADMIN EMAIL");
        textfield1.setVisible(true);
        passwordfield1.promptTextProperty().setValue("NEW ADMIN PASSWORD");
        passwordfield1.setVisible(true);
        addproductbutton.setDisable(true);
        addempployeebutton.setDisable(true);
        showtablebuttonid.setDisable(true);
        addbuttonforadmin.setVisible(true);
        logoutbutton.setDisable(true);
    }
    //WHEN ADD EMPLOYEE IN ADMIN INTERFACE IS CLICKED
    @FXML
    public void onaddemployeebuttonclick(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(false);
        alert.setHeaderText("BE CAREFUL");
        alert.setTitle("FOLLOW");
        alert.setContentText("EMAIL'S FORMAT: name.emp@supermart.com"+"\n"+"PASSWORD : MORE THAN 7 CHARACTERS");
        alert.showAndWait();
        textfield1.promptTextProperty().setValue("NEW EMPLOYEE EMAIL");
        textfield1.setVisible(true);
        passwordfield1.promptTextProperty().setValue("NEW EMPLOYEE PASSWORD");
        passwordfield1.setVisible(true);
        textfield2.promptTextProperty().setValue("EMPLOYEE FULL NAME");
        textfield2.setVisible(true);
        textfield3.promptTextProperty().setValue("MOBILE NUMBER");
        textfield3.setVisible(true);
        textfield4.promptTextProperty().setValue("CURRENT ADMIN ID");
        textfield4.setVisible(true);
        addadminbutton.setDisable(true);
        addproductbutton.setDisable(true);
        showtablebuttonid.setDisable(true);
        addbuttonforemp.setVisible(true);
        logoutbutton.setDisable(true);
        //some operation
    }
    //WHEN ADD PRODUCT IN ADMIN INTERFACE IS CLICKED
    @FXML
    public void onaddproductbuttonclick(ActionEvent event) {
        textfield1.promptTextProperty().setValue("PRODUCT NAME");textfield1.setVisible(true);
        textfield2.promptTextProperty().setValue("PRICE");textfield2.setVisible(true);
        textfield3.promptTextProperty().setValue("COMPANY ID"); textfield3.setVisible(true);
        textfield4.promptTextProperty().setValue("WEIGHT"); textfield4.setVisible(true);
        textfield5.promptTextProperty().setValue("TYPE");textfield5.setVisible(true);
        textfield6.promptTextProperty().setValue("COMPANY NAME");textfield6.setVisible(true);
        textfield7.promptTextProperty().setValue("CURRENT ADMIN ID");textfield7.setVisible(true);
        addadminbutton.setDisable(true);
        addempployeebutton.setDisable(true);
        showtablebuttonid.setDisable(true);
        addbuttonforproduct.setVisible(true);
        logoutbutton.setDisable(true);
        //some operation
    }
    //WHEN ADD BUTTON FOR ADMIN IS CLICKED
    @FXML
    public void onaddbuttonclickforadmin(ActionEvent event) throws SQLException {
        if(textfield1.getText().isEmpty()||passwordfield1.getText().isEmpty())
            emptyfield();
        else
            if(textfield1.getText()!=null&&passwordfield1.getText()!=null)
            {
                dbconnection.addadmin(textfield1.getText(), passwordfield1.getText());
            }
        textfield1.setVisible(false);
        passwordfield1.setVisible(false);
        addadminbutton.setDisable(false);
        addproductbutton.setDisable(false);
        showtablebuttonid.setDisable(false);
        addempployeebutton.setDisable(false);
        addbuttonforadmin.setVisible(false);
        logoutbutton.setDisable(false);
    }
    //FOR LOADING SHOW TABLE PAGE IN ADMIN INTERFACE
    @FXML
    public void onshowtablebuttonclick(ActionEvent event) {
        loadscene("showtable");
    }
    //WHEN ADD BUTTON FOR EMPLOYEE IS CLICKED
    @FXML
    public void onaddbuttonclickforemp(ActionEvent event) throws SQLException {
        if (textfield1.getText().isEmpty()||passwordfield1.getText().isEmpty()||textfield2.getText().isEmpty()||
        textfield3.getText().isEmpty()||textfield4.getText().isEmpty())
            emptyfield();
        else
        {
            dbconnection.addemployee(textfield1.getText(),passwordfield1.getText(),textfield2.getText(),
                    Integer.parseInt(textfield3.getText()),textfield4.getText());
        }
        textfield1.setVisible(false); passwordfield1.setVisible(false);
        textfield2.setVisible(false); textfield3.setVisible(false);textfield4.setVisible(false);
        addadminbutton.setDisable(false);
        addproductbutton.setDisable(false);
        showtablebuttonid.setDisable(false);
        addbuttonforemp.setVisible(false);
        logoutbutton.setDisable(false);
    }
    //WHEN ADD BUTTON FOR PRODUCT IS CLICKED
    @FXML
    public void onaddbuttonclickforproduct(ActionEvent event) throws Exception {
        if (textfield1.getText().isEmpty()||textfield5.getText().isEmpty()||textfield2.getText().isEmpty()||
                textfield3.getText().isEmpty()||textfield4.getText().isEmpty()||textfield6.getText().isEmpty()
        ||textfield7.getText().isEmpty())
            emptyfield();
        else
        {
            dbconnection.addproduct(textfield1.getText(),Double.parseDouble(textfield2.getText()),
                    Integer.parseInt(textfield3.getText()), textfield4.getText(),textfield5.getText(),
                    textfield6.getText(),textfield7.getText());
        }
        textfield1.setVisible(false);textfield2.setVisible(false);
        textfield3.setVisible(false);textfield4.setVisible(false);
        textfield5.setVisible(false);textfield6.setVisible(false);textfield7.setVisible(false);
        addadminbutton.setDisable(false);
        addempployeebutton.setDisable(false);
        showtablebuttonid.setDisable(false);
        addbuttonforproduct.setVisible(false);
        logoutbutton.setDisable(false);
    }
}
