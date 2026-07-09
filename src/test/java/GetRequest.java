import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class GetRequest {
    Response res;
    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "http://localhost:3000";
    }

    @Test
    public void sampleGetRequest() {
        res = given()
                .when()
                .get("/users");

        System.out.println("Status code is :" + res.statusCode());
        System.out.println("response body is....");
        System.out.println(res.getBody().asPrettyString());
        System.out.println(res.getHeaders());

    }

    @Test
    public void responseValidation() {
        given()
                .when()
                .get("/users/1")
                .then()
                    .statusCode(200)
                    .body("id", equalTo(1))
                    .body("email",containsString("john.doe"))
                    .body("email",equalTo("john.doe@example.com"))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .header("Content-Type", containsString("application/json"));
    }

    @Test
    public void logAllExample() {
        given()
                .when()
                    .get("http://localhost:3000/users/1")
                .then()
                    .log().all();
    }

    @Test
    public void pathParamPractice(){
        given()
                    .pathParams("id",2)
                .when()
                    .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("name",equalTo("Jane Smith"))
                .log().all();
    }

    @Test
    public void queryParamExample() {

        given()
                .queryParam("page", 1)
                .queryParam("limit", 5)
                .when()
                .get("/users")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test
    public void userNotFound(){
        given()
                    .pathParams("id",10)
                .when()
                    .get("/users/{id}")
                .then()
                    .statusCode(404);
    }

    @Test
    public void validateCookies() {

        Response response =
                given()
                        .when()
                        .get("/users");

        System.out.println(response.getCookies());
    }
    @Test
    public void getAllEmails() {

        Response response =
                given()
                        .when()
                        .get("/users");

        List<String> emails =
                response.jsonPath().getList("email");

        emails.forEach(System.out::println);
    }

    @Test
    public void responseTimeValidation() {

        given()
                .when()
                .get("/users")
                .then()
                .time(lessThan(2000L));
    }

    @Test
    public void validateNames() {

        given()
                .when()
                .get("/users")
                .then()
                .body("name", hasItems("John Doe", "Jane Smith"));
    }

    @Test
    public void validateArraySize() {

        given()
                .when()
                .get("/users")
                .then()
                .body("size()", equalTo(7));
    }

    @Test
    public void extractResponseData() {

        Response response = given()
                .when()
                .get("/users/1");

        String name = response.jsonPath().getString("name");

        System.out.println(name);

        assertEquals(name, "John Doe");
    }
}