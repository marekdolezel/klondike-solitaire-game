package model.cards;

//import java.awt.Color; // this is for color of a card

/**
 * Interface which is implemented by class representing playing cards.
 * @author Marek Doležel, Radim Červinka
 */
public interface Card {
    enum Suit {CLUBS, DIAMONDS, SPADES, HEARTS};
    enum Color {black, red};

    int getValue();
    Card.Suit getSuit();
    Color getColor();

    boolean isTurnedFaceUp();
    boolean turnFaceUp();
    boolean turnFaceDown();

    int compareValue(Card c);

    Card str2card(String s);
    String card2str();

    boolean isSame(Card c);
}
