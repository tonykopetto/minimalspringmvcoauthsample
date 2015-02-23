package com.kopetto.sample.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

@Configuration
@ComponentScan(basePackages = {
        "com.kopetto.sample.domain"
})
@PropertySource("classpath:properties/global.properties")
@EnableMongoRepositories(
		mongoTemplateRef="mongoTemplate", 
		basePackages = {"com.kopetto.sample.domain"}
)
public class PersistenceContext extends AbstractMongoConfiguration {
	
	@Autowired
	private Environment environment;

	
	@Override
	public String getDatabaseName() {
		
		String dbName = environment.getProperty("mongodb.dbname"); 
		
		if (System.getProperty("db") != null && ! "".equals(System.getProperty("db"))){
			dbName = System.getProperty("db");
		}
		return dbName;
		
	}

	@Override
	protected UserCredentials getUserCredentials() {
		return new UserCredentials(environment.getProperty("mongodb.username"),
				environment.getProperty("mongodb.password"));
	}

	@Override
	public String getMappingBasePackage() {
		return "com.kopetto.sample.domain.dao";
	}

	@Override
	@Bean(name="mongo")
	public Mongo mongo() throws Exception {

		String replicaSet = environment.getProperty("mongodb.replica.set");
		if (replicaSet == null)
			replicaSet = "localhost:27017";
		
		if (System.getProperty("replicaSet") != null && ! "".equals(System.getProperty("replicaSet"))){
			replicaSet = System.getProperty("replicaSet");
		}
		System.out.println ("Connecting to:" + replicaSet);

		List<ServerAddress> addrs = new ArrayList<ServerAddress>();
		 
		String [] replicaSetA = replicaSet.split(",");
		for (String sAddress : replicaSetA) {
			
			String [] sAddressA = sAddress.split(":");
			
			 addrs.add( new ServerAddress(sAddressA [0], Integer.parseInt (sAddressA [1])) );
		}

		MongoOptions mongoOptions = new MongoOptions ();
		mongoOptions.setConnectionsPerHost(Integer.parseInt(environment.getProperty("mongo.connection.max")));
		mongoOptions.autoConnectRetry=true;
		mongoOptions.fsync=true;
		mongoOptions.setReadPreference (ReadPreference.secondaryPreferred());
		
		Mongo mongo = new Mongo(addrs, mongoOptions);
		mongo.setWriteConcern(WriteConcern.SAFE);
		
		return mongo;
	}
	
	
}