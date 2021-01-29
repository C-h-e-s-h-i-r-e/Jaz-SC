package pl.edu.pjwstk.jaz.JazZad4Tests;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.Entities.BranchEntity;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.LoginRequest;
import pl.edu.pjwstk.jaz.RegisterRequest;
import pl.edu.pjwstk.jaz.Requests.BranchNameRequest;
import pl.edu.pjwstk.jaz.Requests.BranchRequest;
import pl.edu.pjwstk.jaz.Requests.CategoryNameRequest;
import pl.edu.pjwstk.jaz.Requests.CategoryRequest;
import pl.edu.pjwstk.jaz.Services.SingleCategory;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class CategoryTests {

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
    }

    @Test
    public void should_respond_200_to_add_category() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category1"))
                .post("/api/addCategory")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_401_to_add_category_to_branch_that_dont_exist() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch5","category"))
                .post("/api/addCategory")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_401_to_add_category_with_name_that_already_exists() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category"))
                .post("/api/addCategory")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category"))
                .post("/api/addCategory")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_403_to_add_category() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category"))
                .post("/api/addCategory")
                .then()
                .statusCode(403);
    }
    @Test
    public void should_respond_403_to_none_add_category() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category"))
                .post("/api/addCategory")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_401_to_edit_category_that_dont_exist() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new CategoryNameRequest("category", "category2"))
                .post("/api/editCategory")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_403_to_edit_category() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new CategoryNameRequest("category", "category2"))
                .post("/api/editCategory")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_403_to_none_edit_category() {
        var response = given()
                .contentType(ContentType.JSON)
                .body(new CategoryNameRequest("category", "category2"))
                .post("/api/editCategory")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_200_to_admin_list_AllCategories() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category8"))
                .post("/api/addCategory")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category9"))
                .post("/api/addCategory")
                .then();
        given()
                .cookies(response.cookies())
                .get("/api/listAllCategories")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_user_list_AllCategories() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category5"))
                .post("/api/addCategory")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category6"))
                .post("/api/addCategory")
                .then();
        var response2 = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response2.cookies())
                .get("/api/listAllCategories")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_403_to_none_list_AllBranches() {
        var response = given()
                .get("/api/listAllCategories")
                .then()
                .statusCode(403);
    }
}