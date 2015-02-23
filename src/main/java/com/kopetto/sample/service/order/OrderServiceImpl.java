package com.kopetto.sample.service.order;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kopetto.sample.domain.dao.order.OrderRepository;
import com.kopetto.sample.domain.entity.order.Order;


/**
* Implementation for OrderService.
*
*/
@Service
public class OrderServiceImpl implements OrderService{
    final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    
	@Override
	public Page<Order> getAllOrdersForUser(Pageable pageable, UserDetails user) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserName (pageable, user.getUsername());
	}

	@Override
	public void saveOrder(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void saveOrders(List<Order> orders) {
		orderRepository.save (orders);
	}


    
}
