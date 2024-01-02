package model.pile;

import java.util.Collections;

/**
 * Class representing stock pile (pack of cards facing down, cards are moved and turned face up) to waste one by one.
 * @author Marek Doležel, Radim Červinka
 */
public class StockPile extends WastePile {


    /**
     * Class constructor.
     */
    public StockPile() {
        super();

    }

    /**
     * Shuffles stock pile.
     */
    public void Shuffle() {
        Collections.shuffle(this.pile);

    }
}
