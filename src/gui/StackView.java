package gui;

import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import model.game.GameListener;
import model.game.SolitaireGame;
import model.cards.Card;

/**
 * Class responsible for drawing stack of cards
 * @author Marek Dolezel
 */
public class StackView extends StackPane implements GameListener {
    private int index;
    private SolitaireGame game;
    private CardImageStore imageStore;
    private CardTransfer cardTransfer;
    private String emptyStyle = "-fx-border-width: 2; -fx-border-color: darkgreen; -fx-border-radius: 3; -fx-max-height: 97px; -fx-max-width: 73px; -fx-min-height: 97px; -fx-min-width: 73px";

    public StackView(int index, SolitaireGame game, CardImageStore imageStore, CardTransfer cardTransfer) {
        this.index = index;
        this.game = game;
        this.imageStore = imageStore;
        this.cardTransfer = cardTransfer;

        buildLayout();

        game.register_listener(this);
    }


    @Override
    public void gameModelChanged() { buildLayout(); }

    /**
     * Display/paint stack of cards.
     */
    private void buildLayout() {
        if (game.getPile(index).isEmpty()) {
            getChildren().clear();
            setStyle(emptyStyle);

            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (cardTransfer.getTransferStatus() == CardTransfer.TransferStatus.PENDING) {
                        System.out.println("moving card "+cardTransfer.getCard().card2str()+" to "+String.valueOf(index));
                        game.move_card(cardTransfer.getCard(),index);
                        cardTransfer.setTransferStatus(CardTransfer.TransferStatus.NOT_STARTED);
                    }

                   // System.out.println(card.card2str());
                }
            });
        }
        else {
            setStyle("");
            getChildren().clear();

            int translate_X = 0;
            int translate_Y = 0;

            for (int i=0; i<this.game.getPile(this.index).size(); i++) {
                Card card = this.game.getPile(this.index).get(i);

                Image image = this.imageStore.getCardImage(card.card2str(), card.isTurnedFaceUp());
                ImageView imageView = new ImageView(image);

                imageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (cardTransfer.getTransferStatus() == CardTransfer.TransferStatus.NOT_STARTED) {
                            cardTransfer.setCard(card);
                            cardTransfer.setTransferStatus(CardTransfer.TransferStatus.PENDING);
                        }
                        else if (cardTransfer.getTransferStatus() == CardTransfer.TransferStatus.PENDING) {
                            System.out.println("moving card "+cardTransfer.getCard().card2str()+" to "+String.valueOf(index));
                            game.move_card(cardTransfer.getCard(),index);
                            cardTransfer.setTransferStatus(CardTransfer.TransferStatus.NOT_STARTED);
                        }

                        System.out.println(card.card2str());
                    }
                });

                imageView.setTranslateY(translate_Y);
                imageView.setTranslateX(translate_X);
                getChildren().add(imageView);

                translate_Y += 17;
                translate_X += 4;

            }


        }
    }


}
