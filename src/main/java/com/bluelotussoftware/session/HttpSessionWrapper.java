/*
 * Copyright 2012 Blue Lotus Software, LLC.
 * Copyright 2012 John Yeary <jyeary@bluelotussoftware.com>.
 * Copyright 2013 Jose Juan Montiel <josejuan.montiel@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id$
 */
package com.bluelotussoftware.session;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * A Wrapper (decorator) for the {@link  HttpSession} object.
 *
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @author Jose Juan Montiel <josejuan.montiel@gmail.com>
 * @version 1.0
 */
public class HttpSessionWrapper implements HttpSession {

	private HttpSession session;
	private String subsession;
	private String subsessionBreadcrumb;

	/**
	 * Constructor that takes an {@link HttpSession} object to wrap.
	 * 
	 * @param session
	 */
	public HttpSessionWrapper(String subsession, String subsessionBreadcrumb, HttpSession session) {
		this.subsession = subsession;
		this.session = session;
		this.subsessionBreadcrumb = subsessionBreadcrumb;
		
		// Take the parent
		String prefix = "";
		if (subsessionBreadcrumb!=null) {
			String[] parentSession = subsessionBreadcrumb.split("/");
			if (parentSession.length>1) {
				// if not the root parent...
				if (!"1".equals(parentSession[parentSession.length-2])) {
					prefix = parentSession[parentSession.length-2]+"#";	
				}
			}
		}

		// If there is a prefix parent session...
		if (!"".equals(prefix)) {
			Enumeration<String> sessionAttrs = getAttributeNames();
			while(sessionAttrs.hasMoreElements()) {
				String sessionAttrName = sessionAttrs.nextElement();
				String sessionAttrNameWithOutPrefix = sessionAttrName.substring(prefix.length());

				// Copy values from inherit session...
				if (sessionAttrName.startsWith(prefix)) {
					session.setAttribute(subsession+"#"+sessionAttrNameWithOutPrefix, session.getAttribute(sessionAttrName));
				}
				
			}
		}
		
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCreationTime() {
        return session.getCreationTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return session.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLastAccessedTime() {
        return session.getLastAccessedTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServletContext getServletContext() {
        return session.getServletContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        session.setMaxInactiveInterval(interval);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpSessionContext getSessionContext() {
        return session.getSessionContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAttribute(String name) {
        return session.getAttribute(subsession+"#"+name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue(String name) {
        return session.getValue(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return session.getAttributeNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getValueNames() {
        return session.getValueNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttribute(String name, Object value) {
        session.setAttribute(subsession+"#"+name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putValue(String name, Object value) {
        session.putValue(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAttribute(String name) {
        session.removeAttribute(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeValue(String name) {
        session.removeValue(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidate() {
        session.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return session.isNew();
    }
}
