package com.github.sankulgarg.logging_tracing.exception;

public class MultilingualBussinessException extends Exception {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7276392011780386160L;

	private ErrorDetails		errorDetails;

	public MultilingualBussinessException(String message) {
		super(message);
	}

	public MultilingualBussinessException(String message, ErrorDetails errorDetails) {
		super(message);
		this.errorDetails = errorDetails;
	}

	public MultilingualBussinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultilingualBussinessException(ErrorDetails errorDetails, Throwable cause) {
		super(cause.getMessage(), cause);
		this.errorDetails = errorDetails;

	}

	public ErrorDetails getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(ErrorDetails errorDetails) {
		this.errorDetails = errorDetails;
	}

}
