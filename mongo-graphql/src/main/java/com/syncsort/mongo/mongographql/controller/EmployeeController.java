package com.syncsort.mongo.mongographql.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.syncsort.mongo.mongographql.service.GraphQLService;
import graphql.ExecutionResult;
import graphql.GraphQLError;

/**
 * 
 * @author SusmitSarkar
 *
 */
@RequestMapping("/v1/employee")
@RestController
public class EmployeeController {

	@Autowired
	GraphQLService graphQLService;

	/*
	 * Query End-point to get data from Mongo-db
	 * http://localhost:8080/v1/employee/getEmployees
	 * query{
			allEmployees {
				employeeId,
				name,
				age,
				address
			}
			employee (employeeId : "RA14BA") {
				employeeId,
				name,
				age,
				address
			}
		}
		mutation{ addEmployee(name: "Google", age: 25, address: "Bangalore") }
		mutation{ deleteEmployee(employeeId: "SU48BA") }
	 */
	@PostMapping(path = "/allOperations", produces = "application/json")
	public ResponseEntity<Object> allEmployeeOperations(@RequestBody String query) {
		ExecutionResult execute = graphQLService.getGraphQL().execute(query);
		if(execute.getErrors().size()>0) 
			return new ResponseEntity<Object>(execute.getErrors().stream().map(error -> {
				return createErrorResponse(error);
			}), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(execute, HttpStatus.OK);
	}

	private Map<String, Object> createErrorResponse(GraphQLError error) {
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("errorType", error.getErrorType());
		errorMap.put("message", error.getMessage());
		return errorMap;
	}
}
