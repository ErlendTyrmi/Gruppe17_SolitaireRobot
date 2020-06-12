import DarkNet_Connection.DatknetConnection;
import DarkNet_Connection.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.Test;
import Test.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * * @author Andreas B.G. Jensen
 */
public class DarknetConnection_Test {
    Test_Create_Image imageCreator = new Test_Create_Image();
    DatknetConnection connection = new DatknetConnection();


    @Test
    public void Test_convert_Image_To_byte_Array(){
        byte[] byteArray = null;
        Image im = imageCreator.Test_calibrateImgBoxes();
        try {
            byteArray = connection.convertImageToByteArray(im);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void Sending_ImageToServer_Test(){
        try {

              //BufferedImage  img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\TestKabale.PNG"));
            BufferedImage  img = ImageIO.read(new File("C:\\Uddannelse\\DTU\\4sem\\CDIO\\Kabale_V2\\ComputerVision\\AllDeck.jpg"));



            Image image = SwingFXUtils.toFXImage(img, null);


            connection.Get_Image_Information(image);


        } catch (UnirestException e) {
            e.printStackTrace();
        }
    catch (IOException e) {
        e.printStackTrace();
    }
    }



        private static void display(BufferedImage image){
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

        private static void ShowJavaFXImage(Image img){
            Image image = img;
            ImageView imageView = new ImageView(image);

            HBox hbox = new HBox(imageView);

            Scene scene = new Scene(hbox, 200, 100);
            //primaryStage.setScene(scene);
            //primaryStage.show();
        }

}
