package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import pojos.AddAMemePojo;
import utils.APIHelperUtil;
import utils.APIValidationUtil;
import utils.BaseUtil;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AddAMemeSteps extends BaseUtil {

    ResponseSpecification resSpec;

    public AddAMemeSteps(){
        logger = LogManager.getLogger(AddAMemeSteps.class.getName());
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
    }

    @Given("I populate add a meme request")
    public void i_populate_add_a_meme_request() throws Exception{
        try{
            //Set Base URI
            RestAssured.baseURI = props.getProperty("base.path");
            //Set base path
            RestAssured.basePath = APIHelperUtil.ADD_A_MEME;
            BaseUtil.logger.log(Level.INFO,"Set Add A Meme request URL as "+props.getProperty("base.path")+APIHelperUtil.ADD_A_MEME);
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occured =>\n"+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @When("I send the add a meme request with following parameters without postSecret and adminPassword headers")
    public void i_send_the_add_a_meme_request_with_following_parameters_without_postsecret_and_adminpassword_headers(DataTable dataTable) throws Exception {
        try{
            List<List<String>> listOfData = dataTable.asLists(String.class);
            //Initialise a POJO of Add a Meme request to be used in request body
            AddAMemePojo addAMeme = APIHelperUtil.buildAddAMemeRequest(listOfData);
            response = given().header("Content-type", "application/json").body(addAMeme).when().post();
            requestCapture.flush();
            BaseUtil.logger.log(Level.INFO,"Request has been sent");
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
            throw new Exception(e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
        }
    }

    @Then("I see following parameters are returned in response with given values")
    public void i_see_following_parameters_are_returned_in_response_with_given_values(DataTable dataTable) throws Exception{
        try{
            List<List<String>> listOfData = dataTable.asLists(String.class);
            //validating response parameters and their value
            for(int i=1;i<listOfData.size();i++) {
                if(listOfData.get(i).get(0).equals("code")){
                    APIValidationUtil.validateBodyParameterHasValue(response, listOfData.get(i).get(0), Integer.parseInt(listOfData.get(i).get(1)));
                }else if(listOfData.get(i).get(0).equals("data")) {
                    APIValidationUtil.validateFieldIsEmpty(response, listOfData.get(i).get(0));
                }else{
                    APIValidationUtil.validateBodyParameterHasValue(response, listOfData.get(i).get(0), listOfData.get(i).get(1));
                }
            }
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
            throw new Exception(e.getMessage()+"\n\nActual Response body is "+response.asString()+"\n");
        }
    }

}
