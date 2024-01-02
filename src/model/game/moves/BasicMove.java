package model.game.moves;

import model.cards.Card;
import model.game.SolitaireGame;
import model.game.moves.Move;

/**
 * Class for most basic card moves (from top of one pile to the top of another.
 * @author Radim Červinka
 */
public class BasicMove implements Move {
    private int fromDest;
    private int toDest;
    private Card c;
    private SolitaireGame game;

    /**
     * Class constructor.
     * @param c Card instance to be moved.
     * @param from Index identifing certain pile on the board, from which the card comes from.
     * @param to Index of pile on the board, which we want to move the card onto.
     * @param g Game instance.
     */
    public BasicMove(Card c, int from, int to, SolitaireGame g) {
        this.fromDest = from;
        this.toDest = to;
        this.c = c;
        this.game = g;
    }

    /**
     * Executes the move. Moves a card from fromDest indexed pile to toDest indexed pile.
     */
    @Override
    public void execute() {
        if (fromDest == 8) {
            c =  game.getPile(fromDest).pop();
            c.turnFaceUp();
            game.getPile(toDest).push(c);
        }
        else {
            game.getPile(toDest).push(game.getPile(fromDest).pop());
        }

    }

    /**
     * Undoes the move. Moves a card from toDest indexed pile to fromDest indexed pile.
     */
    @Override
    public void undo() {
        game.getPile(fromDest).anarchyPush(game.getPile(toDest).pop());
    }
}
