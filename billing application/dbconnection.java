package billingapp;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbconnection {
    // ALL FINAL VALUES USED DURING CONNECTION WITH DATABASE
    private final static String DATABASE_NAME = "sm.db";
    private final static String url = "jdbc:sqlite:" + DATABASE_NAME;
    private final static String ADMIN_TABLE = "AdminDetails";
    private final static String ADMIN_USERNAME="Admin_Username";
    private final static String ADMIN_PASSWORD="Admin_Password";

    private final static String EMPLOYEE_TABLE="EmpDetails";
    private final static String EMPLOYEE_USERNAME="Emp_Username";
    private final static String EMPLOYEE_PASSWORD="Emp_Password";

    private final static String PRODUCT_TABLE="ProductDetails";
    private final static String PRODUCT_ID="Product_ID";
    private final static String PRODUCT_TYPE="Product_Type";

    private final static String COMPANY_TABLE="CompanyDetails";
    private final static String COMPANY_ID="Company_ID";
    private final static String COMPANY_NO_OF_PRODUCTS="CNOP";

    private final static String PURCHASE_TABLE="PurchaseDetails";
    private final static String PURCHASE_PHONE="Customer_Phone";

    private final static String CUSTOMER_TABLE="CustomerDetails";
    private final static String CUSTOMER_PHONE="Customer_Phone";

    private Connection conn ;
    private PreparedStatement pre;
    private ResultSet rs;

    private static Connection con;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;
    public dbconnection()
    {
        conn=Loginmodel.Connector();
        if (conn==null)
            System.exit(0);
    }
    //ONE MORE CONNECTION FUNCTION FOR STATIC FUNCTIONS
    public static Connection connectDb(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn= DriverManager.getConnection(url);
            System.out.println("Local connection");
            return conn;
        } catch (Exception e) {
            return null;
        }
    }
    //REMOVE ADMIN ROW ON CLICK FROM ADMIN INTERFACE
    public static void removeadminrow(String email) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        StringBuilder sb=new StringBuilder();
        sb.append("DELETE FROM"); sb.append(" "); sb.append(ADMIN_TABLE);
        sb.append(" "); sb.append("WHERE");sb.append(" ");
        sb.append(ADMIN_USERNAME);sb.append("=");
        sb.append("'");sb.append(email);sb.append("'");
        try {
            preparedStatement= con.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeall();
        }
    }
    //REMOVE EMPLOYEE ROW ON CLICK FROM ADMIN INTERFACE
    public static void removeemployeerow(String email) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        StringBuilder sb=new StringBuilder();
        sb.append("DELETE FROM"); sb.append(" "); sb.append(EMPLOYEE_TABLE);
        sb.append(" "); sb.append("WHERE");sb.append(" ");
        sb.append(EMPLOYEE_USERNAME);sb.append("=");
        sb.append("'");sb.append(email);sb.append("'");
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeall();
        }
    }
    //ADD ADMIN ROW IN ADMIN INTERFACE. ONLY 3 ADMINS CAN BE ADDED ONE ALREADY EXIST
    public static void addadmin(String id, String pass) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        String sb1="INSERT INTO"+" "+ ADMIN_TABLE+" "+"VALUES('"+id+"','"+pass+"')";
        try{
            preparedStatement=con.prepareStatement(sb1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
          closeall();
        }
    }
    //ADD EMPLOYEE ROW IN TABLE FROM ADMIN INTERFACE
    public static void addemployee(String id, String pass,String name,int phone,String adminwhoadded) throws SQLException {
        preparedStatement=null;
        con=connectDb();
        String sb1="INSERT INTO"+" "+ EMPLOYEE_TABLE+" "+"VALUES('"+id+"','"+pass+"','"+name+"','"+phone+"','"+adminwhoadded+"')";
        System.out.println(sb1);
        try{
            preparedStatement=con.prepareStatement(sb1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        closeall();
        }
    }
    // UPDATING PURCHASE AND CUSTOMER TABLE TABLE ON PURCHASE
    public static void updatepctable(String fullreceipt, double amountpaid, int cusphone,
                                     String cusname, String paymentmode, String employeeemail) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        String purchasequery="INSERT INTO"+" "+PURCHASE_TABLE+" "+"VALUES('"+cusphone+"','"+fullreceipt+"','"+amountpaid+"')";
        try {
            preparedStatement=con.prepareStatement(purchasequery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeall();
        }
        System.out.println("comes outside try catch finally");
        con=connectDb();
        preparedStatement=null;
        String customerquery="INSERT INTO"+" "+CUSTOMER_TABLE+" "+"VALUES('"+cusphone+"','"+cusname+"','"+amountpaid+"','"+paymentmode+"','"+employeeemail+"')";
        try {
            preparedStatement=con.prepareStatement(customerquery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
           closeall();
        }
    }
    //UPDATING PRODUCT AND COMPANY TABLE ON ADDING PRODUCT FROM ADMIN INTERFACE PART 1
    public static void addproduct(String name, double price,int cid,String weight,
                                  String type,String cname,String adminwhoadded) throws Exception {
        con=connectDb();
        preparedStatement=null;
        resultSet=null;
        StringBuilder sb=new  StringBuilder();
        int ID=0;
        sb.append("SELECT count( "); sb.append(PRODUCT_ID);sb.append(")  FROM");
        sb.append(" "); sb.append(PRODUCT_TABLE);
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            ID=Integer.parseInt(String.valueOf(resultSet.getInt(1)));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
           closeall();
            addproduct1(ID,name,price,cid,weight,type,cname,adminwhoadded);
        }
        }
    //UPDATING PRODUCT AND COMPANY TABLE ON ADDING PRODUCT FROM ADMIN INTERFACE PART 2
    private static void addproduct1(int id, String name, double price, int cid, String weight, String type,
                                    String cname, String adminwhoadded)throws Exception {
        preparedStatement=null;
        resultSet=null;
        id=id+1;
        con=connectDb();
        StringBuilder sb=new StringBuilder();
        sb.append("INSERT INTO");sb.append(" ");sb.append(PRODUCT_TABLE);sb.append(" ");
        sb.append("VALUES('");sb.append(id);sb.append("','");sb.append(name);sb.append("','");sb.append(price);
        sb.append("','");sb.append(cid);sb.append("','");sb.append(weight);sb.append("','");sb.append(type);
        sb.append("')");
        try{
            preparedStatement=con.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeall();
            addproduct2(cid,cname,adminwhoadded);
        }

    }
    //UPDATING PRODUCT AND COMPANY TABLE ON ADDING PRODUCT FROM ADMIN INTERFACE PART 3
    private static void addproduct2(int cid, String cname, String adminwhoadded) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        resultSet=null;
        StringBuilder sb1=new StringBuilder();
        sb1.append("SELECT");sb1.append(" ");sb1.append(COMPANY_NO_OF_PRODUCTS);sb1.append(" ");sb1.append("FROM");
        sb1.append(" ");sb1.append(COMPANY_TABLE);sb1.append(" ");sb1.append("WHERE");sb1.append(" ");
        sb1.append(COMPANY_ID);sb1.append("=");sb1.append("'");sb1.append(cid); sb1.append("'");
        int noofproducts=0;
        try{
        preparedStatement=con.prepareStatement(sb1.toString());
        resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            noofproducts=Integer.parseInt(String.valueOf(resultSet.getInt(1)));
        }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }finally {
            closeall();  addproduct3(cid,cname,noofproducts,adminwhoadded);
        }
    }
    //UPDATING PRODUCT AND COMPANY TABLE ON ADDING PRODUCT FROM ADMIN INTERFACE PART 4
    private static void addproduct3(int cid, String cname, int noofproducts, String adminwhoadded) throws SQLException {
        con=connectDb();preparedStatement=null;resultSet=null;
        if(noofproducts>0)
        {
            noofproducts=noofproducts+1;
            String query="UPDATE "+COMPANY_TABLE+" "+"SET"+" "+COMPANY_NO_OF_PRODUCTS+"="+noofproducts+" "+"WHERE"+" "+COMPANY_ID+"="+cid;
           try {
               preparedStatement=con.prepareStatement(query);
               preparedStatement.executeUpdate();
           }catch (SQLException e)
           {
               e.printStackTrace();
           }finally {
             closeall();
           }
        }
        else
        {
            noofproducts=1;
            StringBuilder sb1=new StringBuilder();
            sb1.append("INSERT INTO");sb1.append(" ");sb1.append(COMPANY_TABLE);
            sb1.append(" ");sb1.append("VALUES('");sb1.append(cid);sb1.append("','");sb1.append(cname);sb1.append("','");
            sb1.append(noofproducts);sb1.append("','");sb1.append(adminwhoadded);sb1.append("')");
            System.out.println(sb1.toString());
            try {
                preparedStatement = con.prepareStatement(sb1.toString());
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }finally {
               closeall();
            }

        }
    }

    //FOR REMOVING PRODUCT ROW FROM ADMIN INTERFACE
    public static void removeproductrow(int ID) throws SQLException {
       con = connectDb();
       preparedStatement=null;
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM");
        sb.append(" ");
        sb.append(PRODUCT_TABLE);
        sb.append(" ");
        sb.append("WHERE");
        sb.append(" ");
        sb.append(PRODUCT_ID);
        sb.append("=");
        sb.append("'");
        sb.append(ID);
        sb.append("'");
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
          closeall();
            companytableupdate(ID);
        }
    }
    //FUNCTION TO CLOSE ALL STATEMENT, CONNECTION AND RESULT SET
    public static void closeall() throws SQLException {
        if(resultSet!=null)
            resultSet.close();
        if (preparedStatement!=null)
            preparedStatement.close();
        if (con!=null)
            con.close();
    }
    //UPDATING COMPANY TABLE WHEN PRODUCT ROW IS DELETED PART 1
    //IN PART 1 I HAVE SELECTED THE COMPANY ID USING PRODUCT TABLE
    public static void companytableupdate(int productid) throws SQLException {
        con=connectDb();
        resultSet=null;
        preparedStatement=null;
        int cid=0;
        StringBuilder sb=new StringBuilder();sb.append("SELECT");sb.append(" ");sb.append(COMPANY_ID);sb.append(" ");
        sb.append("FROM");sb.append(" ");sb.append(PRODUCT_TABLE);sb.append(" ");sb.append("WHERE");sb.append(" ");
        sb.append(PRODUCT_ID);sb.append("=");sb.append("'");sb.append(productid);sb.append("'");
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                cid=Integer.parseInt(String.valueOf(resultSet.getInt(1)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeall();
            companytableupdate1(cid);
        }
    }
    //UPDATING COMPANY TABLE WHEN PRODUCT ROW IS DELETED PART 2
    //IN PART 1 I HAVE SELECTED THE NO OF PRODUCTS OF A COMPANY USING COMPANY ID I GOT IN PART 1
    public static void companytableupdate1(int cid) throws SQLException {
        int noofproduct=0;
        con=connectDb();
        preparedStatement=null;
        resultSet=null;
        String str2="SELECT"+" "+COMPANY_NO_OF_PRODUCTS+" "+"FROM"+" "+COMPANY_TABLE+" "+"WHERE" + " "+COMPANY_ID+"="+"'"+cid+"'";
        try {
            preparedStatement=con.prepareStatement(str2);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                noofproduct=Integer.parseInt(String.valueOf(resultSet.getInt(1)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
           closeall();
            companytableupdate2(noofproduct,cid);
        }
    }
    //UPDATING COMPANY TABLE WHEN PRODUCT ROW IS DELETED PART 3
    //IF NO OF PRODUCT IS 1 THEN THE COMPANY ROW DELETED OTHERWISE THE NO OF PRODUCTS IS DECREASED BY 1
    public static void companytableupdate2(int noofproduct,int cid) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        if(noofproduct == 1){
            String str="DELETE FROM"+" "+COMPANY_TABLE+" "+"WHERE"+" "+noofproduct+"="+"'1'";
            try{
                preparedStatement=con.prepareStatement(str);
                preparedStatement.executeUpdate();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }finally {
                if(preparedStatement!=null)
                    preparedStatement.close();
                if(con!=null)
                    con.close();
            }
        }else{
            System.out.println(noofproduct);
            noofproduct=noofproduct-1;
            String query="UPDATE "+COMPANY_TABLE+" "+"SET"+" "+COMPANY_NO_OF_PRODUCTS+"="+noofproduct+" "+"WHERE"+" "+COMPANY_ID+"="+cid;
            try{
                preparedStatement=con.prepareStatement(query);
                preparedStatement.executeUpdate();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }finally {
               closeall();
            }
        }
    }
    //ALERT BOX WHEN SOMEONE TRIES TO DELETE A COMPANY ROW. COMPANY ROW WILL AUTOMATICALLY
    // GET DELETED WHEN NO OF PRODUCTS OF THAT COMPANY BECOME 0.
    public static void removecompanyrow(int ID) throws SQLException{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("CANNOT DELETE COMPANY ROW WITH ID:"+ID);
                alert.setContentText("THE COMPANY ROW CAN'T BE DELETED BECAUSE \n PRODUCTS OF THIS COMPANY STILL EXIST." +
                        "COMPANY WILL BE DELETED AUTOMATICALLY ONE THE\n NO OF PRODUCT OF THAT COMPANY BECOMES 0.");
                alert.setResizable(false);
                alert.showAndWait();
    }
    //FOR REMOVING CUSTOMER ROW FROM ADMIN INTERFACE
    public static void removecustomerrow(int phone) throws SQLException{
        con=connectDb();
        preparedStatement=null;
        StringBuilder sb=new StringBuilder();
        sb.append("DELETE FROM"); sb.append(" "); sb.append(CUSTOMER_TABLE);
        sb.append(" "); sb.append("WHERE");sb.append(" ");
        sb.append(CUSTOMER_PHONE);sb.append("=");
        sb.append("'");sb.append(phone);sb.append("'");
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeall();
        }
    }
    //FOR REMOVING PURCHASE TABLE FROM ADMIN INTERFACE
    public static void removepurchaserow(int phone) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        StringBuilder sb=new StringBuilder();
        sb.append("DELETE FROM"); sb.append(" "); sb.append(PURCHASE_TABLE);
        sb.append(" "); sb.append("WHERE");sb.append(" ");
        sb.append(PURCHASE_PHONE);sb.append("=");
        sb.append("'");sb.append(phone);sb.append("'");
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
           closeall();
        }
    }
    //THIS FUNCTION IS USED TO RETURN CONNECTION VALUE IN BOOLEAN TO CONTROLLER CLASS TO CHECK
    //IF THE CONNECTION BETWEEN THE JAVAFX AND SQLITE3 IS SUCCESSFUL OR NOT
    public boolean isDbconnected()
    {
        try {
            return !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //THIS FUNCTION IS TO CHECK THE CREDENTIALS OF EMPLOYEE
    public boolean logincheckforemployee(String id, String pass) throws SQLException {
        {
            pre = null;
            rs = null;
            String query = "SELECT * FROM " + EMPLOYEE_TABLE + " WHERE " + EMPLOYEE_USERNAME + "=? and " + EMPLOYEE_PASSWORD + "=? ";
            try {
                pre = conn.prepareStatement(query);
                pre.setString(1, id);
                pre.setString(2, pass);
                rs = pre.executeQuery();
                if (rs.next())
                    return true;
                else
                    return false;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if (rs!=null)
                    rs.close();
                if (pre!=null)
                    pre.close();
                if (conn!=null)
                    conn.close();
            }
            return false;
        }
    }
    //THIS FUNCTION IS TO CHECK THE CREDENTIALS OF ADMIN
    public boolean logincheckforadmin(String emailid, String password) throws SQLException {
        pre=null;
        rs=null;
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT * FROM ");sb.append(" ");sb.append(ADMIN_TABLE);sb.append(" ");
        sb.append(" WHERE ");sb.append(ADMIN_USERNAME);sb.append("=? and ");sb.append(ADMIN_PASSWORD);
        sb.append("=? ");
        try {
            pre=conn.prepareStatement(sb.toString());
            pre.setString(1,emailid);
            pre.setString(2,password);
            rs=pre.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs!=null)
                rs.close();
            if (pre!=null)
                pre.close();
            if (conn!=null)
                conn.close();
        }
        return false;
    }
    //THIS FUNCTION IS USED TO SHOW THE EMPLOYEE TABLE TO ADMIN INTERFACE
    public static List<empDetails> loadtableemp() throws SQLException {
        con = connectDb();
        preparedStatement=null;
        resultSet=null;
        StringBuilder sb = new StringBuilder(" SELECT * FROM ");
        sb.append(EMPLOYEE_TABLE);
        try {
            preparedStatement=con.prepareStatement(String.valueOf(sb));
            resultSet=preparedStatement.executeQuery();
            List<empDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                empDetails empDetails=new empDetails();
                empDetails.setEmail(resultSet.getString(1));
                empDetails.setPassword(resultSet.getString(2));
                empDetails.setName(resultSet.getString(3));
                empDetails.setPhone(resultSet.getInt(4));
                empDetails.setAddedbyadmin(resultSet.getString(5));
                data.add(empDetails);
            }
            return data;
        } catch (SQLException e) {
            return null;
        }finally {
          closeall();
        }
    }
    //THIS FUNCTION IS USED TO SHOW THE ADMIN TABLE TO ADMIN INTERFACE
    public static List<adminDetails> loadtableadmin() throws SQLException {
        con = connectDb();
        preparedStatement=null;
        resultSet=null;
        StringBuilder sb = new StringBuilder(" SELECT * FROM "); sb.append(ADMIN_TABLE);
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            List<adminDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                adminDetails adminDetails=new adminDetails();
                adminDetails.setEmail(resultSet.getString(1));
                adminDetails.setPassword(resultSet.getString(2));
                data.add(adminDetails);
            }
            return data;
        } catch (SQLException e) {
            return null;
        }finally {
            closeall();
        }
    }

    //THIS FUNCTION IS USED TO LOAD LIST OF PRODUCTS CATEGORY WISE TO EMPLOYEE INTERFACE
    public static List<productDetails> load_by_category(String category) throws SQLException {
        con=connectDb();
        preparedStatement=null;
        resultSet=null;
        StringBuilder sb=new StringBuilder("SELECT * FROM"); sb.append(" ");
        sb.append(PRODUCT_TABLE); sb.append(" "); sb.append("WHERE"); sb.append(" ");
        sb.append(PRODUCT_TYPE);sb.append("="); sb.append("'"); sb.append(category);
        sb.append("'");
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            List<productDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                productDetails pd = new productDetails();
                pd.setProductID(resultSet.getInt(1));
                pd.setProductname(resultSet.getString(2));
                pd.setProductprice(resultSet.getDouble(3));
                data.add(pd);
            }
            return data;
        }catch (SQLException e){
            return null;
        }finally {
            closeall();
        }
    }
    //THIS FUNCTION WILL AUTOMATICALLY LOAD AND SHOW PRODUCT ID NAME TO
    //EMPLOYEE AS SOON AS THE EMPLOYEE LOGIN SUCCESSFULLY
    public static List<productDetails> load() throws SQLException {
        con = connectDb();
        StringBuilder sb = new StringBuilder(" SELECT * FROM ");
        sb.append(PRODUCT_TABLE);
        sb.append(" ORDER BY ");
        sb.append(PRODUCT_ID);
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            List<productDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                productDetails productDetails=new productDetails();
                productDetails.setProductID(resultSet.getInt(1));
                productDetails.setProductname(resultSet.getString(2));
                productDetails.setProductprice(resultSet.getDouble(3));
                data.add(productDetails);
            }
            return data;
        } catch (SQLException e) {
            return null;
        }finally {
        closeall();
        }
    }
    //FUNCTION TO LOAD PRODUCT TABLE TO ADMIN INTERFACE IN SHOW TABLES INTERFACE
    public static List<productDetails> loadtableproduct() throws SQLException {
       con = connectDb();
       preparedStatement=null;resultSet=null;
        StringBuilder sb = new StringBuilder(" SELECT * FROM ");
        sb.append(PRODUCT_TABLE);
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            List<productDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                productDetails productDetails=new productDetails();
                productDetails.setProductID(resultSet.getInt(1));
                productDetails.setProductname(resultSet.getString(2));
                productDetails.setProductprice(resultSet.getDouble(3));
                productDetails.setCompanyID(resultSet.getInt(4));
                productDetails.setProductweight(resultSet.getString(5));
                productDetails.setTypeofproduct(resultSet.getString(6));
                data.add(productDetails);
            }
            return data;
        } catch (SQLException e) {
            return null;
        }finally {
          closeall();
        }
    }
    //FUNCTION TO LOAD COMPANY TABLE TO ADMIN INTERFACE IN SHOW TABLES INTERFACE
    public static List<companyDetails> loadtablecompany() throws SQLException {
        con = connectDb();
        preparedStatement=null;resultSet=null;
        StringBuilder sb = new StringBuilder(" SELECT * FROM ");
        sb.append(COMPANY_TABLE);
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            List<companyDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                companyDetails companyDetails=new companyDetails();
                companyDetails.setCompanyid(resultSet.getInt(1));
                companyDetails.setCompanyname(resultSet.getString(2));
                companyDetails.setCompanynumberofproduct(resultSet.getInt(3));
                companyDetails.setCompanyadmin(resultSet.getString(4));
                data.add(companyDetails);
            }
            return data;
        } catch (SQLException e) {
            return null;
        }finally {
            closeall();
        }
    }
    //FUNCTION TO LOAD CUSTOMER TABLE TO ADMIN INTERFACE IN SHOW TABLES INTERFACE
    public static List<customerDetails> loadtablecustomer() throws SQLException {
        con = connectDb();
        preparedStatement=null;
        resultSet=null;
        StringBuilder sb = new StringBuilder(" SELECT * FROM ");
        sb.append(CUSTOMER_TABLE);
        try  {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            List<customerDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                customerDetails customerDetails=new customerDetails();
                customerDetails.setCustomerphone(resultSet.getInt(1));
                customerDetails.setCustomername(resultSet.getString(2));
                customerDetails.setAmountpaid(resultSet.getDouble(3));
                customerDetails.setPaymentmode(resultSet.getString(4));
                customerDetails.setEmpemail(resultSet.getString(5));
                data.add(customerDetails);
            }
            return data;
        } catch (SQLException e) {
            return null;
        }finally {
            closeall();
        }
    }
    //FUNCTION TO LOAD PURCHASE TABLE TO ADMIN INTERFACE IN SHOW TABLES INTERFACE
    public static List<purchaseDetails> loadtablepurchase() throws SQLException {
       con = connectDb();
       preparedStatement=null;
       resultSet=null;
        StringBuilder sb = new StringBuilder(" SELECT * FROM ");
        sb.append(PURCHASE_TABLE);
        try {
            preparedStatement=con.prepareStatement(sb.toString());
            resultSet=preparedStatement.executeQuery();
            List<purchaseDetails> data = new ArrayList<>();
            while (resultSet.next()) {
                purchaseDetails purchaseDetails=new purchaseDetails();
                purchaseDetails.setCustomerphone(resultSet.getInt(1));
                purchaseDetails.setProductnames(resultSet.getString(2));
                purchaseDetails.setTotalprice(resultSet.getDouble(3));
                data.add(purchaseDetails);
            }
            return data;
        } catch (SQLException e) {
            return null;
        }finally {
            closeall();
        }
    }
}

