package com.wxfwh.dto;

import java.io.Serializable;

public class RespDto implements Serializable {

    private static final long serialVersionUID = -5445762954238303616L;

    private int status;
    private String msg;
    private Object data;
    private boolean success;

    public RespDto() {
    }

    public RespDto(int status) {
        this.status = status;
    }

    public RespDto(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public RespDto(boolean success, int status, String msg, Object data) {
        this.success = success;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public RespDto(boolean success, int status, String msg) {
        this.success = success;
        this.status = status;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
