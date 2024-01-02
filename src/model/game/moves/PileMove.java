package model.game.moves;

import model.cards.Card;
import model.pile.Pile;
import model.game.SolitaireGame;

/**
 * Class represents move that moves sequence of cards (which is on top of one pile) on the top of another pile.
 * In this game this happens on table piles only (which is managed before making an instance of this class).
 * @author Radim Červinka
 */
public class PileMove implements Move {
    private int fromDest;
    private int toDest;
    private Card c;
    private Pile p;
    private SolitaireGame game;

    /**
     * Class constructor.
     * @param c Card instance starting the card sequence to be moved.
     * @param from Index identifing certain pile on the board, from which the card comes from.
     * @param to Index of pile on the board, which we want to move the card onto.
     * @param g Game instance.
     */
    public PileMove(Card c, int from, int to, SolitaireGame g) {
        this.fromDest = from;
        this.toDest = to;
        this.c = c;
        this.game = g;
    }

    /**
     * Executes the move. Pops card sequence from fromDest indexed pile and pushes it to toDest indexed pile.
     */
    @Override
    public void execute() {
        int pile_index = game.findCardInPile(c, fromDest);
        p = game.getPile(fromDest).pop(pile_index);
        for(int i = p.size()-1; i >= 0; i--) {
            game.getPile(toDest).push(p.pop());
        }
    }

    /**
     * Undoes the move. Pops card sequence from toDest indexed pile and pushes it to fromDest indexed pile.
     */
    @Override
    public void undo() {
        int pile_index = game.findCardInPile(c, toDest);
        p = game.getPile(toDest).pop(pile_index);
        for(int i = p.size()-1; i >= 0; i--) {
            game.getPile(fromDest).anarchyPush(p.pop());
        }
    }
}
