package com.babyplug.challenge.core;

import java.io.Serializable;

public class ProcessResult implements Serializable{

    private static final long serialVersionUID = 1L;

    private String statusCode;
    private String statusMsg;

    private Object resultValue;

    public ProcessResult() {
    }

    public ProcessResult(String statusCode, Object resultValue) {
        this.statusCode = statusCode;
        this.resultValue = resultValue;
    }

    public ProcessResult(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public ProcessResult(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public void setResultValue(Object resultValue) {
        this.resultValue = resultValue;
    }

    public Object getResultValue() {
        return resultValue;
    }


}
