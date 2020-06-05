package com.github.sankulgarg.logging_tracing.handlers;

import java.io.IOException;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.github.sankulgarg.logging_tracing.constants.ApplicationConstants;
import com.github.sankulgarg.logging_tracing.dto.LogInfoDTO;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		String message = ThreadContext.get(ApplicationConstants.LOG_INFO);
		LogInfoDTO dto = null;
		if (message != null)
			dto = LogInfoDTO.toObject(message);
		if (dto != null)
			request.getHeaders().set(ApplicationConstants.REQUEST_ID, dto.getRequestId());
		return execution.execute(request, body);
	}
}