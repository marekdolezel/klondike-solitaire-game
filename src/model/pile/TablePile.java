package model.pile;

import model.cards.Card;


/**
 * Class representing 1 of 7 table piles on the board.
 * @author Marek Doležel, Radim Červinka
 */
public class TablePile extends WastePile {

    /**
     * Class constructor.
     */
    public TablePile() {
        super();
    }

    /**
     * @param c card to be pushed onto the pile
     * @return returns true when card can be pushed according to rules, false otherwise
     */
    // color, value
    @Override
    public boolean push(Card c) {
        if (!this.isEmpty()) {
            if (this.get().getColor() == c.getColor())
                return false;

            if (c.getValue() + 1 == this.get().getValue())
                return pile.add(c);
        }
        else { // when table is empty we can insert only King
            if (c.getValue() == 13)
                return pile.add(c);
        }

        return false;

    }


    /**
     * @param index Tells from which index (position in pile) we want to pop the sequence (pile).
     * @return returns popped pile (sequence) of Card starting from given index.
     */
    public Pile pop(int index) {
        Pile temp_pile = new WastePile();
        int pile_last_index = this.pile.size() - 1;


        for(int i=pile_last_index; i >= index; i--) {
            temp_pile.anarchyPush(this.pile.get(i));
            this.pile.remove(i);
        }

        return temp_pile;
    }

}
