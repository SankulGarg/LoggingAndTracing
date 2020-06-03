package com.github.sankulgarg.logging_tracing.exception;

import org.springframework.http.HttpStatus;

public final class ErrorDetails {
	private int		responseCode	= HttpStatus.INTERNAL_SERVER_ERROR.value();
	private String	errorCode;
	private String	details;

	public ErrorDetails(int responseCode, String errorCode, String details) {
		super();
		this.responseCode = responseCode;
		this.errorCode = errorCode;
		this.details = details;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
