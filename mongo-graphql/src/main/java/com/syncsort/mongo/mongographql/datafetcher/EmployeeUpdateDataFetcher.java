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
public class EmployeeUpdateDataFetcher implements DataFetcher<Boolean>{

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Boolean get(DataFetchingEnvironment environment) {
		Employee employee = new Employee();
		boolean updateStatus = false;
		try {
			String employeeId = environment.getArgument("employeeId");
			employee.setEmployeeId(employeeId);
			Employee previousEmployeeRecord = getPreviousEmployeeRecord(employeeId);

			Integer age = environment.getArgument("age");
			if(age != null)
				employee.setAge(age);
			else employee.setAge(previousEmployeeRecord.getAge());

			String address = environment.getArgument("address");
			if( address !=null && !address.isEmpty())
				employee.setAddress(address);
			else employee.setAddress(previousEmployeeRecord.getAddress());

			employeeRepository.save(employee);
			updateStatus = true;
		} catch (Exception e) {
			System.out.println("Exception while updating due to: "+e.getMessage());
		}
		return updateStatus;
	}

	private Employee getPreviousEmployeeRecord(String employeeId) {
		return employeeRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(ErrorCode.BAD_REQUEST.getStatusCode(), employeeId));
	}
	
}
