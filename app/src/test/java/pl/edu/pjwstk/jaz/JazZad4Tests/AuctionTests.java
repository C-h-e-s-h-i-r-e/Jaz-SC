package pl.edu.pjwstk.jaz.JazZad4Tests;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.LoginRequest;
import pl.edu.pjwstk.jaz.RegisterRequest;
import pl.edu.pjwstk.jaz.Requests.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AuctionTests {

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
        given()
                .contentType(ContentType.JSON)
                .body(new RegisterRequest("user2", "user2"))
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
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category"))
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new BranchRequest("branch","category2"))
                .post("/api/addCategory")
                .thenReturn();
    }

    @Test
    public void should_respond_200_to_admin_add_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title_admin4",100, "category2", photos,params))
                .post("/api/addAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_401_to_admin_add_Auction_to_none_existing_Category() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title5_admin",100, "category7", photos,params))
                .post("/api/addAuction")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_200_to_user_add_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title5_user",100, "category", photos,params))
                .post("/api/addAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_admin_edit_own_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title3_admin",100, "category2", photos,params))
                .post("/api/addAuction")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionNameRequest("description2","title2_admin","title3_admin", 1002,"category2",photos,params))
                .post("/api/editAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_user_edit_own_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title_user",100, "category", photos,params))
                .post("/api/addAuction")
                .then();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionNameRequest("description2","title2_user","title_user", 1002,"category2",photos,params))
                .post("/api/editAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_admin_edit_user_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title_user",100, "category2", photos,params))
                .post("/api/addAuction")
                .then();
        var response2 = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response2.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionNameRequest("description2","title_admin","title_user", 1002,"category2",photos,params))
                .post("/api/editAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_admin_edit_user2_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user2", "user2"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title_user2",100, "category2", photos,params))
                .post("/api/addAuction")
                .then();
        var response2 = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response2.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionNameRequest("description2","title_admin","title_user2", 1002,"category2",photos,params))
                .post("/api/editAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_401_to_user_edit_user2_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user2", "user2"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title_user2",100, "category2", photos,params))
                .post("/api/addAuction")
                .then();
        var response2 = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response2.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionNameRequest("description2","title_user","title_user2", 1002,"category2",photos,params))
                .post("/api/editAuction")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_401_to_user_edit_admin_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title3_admin",100, "category2", photos,params))
                .post("/api/addAuction")
                .then();
        var response2 = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response2.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionNameRequest("description2","title_user","title3_admin", 1002,"category2",photos,params))
                .post("/api/editAuction")
                .then()
                .statusCode(401);
    }

    @Test
    public void should_respond_200_to_user_show_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title8_user",100, "category", photos,params))
                .post("/api/addAuction")
                .then();
        given()
                .cookies(response.cookies())
                .param("title", "title8_user")
                .get("/api/showAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_admin_show_Auction() {
        List<String> photos = new ArrayList<>();
        photos.add("photo1");
        photos.add("photo2");

        List<String> params = new ArrayList<>();
        params.add("para1");
        params.add("para2");

        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .contentType(ContentType.JSON)
                .body(new AuctionRequest("description","title7_admin",100, "category", photos,params))
                .post("/api/addAuction")
                .then();
        given()
                .cookies(response.cookies())
                .param("title", "title7_admin")
                .get("/api/showAuction")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_403_to_none_show_Auction() {
        given()
                .param("title", "title_user")
                .get("/api/showAuction")
                .then()
                .statusCode(403);
    }

    @Test
    public void should_respond_200_to_admin_list_AllAuctions() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/listAllAuctions")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_200_to_user_list_AllAuctions() {
        var response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("user", "user"))
                .post("/api/login")
                .thenReturn();
        given()
                .cookies(response.cookies())
                .get("/api/listAllAuctions")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_respond_403_to_none_list_AllAuctions() {
        given()
                .get("/api/listAllAuctions")
                .then()
                .statusCode(403);
    }




}