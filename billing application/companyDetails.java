package billingapp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class companyDetails {
    private SimpleIntegerProperty companyid;
    private SimpleStringProperty companyname;
    private SimpleIntegerProperty companynumberofproduct;
    private SimpleStringProperty companyadmin;
    public companyDetails() {
        this(new SimpleIntegerProperty(),new SimpleStringProperty(),new SimpleIntegerProperty(),new SimpleStringProperty());
    }
    public companyDetails(SimpleIntegerProperty companyid, SimpleStringProperty companyname, SimpleIntegerProperty companynumberofproduct,
                          SimpleStringProperty companyadmin) {
        this.companyid = companyid;
        this.companyname = companyname;
        this.companynumberofproduct = companynumberofproduct;
        this.companyadmin = companyadmin;
    }
    public int getCompanyid() {
        return companyid.get();
    }
    public SimpleIntegerProperty companyidProperty() {
        return companyid;
    }
    public void setCompanyid(int companyid) {
        this.companyid= new SimpleIntegerProperty(companyid);
    }
    public String getCompanyname() {
        return companyname.get();
    }
    public SimpleStringProperty companynameProperty() {
        return companyname;
    }
    public void setCompanyname(String companyname) {
        this.companyname=new SimpleStringProperty(companyname);
    }
    public int getCompanynumberofproduct() {
        return companynumberofproduct.get();
    }
    public SimpleIntegerProperty companynumberofproductProperty() {
        return companynumberofproduct;
    }
    public void setCompanynumberofproduct(int companynumberofproduct) {
        this.companynumberofproduct=new SimpleIntegerProperty(companynumberofproduct);
    }
    public String getCompanyadmin() {
        return companyadmin.get();
    }
    public SimpleStringProperty companyadminProperty() {
        return companyadmin;
    }
    public void setCompanyadmin(String companyadmin) {
        this.companyadmin=new SimpleStringProperty(companyadmin);
    }
}

