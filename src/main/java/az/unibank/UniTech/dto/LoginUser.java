package az.unibank.UniTech.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginUser {

    private String pincode;
    private String password;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}