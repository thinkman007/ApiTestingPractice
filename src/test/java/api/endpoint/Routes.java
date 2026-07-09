package api.endpoint;

/*
Swagger URI --> https://petstore.swagger.io

Create user(Post) : https://petstore.swagger.io/v2/user
Get user (Get): https://petstore.swagger.io/v2/user/{username}
Update user (Put) : https://petstore.swagger.io/v2/user/{username}
Delete user (Delete) : https://petstore.swagger.io/v2/user/{username}

*/

public class Routes {
    public static String baseUrl="https://petstore.swagger.io/v2";
    //user module
    public static  String postUrl=baseUrl + "/user";
    public static String getUrl=baseUrl+"/user/{username}";
    public  static  String putUrl= baseUrl + "/user/{username}";
    public static String deleteUrl = baseUrl + "/user/{username}";
}
