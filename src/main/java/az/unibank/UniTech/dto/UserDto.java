package az.unibank.UniTech.dto;

import az.unibank.UniTech.model.User;
import lombok.Getter;
import lombok.Setter;

public class UserDto {

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

    public User getUserFromDto() {
        User user = new User();
        user.setPincode(pincode);
        user.setPassword(password);
        return user;
    }
}