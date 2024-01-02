package model.game.moves;

import model.cards.Card;
import model.game.moves.Move;

/**
 * Class for representing card turns as a move.
 * @author Radim Červinka
 */
public class CardTurn implements Move {
    private Card card;

    /**
     * Class constructor.
     * @param c Card instance to be turned.
     */
    public CardTurn(Card c) {
        this.card = c;
    }

    /**
     * Executes the move. Turns the Card card.
     */
    @Override
    public void execute() {
        if (card.isTurnedFaceUp()) {
            card.turnFaceDown();
        }
        else
            card.turnFaceUp();
    }
    /**
     * Turns the Card card.
     */
    @Override
    public void undo() {
        this.execute();
    }
}
