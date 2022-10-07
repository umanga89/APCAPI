package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import utils.APIValidationUtil;
import utils.BaseUtil;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetAllMemesSteps extends BaseUtil {

    public GetAllMemesSteps(){
        logger = LogManager.getLogger(GetAllMemesSteps.class.getName());
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
    }

    @Given("I populate get all memes request")
    public void i_populate_get_all_memes_request() throws Exception{
        try{
            //Set Base URI
            RestAssured.baseURI = props.getProperty("base.path");
            //Set Base Path to empty as Get All Memes does not have a base path
            RestAssured.basePath = "";
            BaseUtil.logger.log(Level.INFO,"Set Get All Memes request URL as "+props.getProperty("base.path"));
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @When("I send the get all memes request")
    public void i_send_get_all_memes_request() throws Exception {
        try{
            //Using log all to see the actual request sent
            response = given().log().all().when().get();
            requestCapture.flush();
            BaseUtil.logger.log(Level.INFO,"Request has been sent to "+props.getProperty("base.path"));
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
            throw new Exception(e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
        }
    }

    @Then("I see following parameters are returned in get all memes response and not empty")
    public void i_see_following_parameters_are_returned_in_get_all_memes_response_and_not_empty(DataTable dataTable) {
        try{
            List<List<String>> listOfData = dataTable.asLists(String.class);
            //Validating parameters in response body
            for(int i=1;i<listOfData.size();i++) {
                if(listOfData.get(i).get(0).equals("code")){
                    APIValidationUtil.validateBodyParameterHasValue(response, listOfData.get(i).get(0), Integer.parseInt(listOfData.get(i).get(1)));
                } else if(listOfData.get(i).get(0).equals("data")){
                    APIValidationUtil.validateFieldIsNotEmpty(response, listOfData.get(i).get(0));
                } else if(listOfData.get(i).get(0).equals("next")){
                    APIValidationUtil.validateFieldIsNotEmpty(response, listOfData.get(i).get(0));
                } else {
                    APIValidationUtil.validateBodyParameterHasValue(response, listOfData.get(i).get(0), listOfData.get(i).get(1));
                }
            }

        }catch (AssertionError e){
            logger.log(Level.ERROR,"Response body assertion error"+ e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
        }
    }

    @Then("I see following parameters are returned in every record of {string} json array in response and not empty")
    public void i_see_following_parameters_are_returned_in_every_record_of_json_array_in_response_and_not_empty(String jsonArray, DataTable dataTable) {
        try{
            List<List<String>> listOfData = dataTable.asLists(String.class);
            //Validating parameters in the data json array
            for(int i=1;i<listOfData.size();i++) {
                APIValidationUtil.validateAllRecordsHasGivenParameter(response, jsonArray, listOfData.get(i).get(0));
                APIValidationUtil.validateAllRecordsHasAValueForGivenParameter(response, jsonArray, listOfData.get(i).get(0));
            }

        }catch (AssertionError e){
            logger.log(Level.ERROR,"Response body assertion error"+e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
            throw new AssertionError(e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
        }
    }
}
