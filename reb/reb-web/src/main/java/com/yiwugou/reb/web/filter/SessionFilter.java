package com.yiwugou.reb.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

import com.yiwugou.reb.api.utils.CookieUtils;

public class SessionFilter implements Filter {
    private FilterConfig filterConfig;

    private MemcachedClient memcachedClient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(this.filterConfig.getServletContext());
        memcachedClient = (MemcachedClient) context.getBean("memcachedClient");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        this.processSession(request, response);
        chain.doFilter(request, response);
    }

    private void processSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, CookieUtils.SESSION_ID);
        if (cookie != null) {
            String sessionId = cookie.getValue();
            if (StringUtils.isNotBlank(sessionId)) {
                Object session = this.memcachedClient.get(sessionId);
                if (session != null) {
                    request.getSession().setAttribute(CookieUtils.SESSION_ID, session);
                }
            }
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

}
