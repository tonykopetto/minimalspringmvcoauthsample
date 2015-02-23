package com.kopetto.sample.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;




/**Base repository, holds mongoTemplate
 *
 */
public class BaseRepositoryImpl{

	@Autowired
	protected MongoTemplate mongoTemplate;

}
