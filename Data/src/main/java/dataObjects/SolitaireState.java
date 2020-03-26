package dataObjects;

import java.sql.Timestamp;
import java.util.List;

/* State
Jeg tænker at statemappen skal representere et state af spillet, altså hvilke kort der ligger i hver bunke.
En ide kan være at der findes en dynamisk list der representere hver bunke.
@author Andreas, Erlend
*/

public class SolitaireState {
    public final String time = new Timestamp(System.currentTimeMillis()).toString();// Timestamp ID for test and log

    private boolean stockEmpty = true; // Cards to draw, face not visible
    private List<Card> drawnCards; // Drawn cards, 0 to 3
    private Card[] foundations = new Card[4]; // Four piles, goal, only top card visible
    private List<List<Card>> piles; // The seven rows

    public boolean isStockEmpty() {
        return stockEmpty;
    }

    public void setStockEmpty(boolean stockEmpty) {
        // Set this to true if the stock (draw pile) is empty.
        this.stockEmpty = stockEmpty;
    }

    public List<Card> getDrawnCards() {
        return drawnCards;
    }

    public void setDrawnCards(List<Card> drawnCards) throws Exception {
        if (drawnCards.size() > 3) {
            throw new Exception("Too many (visible) cards drawn: " + drawnCards.size() + ".");
        }
        this.drawnCards = drawnCards;
    }

    public Card[] getFoundations() {
        return foundations;
    }

    public void setFoundations(Card[] foundations) throws Exception {
        if (foundations.length != 4) {
            throw new Exception("Should always be four foundations (including empty piles), was " + foundations.length + ".");
        }
        this.foundations = foundations;
    }

    public List<List<Card>> getPiles() {
        return piles;
    }

    public void setPiles(List<List<Card>> piles) throws Exception {
        if (piles.size() != 7) {
            throw new Exception("Should always be seven piles (including empty piles), was " + piles.size() + ".");
        }
        this.piles = piles;
    }

    // Add one row to pile at a time.
    public void addRowToPile(List<Card> row) {
        piles.add(row);
    }
}

