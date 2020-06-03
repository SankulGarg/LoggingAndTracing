package com.github.sankulgarg.logging_tracing.handlers;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.sankulgarg.logging_tracing.exception.MultilingualBussinessException;
import com.github.sankulgarg.logging_tracing.helper.ResponseEntityComposer;
import com.github.sankulgarg.logging_tracing.manager.ExceptionManager;

/**
 * Class enabling centralized exception handling across all @RequestMapping methods.  
 *
 * @author sankul.garg
 *
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		return new ResponseEntity<>("Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleExceptions(RuntimeException ex, WebRequest request) {
		ExceptionManager.handleException(ex, "RuntimeException" + ex.getClass().getName());
		return ResponseEntityComposer.create(request, ex);

	}

	/**
	 * Catching custom MultilingualBussinessException and changing the response entity message as per the locale
	 * @param ex The custom MultilingualBussinessException 
	 * @param request The Web Request in context
	 * @return new Response Entity with custom language exception
	 */
	@ExceptionHandler({ MultilingualBussinessException.class })
	public ResponseEntity<Object> multilingualBussinessException(MultilingualBussinessException ex, WebRequest request) {
		return ResponseEntityComposer.createBussinessException(request, ex);
	}

}