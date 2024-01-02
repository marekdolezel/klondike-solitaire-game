package gui;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.game.GameListener;
import model.game.SolitaireGame;
import model.cards.Card;
//import model.pile.Pile;


/**
 * Class responsible for drawing waste pile
 * @author Marek Dolezel
 */
public class WastePileView extends HBox implements GameListener {
    private int index;
    private SolitaireGame game;
    private CardImageStore imageStore;
    private CardTransfer cardTransfer;

    private String emptyStyle = "-fx-border-width: 2; -fx-border-color: darkgreen; -fx-border-radius: 3; -fx-max-height: 97px; -fx-max-width: 73px";

    public WastePileView(int index, SolitaireGame game, CardImageStore imageStore, CardTransfer cardTransfer) {
        this.index = index;
        this.game = game;
        this.imageStore = imageStore;
        this.cardTransfer = cardTransfer;

        buildLayout();

        game.register_listener(this);
    }

    @Override
    public void gameModelChanged() {
        buildLayout();
    }

    /**
     * Display waste pile
     */
    private void buildLayout() {
        Card cardTop = game.getPile(index).get();

        if (game.getPile(index).isEmpty()) {
            getChildren().clear();
            setStyle(emptyStyle);
        }
        else {
            getChildren().clear();

            ImageView imageView = new ImageView(imageStore.getCardImage(cardTop.card2str(), true));

            imageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (cardTransfer.getTransferStatus() == CardTransfer.TransferStatus.NOT_STARTED) {
                        cardTransfer.setCard(cardTop);
                        cardTransfer.setTransferStatus(CardTransfer.TransferStatus.PENDING);
                    }
                    else if (cardTransfer.getTransferStatus() == CardTransfer.TransferStatus.PENDING) {
                        System.out.println("moving card "+cardTransfer.getCard().card2str()+" to "+String.valueOf(index));
                        game.move_card(cardTransfer.getCard(),index);
                        cardTransfer.setTransferStatus(CardTransfer.TransferStatus.NOT_STARTED);
                    }

                    System.out.println(cardTop.card2str());

                }
            });


            getChildren().add(imageView);
        }
    }
}
