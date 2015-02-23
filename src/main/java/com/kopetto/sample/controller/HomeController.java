package com.kopetto.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for home page, about page, etc.
 * 
 */
@Controller
public class HomeController extends BaseController {
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model uiModel) {
    	return "home";
    }
    
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin (Model uiModel) {
    	return "signin";
    }
    
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account (Model uiModel) {
    	return "account";
    }
    
}
