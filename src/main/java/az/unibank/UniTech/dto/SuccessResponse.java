package az.unibank.UniTech.dto;

import lombok.Data;

public class SuccessResponse {
    private int code;
    private Boolean success;
    private int id;

    public SuccessResponse(int code, Boolean success, int id) {
        this.code = code;
        this.success = success;
        this.id = id;
    }

    public SuccessResponse(int code, Boolean success) {
        this.code = code;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
