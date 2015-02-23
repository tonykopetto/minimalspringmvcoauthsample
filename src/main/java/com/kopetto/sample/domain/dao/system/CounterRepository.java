package com.kopetto.sample.domain.dao.system;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kopetto.sample.domain.entity.Counter;

/**
 *
 */
public interface CounterRepository extends MongoRepository<Counter, String>, CounterRepositoryCustom {
    
}
