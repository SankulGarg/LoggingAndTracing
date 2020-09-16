package com.github.sankulgarg.logging_tracing.handlers;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.github.sankulgarg.logging_tracing.constants.ApplicationConstants;
import com.github.sankulgarg.logging_tracing.manager.RequestIdHelper;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		String requestId = RequestIdHelper.getRequestId();

		if (requestId != null && !requestId.isEmpty())
			request.getHeaders().set(ApplicationConstants.REQUEST_ID, requestId);
		return execution.execute(request, body);
	}
}