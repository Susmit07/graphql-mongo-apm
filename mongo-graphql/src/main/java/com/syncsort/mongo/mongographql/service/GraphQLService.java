package com.syncsort.mongo.mongographql.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.syncsort.mongo.mongographql.datafetcher.AllEmployeeDataFetcher;
import com.syncsort.mongo.mongographql.datafetcher.EmployeeByIdDataFetcher;
import com.syncsort.mongo.mongographql.datafetcher.EmployeeDeleteByIdDataFetcher;
import com.syncsort.mongo.mongographql.datafetcher.EmployeeSaveDataFetcher;
import com.syncsort.mongo.mongographql.datafetcher.EmployeeUpdateDataFetcher;
import com.syncsort.mongo.mongographql.repository.EmployeeRepository;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class GraphQLService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Value("classpath:employee.graphqls")
	Resource resource;

	private GraphQL graphQL;

	@Autowired
	private AllEmployeeDataFetcher allEmployeeDataFetcher;

	@Autowired
	private EmployeeByIdDataFetcher employeeByIdDataFetcher;

	@Autowired
	private EmployeeSaveDataFetcher employeeSaveDataFetcher;

	@Autowired
	private EmployeeUpdateDataFetcher employeeUpdateDataFetcher;

	@Autowired
	private EmployeeDeleteByIdDataFetcher employeeDeleteByIdDataFetcher;

	@PostConstruct
	private void loadGraphQLSchema() throws IOException {
		File graphQLSchemaFile = resource.getFile();
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(graphQLSchemaFile);
		RuntimeWiring wiring = buildRuntimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", runTimeTypeWiringForDataFetching -> runTimeTypeWiringForDataFetching
						.dataFetcher("allEmployees", allEmployeeDataFetcher)
						.dataFetcher("employee", employeeByIdDataFetcher))
				.type("Mutation", runTimeTypeWiringForDataPersisting -> runTimeTypeWiringForDataPersisting
						.dataFetcher("addEmployee", employeeSaveDataFetcher))
				.type("Mutation", runTimeTypeWiringForDataUpdate -> runTimeTypeWiringForDataUpdate
						.dataFetcher("updateEmployee", employeeUpdateDataFetcher))
				.type("Mutation", runTimeTypeWiringForDataDelete -> runTimeTypeWiringForDataDelete
						.dataFetcher("deleteEmployee", employeeDeleteByIdDataFetcher))
				.build();
	}

	public GraphQL getGraphQL() {
		return graphQL;
	}
}
