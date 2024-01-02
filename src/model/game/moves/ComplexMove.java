package model.game.moves;

import model.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents moves done by player. Contains list of elementary moves done to perform given player move.
 * @author Radim Červinka
 */
public class ComplexMove implements Move {

    private final List<Move> moves = new ArrayList<>();

    /**
     * Class constructor.
     * @param basic_moves List of basic moves to be perfomed to perform ComplexMove, which represents player moves.
     */
    public ComplexMove(List<Move> basic_moves) {
        for( Move bmove : basic_moves ) {
            moves.add(bmove);
        }
    }

    /**
     * Executes the move. Goes through the list of moves and performs each one of them.
     */
    @Override
    public void execute() {
        for (Move bmove : moves) {
            bmove.execute();
        }
    }

    /**
     * Undoes the move. Goes through the list from the end and undoes each basic move in the list.
     */
    @Override
    public void undo() {
        for(int i = moves.size()-1; i >= 0; i--) {
            moves.get(i).undo();
        }
    }

}
