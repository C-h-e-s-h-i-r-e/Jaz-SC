package pl.edu.pjwstk.jaz.JazZad4Tests;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.LoginRequest;
import pl.edu.pjwstk.jaz.RegisterRequest;
import pl.edu.pjwstk.jaz.Requests.BranchNameRequest;
import pl.edu.pjwstk.jaz.Requests.BranchRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@IntegrationTest
public class BranchTests {

    @BeforeClass
    public static void beforeClass() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(new RegisterRequest("admin", "admin"))
                .when()
                .post("/api/register")
                .thenReturn();
        given()
                .contentType(ContentType.JSON)
                .body(new RegisterRequest("user", "user"))
                .when()
                .post("/api/register")
                .thenReturn();
    }

    @Test
    public void should_respond_200_to_admin_add_branch() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch"))
                .post("/api/addBranch")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_401_to_add_branch_with_name_that_already_exists() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch"))
                .post("/api/addBranch")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch"))
                .post("/api/addBranch")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_403_to_user_add_branch() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch"))
                .post("/api/addBranch")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_403_to_none_add_branch() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch"))
                .post("/api/addBranch")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_200_to_admin_edit_branch() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchNameRequest("branch", "branch2"))
                .post("/api/editBranch")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_401_to_edit_branch_that_dont_exists() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchNameRequest("branch3", "branch2"))
                .post("/api/editBranch")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_403_to_user_edit_branch() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchNameRequest("branch2", "branch"))
                .post("/api/editBranch")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_403_to_none_edit_branch() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new BranchNameRequest("branch2", "branch"))
                .post("/api/editBranch")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_200_to_admin_list_AllBranches() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch5"))
                .post("/api/addBranch")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch6"))
                .post("/api/addBranch")
                .then();
        given()
                .cookies(response.cookies())
                .get("/api/listAllBranches")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_user_list_AllBranches() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch55"))
                .post("/api/addBranch")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch65"))
                .post("/api/addBranch")
                .then();
        var response2 = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response2.cookies())
                .get("/api/listAllBranches")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_403_to_none_list_AllBranches() {
        var response = given()
                .get("/api/listAllBranches")
                .then()
                .statusCode(403);
    }


}