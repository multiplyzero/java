package com.yiwugou.reb.api.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

public class CookieUtils {

    private final static String COOKIE_DOMAIN = "";

    public final static String SESSION_ID = "sessionId";

    /**
     * 30 mins
     */
    public static final int MAX_AGE = 30 * 60;

    /**
     * 浏览器退出失效
     */
    public static final int MAX_AGE_BROWSER_OUT = -1;

    public static String generateSessionId() {
        return UUID.randomUUID().toString();

    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie c = WebUtils.getCookie(request, name);
        if (c != null) {
            return c.getValue();
        }
        return null;
    }

    /**
     * setCookie
     * 
     * @param response
     * @param name
     * @param value
     * @param maxAge
     *            cookie有效时间 -1为浏览器退出就无效
     * @param httpOnly
     *            安全标识 true为不允许js脚本访问
     * @param cookieDomain
     *            cookie域
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly,
            String cookieDomain) {
        CookieGenerator c = new CookieGenerator();
        c.setCookieDomain(StringUtils.trimToEmpty(cookieDomain));
        c.setCookieMaxAge(maxAge);
        c.setCookieName(name);
        c.setCookieHttpOnly(httpOnly);
        c.addCookie(response, value);
    }

    public static void removeCookie(HttpServletResponse response, String name, String cookieDomain) {
        CookieGenerator c = new CookieGenerator();
        c.setCookieName(name);
        c.setCookieDomain(StringUtils.trimToEmpty(cookieDomain));
        c.removeCookie(response);
    }

}
