package com.github.sankulgarg.manager;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import com.github.sankulgarg.exception.ErrorDetails;
import com.github.sankulgarg.exception.MultilingualBussinessException;

/**
 * @author sankul.garg
 *
 */
public class ExceptionManager {
	private static final Logger LOG = LoggersManager.getLogger(ExceptionManager.class);

	private ExceptionManager() {
	}

	private static void logError(Exception exception, String message) {
		if (LOG.isErrorEnabled()) {
			StringBuilder errorMessage = new StringBuilder(message == null ? "" : message);
			errorMessage.append(System.lineSeparator()).append(ExceptionUtils.getStackTrace(exception));
			LOG.error(errorMessage.toString());
		}
	}

	public static void handleException(Exception exception, String message) {
		logError(exception, message);
	}

	public static void handleAndThrowException(Exception exception, ErrorDetails errorDetails, String message) throws MultilingualBussinessException {
		if (errorDetails == null)
			errorDetails = new ErrorDetails(500, "500", message);
		MultilingualBussinessException businessException = new MultilingualBussinessException(errorDetails, exception);
		logError(exception, message);
		throw businessException;
	}

}
