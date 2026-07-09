package api.testcases;

import api.endpoint.UserEndpoints;
import api.payload.User;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidation {
    Faker faker;
    User userpayload;

    public Logger logger;

    Response response;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userpayload = new User();

        userpayload.setId(faker.idNumber().hashCode());
        userpayload.setUsername(faker.name().username());
        userpayload.setFirstName(faker.name().firstName());
        userpayload.setLastName(faker.name().lastName());
        userpayload.setEmail(faker.internet().safeEmailAddress());
        userpayload.setPassword(faker.internet().password(5, 10));
        userpayload.setPhone(faker.phoneNumber().cellPhone());

        logger = LogManager.getLogger(this.getClass());

    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("********creating user********");
        Response resp = UserEndpoints.createUser(userpayload);
        resp.then().log().all();

        Assert.assertEquals(resp.getStatusCode(), 200);

        logger.info("********user created successfully ********");
    }

    @Test(priority = 2)
    public void testGetUserByName() {
        logger.info("********get user details********");
        response = UserEndpoints.readUser(this.userpayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);

        logger.info("********received user details********");
    }

    @Test(priority = 3)
    public void validateSchema(){
        logger.info("********validation user Schema********");
        response.then()
                .statusCode(200)
                .log().body()
                .body(matchesJsonSchemaInClasspath("schemas/UserSchema.json"));
        logger.info("********user Schema validated successfully********");
    }
}

