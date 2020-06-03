package com.github.sankulgarg.logging_tracing.helper;

import java.util.Iterator;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.github.sankulgarg.logging_tracing.constants.ApplicationConstants;
import com.github.sankulgarg.logging_tracing.exception.ErrorDetails;
import com.github.sankulgarg.logging_tracing.exception.MultilingualBussinessException;

/** Composing custom response entity as per the language defined in the culture
 * @author sankul.garg
 *
 */
public class ResponseEntityComposer {
	private static final String	PROPERTY		= ".properties";
	private static final String	Accept_Language	= "Accept-Language";

	private ResponseEntityComposer() {
	}

	public static ResponseEntity<Object> create(WebRequest request, RuntimeException ex) {
		HttpHeaders httpHeaders = generateHeaders(request);
		return new ResponseEntity<>(new ErrorDetails(500, ex.getLocalizedMessage(), null), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static ResponseEntity<Object> createBussinessException(WebRequest request, MultilingualBussinessException ex) {
		HttpHeaders httpHeaders = generateHeaders(request);

		return new ResponseEntity<>(localeErrorDetails(ex.getErrorDetails(), request.getHeader(Accept_Language)), httpHeaders,
				HttpStatus.valueOf(ex.getErrorDetails().getResponseCode()));

	}

	private static ErrorDetails localeErrorDetails(ErrorDetails errorDetails, String language) {
		String message = LanguageLocaleProperties.getMessage(language == null ? "" : language + PROPERTY, errorDetails.getErrorCode());
		errorDetails.setDetails(message);
		return errorDetails;

	}

	private static HttpHeaders generateHeaders(WebRequest request) {
		HttpHeaders httpHeaders = new HttpHeaders();
		Iterator<String> headers = request.getHeaderNames();
		headers.forEachRemaining(k -> httpHeaders.add(k, request.getHeader(k)));
		if (!httpHeaders.containsKey(ApplicationConstants.REQUEST_ID))
			httpHeaders.add(ApplicationConstants.REQUEST_ID, ThreadContext.getContext().get(ApplicationConstants.REQUEST_ID));

		return httpHeaders;
	}
}
