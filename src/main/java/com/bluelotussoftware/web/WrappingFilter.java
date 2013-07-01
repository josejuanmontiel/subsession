package com.bluelotussoftware.web;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.bluelotussoftware.session.HttpSessionWrapper;
import com.bluelotussoftware.session.impl.HttpServletRequestWrapperImpl;

/**
 * {@link Filter} used for wrapping the {@link HttpServletRequest} with an
 * implementation of the {@link HttpServletRequestWrapper} that provides a
 * custom {@link HttpSessionWrapper}.
 *
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @author Jose Juan Montiel <josejuan.montiel@gmail.com>
 * @version 1.0
 */
@WebFilter(filterName = "WrappingFilter", urlPatterns = {"/*"},
dispatcherTypes = {
    DispatcherType.FORWARD, DispatcherType.ERROR,
    DispatcherType.REQUEST, DispatcherType.INCLUDE
})
public class WrappingFilter implements Filter {
 
    /**
     * Default Constructor
     */
    public WrappingFilter() {
    	System.out.println("Instance created of " + getClass().getName());
    }
 
    /**
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
    	
        HttpServletRequest hsr = (HttpServletRequest) request;
        HttpServletRequestWrapperImpl hsrwi = new HttpServletRequestWrapperImpl(hsr);
        
		/*
		 * use the ServletContext.log method to log filter messages
		 */
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		// log the session ID
		System.out.println("session ID: " + session.getId());
		
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("subsession")) {
					System.out.println("subsession: " + cookie.getValue());
				}
				if (cookie.getName().equals("subsession_breadcrumb")) {
					System.out.println("subsession_breadcrumb: " + cookie.getValue());
				}
			}
		}
        
        chain.doFilter(hsrwi, response);
    }
 
    /**
     * {@inheritDoc}
     */
    public void destroy() {
        //NO-OP
    }
 
    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig filterConfig) {
        //NO-OP
    }
}