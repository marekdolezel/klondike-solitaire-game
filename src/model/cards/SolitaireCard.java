package model.cards;

//import java.awt.Color;
import java.io.Serializable;

/**
 * Class represents playing cards.
 * @author Marek Doležel, Radim Červinka
 */
public class SolitaireCard implements Card,Serializable {
    private int value;
    private Color color;
    private Card.Suit suit;
    private String card_str;

    private boolean turnedUp;

    /**
     * @param value value of the card
     * @param suit suit of the card
     */
    public SolitaireCard(int value, Suit suit) {
        this.value      = value;
        this.suit       = suit;
        this.turnedUp   = false;
        this.card_str   = card2str();

        if (suit == Suit.SPADES || suit == Suit.CLUBS)
            this.color = Color.black;
        else
            this.color = Color.red;

    }

    /**
     * @return returns value of the card
     */
    @Override
    public int getValue() { return this.value; }

    /**
     * @return returns suit of the card
     */
    @Override
    public Suit getSuit() { return this.suit; }

    /**
     * @return returns color of the card
     */
    @Override
    public Color getColor() { return this.color; }

    /**
     * @return returns true when the card is turned faced-up, false otherwise
     */
    @Override
    public boolean isTurnedFaceUp() { return this.turnedUp; }

    /**
     * @return returns true when the card was turned face-up, false otherwise
     */
    @Override
    public boolean turnFaceUp() {
        if (!this.turnedUp) {
            this.turnedUp = true;
            return true;
        }

        return false;
    }

    /**
     * @return returns true when the card was turned face-down, false otherwise
     */
    @Override
    public boolean turnFaceDown() {
        if (this.turnedUp) {
            this.turnedUp = false;
            return true;
        }

        return false;
    }

    /**
     * @param c the card to compare with
     * @return returns difference between values of cards
     */
    @Override
    public int compareValue(Card c) { return this.value - c.getValue(); }

    /**
     * Transforms this card into string representation.
     * @return String representation in format - example: 01H = Ace of hearts.
     */
    public String card2str() {
        int value = getValue();
        Card.Suit suit =  getSuit();

        String res = new String();

        if(value<10)
            res ="0";

        res += String.valueOf(value);

        return res   + suit.toString().substring(0,1);
    }

    /**
     * Generates Card instance from given String s. String is in format - example: H10 = the 10 of hearts.
     * @param s String representing playing card.
     * @return Card instance.
     */
    public Card str2card(String s) {
        Card.Suit suit = null;
        switch (s.substring(0,1)) {
            case "H":
                suit = Card.Suit.HEARTS;
                break;
            case "D":
                suit = Card.Suit.DIAMONDS;
                break;
            case "C":
                suit = Card.Suit.CLUBS;
                break;
            case "S":
                suit = Card.Suit.SPADES;
                break;
        }

        int value = Integer.parseInt(s.substring(1));
        return (new SolitaireCard(value, suit));
    }


    /**
     * Compares this card to the given card.
     * @param c Card instance to be compared with this Card instance.
     * @return Returns whether this card has the same suit and value as given Card.
     */
    public boolean isSame(Card c) {
        if (this.value == c.getValue() && this.suit == c.getSuit())
            return true;
        else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolitaireCard that = (SolitaireCard) o;

        if (value != that.value) return false;
        if (turnedUp != that.turnedUp) return false;
        if (!color.equals(that.color)) return false;
        if (suit != that.suit) return false;
        return card_str != null ? card_str.equals(that.card_str) : that.card_str == null;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (suit != null ? suit.hashCode() : 0);
        result = 31 * result + (turnedUp ? 1 : 0);
        return result;
    }
}

