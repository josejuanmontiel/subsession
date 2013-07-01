package com.bluelotussoftware.session.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
 
/**
 *
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @author Jose Juan Montiel <josejuan.montiel@gmail.com>
 * @version 1.0
 */
public class HttpServletRequestWrapperImpl extends HttpServletRequestWrapper {
 
	private String subsession;
	private String subsessionBreadcrumb;
	
    public HttpServletRequestWrapperImpl(HttpServletRequest request) {
        super(request);
    }
 
	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will return an instance of {@link HttpSessionWrapper}
	 * </p>
	 */
    @Override
    public HttpSession getSession() {
        return getSession(true);
    }
 
	/**
	 * {@inheritDoc}
	 * <p>
	 * This method will return an instance of {@link HttpSessionWrapper}
	 * </p>
	 */
    @Override
    public HttpSession getSession(boolean create) {
        HttpSession session = super.getSession(create);
        if (session != null) {
        	
        	Cookie[] cookies = ((HttpServletRequest)super.getRequest()).getCookies();

    		if (cookies != null) {
    			for (Cookie cookie : cookies) {
    				if (cookie.getName().equals("subsession")) {
    					this.subsession = cookie.getValue();
    				}
    				if (cookie.getName().equals("subsession_breadcrumb")) {
    					this.subsessionBreadcrumb = cookie.getValue();
    				}
    			}
    		}
        	
            return new HttpSessionWrapperImpl(this.subsession, subsessionBreadcrumb, session);
        } else {
            return session;
        }
    }
}
