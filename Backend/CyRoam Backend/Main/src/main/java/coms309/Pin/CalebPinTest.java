package coms309.Pin;

import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CalebPinTest {
    @LocalServerPort
    int port;

    int testPinId;
    int testPinIdTwo;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createTest() {
        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body("{" +
                        "    \"x\":101," +
                        "    \"y\":201," +
                        "    \"name\" : \"Caleb Pin Test\"" +
                        "}").
                when().
                post("/pins");

        int statusCode = response.statusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals(101, returnObj.get("x"));
            assertEquals(201, returnObj.get("y"));
            assertEquals("Caleb Pin Test", returnObj.get("name"));
            testPinId = returnObj.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateSplashTest() {
        Response response = RestAssured.given().
                contentType(ContentType.TEXT).
                body("Example splash").
                when().
                put("/pins/1/splash");

        int statusCode = response.statusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals("Example splash", returnString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() {
        Response response = RestAssured.given().
                when().
                delete("/pins/" + testPinId);

        Response response2 = RestAssured.given().
                when().
                get("/pins/" + testPinId);

        int statusCode = response.statusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        String return2String = response2.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals(testPinId, returnObj.getInt("id"));
            assertEquals("", return2String);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteMultiTest() {
        Response testPinOne = RestAssured.given().
                contentType(ContentType.JSON).
                body("{" +
                        "    \"x\":101," +
                        "    \"y\":201," +
                        "    \"name\" : \"Caleb Pin Test\"" +
                        "}").
                when().
                post("/pins");
        String testPinOneResponse = testPinOne.getBody().asString();
        try {
        JSONArray returnPinArr = new JSONArray(testPinOneResponse);
        JSONObject returnObjPin = returnPinArr.getJSONObject(returnPinArr.length()-1);
        testPinId = returnObjPin.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response testPinTwo = RestAssured.given().
                contentType(ContentType.JSON).
                body("{" +
                        "    \"x\":101," +
                        "    \"y\":201," +
                        "    \"name\" : \"Caleb Pin Test\"" +
                        "}").
                when().
                post("/pins");
        String testPinTwoResponse = testPinTwo.getBody().asString();
        try {
        JSONArray returnArrPinTwo = new JSONArray(testPinTwoResponse);
        JSONObject returnObjPinTwo = returnArrPinTwo.getJSONObject(returnArrPinTwo.length()-1);
        testPinIdTwo = returnObjPinTwo.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body("[" + testPinId + ", " + testPinIdTwo + "]").
                when().
                delete("/pins");

        Response response2 = RestAssured.given().
                when().
                get("/pins/" + testPinId);

        int statusCode = response.statusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        String return2String = response2.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
            assertEquals(testPinId, returnObj.getInt("id"));
            assertEquals("", return2String);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
