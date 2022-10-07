package utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import static org.hamcrest.Matchers.*;

public class APIValidationUtil {

    public static void validateStatusCode(Response response, int statusCode) throws AssertionError {
        try {
            response.then().statusCode(statusCode);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateAllRecordsHasGivenParameter(Response response, String jsonPath, String fieldName) throws AssertionError {
        try {
            response.then().assertThat().body(jsonPath, everyItem(hasKey(fieldName)));
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage() + "\nfield \"" + fieldName + "\" is not found in one or more records in data array \n\nActual Response body is "+response.asString()+"\n");
        }
    }

    public static void validateAllRecordsHasAValueForGivenParameter(Response response, String jsonPath, String fieldName) throws AssertionError {
        boolean hasValue = true;
        int count = 0;
        try {
            JSONObject rootResponse = new JSONObject(response.getBody().asString());
            JSONArray jsonArray = rootResponse.getJSONArray(jsonPath);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = i;
                if (jsonObject.get(fieldName).equals("") || jsonObject.get(fieldName) == null) {
                    hasValue = false;
                }
            }
            Assert.assertTrue(hasValue);
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage() + "field \"" + fieldName + "\" does not have a value in " + count + " record in data array \n\nActual Response body is "+response.asString()+"\n");
        }
    }

    public static void validateBodyParameterHasValue(Response response, String fieldName, Object value) throws AssertionError {
        try {
            response.then().assertThat().body(fieldName, is(value));
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage());
        }
    }

    public static void validateFieldIsEmpty(Response response, String fieldName) throws AssertionError {
        try {
            response.then().assertThat().body(fieldName, is(empty()));
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage());
        }
    }

    public static void validateFieldIsNotEmpty(Response response, String fieldName) throws AssertionError {
        try {
            response.then().assertThat().body(fieldName, not(empty()));
        } catch (AssertionError e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
