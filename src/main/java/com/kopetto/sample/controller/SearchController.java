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
public class SearchController extends BaseController{
	
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search (Model uiModel) {
    	return "search";
    }
}
