package model.pile;

import model.cards.Card;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class representing the waste pile on the board, which contains cards taken from stock pile and turned face up.
 * @author Marek Doležel, Radim Červinka
 */
public class WastePile implements Pile {

    protected Stack<Card> pile;

    /**
     * Class constructor.
     */
    public WastePile() {
        this.pile = new Stack<>();
    }


    /**
     * @param index Tells from which index (position in pile) we want to pop the sequence (pile).
     * @return returns popped pile (sequence) of Card starting from given index.
     */
    public Pile pop(int index) {
        Pile temp_pile = null;
        int pile_last_index = this.pile.size() - 1;


        for(int i=pile_last_index; i >= index; i--) {
            temp_pile.push(this.pile.get(i));
            this.pile.remove(i);
        }

        return temp_pile; // for now
    }

    /**
     * @return returns true if pile is empty, false otherwise
     */
    public boolean isEmpty() { return pile.isEmpty(); }

    /**
     * @return returns popped Card when pile is not empty, null otherwise
     */
    public Card pop() { return this.isEmpty() ? null : this.pile.remove(this.size() - 1); }


    /**
     * @param c card to be pushed onto the pile
     * @return returns true if operation was successful, false otherwise
     */
    public boolean push(Card c) {
        if (c == null)
            return false;

        this.pile.push(c);
        return true;
    }

    /**
     * @return returns card from top of pile (no remove), null when pile is empty
     */
    public Card get() {
        int index = this.pile.size() - 1;

        if (index >= 0) {
            return this.pile.get(index);
        }

        return null;
    }

    /**
     * @param index index of the card
     * @return returns card from top of pile (no remove), null when pile is empty
     */
    public Card get(int index) {
        int pindex = this.pile.size() - 1;

        if (pindex >= 0) {
            return this.pile.get(index);
        }

        return null;
    }

    /**
     * @param c card to be pushed onto the pile
     * @return returns true when card can be pushed (this method is for init only)
     */
    public boolean anarchyPush(Card c) {
        if ( c == null)
            return false;

        this.pile.push(c);
        return true;
    }

    public void Shuffle() {};

    /**
     * @return returns size of pile
     */
    public int size() { return pile.size(); }
}
