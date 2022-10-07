package utils;

import io.restassured.RestAssured;
import pojos.AddAMemePojo;

import java.util.List;
import java.util.Map;

public class APIHelperUtil {

    //API Paths
    public static final String ADD_A_MEME = "add/";

    //A method to populate Add A Meme POJO
    public static AddAMemePojo buildAddAMemeRequest(List<List<String>> listOfData){
        AddAMemePojo addAMemePojo = new AddAMemePojo();
        addAMemePojo.setName(listOfData.get(1).get(1));
        addAMemePojo.setTags(listOfData.get(2).get(1));
        addAMemePojo.setImage(listOfData.get(3).get(1));

        return addAMemePojo;
    }
}
