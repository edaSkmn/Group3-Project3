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

public class US_2_AttestationsTest {

    Cookies cookies;
    String attestationID;
    String attestationName;

    @BeforeClass
    public void loginCampuss() {

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
        return RandomStringUtils.randomAlphabetic(7);
    }


    @Test
    public void createAttestations(){

        attestationName=getRandomName();

        US_1_PositionCategories attestation=new US_1_PositionCategories();
        attestation.setName(attestationName);

        attestationID=
                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(attestation)

                        .when()
                        .post("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }
    @Test(dependsOnMethods = "createAttestations", priority = 1)
    public void createAttestationNegative(){

        US_1_PositionCategories attestation=new US_1_PositionCategories();
        attestation.setName(attestationName);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(attestation)

                .when()
                .post("school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already exist"))
        ;
    }
    @Test(dependsOnMethods = "createAttestations", priority = 2)
    public void updateAttestation(){

        attestationName=getRandomName();

        US_1_PositionCategories attestation=new US_1_PositionCategories();
        attestation.setName(attestationName);;
        attestation.setId(attestationID);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(attestation)

                .when()
                .put("school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(attestationName))
        ;
    }
    @Test(dependsOnMethods = "updateAttestation")
    public void deleteAttestationById(){

        given()
                .cookies(cookies)
                .pathParam("attestationID",attestationID)
                .log().uri()

                .when()
                .delete("school-service/api/attestation/{attestationID}")

                .then()
                .log().body()
                .statusCode(204)
        ;
    }
    @Test(dependsOnMethods = "deleteAttestationById")
    public void deleteAttestationByNegative(){

        given()
                .cookies(cookies)
                .pathParam("attestationID",attestationID)
                .log().uri()

                .when()
                .delete("school-service/api/attestation/{attestationID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("attestation not found"))
        ;
    }
}


