package gui;

/**
 * Class for single game board.
 * @author Marek Dolezel
 */

import javafx.scene.layout.GridPane;
import model.game.SolitaireGame;

public class SolitaireGameView extends GridPane {

    private StockPileView stockPileView;
    private WastePileView wastePileView;

    private SuitPileView[] suitPileView = new SuitPileView[4];
    private StackView[] stackView = new StackView[7];

    private SolitaireGame game;
    private CardImageStore imageStore;
    private CardTransfer cardTransfer;


    public SolitaireGameView(String color) {
        game = new SolitaireGame();
        buildLayout();

    }

    /**
     * Displays game board.
     */
    private void buildLayout() {
        imageStore = new CardImageStore();
        cardTransfer = new CardTransfer();

        wastePileView = new WastePileView(8, game, imageStore, cardTransfer);
        stockPileView = new StockPileView(7, game, imageStore);


        setHgap(30);
        setVgap(30);


        setStyle("-fx-background-color:green");


        add(stockPileView, 0,0);
        add(wastePileView, 1, 0);

        for (int i=0; i<4; i++) {
            suitPileView[i] = new SuitPileView(i+9,game, imageStore,cardTransfer);
            add(suitPileView[i],i+3,0);

        }

        for (int i=0; i<7; i++) {
            stackView[i] = new StackView(i,game, imageStore, cardTransfer);
            add(stackView[i],i,1);
        }
    }

    /**
     * Removes all GUI elements from SolitaireGameView.
     */
    private void cleanUp() {
        getChildren().clear();

    }

    /**
     * Wrapper for Solitaire class
     * calls undo function implemented by SolitaireGame class
     */
    public void undo() {
        game.undo_move();
    }

    /**
     * Wrapper for Solitaire class
     * calls help move function implemented by SolitaireGame class
     */
    public String help_move() {
        return game.help_move();
    }

    /**
     * Wrapper for Solitaire class
     * Cleans game board and initializes new game.
     */

    public void setNew() {
        game = new SolitaireGame();
        cleanUp();
        buildLayout();

    }

    /**
     * Wrapper for Solitaire class
     * calls toFile function implemented by SolitaireGame class
     */
    public  void toFile(String filename) {
        game.toFile(filename);
    }

    /**
     * Wrapper for Solitaire class
     * calls fromFile function implemented by SolitaireGame class
     */
    public boolean fromFile(String filename) {
        boolean res;
        res = game.fromFile(filename);
        if (!res)
            return res;
        cleanUp();


        buildLayout();
        return true;
    }

}
