import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.UserModel;

public class Test_POST {

    @Test(testName = "Execute POST api/users", suiteName = "post")
    public void test_1_post() {

        JSONObject request = new JSONObject();
        request.put("name", "morpheus");
        request.put("job", "leader");
        System.out.println(request);
        System.out.println(request.toJSONString());

        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post(EndPoints.API_USER);
        response.then().log().status().log().body();
        String json = response.then().extract().body().asString();
        UserModel responseObjects = new Gson().fromJson(json, UserModel.class);
        int code = response.statusCode();
        Assert.assertEquals(HttpStatus.SC_CREATED, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
        Assert.assertNotNull(responseObjects, ErrorMessages.UNSUCCESSFUL_MAPPING);
        Assert.assertEquals(responseObjects.getName(), "morpheus");
        Assert.assertEquals(responseObjects.getJob(), "leader");

    }
}
