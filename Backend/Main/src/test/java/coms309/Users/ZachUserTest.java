package coms309.Users;

import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured.*;
 import       io.restassured.matcher.RestAssuredMatchers.*;
   import     org.hamcrest.Matchers.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ZachUserTest {
    @LocalServerPort
    int port;
    int testuId;
    String testUser;
    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void loginTest() {
        // Building JSON using a Map for better structure and readability
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "bossf");
        credentials.put("password", "123"); // Assuming password should be a string

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials) // using the map as the body
                .when()
                .post("/userCheck");

        int statusCode = response.statusCode();
        Assertions.assertEquals(200, statusCode, "Expected HTTP status 200");

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            Assertions.assertEquals(1, returnObj.getInt("userID"), "Unexpected user ID");
            Assertions.assertEquals("bossf", returnObj.getString("username"), "Unexpected username");
            testuId =returnObj.getInt("userID");
            // Uncomment and adjust this line if the name assertion is required:
            // Assertions.assertEquals("Caleb Pin Test", returnObj.getString("name"), "Unexpected name");

        } catch (JSONException e) {
            Assertions.fail("JSON parsing failed: " + e.getMessage());
        }
    }
    @Test
    public void grabUserThroughID() {
        Response response = RestAssured.given().
                when().
                get("/users/"+ 1);

        int statusCode = response.statusCode();
        Assertions.assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            Assertions.assertEquals("bossf", returnObj.get("username"));
            //Assertions.assertEquals("Caleb Pin Test", returnObj.get("name"));
            testUser = returnObj.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
            Assertions.fail("JSON parsing failed: " + e.getMessage());
        }
    }
    @Test
    public void UserPerms() {// Building JSON using a Map for better structure and readability
    Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "bossf");
        credentials.put("promotion", 2); // Assuming password should be a string

    Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(credentials) // using the map as the body
            .when()
            .put("/userPerms");

    int statusCode = response.statusCode();
        Assertions.assertEquals(200, statusCode, "Expected HTTP status 200");

    String returnString = response.getBody().asString();
        try {
        JSONObject returnObj = new JSONObject(returnString);
        Assertions.assertEquals(1, returnObj.getInt("uId"), "Unexpected user ID");
        Assertions.assertEquals("bossf", returnObj.getString("username"), "Unexpected username");
        Assertions.assertEquals(2, returnObj.getInt("permissions"), "Unexpected perms");
        // Uncomment and adjust this line if the name assertion is required:
        // Assertions.assertEquals("Caleb Pin Test", returnObj.getString("name"), "Unexpected name");

    } catch (JSONException e) {
        Assertions.fail("JSON parsing failed: " + e.getMessage());
    }

    }

    @Test
    public void UserSetScore() {// Building JSON using a Map for better structure and readability
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", "bossf");
        credentials.put("score", 1000); // Assuming password should be a string

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials) // using the map as the body
                .when()
                .put("/setScore");

        int statusCode = response.statusCode();
        Assertions.assertEquals(200, statusCode, "Expected HTTP status 200");

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            Assertions.assertEquals(1, returnObj.getInt("uId"), "Unexpected user ID");
            Assertions.assertEquals("bossf", returnObj.getString("username"), "Unexpected username");
            Assertions.assertEquals(2, returnObj.getInt("permissions"), "Unexpected perms");
            Assertions.assertEquals(1000, returnObj.getInt("score"), "Unexpected score");
            // Uncomment and adjust this line if the name assertion is required:
            // Assertions.assertEquals("Caleb Pin Test", returnObj.getString("name"), "Unexpected name");


        } catch (JSONException e) {
            Assertions.fail("JSON parsing failed: " + e.getMessage());
        }

    }
    @Test
    public void statsTest() {
        Response response = RestAssured.given().
                when().
                get("/Statistics/"+ 1);

        int statusCode = response.statusCode();
        Assertions.assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            Assertions.assertEquals(7, returnObj.get("id"));
            //Assertions.assertEquals("Caleb Pin Test", returnObj.get("name"));
            //testUser = returnObj.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
            Assertions.fail("JSON parsing failed: " + e.getMessage());
        }
    }
    @Test
    public void friendTest() {// Building JSON using a Map for better structure and readability
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("curUsername", "stinky");
        credentials.put("friendUsername", "poopy"); // Assuming password should be a string

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(credentials) // using the map as the body
                .when()
                .post("/addFriend");

        int statusCode = response.statusCode();
        Assertions.assertEquals(200, statusCode, "Expected HTTP status 200");

        String returnString = response.getBody().asString();
        try {
            JSONObject returnObj = new JSONObject(returnString);
            Assertions.assertEquals("stinky", returnObj.getString("curUsername"), "Unexpected user");
            Assertions.assertEquals("poopy", returnObj.getString("friendUsername"), "Unexpected user");

            // Uncomment and adjust this line if the name assertion is required:
            // Assertions.assertEquals("Caleb Pin Test", returnObj.getString("name"), "Unexpected name");


        } catch (JSONException e) {
            Assertions.fail("JSON parsing failed: " + e.getMessage());
        }

    }
}
