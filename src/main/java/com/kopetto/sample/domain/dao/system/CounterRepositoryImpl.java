package com.kopetto.sample.domain.dao.system;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.kopetto.sample.domain.dao.BaseRepositoryImpl;
import com.kopetto.sample.domain.entity.Counter;




/**Custom methods for CounterRepository repository - implementation
 *
 */
public class CounterRepositoryImpl extends BaseRepositoryImpl implements CounterRepositoryCustom {

	@Override
    public long increaseCounter(String counterName){
        Query query = new Query(Criteria.where("name").is(counterName));
        Update update = new Update().inc("sequence", 1);
        Counter counter = mongoTemplate.findAndModify(query, update, Counter.class); // return old Counter object
        if (counter == null){
            counter = new Counter();
            counter.setName(counterName);
            counter.setSequence(2); //should increase by one.
            mongoTemplate.save(counter);
            return 1;
        }
        return counter.getSequence();
    }

	@Override
    public long getCount(String counterName){
        Query query = new Query(Criteria.where("name").is(counterName));
        Counter counter = mongoTemplate.findOne(query, Counter.class); // return old Counter object
        if (counter != null){
            return counter.getSequence();
        } else {
            return 0l;
        }
    }
	
}
