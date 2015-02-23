package com.kopetto.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopetto.sample.domain.dao.system.CounterRepositoryImpl;

/**Deal with coutners
 *
 */
@Service
public class CounterServiceImpl implements CounterService {
    public static final String USER_ID_SEQUENCE_NAME = "user_id";
    
    @Autowired
    private CounterRepositoryImpl counterRepositoryImpl;

    @Override
    public long getNextUserIdSequence() {
        return counterRepositoryImpl.increaseCounter (USER_ID_SEQUENCE_NAME);
    }



}
