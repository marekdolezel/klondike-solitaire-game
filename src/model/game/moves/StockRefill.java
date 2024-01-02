package model.game.moves;

import model.cards.Card;
import model.game.SolitaireGame;

/**
 * Class represents move which moves all cards from waste pile back to stock pile.
 * @author Radim Červinka
 */
public class StockRefill implements Move {
    private SolitaireGame game;

    /**
     * Class constructor.
     * @param g Game instance.
     */
    public StockRefill(SolitaireGame g) {
        this.game = g;
    }

    /**
     * Moves and turns the cards from waste pile back to stock pile.
     */
    @Override
    public void execute() {
        while (game.waste_pile.size()>0) {
            Card c = game.waste_pile.pop();
            c.turnFaceDown();
            game.stock_pile.push(c);
        }
    }

    /**
     * Undoes refilling the stock pile, moves those cards back to waste pile.
     */
    @Override
    public void undo() {
        while (game.stock_pile.size()>0) {
            Card c = game.stock_pile.pop();
            c.turnFaceUp();
            game.waste_pile.push(c);
        }
    }
}
