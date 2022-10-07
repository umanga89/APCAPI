package steps;

import io.cucumber.java.en.Then;
import org.apache.commons.io.output.WriterOutputStream;
import utils.APIValidationUtil;
import utils.BaseUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.io.PrintStream;
import java.io.StringWriter;

public class CommonSteps extends BaseUtil {

    public CommonSteps() {
        logger = LogManager.getLogger(CommonSteps.class.getName());
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
    }

    @Then("I should see response code returned as {int}")
    public void i_should_see_response_with_status_code_as(Integer statusCode) throws AssertionError {
        try {
            APIValidationUtil.validateStatusCode(response, statusCode);
        } catch (AssertionError e) {
            logger.log(Level.ERROR, "Status code mismatch");
            logger.log(Level.ERROR, e.getMessage() + "\n\nActual Response body is " + response.asString() + "\n");
            throw new AssertionError(e.getMessage() + "\n\nActual Response body is " + response.asString() + "\n");
        }
    }
}
