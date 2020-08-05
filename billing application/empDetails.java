package billingapp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class empDetails {
    private SimpleStringProperty email,password,name;
    private SimpleIntegerProperty phone;
    private SimpleStringProperty addedbyadmin;

    public empDetails(SimpleStringProperty email, SimpleStringProperty password, SimpleStringProperty name, SimpleIntegerProperty phone, SimpleStringProperty addedbyadmin) {
        this.email = email;
        this.password = password;
        this.addedbyadmin = addedbyadmin;
        this.name = name;
        this.phone = phone;
    }

    public empDetails()
     {
         this(new SimpleStringProperty(),new SimpleStringProperty(),new SimpleStringProperty(),new SimpleIntegerProperty(),
                 new SimpleStringProperty());
     }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email=new SimpleStringProperty(email);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name=new SimpleStringProperty(name);
    }

    public int getPhone() {
        return phone.get();
    }

    public SimpleIntegerProperty phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone=new SimpleIntegerProperty(phone);
    }
    public String getAddedbyadmin() {
        return addedbyadmin.get();
    }

    public SimpleStringProperty addedbyadminProperty() {
        return addedbyadmin;
    }

    public void setAddedbyadmin(String addedbyadmin) {
        this.addedbyadmin =new SimpleStringProperty(addedbyadmin);
    }
}

