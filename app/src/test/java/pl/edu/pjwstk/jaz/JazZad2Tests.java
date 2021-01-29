package pl.edu.pjwstk.jaz;


import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class JazZad2Tests {

    @BeforeClass
    public static void beforeClass() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(new RegisterRequest("s18855", "password"))
                .when()
                .post("/api/register")
                .thenReturn();
        given()
                .contentType(ContentType.JSON)
                .body(new RegisterRequest("s18855v2", "password"))
                .when()
                .post("/api/register")
                .thenReturn();
    }

    // test daje 200 bo dane logowania sa poprawne
    @Test
    public void should_respond_200_to_login_request(){
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("s18855", "password"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/auth0/article/1")
                .then()
                .statusCode(200);
    }

    // test daje 401 bo dane logowania sa NIEpoprawne
    @Test
    public void should_respond_401_to_login_request() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("s18855", "wrongpassword"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/article/1")
                .then()
                .statusCode(403);

    }

    // test daje 401 bo Get stara sie dostac do auth0 bez uzytkownika
    @Test
    public void should_respond_401_to_auth0(){
        given()
                .get("/api/auth0/article/1")
                .then()
                .statusCode(401);
    }

    // test daje 200 bo admin ma role admin i moze dostac sie do /admin
    @Test
    public void should_respond_200_to_role_admin() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("s18855", "password"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/admin")
                .then()
                .statusCode(200);

    }

    // test daje 403 bo user ma role user i nie moze dostac sie do /admin
    @Test
    public void should_respond_403_to_role_user() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("s18855v2", "password"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/admin")
                .then()
                .statusCode(403);

    }

    // test daje 200 bo user ma role user i moze dostac sie do /user
    @Test
    public void should_respond_200_to_role_user() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("s18855v2", "password"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/user")
                .then()
                .statusCode(200);

    }

    // test daje 200 bo admin ma role admin i moze dostac sie do /user
    @Test
    public void should_respond_200_to_role_admin_v2() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("s18855", "password"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/user")
                .then()
                .statusCode(200);

    }

    // test daje 200 bo jest pierwszym uzytkonikiem z dana nazwa w Mapie
    @Test
    public void should_respond_200_to_register_request() {
        given()
                .contentType(ContentType.JSON)
                .body(new RegisterRequest("login", "password"))
                .when()
                .post("/api/register")
                .then()
                .statusCode(200);

    }

}
