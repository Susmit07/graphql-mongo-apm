package com.syncsort.mongo.mongographql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
public class MongoGraphqlApplication {

	public static void main(String[] args) {
		// Not ideal way, better to specify configuration with run command.
		ElasticApmAttacher.attach();
		SpringApplication.run(MongoGraphqlApplication.class, args);
	}

}
