
package com.example.haydane.safezone.response;





public class ResponseUser {


    private String msg;

    private String error;

    private String success;

    public String getError() {
        return error;
    }

    public void setError(String errorMsg) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseUser{" +
                "msg='" + msg + '\'' +
                ", error='" + error + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
