package dev.pgjbz;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(value = PostgresResourceTest.class)
public class ProductControllerTest {

    @Test
    public void testProductsEndpoint() {
        given()
          .when().get("/products")
          .then()
             .statusCode(200)
             .body(is("[{\"id\":1,\"name\":\"Vassoura\",\"price\":10.99}]"));
    }

}