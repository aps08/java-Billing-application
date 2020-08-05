package billingapp;

import javafx.beans.property.SimpleStringProperty;

public class adminDetails {
    private SimpleStringProperty email;private SimpleStringProperty password;

    public  adminDetails(){
        this(new SimpleStringProperty(),new SimpleStringProperty());
    }
    public adminDetails(SimpleStringProperty email, SimpleStringProperty password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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
}
