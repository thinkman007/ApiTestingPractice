import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginAndGetUsersTest{

    private String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:3000";
    }

    @Test(priority = 1)
    public void login() {

        String requestBody = """
                {
                    "username":"admin",
                    "password":"admin123"
                }
                """;

        token = given()
                .contentType(ContentType.JSON)
                .body(requestBody)

                .when()
                .post("/login")

                .then()
                .statusCode(200)
                .extract()
                .path("token");

        Assert.assertNotNull(token);

        System.out.println("Token : " + token);
    }

    @Test(priority = 2)
    public void getAllUsers() {

        Response response = given()
                .header("Authorization", "Bearer " + token)

                .when()
                .get("/users")

                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .extract()
                .response();

        response.prettyPrint();
    }

    @Test(priority = 3)
    public void createUser() {

        String requestBody = """
            {
                "id": 5,
                "username": "john",
                "role": "tester"
            }
            """;

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(requestBody)

                .when()
                .post("/users")

                .then()
                .statusCode(201)
                .body("message", equalTo("User created successfully"))
                .body("user.username", equalTo("john"))
                .body("user.role", equalTo("tester"))
                .extract()
                .response();

        response.prettyPrint();
    }

    @Test(priority = 4)
    public void getAllUsersAfterUpdate() {

        Response response = given()
                .header("Authorization", "Bearer " + token)

                .when()
                .get("/users")

                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .extract()
                .response();

        response.prettyPrint();
    }

}