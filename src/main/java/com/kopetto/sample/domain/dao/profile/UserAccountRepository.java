package com.kopetto.sample.domain.dao.profile;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kopetto.sample.domain.entity.profile.User;


/**
 * MongoDB Repository for UserAccount entity.
 * 
 */
public interface UserAccountRepository extends MongoRepository<User, String>{
    
    User findByName(String name);
    
    Page<User> findAllOrderByUserId(Pageable pageable);

	List<User>  findByDisplayName(String displayName);
	
	@Query ("{$or:[{ 'displayName'  : {$regex: ?0, $options: 'i'}}, { 'fbUserName'  : {$regex: ?0, $options: 'i'}}, { 'email'  : {$regex: ?0, $options: 'i'}}, { 'webSite'  : {$regex: ?0, $options: 'i'}}, { 'userId'  : {$regex: ?0, $options: 'i'}}] } ")
	Page<User> findByFilter(Pageable pageable, String filter);

	User findByEmail(String userId);
	
}
