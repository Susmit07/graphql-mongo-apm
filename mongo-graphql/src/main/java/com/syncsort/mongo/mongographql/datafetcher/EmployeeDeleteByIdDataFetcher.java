package com.syncsort.mongo.mongographql.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.syncsort.mongo.mongographql.repository.EmployeeRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class EmployeeDeleteByIdDataFetcher implements DataFetcher<Boolean> {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Boolean get(DataFetchingEnvironment environment) {
		boolean deleteStatus = false;
		try {
			String employeeId = environment.getArgument("employeeId");
			employeeRepository.deleteById(employeeId);
			deleteStatus = true;
		} catch (Exception e) {
			System.out.println("Exception while deleting due to: "+e.getMessage());
		}
		return deleteStatus;
	}

}
