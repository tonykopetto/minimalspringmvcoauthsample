package com.kopetto.sample.domain.dao.system;




/**Custom methods for CounterRepository repository
 *
 */
public interface CounterRepositoryCustom {
    public long increaseCounter(String counterName);

    public long getCount(String counterName);

}
