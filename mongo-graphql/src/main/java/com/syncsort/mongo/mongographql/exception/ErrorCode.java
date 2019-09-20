package com.syncsort.mongo.mongographql.exception;

public enum ErrorCode {
	
	BAD_REQUEST(400);

	private final int statusCode;

	ErrorCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
