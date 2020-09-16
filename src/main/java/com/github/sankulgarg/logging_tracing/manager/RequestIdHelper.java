package com.github.sankulgarg.logging_tracing.manager;

import org.apache.logging.log4j.ThreadContext;

import com.github.sankulgarg.logging_tracing.constants.ApplicationConstants;
import com.github.sankulgarg.logging_tracing.dto.LogInfoDTO;

/**
 * @author sankul.garg
 * A Helper class which interacts with MDC to get requestId in a static way. 
 */
public class RequestIdHelper {

	private RequestIdHelper() {
		super();
	}

	/**
	 * @return RequestId associated with the current MDC 
	 */
	public static String getRequestId() {
		String message = ThreadContext.get(ApplicationConstants.LOG_INFO);
		LogInfoDTO dto = null;
		if (message != null)
			dto = LogInfoDTO.toObject(message);
		if (dto != null)
			return dto.getRequestId();
		return null;
	}
}
