package com.kopetto.sample.controller;

import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.kopetto.sample.util.UserUtils;

/**
 * Base Controller
 * 
 */
public class BaseController {
	
    @ModelAttribute("loggedinUser")
    public UserDetails loggedInUser() {
        return UserUtils.getLoginUser();
    }
    
    @ModelAttribute(value="pageId")
    public String  getPageId (HttpServletRequestWrapper request) {
    	String requestUri = request.getRequestURI();
    	String rc = null;
    	
    	if (requestUri.indexOf("account") > -1)
    		rc = "account";
    	else if (requestUri.indexOf("search") > -1)
    		rc = "search";
    	else if (requestUri.indexOf("signin") > -1)
    		rc = "signin";
    	else if (requestUri.indexOf("orders") > -1)
    		rc = "orders";
    	else 
    		rc = "home";
    	
    	return rc;
    }
    
    @ModelAttribute(value="requestUri")
    public String  getRequetUrl (HttpServletRequestWrapper request) {
    	return request.getRequestURI();
    }
    
}
