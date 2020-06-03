package com.github.sankulgarg.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sankulgarg.manager.ExceptionManager;

public class LogInfoDTO {
	private static final ObjectMapper	mapperObj	= new ObjectMapper();
	private String						requestId;
	private ContextsMap<String, String>	contexts;

	public static LogInfoDTO toObject(String message) {
		try {
			return mapperObj.readValue(message, LogInfoDTO.class);
		}
		catch (Exception e) {
			ExceptionManager.handleException(e, "error string to object json");
			return new LogInfoDTO();
		}
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public ContextsMap<String, String> getContexts() {
		return contexts;
	}

	public void setContexts(ContextsMap<String, String> contexts) {
		this.contexts = contexts;
	}

	@Override
	public String toString() {
		try {
			return mapperObj.writeValueAsString(this);
		}
		catch (JsonProcessingException e) {
			ExceptionManager.handleException(e, "error while processing json");
			return null;
		}
	}
}
