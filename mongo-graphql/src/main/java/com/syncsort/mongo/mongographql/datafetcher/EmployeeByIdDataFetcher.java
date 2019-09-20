package com.syncsort.mongo.mongographql.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.syncsort.mongo.mongographql.exception.EmployeeNotFoundException;
import com.syncsort.mongo.mongographql.exception.ErrorCode;
import com.syncsort.mongo.mongographql.model.Employee;
import com.syncsort.mongo.mongographql.repository.EmployeeRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class EmployeeByIdDataFetcher implements DataFetcher<Employee> {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee get(DataFetchingEnvironment environment) {
		String employeeId = environment.getArgument("employeeId");
		return employeeRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(ErrorCode.BAD_REQUEST.getStatusCode(), employeeId));
	}
}
