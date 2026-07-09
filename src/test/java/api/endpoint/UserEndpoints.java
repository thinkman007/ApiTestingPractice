package api.endpoint;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

//https://petstore.swagger.io/#/user/createUsersWithListInput

public class UserEndpoints {
    public static Response createUser(User payload){
        Response resp= given()
                         .contentType(ContentType.JSON)
                         .accept(ContentType.JSON)
                         .body(payload)
                        .when()
                         .post(Routes.postUrl);
        return resp;
    }

    public static  Response readUser(String userName){
        Response resp= given()
                .pathParams("username",userName)
                .when()
                .get(Routes.getUrl);

        return resp;
    }

    public static Response updateUser(User payload, String userName){
        Response resp= given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParams("username",userName)
                .body(payload)
                .when()
                .put(Routes.putUrl);
        return resp;
    }

    public static Response deleteUser(String userName){
        Response resp= given()
                            .pathParams("username",userName)
                        .when()
                            .get(Routes.deleteUrl);
        return resp;
    }
}
