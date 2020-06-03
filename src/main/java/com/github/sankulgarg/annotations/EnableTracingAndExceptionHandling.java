package com.github.sankulgarg.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import com.github.sankulgarg.aop.ExceptionAdvice;
import com.github.sankulgarg.aop.RequestHandler;

/**
 * Annotation enabling the logging framework,
 *  with request tracing and multilingual exception handling 
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@ImportAutoConfiguration(value = { ExceptionAdvice.class, RequestHandler.class })
public @interface EnableTracingAndExceptionHandling {

}
