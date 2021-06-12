package com.vv.personal;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExpenseSimulatorControllerTest {

    @Test
    public void testConfigsEndPoint() {
        given()
                .when().get("/controller/configs")
                .then()
                .statusCode(200)
                .body(is(String.format("Expense Sim config :: banks file loc: %s, transactions files loc: %s",
                        "landing/banks.active.json", "landing/transactions.active.json")));
    }

}