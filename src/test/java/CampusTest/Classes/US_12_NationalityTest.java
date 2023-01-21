package CampusTest.Classes;

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

public class US_12_NationalityTest {

    Cookies cookies;
    String nationalityID;
    String nationalityName;

    US_12_Nationality nationality = new US_12_Nationality();


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

    @Test
    public void createNationality() {

        nationalityName = getRandomName();

        nationality.setName(nationalityName);

        nationalityID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(nationality)

                        .when()
                        .post("school-service/api/nationality")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }

    @Test(dependsOnMethods = "createNationality", priority = 1)
    public void createNationalityNegative() {
        nationality.setName(nationalityName);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(nationality)

                .when()
                .post("school-service/api/nationality")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already exists"))
        ;
    }

    @Test(dependsOnMethods = "createNationality", priority = 2)
    public void updateNationality() {
        nationalityName = getRandomName();
        nationality.setId(nationalityID);
        nationality.setName(nationalityName);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(nationality)

                .when()
                .put("school-service/api/nationality")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(nationalityName))
        ;
    }

    @Test(dependsOnMethods = "updateNationality")
    public void deleteNationalityById() {

        given()
                .cookies(cookies)
                .pathParam("nationalityID", nationalityID)
                .log().uri()

                .when()
                .delete("school-service/api/nationality/{nationalityID}")

                .then()
                .log().body()
                .statusCode(200)
        ;

    }

    @Test(dependsOnMethods = "deleteNationalityById")
    public void deleteNationalityByIdNegative() {

        given()
                .cookies(cookies)
                .pathParam("nationalityID", nationalityID)
                .log().uri()

                .when()
                .delete("school-service/api/nationality/{nationalityID}")

                .then()
                .log().body()
                .statusCode(400)
               .body("message", equalTo("Nationality not  found"))
        ;
    }
}
