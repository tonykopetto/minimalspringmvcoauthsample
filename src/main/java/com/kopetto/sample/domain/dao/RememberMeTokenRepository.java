package com.kopetto.sample.domain.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kopetto.sample.domain.entity.RememberMeToken;

/**
 * MongoDB Repository for RememberMeToken entity.
 * 
 */
public interface RememberMeTokenRepository extends MongoRepository<RememberMeToken, String>{
    
    RememberMeToken findBySeries(String series);
    
    List<RememberMeToken> findByUsername(String username);
}
