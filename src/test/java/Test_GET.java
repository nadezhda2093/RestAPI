import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_GET {

    @Test(testName = "Execute GET users?page={numberPage}", suiteName = "get")
    public void test_01() {
        int page = 2;
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("numberPage", page)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.API_USERS);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);


        JsonPath path = response.getBody().jsonPath();
        Assert.assertNotNull(path.getJsonObject("data").toString());
    }

}
