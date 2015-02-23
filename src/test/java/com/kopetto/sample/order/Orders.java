package com.kopetto.sample.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.kopetto.sample.TestListener;
import com.kopetto.sample.configuration.MainContext;
import com.kopetto.sample.configuration.PersistenceContext;
import com.kopetto.sample.domain.entity.order.Order;
import com.kopetto.sample.service.order.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MainContext.class, PersistenceContext.class}, loader=AnnotationConfigContextLoader.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, TestListener.class})
public class Orders {

	@Autowired
	private OrderService orderService;
	
	@Test
	public void insertOrder () {
		
		//insert one order
		Order order = new Order ("#1", "First order", "tony.kopetto");
		orderService.saveOrder (order);
		System.out.println ("order inserted");
	}
	
	@Test
	public void insertOrders () {
		
		//insert one order
		List<Order> orders = new ArrayList<Order> ();
		for (int i = 2; i < 100; i ++){
			orders.add(new Order ("#" + i, "Order #" + i, "tony.kopetto"));
		}
		
		orderService.saveOrders (orders);
		System.out.println ("orders inserted");
	}
}
