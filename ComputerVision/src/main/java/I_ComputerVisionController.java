import dataObjects.Card;
import javafx.scene.image.Image;

import java.util.List;

/**
 * @author Erlend
 */

public interface I_ComputerVisionController {
    // Return all fully visible cards as a list to the controller.
    // Empty piles should be in the list, so the length is always 12:
    // 0 = drawn card, foundation 1-4, pile 5-13
    List<Card> getSolitaireCards(Image img);
}
