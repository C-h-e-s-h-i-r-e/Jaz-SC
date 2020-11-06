package pl.edu.pjwstk.jaz.average;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AverageControllerIT {
    @Test
    public void should_calculate_simple_average() {
        var test1 = given()
                .param("numbers", "1,2,3,4")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average : 2.5"));
    }

    @Test
    public void should_calculate_without_zero() {
        var test1 = given()
                .param("numbers", "4,3,1,7,5")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average : 4"));
    }

    @Test
    public void should_calculate_simple_average2() {
        var test1 = given()
                .param("numbers", "2,1")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average : 1.5"));
    }

    @Test
    public void should_calculate_average_to2decilam_places() {
        var test1 = given()
                .param("numbers", "2,1,1")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average : 1.33"));
    }

    @Test
    public void should_ask_for_params() {
        var test1 = given()
                .param("numbers", "")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Put parameters."));
    }

}
