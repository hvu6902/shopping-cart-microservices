package com.example.microservices.product;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;
import net.bytebuddy.asm.Advice.Local;

// @Import(TestcontainersConfiguration.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
	
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	static {
		mongoDBContainer.start();

	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
					"name": "Product 1",
					"description": "Description for Product 1",
					"price": 100.00
				}
				""";

		RestAssured.given()
			.contentType("application/json")
			.body(requestBody)
			.when()
			.post("/api/product")
			.then()
			.body("id", Matchers.notNullValue())
			.body("name", Matchers.equalTo("Product 1"))
			.body("description", Matchers.equalTo("Description for Product 1"))
			.body("price", Matchers.equalTo(100.00f));

			
	}

}
