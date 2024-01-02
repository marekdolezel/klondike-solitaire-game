package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.HashMap;

/**
 * Loads images from resources folder. Image of a card is returned on-demand.
 * @author Marek Dolezel
 */

public class CardImageStore {

    private HashMap<String, Image> hash_map = new HashMap<>();
    private String[] suits = {"S", "H", "D", "C"};

    /**
     * Loads images from resources folder and saves them in HashMap indexed by string
     * representation of a card.
     */
    public CardImageStore() {
        String index_str = "";

        File cardBackFile = new File("resources/B.GIF");
        Image cardBackImage = new Image(cardBackFile.toURI().toString());


        for (int i=1; i<=13; i++) {
            index_str = "";
            if (i < 10)
                index_str = "0";


            index_str += String.valueOf(i);

            for (String suit: suits) {
                File file = new File("resources/"+index_str+suit+".GIF");
                Image image = new Image(file.toURI().toString());

                hash_map.put(index_str+suit,  image);
                hash_map.put(index_str+suit+"B", cardBackImage);


            }
        }
    }

    /**
     * Returns image of a card.
     * @param card representation of card
     * @param faceup side of card
     * @return
     */
    public Image getCardImage(String card, boolean faceup) {
        if (faceup == true)
            return hash_map.get(card);
        else
            return hash_map.get(card+"B");

    }
}
