package com.syncsort.mongo.mongographql.datafetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.syncsort.mongo.mongographql.model.Employee;
import com.syncsort.mongo.mongographql.repository.EmployeeRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllEmployeeDataFetcher implements DataFetcher<List<Employee>> {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public List<Employee> get(DataFetchingEnvironment environment) {
		return employeeRepository.findAll();
	}
}
