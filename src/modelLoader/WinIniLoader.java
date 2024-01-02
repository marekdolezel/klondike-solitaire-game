package modelLoader;

import model.cards.Card;
import model.game.SolitaireGame;
import model.pile.Pile;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Class responsible for loading/saving SolitaireGame instance to file
 * @author Marek Dolezel
 */
public class WinIniLoader {
    SolitaireGame game;

    public WinIniLoader(SolitaireGame game) {
        this.game = game;

    }


    private static final int STOCK_PILE_INDEX = 7;
    private static final int WASTE_PILE_INDEX = 8;
    private static final int TABLE_PILE_START_INDEX = 0;
    private static final int FOUNDATION_PILE_START_INDEX = 9;

    /**
     * Store
     * @param filename where to store file with game
     * @return true on success
     */
    public boolean toFile(String filename) {
        StringBuilder modelBuilder = new StringBuilder();

        modelBuilder.append(serializePile("[Stock]\n", game.getPile(STOCK_PILE_INDEX), false, -1));
        modelBuilder.append(serializePile("[Waste]\n", game.getPile(WASTE_PILE_INDEX), false, -1));

        modelBuilder.append("[Table]\n");
        for (int i = TABLE_PILE_START_INDEX; i < 7; i++) {
            modelBuilder.append(serializePile("", game.getPile(i), true, i));
        }

        modelBuilder.append("[Foundation]\n");
        for (int i = FOUNDATION_PILE_START_INDEX; i <= 12; i++) {
            modelBuilder.append(serializePile("", game.getPile(i), false, i - 9));
        }

        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(modelBuilder.toString());
        } catch (Exception e) {
            // Log the exception or print more info
            return false;
        }

        return true;
    }

    /**
     * @param pile pile to print
     * @param turnPoint check for CardUp marker (only for TablePile)
     * @param contentIndex content index for ini file
     * @return
     */
    private String serializePile(String header, Pile pile, boolean turnPoint, int contentIndex) {
        StringBuilder modelStr = new StringBuilder(header);
        String contentKey = contentIndex == -1 ? "content = " : "content" + contentIndex + " = ";

        modelStr.append(contentKey);
        if (pile.size() == 0) {
            modelStr.append("empty\n");
            return modelStr.toString();
        }

        boolean state = false;
        for (int i = 0; i < pile.size(); i++) {
            Card card = pile.get(i);
            if (turnPoint && card.isTurnedFaceUp() && !state) {
                modelStr.append("| ");
                state = true;
            }
            modelStr.append(card.card2str()).append(" ");
        }
        modelStr.append("\n");
        return modelStr.toString();
    }




    /**
     * Loads game from file to internal representation
     * @param filename name of file with game
     * @return If action was successful
     */
    public boolean fromFile(String filename) {

        for (int i=0; i<12; i++)
            game.pilePop(game.getPile(i));


        try {
            Wini ini = new Wini(new File(filename));

            Collection<Profile.Section> list = ini.values();
            for(Profile.Section section : list){

                if (section.getName().equals("Stock")) {
                    String stock_str = section.get("content");
                    if (!stock_str.equals("empty"))
                        loadPile(7,stock_str);

                }
                else if (section.getName().equals("Waste")) {
                    String waste_str = section.get("content");
                    if (!waste_str.equals("empty"))
                        loadPile(8,waste_str);

                }

                else if (section.getName().equals("Table")) {
                    for (int i=0; i<7; i++) {
                        String table_str = section.get("content"+i);
                        if (!table_str.equals("empty"))
                            loadPile(i,table_str);

                    }

                }

                else if (section.getName().equals("Foundation")) {
                    for (int i=0; i<4; i++) {
                        String foundation_str = section.get("content"+i);
                        if (!foundation_str.equals("empty"))
                            loadPile(i+9,foundation_str);

                    }

                }
            }

        } catch(java.io.IOException e) {
            System.out.println("Oops we have failed!");
        }

        return true;
    }


    /**
     * @param pile_index where to store
     * @param pile_str name of section in ini
     */
    private void loadPile(int pile_index, String pile_str) {

        Pile stock_pile = game.getPile(pile_index);

        String[] cards = pile_str.split(" ");

        boolean faceUp = false;
        for (int i=0; i<cards.length; i++) {

            if (cards[i].equals("|")) {
                faceUp = true;
                continue;
            }

            Card card = game.str2card(cards[i]);

            if (pile_index>=9 && pile_index<=12)
                card.turnFaceUp();


            if (faceUp)
                card.turnFaceUp();

            if (pile_index>=0 && pile_index<=6)
                stock_pile.anarchyPush(card);
            else
                stock_pile.push(card);
        }
    }
}