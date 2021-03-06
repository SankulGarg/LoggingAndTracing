package com.github.sankulgarg.logging_tracing.handlers;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.github.sankulgarg.logging_tracing.constants.ApplicationConstants;
import com.github.sankulgarg.logging_tracing.dto.LogInfoDTO;
import com.github.sankulgarg.logging_tracing.manager.ExceptionManager;
import com.github.sankulgarg.logging_tracing.manager.LoggersManager;

/**
 * @author sankul.garg
 *
 */
@Component
@Order(1)
@Aspect
public class RequestIdHandler extends GenericFilterBean {
	private static final Logger LOG = LoggersManager.getLogger(RequestIdHandler.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,PATCH, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "*");

		try {

			if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
				response.setStatus(200);
				chain.doFilter(request, response);
				return;
			}
			String requestId = request.getHeader(ApplicationConstants.REQUEST_ID);
			if (requestId == null)
				requestId = java.util.UUID.randomUUID().toString();
			String message = ThreadContext.get(ApplicationConstants.LOG_INFO);

			LogInfoDTO dto = null;
			if (message != null)
				dto = LogInfoDTO.toObject(message);
			else
				dto = new LogInfoDTO();
			dto.setRequestId(requestId);
			CloseableThreadContext.put(ApplicationConstants.REQUEST_ID, requestId);
			CloseableThreadContext.put(ApplicationConstants.LOG_INFO, dto.toString());
			if (LOG.isInfoEnabled())
				LOG.info(" {} :referer: {}  :host: {}" ,request.getRequestURL() ,request.getHeader(HttpHeaders.REFERER) ,
						request.getRemoteHost());			
		}
		catch (Exception e) {
			ExceptionManager.handleException(e, "error while adding requestId");
		}
		chain.doFilter(request, response);
	}

}