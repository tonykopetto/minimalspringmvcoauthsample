package com.kopetto.sample.service.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import com.kopetto.sample.domain.entity.order.Order;

/**
 *
 */
public interface OrderService {

	Page<Order> getAllOrdersForUser(Pageable pageable, UserDetails loginUser);

	void saveOrder(Order order);

	void saveOrders(List<Order> orders);
	
}