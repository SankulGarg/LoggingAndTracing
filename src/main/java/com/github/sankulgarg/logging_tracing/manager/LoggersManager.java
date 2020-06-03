package com.github.sankulgarg.logging_tracing.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggersManager {

	private LoggersManager() {
		super();
	}

	public static <T> Logger getLogger(Class<T> klass) {
		return LoggerFactory.getLogger(klass);

	}
}
