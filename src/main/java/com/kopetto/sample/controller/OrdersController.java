package com.kopetto.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kopetto.sample.domain.entity.order.Order;
import com.kopetto.sample.service.order.OrderService;
import com.kopetto.sample.util.PageHolder;
import com.kopetto.sample.util.UserUtils;

/**
 * Controller for orders
 * 
 */
@Controller
public class OrdersController extends BaseController {
	
	@Autowired
	private OrderService orderService;
	
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders (Model uiModel) {
    	return "orders";
    }

    @RequestMapping(value = "/orders/", method = RequestMethod.GET)
    public String getOrders (Model uiModel, Pageable pageable) {
    	
    	//get orders from DB
    	Page<Order> orders = orderService.getAllOrdersForUser (pageable, UserUtils.getLoginUser());
    	uiModel.addAttribute("orders", orders);
    	uiModel.addAttribute("page", new PageHolder<Order> (orders));
    	
    	return "order/orderList";
    }

    @RequestMapping(value = "/extractToken", method = RequestMethod.GET)
    public String extractToken () {
    	return "extractToken";
    }

}
