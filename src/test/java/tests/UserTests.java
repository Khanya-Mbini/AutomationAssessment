package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class UserTests {

    @Test
    public void GetSingleUser() {
        Response response = RestAssured.when().get("https://jsonplaceholder.typicode.com/users/1");
        response.then().assertThat().statusCode(200);
        response.then().body("name", is("Leanne Graham"));
    }

    @Test
    public void GetAllUsers() {
        RestAssured.get("https://jsonplaceholder.typicode.com/users").then().assertThat()
                .body("size()", is(10));
    }

    @Test
    public void CreateNewUser() {
        Map<String, String> User = new HashMap<>();
        User.put("name", "Sizwe Bee");
        User.put("username", "SizeBee");
        User.put("email", "sizbee@webmail.com");

        given()
                .contentType("application/json")
                .body(User)
                .when().post("https://jsonplaceholder.typicode.com/users").then()
                .statusCode(201);
    }

}
