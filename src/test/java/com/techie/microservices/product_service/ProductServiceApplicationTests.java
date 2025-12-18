package com.techie.microservices.product_service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.mongodb.MongoDBContainer;

//@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

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
	void shouldCreatedProduct() {
        String requestBody = """
                {
                    "name": "iPhone 13",
                    "description": "Latest model of iPhone",
                    "price": 999.99
                }
                """;
        RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).when().post("/api/products")
                .then().statusCode(201)
                .body("name", Matchers.equalTo("iPhone 13"))
                .body("description", Matchers.equalTo("Latest model of iPhone"))
                .body("price", Matchers.equalTo(999.99F));
	}

}
