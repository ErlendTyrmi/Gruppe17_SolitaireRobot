package Data;

import Converter.Util.Sorting.I_Sorting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author Andreas B.G. Jensen
 * This class holds and calculates the calibration of gridlines and the elements in the upper and the lower row.
 * The upper row represents a List of elements (drawcard, foundation) that may be presen in the row.
 * The lower row represents the the elements found in the solitaire piles 1 -7.
 *
 * //HashMap rowFixedGridLines holds X coordinates for calibrating of the lower row according to the X koordinate.
 * <List<JsonDTO>upperRow> and <List<JsonDTO>lowerRow>  represents the upper row and can be changed after calibration. Everytime an output from Darknet has to be evaluated, the upperRow and lowerRow will change according to the output.
 *  List<JsonDTO> calubrationList is used for calculating rowFixedGidLines.
 *  double separationLine represent the Y-koordinate on which the upper and lower row will be separated.
 *  static double drawCardSeparationLineX represents the X-koordinate on which the draw card will be destinguished from the Foundation piles in the upper row.
 */
public class BufferElement {
    private final double drawCard_HorisontalBuffer = 100.0;
    private I_Sorting sortingObject;
    private List<JsonDTO> calubrationList;
    private List<JsonDTO> upperRow = new ArrayList<>();
    private List<JsonDTO> lowerRow = new ArrayList<>();
    private static double separationLine = 0;
    private static double drawCardSeparationLineX = 0;
    private static HashMap<Integer, Double> rowFixedGridLines;

    public BufferElement(List<JsonDTO> calubrationList, I_Sorting sortingObject) {
        this.calubrationList = calubrationList;
        this.sortingObject = sortingObject;
    }


    /**
     * @author Andreas B.G. Jensen
     * Method is used for calibration of the first image.
     * The method sorts all the elements according to Y-coordinate in increasing order.
     * The lowes Y-koordinate represent the Card in the drawPile. The rest of the elements represent the
     *
     * Deviding Darknet output, between the upper and the lower row.
     */
    public void calibrateImageInputDimensions(){
        calubrationList = sortingObject.sortingTheListAccordingToY(calubrationList);
        List<JsonDTO> upperElements = calubrationList;
        for(int i = 0; i<upperElements.size();i++){
            if(upperElements.get(i).getCat().equals(upperElements.get(0).getCat())){
                upperRow.add(upperElements.get(i));
            }
        }
        lowerRow = removeSameElementsInList(calubrationList,upperRow);
       //Chect for correct input
        //Else throw an error
        setDrawCardSeparationLineX();
        calculateVerticalGrid();
        calculateBufferY();
    }


    /**
     * @author Andreas B.G. Jensen
     * This method should only be used in the calibrateImageInputDimensions due to its hardcoading
     * This method will set the separationLine which is the Y-koordinate that separates the upper row from the lower row.
     * Returns the averaging distance between the card from the upperRow that is closest to the lowerRow and the distance from the lower row that
     * is closes to the upperRow.
     *
     */
    public void calculateBufferY(){

        upperRow = sortingObject.sortingTheListAccordingToY(upperRow);
        lowerRow = sortingObject.sortingTheListAccordingToY(lowerRow);

        double highesYFromUppeRow =upperRow.get(upperRow.size()-1).getY();
        double lowestYFromLowerRow = lowerRow.get(0).getY();

        double buffer = (lowestYFromLowerRow-highesYFromUppeRow)/2;

        separationLine = highesYFromUppeRow+buffer;
    }


    /**
     * @author Andreas B.G. Jensen
     * Set the extra buffer from the drawcard to the X-Koordinate in which the drawCard wont be evaluated as a drawcard anymore.
     * NB: In stead it will be evaluated as a foundation.
     */
    private void setDrawCardSeparationLineX(){
        List<JsonDTO> rowUpperRow = sortingObject.sortingTheListAccordingToX(upperRow);
        drawCardSeparationLineX = rowUpperRow.get(rowUpperRow.size()-1).getX()+drawCard_HorisontalBuffer;
    }

    /**
     * @author Andreas B.G. Jensen
     * Removes identical elements from one list in another.
     * @param listToRemoveFrom
     * @param listToCompare
     * @return List<JsonDTO>
     */
    private List<JsonDTO> removeSameElementsInList(List<JsonDTO> listToRemoveFrom, List<JsonDTO> listToCompare){
        for(int i = 0; i<listToCompare.size();i++){
            if(listToRemoveFrom.contains(listToCompare.get(i))){
                listToRemoveFrom.remove(listToCompare.get(i));
            }
        }
        return listToRemoveFrom;
    }

    /**
     * @author Andreas B.G. Jensen
     * This method should only be run in the method calibrateImageInputDimensions()
     * Calculates a fixed coordinate on each pile in the lower row. The Fixpoint is calculated by averaging the X-coordinates
     * from identical cards. Remember that the DarknetOut can give two (maby more) detection for the same card.
     * The list rowFixedGridLines will be used every time a new darknetinput will be evaluated, and the list will be used for
     * mapping the incomming detection input to the correct pile.
     *
     * @return void
     */
    public void calculateVerticalGrid(){
         rowFixedGridLines = new HashMap<>();
        lowerRow = sortingObject.sortingTheListAccordingToX(lowerRow);

        int rowCounter = 0;
        //while(rowCounter<7) {
            for (int i = 0; i < lowerRow.size(); i++) {
                double lowX = lowerRow.get(i).getX();
                double highX = lowerRow.get(i).getX();
                for (int j = i; j < lowerRow.size(); j++) {

                    //Finding the highest X value of the same type of card
                    if(lowerRow.get(i).getCat().equals(lowerRow.get(j).getCat())){
                        if(lowerRow.get(j).getX()>=highX){
                            highX = lowerRow.get(j).getX()+lowerRow.get(j).getW();
                        }
                    }else{
                        i=j-1;
                        break;
                    }

                }
                rowFixedGridLines.put(rowCounter, calculateAverageX(lowX,highX).doubleValue());
                if(rowCounter==6) break;
                rowCounter++;

            }
    }


    /**
     * @author Andreas B.G. Jensen
     * This method devides an incomming list into the upper- and lower lower row.
     * The deviding aspect will be based on the calibration (method calibrateImageInputDimensions()) which
     * must be run before using this method.
     * @param preCardList
     * @return void
     */
    public void setNewUpperAndLowerRow(List<JsonDTO> preCardList){
        preCardList = sortingObject.sortingTheListAccordingToY(preCardList);
        List<JsonDTO> upperElements = preCardList;
        for(int i = 0; i<upperElements.size();i++){
            if(upperElements.get(i).getY()<separationLine){
                upperRow.add(upperElements.get(i));
            }
        }
        lowerRow = removeSameElementsInList(preCardList,upperRow);
    }

    /**
     * @author Andreas B.G. Jensen
     * Calculates the average between two points
     * @param lowX
     * @param highX
     * @return Double
     */
    public Double calculateAverageX(double lowX, double highX){
        Double average = lowX+((highX-lowX)/2);
        return average;
    }

    public List<JsonDTO> getUpperRow(){
        return upperRow;
    }

    public List<JsonDTO> getLowerRow(){
        return lowerRow;
    }

    public double getSeparationLine(){
        return separationLine;
    }

    public HashMap<Integer, Double> getRowFixedGridLines(){
        return rowFixedGridLines;
    }

    public double getDrawCardSeparationLine(){
        return drawCardSeparationLineX;
    }

}
