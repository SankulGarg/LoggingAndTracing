package com.github.sankulgarg.logging_tracing.helper;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;

import com.github.sankulgarg.logging_tracing.constants.ApplicationConstants;
import com.github.sankulgarg.logging_tracing.dto.ContextsMap;
import com.github.sankulgarg.logging_tracing.dto.LogInfoDTO;
import com.github.sankulgarg.logging_tracing.manager.ExceptionManager;

public class LogContexts {
	private LogContexts() {
		super();
	}

	public static void add(String key, String value) {
		String message = ThreadContext.get(ApplicationConstants.LOG_INFO);
		try {
			LogInfoDTO dto = null;
			if (message != null)
				dto = LogInfoDTO.toObject(message);
			else
				dto = new LogInfoDTO();
			ContextsMap<String, String> map = dto.getContexts();
			if (map == null) {
				map = new ContextsMap<>();
				dto.setContexts(map);
			}
			map.put(key, value);
			CloseableThreadContext.put(ApplicationConstants.LOG_INFO, dto.toString());
		}
		catch (Exception e) {
			ExceptionManager.handleException(e, "error while adding context");

		}
	}
}
