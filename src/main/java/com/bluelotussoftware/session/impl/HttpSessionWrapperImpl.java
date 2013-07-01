package com.bluelotussoftware.session.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.bluelotussoftware.session.HttpSessionWrapper;

/**
 *
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @version 1.0
 */
public class HttpSessionWrapperImpl extends HttpSessionWrapper {
 
    public static final String INFO = "HttpSessionWrapperImpl/1.0";
 
    public HttpSessionWrapperImpl(String subsession, String subsessionBreadcrumb, HttpSession session) {
        super(subsession, subsessionBreadcrumb, session);
    }
 
    public List<String> getAttributesNames() {
        Enumeration<String> names = getAttributeNames();
        return Collections.list(names);
    }
}
