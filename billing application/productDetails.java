package billingapp;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class productDetails {
    private SimpleStringProperty productname;
    private SimpleIntegerProperty companyID;
    private SimpleIntegerProperty productID;
    private SimpleDoubleProperty productprice;
    private SimpleStringProperty typeofproduct;
    private SimpleStringProperty productweight;

    public String getProductweight() {
        return productweight.get();
    }

    public SimpleStringProperty productweightProperty() {
        return productweight;
    }

    public void setProductweight(String productweight) {
        this.productweight=new SimpleStringProperty(productweight);
    }

    public String getTypeofproduct() {
        return typeofproduct.get();
    }

    public SimpleStringProperty typeofproductProperty() {
        return typeofproduct;
    }

    public void setTypeofproduct(String typeofproduct) {
        this.typeofproduct =new SimpleStringProperty(typeofproduct);
    }
    public productDetails() {
        this(new SimpleStringProperty(),new SimpleIntegerProperty(),new SimpleIntegerProperty(), new SimpleDoubleProperty(),
                new SimpleStringProperty(),new SimpleStringProperty());
    }

    public productDetails(String name, Double price) {
        this(new SimpleStringProperty(name),new SimpleIntegerProperty(),new SimpleIntegerProperty(),new SimpleDoubleProperty(price),
                new SimpleStringProperty(),new SimpleStringProperty());
    }

    public String getProductname() {
        return productname.get();
    }

    public SimpleStringProperty productnameProperty() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = new SimpleStringProperty(productname);
    }

    public int getCompanyID() {
        return companyID.get();
    }

    public SimpleIntegerProperty companyIDProperty() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = new SimpleIntegerProperty(companyID);
    }

    public int getProductID() {
        return productID.get();
    }

    public SimpleIntegerProperty productIDProperty() {
        return productID;
    }

    public void setProductID(int  productID) {
        this.productID = new SimpleIntegerProperty(productID);
    }

    public double getProductprice() {
        return productprice.get();
    }

    public SimpleDoubleProperty productpriceProperty() {
        return productprice;
    }
    public productDetails(SimpleStringProperty productname, SimpleIntegerProperty companyID, SimpleIntegerProperty productID,
                          SimpleDoubleProperty productprice, SimpleStringProperty typeofproduct, SimpleStringProperty productweight) {
        this.productname = productname;
        this.companyID = companyID;
        this.productID = productID;
        this.productprice = productprice;
        this.typeofproduct = typeofproduct;
        this.productweight=productweight;
    }

    public void setProductprice(double productprice) {
        this.productprice = new SimpleDoubleProperty(productprice);
    }

}
