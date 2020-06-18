package Converter;

import Converter.Util.SortingHelperClass;
import Converter.Util.Util;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.DatknetConnection;
import DarkNet_Connection.I_Connection;
import Data.BufferElement;
import Data.JsonDTO;
import Data.PreCard;
import com.google.gson.JsonArray;
import com.mashape.unirest.http.exceptions.UnirestException;
import computerVision.I_ComputerVisionController;
import dataObjects.TopCards;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.sql.Connection;
import java.util.*;

/**
 * * @author Andreas B.G. Jensen
 */
public class Convertion implements I_ComputerVisionController {

    boolean test = true;
    SortingHelperClass sorting;
    I_Connection connection;
    BufferElement buffer;
    BoxMapping mapper;

    public Convertion(){
        if(test){
            connection = new Darknet_Stub();
        }
        else{
            connection = new DatknetConnection();
        }
        sorting = new SortingHelperClass();
    }



//TODO: Implement so that this method
    @Override
    public TopCards getSolitaireCards(Image img) {

    try {
        List<JsonDTO> returnImages = ConvertImage(img);
        buffer = new BufferElement(returnImages,sorting);
        mapper = new BoxMapping(buffer);

        //List<double[]> boxesArea = boxCreator.returnImgBoxes(img, returnImages);
       // mapping.makeBoxMapping(returnImages, new TopCards());
        System.out.println("Test");
        //return boxCreator.boxMapping(returnImages,boxesArea,img);
        return mapper.makeBoxMapping(returnImages);

    }catch (Exception e){
        e.printStackTrace();
    }
    return null;
    }





    public List<JsonDTO> ConvertImage(Image img){
        JsonArray returnArray = null;
        try {
            return connection.Get_Image_Information(img);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
       // return utility.getPreCard(returnArray);
        return null;

    }






}
