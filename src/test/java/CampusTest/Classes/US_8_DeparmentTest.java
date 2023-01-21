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

public class US_8_DeparmentTest {


    Cookies cookies;
    String departmentID;
    String departmentName;
    String departmentCode;

    US_8_Department department = new US_8_Department();

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
        return RandomStringUtils.randomAlphabetic(12);
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(8);
    }


    @Test
    public void createDepartment() {
        departmentName = getRandomName();
        departmentCode = getRandomCode();

        department.setName(departmentName);
        department.setCode(departmentCode);
        department.setSchool("6390f3207a3bcb6a7ac977f9");

        departmentID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(department)

                        .when()
                        .post("school-service/api/department")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }

    @Test(dependsOnMethods = "createDepartment", priority = 1)
    public void createDepartmentNegative() {

        department.setName(departmentName);
        department.setCode(departmentCode);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(department)

                .when()
                .post("school-service/api/department")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"))

        ;
    }

    @Test(dependsOnMethods = "createDepartment", priority = 2)
    public void updateDepartment() {
        departmentName = getRandomName();
        departmentCode = getRandomCode();

        department.setId(departmentID);
        department.setName(departmentName);
        department.setCode(departmentCode);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(department)

                .when()
                .put("school-service/api/department")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(departmentName))
        ;
    }

    @Test(dependsOnMethods = "updateDepartment")
    public void deleteDepartmentById() {

        given()
                .cookies(cookies)
                .pathParam("departmentID", departmentID)
                .log().uri()

                .when()
                .delete("school-service/api/department/{departmentID}")

                .then()
                .log().body()
                .statusCode(204)
        ;

    }

    @Test(dependsOnMethods = "deleteDepartmentById")
    public void deleteDepartmentByIdNegative() {

        given()
                .cookies(cookies)
                .pathParam("departmentID", departmentID)
                .log().uri()

                .when()
                .delete("school-service/api/department/{departmentID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Department not found"))  //burada 204 veriyor 204 urunun silindigine dair kod onu vermemeli 400 lu kod vermeli
        ;
    }


}


