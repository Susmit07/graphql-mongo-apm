package com.syncsort.mongo.mongographql.exception;


import graphql.GraphQLError;

public interface GraphQlExceptionWithErrorStatusCode extends GraphQLError{
	int getCode();
}
