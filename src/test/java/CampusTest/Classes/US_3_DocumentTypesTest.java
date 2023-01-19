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


public class US_3_DocumentTypesTest {
    Cookies cookies;
    String documentsId;
    String name;
    String[] stage={"EMPLOYMENT"};
    String description;

    US_3_DocumentTypes documentTypes;


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
                        //.log().body()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }
    public String getRandomName() {  return RandomStringUtils.randomAlphabetic(6); }
    public String getRandomdescription() {  return RandomStringUtils.randomAlphabetic(10); }

    @Test
    public void addDocumentTypes(){
        documentTypes=new US_3_DocumentTypes("6390f3207a3bcb6a7ac977f9",stage);
        name=getRandomName();
        description=getRandomdescription();


        documentTypes.setName(name);
        documentTypes.setDescription(description);

        documentsId=  given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(documentTypes)
                .when()
                .post("school-service/api/attachments/create")
                .then()
                .log().body()
                .statusCode(201)
              .extract().jsonPath().getString("id");
    }

    @Test(dependsOnMethods = "addDocumentTypes")
    public void addDocumentTypesNegative() {
        documentTypes.setName(name);
        documentTypes.setAttachmentStages(stage);
        documentTypes.setDescription(description);
        documentTypes.setId(documentsId);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(documentTypes)
                .when()
                .post("school-service/api/attachments/create")
                .then()
                .log().body()
                .statusCode(400)
               ;

    }


    @Test(dependsOnMethods = "addDocumentTypes")
    public void updateDocumentTypes() {
        name = getRandomName();
        description = getRandomdescription();
        documentTypes.setId(documentsId);

        documentTypes.setName(name);
        documentTypes.setDescription(description);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(documentTypes)

                .when()
                .put("school-service/api/attachments")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(name))
                .body("id", equalTo(documentsId));
    }



    @Test(dependsOnMethods = "updateDocumentTypes")
    public void DeleteDocumentTypes() {
        given()
                .pathParam("documentsID", documentsId)
                .cookies(cookies)
                .log().uri()
                .when()
                .delete("school-service/api/attachments/{documentsID}")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "DeleteDocumentTypes")
    public void DeleteDocumentTypesNegative() {
        given()
                .pathParam("documentsID", documentsId)
                .cookies(cookies)
                .log().uri()
                .when()
                .delete("school-service/api/attachments/{documentsID}")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Attachment Type not found"));
    }



}
