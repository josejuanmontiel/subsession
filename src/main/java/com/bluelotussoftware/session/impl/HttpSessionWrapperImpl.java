package com.bluelotussoftware.session.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bluelotussoftware.session.HttpSessionWrapper;

/**
 *
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @version 1.0
 */
public class HttpSessionWrapperImpl extends HttpSessionWrapper {
 
    public static final String INFO = "HttpSessionWrapperImpl/1.0";
 
    public HttpSessionWrapperImpl(HttpSession session, HttpServletRequest request, HttpServletResponse response, String subsession, String subsessionBreadcrumb) {
        super(session, request, response, subsession, subsessionBreadcrumb);
    }
 
    public List<String> getAttributesNames() {
        Enumeration<String> names = getAttributeNames();
        return Collections.list(names);
    }
}
