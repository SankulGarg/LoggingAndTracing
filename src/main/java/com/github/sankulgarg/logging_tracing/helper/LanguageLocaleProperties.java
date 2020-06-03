package com.github.sankulgarg.logging_tracing.helper;

import java.io.InputStream;
import java.util.Properties;

import com.github.sankulgarg.logging_tracing.manager.ExceptionManager;

/**
 * fetching the property value from specific property file defined by the culture
 * @author sankul.garg
 *
 */
public class LanguageLocaleProperties {
	private LanguageLocaleProperties() {
		super();
	}

	private static final String DEFAULT_LANGUAGE = "en-UK.properties";

	public static String getMessage(String languageFile, String propertyName) {
		if (propertyName == null) {
			propertyName = "500";
		}
		if (languageFile == null)
			languageFile = DEFAULT_LANGUAGE;
		Properties prop = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(languageFile);
		if (inputStream == null)
			return getMessage(DEFAULT_LANGUAGE, propertyName);
		try {
			prop.load(inputStream);
		}
		catch (Exception e) {
			ExceptionManager.handleException(e, "error while passing locale property file");
		}
		if (prop.isEmpty())
			return getMessage(DEFAULT_LANGUAGE, propertyName);
		return prop.getProperty(propertyName);

	}

}