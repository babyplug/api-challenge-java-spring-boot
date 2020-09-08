package com.babyplug.challenge.utils;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

public class MessagesUtils {
	
	private MessageSourceAccessor accessor;
	
	public MessagesUtils(String msgFileName) {
		ReloadableResourceBundleMessageSource msgBundle = new ReloadableResourceBundleMessageSource();
		
		msgBundle.setBasename("classpath:/messages/" + msgFileName);
		msgBundle.setDefaultEncoding("UTF-8");
		this.accessor = new MessageSourceAccessor(msgBundle, Locale.US);
	}
	
	public String get(String code) {
		return accessor.getMessage(code);
	}
	
}
