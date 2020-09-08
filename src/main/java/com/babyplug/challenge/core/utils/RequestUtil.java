package com.babyplug.challenge.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RequestUtil {

	private static final Logger LOGGER = LogManager.getLogger(RequestUtil.class);
	public static final String JSON_VALUE = "{\"%s\": %s}";
	
	public static void sendJsonResponse(HttpServletResponse response, String key, String message) {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			response.getWriter().write(String.format(JSON_VALUE, key, message));
		} catch (IOException e) {
			LOGGER.error("error writing json to response", e);
		}
	}
}
