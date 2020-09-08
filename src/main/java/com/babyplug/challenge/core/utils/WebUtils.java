package com.babyplug.challenge.core.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    private HttpServletRequest request;
 
    public WebUtils(HttpServletRequest request) {
        this.request = request;
    }

    public String getClientIp() {
            String ip = request.getHeader("X-Real-IP");
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        return request.getRemoteAddr();
    }

}
