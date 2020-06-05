package com.github.sankulgarg.logging_tracing.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import com.github.sankulgarg.logging_tracing.config.RestTemplateConfigurator;
import com.github.sankulgarg.logging_tracing.handlers.ExceptionAdvice;
import com.github.sankulgarg.logging_tracing.handlers.RequestIdHandler;

/**
 * Annotation enabling the logging framework,
 *  with request tracing and multilingual exception handling 
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@ImportAutoConfiguration(value = { ExceptionAdvice.class, RequestIdHandler.class, RestTemplateConfigurator.class })
public @interface EnableTracingAndExceptionHandling {

}
