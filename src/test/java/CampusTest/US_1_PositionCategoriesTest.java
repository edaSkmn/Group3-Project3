package CampusTest;

import CampusTest.Classes.US_1_PositionCategories;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class US_1_PositionCategoriesTest {

    Cookies cookies;
    String positionID;
    String positionName;


    @BeforeClass
    public void loginCampus() {
        baseURI = "https://test.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "turkeyts");
        credential.put("password", "TechnoStudy123");
        credential.put("rememberMe", "true");

        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;

    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3);
    }


    @Test
    public void createPositionCategories() {
        positionName = getRandomName();

        US_1_PositionCategories position=new US_1_PositionCategories();
        position.setName(positionName);


        positionID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(position)

                        .when()
                        .post("school-service/api/position-category")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }

    @Test(dependsOnMethods = "createPositionCategories", priority = 1)
    public void createPositionCategoriesNegative() {

        US_1_PositionCategories position=new US_1_PositionCategories();
        position.setName(positionName);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(position)

                .when()
                .post("school-service/api/position-category")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already exist"))
        ;


    }

    @Test(dependsOnMethods = "createPositionCategories", priority = 2)
    public void updatePositionCategories() {

        positionName=getRandomName();

        US_1_PositionCategories position=new US_1_PositionCategories();
        position.setId(positionID);
        position.setName(positionName);



        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(position)

                .when()
                .put("school-service/api/position-category")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(positionName))

        ;


    }

    @Test(dependsOnMethods = "updatePositionCategories")
    public void deletePositionCategoriesById() {

        given()
                .cookies(cookies)
                .pathParam("positionID", positionID)
                .log().uri()

                .when()
                .delete("school-service/api/position-category/{positionID}")

                .then()
                .log().body()
                .statusCode(204)

        ;


    }

    @Test(dependsOnMethods = "deletePositionCategoriesById")
    public void deletePositionCategoriesByIdNegative() {

        given()
                .cookies(cookies)
                .pathParam("positionID", positionID)
                .log().uri()
                .when()
                .delete("school-service/api/position-category/{positionID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("PositionCategory not  found") )
        ;

    }




}
