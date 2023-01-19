package CampusTest.Classes;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class US_4_FieldsTest {
    Cookies cookies;
    String fieldsID;
    String fieldsName;
    String fieldsCode;
    String schoolId;
    US_4_Fields fields=new US_4_Fields();

    @BeforeClass
    public void loginCampus()
    {
        baseURI="https://test.mersys.io/";

        Map<String,String> credential=new HashMap<>();
        credential.put("username", "turkeyts");
        credential.put("password", "TechnoStudy123");
        credential.put("rememberMe", "true");

        cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;

    }

    public String getRandomName() {  return RandomStringUtils.randomAlphabetic(8); }
    public String getRandomCode() {  return RandomStringUtils.randomAlphabetic(4); }


    @Test()
    public void addFields()
    {

        fieldsName=getRandomName();
        fieldsCode=getRandomCode();
        fields.setName(fieldsName);
        fields.setCode(fieldsCode);
        fields.setSchoolId(schoolId);
        fields.setSchoolId("6390f3207a3bcb6a7ac977f9");

        fieldsID=  given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(fields)

                .when()
                .post("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id");
    }

    @Test(dependsOnMethods = "addFields")
    public void addFieldsNegative()
    {

        fields.setName(fieldsName);
        fields.setCode(fieldsCode);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(fields)

                .when()
                .post("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",containsString("already exists"));

    }
    @Test(dependsOnMethods = "addFieldsNegative")
    public void editFields()
    {
        fieldsName=getRandomName();
        fieldsCode=getRandomCode();

        fields.setId(fieldsID);
        fields.setName(fieldsName);
        fields.setCode(fieldsCode);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(fields)

                .when()
                .put("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(fieldsName))
                .body("id",equalTo(fieldsID));


    }


    @Test(dependsOnMethods = "editFields")
    public void DeleteFields() {
        given()
                .pathParam("fieldsID", fieldsID)
                .cookies(cookies)
                .log().uri()
                .when()
                .delete("school-service/api/entity-field/{fieldsID}")

                .then()
                .log().body()
                .statusCode(204);
    }

    @Test(dependsOnMethods = "DeleteFields")
    public void DeleteFieldsNegative() {
        given()
                .pathParam("fieldsID", fieldsID)
                .cookies(cookies)
                .log().uri()
                .when()
                .delete("school-service/api/entity-field/{fieldsID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("EntityField not found"));
    }


}
