package CampusTest;

import CampusTest.Classes.US_1_PositionCategories;
import CampusTest.Classes.US_3_DocumentTypes;
import CampusTest.Classes.US_9_BankAccounts;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class US_9_BankAccountsTest {
    Cookies cookies;
    String bankAccountID;
    String bankAccountName;
    String bankAccountIban;
    String bankAccountIntegrationCode;
    String[] bankAccountCurr={"USD"};

    US_9_BankAccounts bankAccounts;

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
        return RandomStringUtils.randomAlphabetic(5);
    }
    public String getRandomIban() {return RandomStringUtils.randomAlphabetic(8);}
    public String getRandomIntegrationCode()  {return RandomStringUtils.randomNumeric(4);}
    @Test
    public void createBankAccount(){

        bankAccountName=getRandomName();
        bankAccountIban=getRandomIban();
        bankAccountIntegrationCode=getRandomIntegrationCode();

        bankAccounts=new US_9_BankAccounts("6390f3207a3bcb6a7ac977f9",bankAccountCurr);
        bankAccounts.setIban(bankAccountIban);
        bankAccounts.setIntCode(bankAccountIntegrationCode);
        bankAccounts.setName(bankAccountName);

        bankAccountID=
                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(bankAccounts)

                        .when()
                        .post("school-service/api/bank-accounts")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }

    @Test(dependsOnMethods = "createBankAccount", priority = 1)
    public void createBankAccountNegative(){

        bankAccounts.setCurr(bankAccountCurr);
        bankAccounts.setIban(bankAccountIban);
        bankAccounts.setIntCode(bankAccountIntegrationCode);
        bankAccounts.setName(bankAccountName);

        bankAccountID=
                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(bankAccounts)

                        .when()
                        .put("school-service/api/bank-accounts")

                        .then()
                        .log().body()
                        .statusCode(400)
                        .extract().jsonPath().getString("id")
        ;
    }
    @Test(dependsOnMethods = "createBankAccount", priority = 2)
    public void updateBankAccount(){

        bankAccountName=getRandomName();

        bankAccounts.setCurr(bankAccountCurr);
        bankAccounts.setIban(bankAccountIban);
        bankAccounts.setIntCode(bankAccountIntegrationCode);

                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(bankAccounts)

                        .when()
                        .post("school-service/api/bank-accounts")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name", equalTo(bankAccountName))
        ;
    }
    @Test(dependsOnMethods = "updateBankAccount")
    public void deleteBankAccount(){

        given()
                .pathParam("bankAccountID", bankAccountID)
                .cookies(cookies)
                .log().uri()

                .when()
                .post("school-service/api/bank-accounts/{bankAccountID}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }
    @Test(dependsOnMethods = "deleteBankAccount")
    public void deleteBankAccountNegative(){

        given()
                .pathParam("bankAccountID", bankAccountID)
                .cookies(cookies)
                .log().uri()

                .when()
                .post("school-service/api/bank-accounts/{bankAccountID}")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

}
