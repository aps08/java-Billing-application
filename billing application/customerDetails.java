package billingapp;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class customerDetails {
    private SimpleIntegerProperty customerphone;
    private SimpleStringProperty customername;
    private SimpleDoubleProperty amountpaid;
    private SimpleStringProperty paymentmode;
    private SimpleStringProperty empemail;
    public customerDetails(SimpleIntegerProperty customerphone, SimpleStringProperty customername, SimpleDoubleProperty amountpaid,
                           SimpleStringProperty paymentmode, SimpleStringProperty empemail) {
        this.customerphone = customerphone;
        this.customername = customername;
        this.amountpaid = amountpaid;
        this.paymentmode = paymentmode;
        this.empemail = empemail;
    }
    public customerDetails()
    {
     this(new SimpleIntegerProperty(),new SimpleStringProperty(),new SimpleDoubleProperty(),new SimpleStringProperty(),
             new SimpleStringProperty());
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
    public String getCustomername() {
        return customername.get();
    }
    public SimpleStringProperty customernameProperty() {
        return customername;
    }
    public void setCustomername(String customername) {
        this.customername=new SimpleStringProperty(customername);
    }
    public double getAmountpaid() {
        return amountpaid.get();
    }
    public SimpleDoubleProperty amountpaidProperty() {
        return amountpaid;
    }
    public void setAmountpaid(double amountpaid) {
        this.amountpaid=new SimpleDoubleProperty(amountpaid);
    }
    public String getPaymentmode() {
        return paymentmode.get();
    }
    public SimpleStringProperty paymentmodeProperty() {
        return paymentmode;
    }
    public void setPaymentmode(String paymentmode) {
        this.paymentmode=new SimpleStringProperty(paymentmode);
    }
    public String getEmpemail() {
        return empemail.get();
    }
    public SimpleStringProperty empemailProperty() {
        return empemail;
    }
    public void setEmpemail(String empemail) {
        this.empemail=new SimpleStringProperty(empemail);
    }
}
