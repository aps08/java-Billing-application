package billingapp;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class purchaseDetails {
    private SimpleIntegerProperty customerphone;
    private SimpleStringProperty productnames;
    private SimpleDoubleProperty totalprice;
    public purchaseDetails(){
        this(new SimpleIntegerProperty(),new SimpleStringProperty(),new SimpleDoubleProperty());
    }
    public purchaseDetails(SimpleIntegerProperty customerphone, SimpleStringProperty productnames, SimpleDoubleProperty totalprice) {
        this.customerphone = customerphone;
        this.productnames = productnames;
        this.totalprice = totalprice;
    }
    public int getCustomerphone() {
        return customerphone.get();
    }
    public SimpleIntegerProperty customerphoneProperty() {
        return customerphone;
    }
    public void setCustomerphone(int customerphone) {
        this.customerphone=new SimpleIntegerProperty(customerphone);
    }
    public String getProductnames() {
        return productnames.get();
    }
    public SimpleStringProperty productnamesProperty() {
        return productnames;
    }
    public void setProductnames(String productnames) {
        this.productnames=new SimpleStringProperty(productnames);
    }
    public double getTotalprice() {
        return totalprice.get();
    }
    public SimpleDoubleProperty totalpriceProperty() {
        return totalprice;
    }
    public void setTotalprice(double totalprice) {
        this.totalprice=new SimpleDoubleProperty(totalprice);
    }
}
