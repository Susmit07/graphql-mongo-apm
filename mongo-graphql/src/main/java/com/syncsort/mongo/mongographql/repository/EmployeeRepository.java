package com.syncsort.mongo.mongographql.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.syncsort.mongo.mongographql.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String>{

	@Query(value = "{ 'employeeId': ?0 }")
	Optional<Employee> findByEmployeeId(String employeeId);

}
