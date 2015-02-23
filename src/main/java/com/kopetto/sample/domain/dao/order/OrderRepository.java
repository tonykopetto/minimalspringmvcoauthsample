package com.kopetto.sample.domain.dao.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.kopetto.sample.domain.entity.order.Order;

/**
 *
 */
public interface OrderRepository extends MongoRepository<Order, String> {

	Page<Order> findByUserName (Pageable pageable, String userName);
    
}
