package model.pile;

import model.cards.Card;

/**
 * Class representing 1 of 4 foundation piles on the board.
 * @author Marek Doležel, Radim Červinka
 */
public class FoundationPile extends WastePile {
    private Card.Suit suit;

    /**
     * Class constructor.
     */
    public FoundationPile() {
        super();
    }

    /**
     * @param c card to be pushed onto the pile
     * @return returns true when suit of the card is same as suit of pile
     * and value of pushed card must be one bigger, otherwise false
     */
    @Override
    public boolean push(Card c) {

        if (pile.isEmpty() && c.getValue() == 1)
            return pile.add(c);
        else if (!pile.isEmpty()) {
            Card.Suit topCardSuit = pile.peek().getSuit();
            if (topCardSuit == c.getSuit() && this.get().getValue() + 1 == c.getValue() )
                return pile.add(c);
        }

        return false;

    }


}
