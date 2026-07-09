import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APIchaining {
    int id;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "http://localhost:3000";
    }

    @Test(priority = 1)
    public void postRequest() {
        System.out.println("Priority 1");
        String requestBody = """
                {
                        "id": 101,
                        "name": "John",
                        "email": "john@test.com"
                }
                """;
        id = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .path("id");
    }

    @Test(priority = 2)
    public void getData() {
        System.out.println("Priority 2");
        given()
                .pathParams("id", id)
                .contentType(ContentType.JSON)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("John"))
                .log().all();
    }

    @Test(priority = 3)
    public void deleteUser() {
        System.out.println("Priority 3");
        given()
                .pathParam("id", id)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test(priority = 4)
    public void validateDelete() {
        System.out.println("Priority 4");
        given()
                .pathParam("id", id)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404)
                .log().all();

    }
}
