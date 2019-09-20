package com.syncsort.mongo.mongographql.datafetcher;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.syncsort.mongo.mongographql.model.Employee;
import com.syncsort.mongo.mongographql.repository.EmployeeRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class EmployeeSaveDataFetcher implements DataFetcher<Boolean>{

	@Autowired
	EmployeeRepository employeeRepository;

	private final Random random = new Random();

	@Override
	public Boolean get(DataFetchingEnvironment environment) {
		boolean insertionStatus = false;
		try {
			String name = environment.getArgument("name");
			int age = environment.getArgument("age");
			String address = environment.getArgument("address");
			Employee employee = Employee.builder().employeeId(name.toUpperCase().substring(0,2)+random.nextInt(100)+address.toUpperCase().substring(0, 2))
					.name(name).age(age).address(address).build();
			employeeRepository.save(employee);
			insertionStatus = true;
		} catch (Exception e) {
			System.out.println("Exception in persisting the record due to: "+e.getMessage());
		}
		return insertionStatus;
	}
}
