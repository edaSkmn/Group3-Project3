package CampusTest;

import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class US_2_Attestations {

    @BeforeClass
    public void loginCampuss() {

        baseURI = "https://test.mersys.io/";
        Map<String, String> credential = new HashMap<>();
        credential.put("username", "turkeyts");
        credential.put("password", "TechnoStudy123");
        credential.put("rememberMe", "true");
    }

    }
