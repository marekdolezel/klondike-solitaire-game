package model.game.moves;

/**
 * Interface to be implemented by classes representing various moves done in the game.
 * @author Radim Červinka
 */
public interface Move {
    void execute();
    void undo();
}
