package api.endpoint;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

//https://petstore.swagger.io/#/user/createUsersWithListInput

public class UserEndpoints2 {
    static ResourceBundle getUrl(){
        ResourceBundle resb=ResourceBundle.getBundle("Routes");
        return resb;
    }

    public static Response createUser(User payload){
        String post_url= getUrl().getString("post_url");
        Response resp= given()
                         .contentType(ContentType.JSON)
                         .accept(ContentType.JSON)
                         .body(payload)
                        .when()
                         .post(post_url);
        return resp;
    }

    public static  Response readUser(String userName){
        String get_url= getUrl().getString("get_url");
        Response resp= given()
                .pathParams("username",userName)
                .when()
                .get(get_url);

        return resp;
    }

    public static Response updateUser(User payload, String userName){
        String put_url= getUrl().getString("put_url");
        Response resp= given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParams("username",userName)
                .body(payload)
                .when()
                .put(put_url);
        return resp;
    }

    public static Response deleteUser(String userName){
        String delete_url= getUrl().getString("put_url");
        Response resp= given()
                            .pathParams("username",userName)
                        .when()
                            .get(delete_url);
        return resp;
    }
}
