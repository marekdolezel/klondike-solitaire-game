package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import model.game.GameListener;
import model.game.SolitaireGame;
import model.cards.Card;


/**
 * Class responsible for drawing stock pile
 * @author Marek Dolezel
 */
public class StockPileView extends HBox implements GameListener {
    private int index;
    private SolitaireGame game;
    private CardImageStore imageStore;
    private String emptyStyle = "-fx-border-width: 2; -fx-border-color: darkgreen; -fx-border-radius: 3; -fx-max-height: 97px; -fx-max-width: 73px";

    private Button stockButton;

    public StockPileView(int index, SolitaireGame game, CardImageStore imageStore) {
        this.index = index;
        this.game = game;
        this.imageStore = imageStore;

        stockButton = new Button();
        stockButton.managedProperty().bind(stockButton.visibleProperty());
        stockButton.setStyle("-fx-background-color: transparent; -fx-max-height: 97px; -fx-max-width: 73px;");

        getChildren().add(stockButton);

        buildLayout();

        stockButton.setOnMouseReleased(handler);

        setOnMouseReleased(handler);

        this.game.register_listener(this);
    }

    EventHandler handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            if (game.getPile(index).isEmpty()) {
                game.move_card(null, 8);
                System.out.println("stock pile is empty");

            }
            else {
                game.move_card(game.getPile(index).get(), 8);
            }

        }
    };

    /**
     * Display/print stock pile
     */
    private void buildLayout() {
        Card cardTop = game.getPile(index).get();

        if (game.getPile(index).isEmpty()) {
            setStyle(emptyStyle);
            getChildren().get(0).setVisible(false);
        }
        else {
            setStyle("");
            getChildren().get(0).setVisible(true);

            stockButton.setGraphic(new ImageView(imageStore.getCardImage(cardTop.card2str(), false)));
        }
    }

    @Override
    public void gameModelChanged() {
        buildLayout();
    }
}
