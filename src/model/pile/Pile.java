package model.pile;

import model.cards.Card;

/**
 * Interface for various piles on the board.
 * @author Marek Doležel, Radim Červinka
 */
public interface Pile {
    boolean isEmpty();
    Card pop();
    Pile pop(int index);
    boolean push(Card card);
    int size();
    void Shuffle();

    Card get();
    Card get(int index);

    boolean anarchyPush(Card card);
}
