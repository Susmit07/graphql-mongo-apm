package com.syncsort.mongo.mongographql.exception;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.language.SourceLocation;

public class EmployeeNotFoundException extends RuntimeException implements GraphQlExceptionWithErrorStatusCode{

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String employeeID;
	
	public EmployeeNotFoundException(int errorCode, String employeeID) {
		this.errorCode=errorCode;
		this.employeeID = employeeID;
	}

	@Override
	public String getMessage() {
		return "The employee with ID: " + employeeID + " is not registered, please check or create a new entry";
	}

	@Override
	public int getCode() {
		return errorCode;
	}

	@Override
	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.DataFetchingException;
	}

}
