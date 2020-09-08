package com.babyplug.challenge.core.exception;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String message;
	private Object errObj;

	public ErrorMessage(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getErrObj() {
		return errObj;
	}

	public void setErrObj(Object errObj) {
		this.errObj = errObj;
	}

}
