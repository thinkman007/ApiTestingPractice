import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoginAPITest {

    private String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:3000";
    }

    @Test
    public void validLoginTest() {

//        String requestBody = """
//                {
//                    "username":"admin",
//                    "password":"admin123"
//                }
//                """;

        LoginRequest request = new LoginRequest("admin", "admin123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .response();
        // Print response
        response.prettyPrint();

        // Validate response message
        Assert.assertEquals(response.jsonPath().getString("message"),
                "Login Successful");

        // Extract token
        token = response.jsonPath().getString("token");

        // Validate token is present
        Assert.assertNotNull(token);
        Assert.assertFalse(token.isEmpty());

        System.out.println("JWT Token : " + token);
    }


}