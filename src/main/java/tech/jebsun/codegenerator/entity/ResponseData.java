package tech.jebsun.codegenerator.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JebSun on 2018/3/5.
 */
public class ResponseData {

    private Boolean success = true;

    private String message;

    private List rows = new ArrayList<>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
