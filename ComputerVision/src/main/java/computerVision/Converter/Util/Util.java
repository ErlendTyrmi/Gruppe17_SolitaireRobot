package computerVision.Converter.Util;

import Data.JsonDTO;
import Data.PreCard;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataObjects.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/*
 * @author Andreas B.G. Jensen
Class used for mapping a JsonObject to PreCard objects.
 */
public class Util {

    /**
     * @deprecated
     * @param obj
     * @return
     */
    public List<PreCard> getPreCard(JsonArray obj){

        List<PreCard> preCardList = new ArrayList<PreCard>();
        for(int i = 0; i<obj.size();i++){
            PreCard preCard = new PreCard();
            JsonObject element = obj.get(i).getAsJsonObject();
            preCard.setUpperCoordinate(getUpperCoordinate(element));
            preCard.setLowerCoordinate(getLowerCoordinate(element));
            preCard.setColor(getColor(element));
            preCard.setRank(getRank(element));
            preCardList.add(preCard);
        }

        return preCardList;
    }

    /**
     * @deprecated
     * @param obj
     * @return
     */
    private Point getUpperCoordinate(JsonObject obj){
        double X_koordinate = getUpperKoordinate_X(obj);
        double Y_koordinate = getUpperKoordinate_Y(obj);

        Point newPoint= new Point();
        newPoint.setLocation(X_koordinate,Y_koordinate);

        return newPoint;
    }

    /**
     * @deprecated
     * @param obj
     * @return
     */
    private Point getLowerCoordinate(JsonObject obj){
        double X_koordinate = getLowerKoordinate_X(obj);
        double Y_koordinate = getLowerKoordinate_Y(obj);

        Point newPoint= new Point();
        newPoint.setLocation(X_koordinate,Y_koordinate);

        return newPoint;
    }


    /**
     * @deprecated
     * @param obj
     * @return
     */
    private double getUpperKoordinate_X(JsonObject obj){
        return obj.get("upperKoordinate_X").getAsInt();

    }

    /**
     * @deprecated
     * @param obj
     * @return
     */
    private double getUpperKoordinate_Y(JsonObject obj){
        return obj.get("upperKoordinate_Y").getAsInt();

    }

    /**
     * @deprecated
     * @param obj
     * @return
     */
    private double getLowerKoordinate_X(JsonObject obj){
        return obj.get("lowerKoordinate_X").getAsInt();

    }

    /**
     * @deprecated
     * @param obj
     * @return
     */
    private double getLowerKoordinate_Y(JsonObject obj){
        return obj.get("lowerKoordinate_Y").getAsInt();

    }

    /**
     * @deprecated
     * @param obj
     * @return
     */
    private int getRank(JsonObject obj){
        String classsss = obj.get("Classification").toString();
        classsss = classsss.replace("\"","");
        String subString = classsss.substring(1);
        int rank = Integer.parseInt(subString);
        return rank;
    }


    /**
     * @deprecated
     * @param obj
     * @return
     */
    private String getColor(JsonObject obj){
        String classsss = obj.get("Classification").toString();
        classsss = classsss.replace("\"","");
        String color = classsss.substring(0,1);
        return color;
    }

    public static void display(BufferedImage image){
        JFrame frame = null;
        JLabel label = null;
        if(frame==null){
            frame=new JFrame();
            frame.setTitle("stained_image");
            frame.setSize(image.getWidth(), image.getHeight());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            label=new JLabel();
            label.setIcon(new ImageIcon(image));
            frame.getContentPane().add(label, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        }else label.setIcon(new ImageIcon(image));
    }

    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    /**
     * @Auther Andreas B.G. Jensen
     * Converts a JsonDTO to a the DataObject Card
     * @param json
     * @return
     */

    public static Card convertToCard(JsonDTO json){
        Card newCard;
        try {
        if(json.getCat().length()==3){
            int rank = 10;
            Card.Suit suite = createSuit(json.getCat().substring(2,3));
            newCard = new Card(suite,rank);


        }else{

            String rank = json.getCat().substring(0,1);
            switch (rank){
                case "J":{ rank = "11"; break;}
                case "Q":{ rank = "12"; break;}
                case "K":{ rank = "13"; break;}
                case "A":{ rank = "1"; break;}
            }

            String suite = json.getCat().substring(1,2);

                newCard = new Card(createSuit(suite),Integer.parseInt(rank));
        }

            return newCard;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param lowX
     * @param highX
     * @return Double
     * @author Andreas B.G. Jensen
     * Calculates the average between two X-coordinates
     */
    public static Double calculateAverageX(double lowX, double highX) {
        Double average = lowX + ((highX - lowX) / 2);
        return average;
    }

    private static Card.Suit createSuit(String suite){

        switch (suite){
            case "h": return Card.Suit.HEART;
            case "d": return Card.Suit.DIAMOND;
            case "s": return Card.Suit.SPADE;
            case "c": return Card.Suit.CLUB;

        }
        return null;
    }

}
