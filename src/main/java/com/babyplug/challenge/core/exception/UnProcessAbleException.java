package com.babyplug.challenge.core.exception;

import java.util.List;

public class UnProcessAbleException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<String> errMsgs;
    private String code;
    private String message;
    private Object responseObj;

    public UnProcessAbleException(String code, List<String> errMsgs) {
        this.errMsgs = errMsgs;
        this.code = code;
    }

    public UnProcessAbleException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public UnProcessAbleException(String code, String message, Object responseObj) {
        this.code = code;
        this.message = message;
        this.responseObj = responseObj;
    }

    public List<String> getErrMsgs() {
        return errMsgs;
    }

    public void setErrMsgs(List<String> errMsgs) {
        this.errMsgs = errMsgs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(Object responseObj) {
        this.responseObj = responseObj;
    }


}
