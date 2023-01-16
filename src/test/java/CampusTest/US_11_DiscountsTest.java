package CampusTest;

import CampusTest.Classes.US_11_Discounts;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class US_11_DiscountsTest {

    Cookies cookies;
    String discountsID;
    String discountsDescription;
    String discountsCode;
    String discountsPriority;

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


    public String getRandomDescription(){

        return RandomStringUtils.randomAlphabetic(6);
    }

    public String getRandomCode(){

        return RandomStringUtils.randomNumeric(4);
    }

    public String getRandomPriority(){

        return RandomStringUtils.randomNumeric(4);
    }

    @Test
    public void createDiscounts(){

        discountsDescription=getRandomDescription();
        discountsCode=getRandomCode();
        discountsPriority=getRandomPriority();

        US_11_Discounts discounts=new US_11_Discounts();
        discounts.setDescription(discountsDescription);
        discounts.setCode(discountsCode);
        discounts.setPriority(discountsPriority);

        discountsID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(discounts)

                        .when()
                        .post("school-service/api/discounts")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")

                ;

    }
    @Test(dependsOnMethods = "createDiscounts", priority = 1)
    public void createDiscountsNegative(){

        US_11_Discounts discounts=new US_11_Discounts();
        discounts.setDescription(discountsDescription);
        discounts.setCode(discountsCode);
        discounts.setPriority(discountsPriority);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(discounts)

                .when()
                .post("school-service/api/discounts")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already exist"))

                ;

    }
    @Test(dependsOnMethods = "createDiscounts", priority = 2)
    public void updateDiscounts(){

        discountsDescription=getRandomDescription();
        discountsCode=getRandomCode();
        discountsPriority=getRandomPriority();

        US_11_Discounts discounts=new US_11_Discounts();
        discounts.setId(discountsID);
        discounts.setDescription(discountsDescription);
        discounts.setCode(discountsCode);
        discounts.setPriority(discountsPriority);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(discounts)

                .when()
                .put("school-service/api/discounts")

                .then()
                .log().body()
                .statusCode(200)
                .body("description", equalTo(discountsDescription))

                ;

    }

    @Test(dependsOnMethods = "updateDiscounts")
    public void deleteDiscountsById(){

        given()
                .cookies(cookies)
                .pathParam("discountsID", discountsID)
                .log().uri()

                .when()
                .delete("school-service/api/discounts/{discountsID}")

                .then()
                .log().body()
                .statusCode(200)

                ;

    }
    @Test(dependsOnMethods = "deleteDiscountsById")
    public void deleteDiscountsByIdNegative(){
        given()
                .cookies(cookies)
                .pathParam("discountsID", discountsID)
                .log().uri()

                .when()
                .delete("school-service/api/discounts/{discountsID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Discount not found"))

                ;



    }


}
