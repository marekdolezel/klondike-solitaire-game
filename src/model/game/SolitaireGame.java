/**
 * Created by radim on 25.4.17.
 */
package model.game;

import modelLoader.WinIniLoader;
import java.util.ArrayList;
import java.util.List;

import model.cards.Card;
import model.cards.SolitaireCard;
import model.pile.*;
import model.game.moves.*;


/**
 * Class of game board, managing piles, cards and moves.
 * @author Marek Doležel, Radim Červinka
 */
public class SolitaireGame {
    public Pile stock_pile;
    public Pile waste_pile;
    public List<Pile> table_pile = new ArrayList<>();
    public List<Pile> foundation_pile = new ArrayList<>();

    WinIniLoader loader;

    List<GameListener> listeners = new ArrayList<>();

    List<Move> performed_moves = new ArrayList<>();

    /**
     * Class constructor.
     */
    public SolitaireGame() {

        this.stock_pile = new StockPile();
        this.waste_pile = new WastePile();

        for(int i=1; i<=7; i++)
            this.table_pile.add(new TablePile());

        for (int i=1; i<=4; i++)
            this.foundation_pile.add(new FoundationPile());

        stock_pile = new StockPile();

        for (int i = 1; i <= 13; i++)
            stock_pile.push(new SolitaireCard(i, Card.Suit.CLUBS));

        for (int i = 1; i <= 13; i++)
            stock_pile.push(new SolitaireCard(i, Card.Suit.DIAMONDS));

        for (int i = 1; i <= 13; i++)
            stock_pile.push(new SolitaireCard(i, Card.Suit.HEARTS));

        for (int i = 1; i <= 13; i++)
            stock_pile.push(new SolitaireCard(i, Card.Suit.SPADES));

        stock_pile.Shuffle();

        loader = new WinIniLoader(this);

        distribute_cards();

    }


    public SolitaireGame(boolean creatAndDistribute) {
        this.stock_pile      = new StockPile();
        this.waste_pile = new WastePile();

        for(int i=1; i<=7; i++)
            this.table_pile.add(new TablePile());

        for (int i=1; i<=4; i++)
            this.foundation_pile.add(new FoundationPile());

        stock_pile = new StockPile();

        loader = new WinIniLoader(this);

        if (creatAndDistribute) {

            for (int i = 1; i <= 13; i++)
                stock_pile.push(new SolitaireCard(i, Card.Suit.DIAMONDS));

            for (int i = 1; i <= 13; i++)
                stock_pile.push(new SolitaireCard(i, Card.Suit.HEARTS));

            for (int i = 1; i <= 13; i++)
                stock_pile.push(new SolitaireCard(i, Card.Suit.SPADES));

            stock_pile.Shuffle();

            distribute_cards();
        }


    }


    /**
     * @param index identifies certain pile on the board.
     * @return Pile instance found by index.
     */
    public Pile getPile(int index) {
        if (index < 0 || index > 12)
            return null;

        if (index >= 0 && index <= 6)
            return this.table_pile.get(index);

        if (index == 7)
            return this.stock_pile;

        if (index == 8)
            return this.waste_pile;

        if (index >= 9 && index <= 12)
            return this.foundation_pile.get(index-9);
        return null;
    }


    /**
     * Distributes cards from stock pile on the board according to game rules before the game starts.
     */
    private void distribute_cards() {
        for (int i = 0; i < 7; i++) {
            for (int card = 0; card < i + 1; card++) {
                this.table_pile.get(i).anarchyPush(stock_pile.pop());


            } //TODO: turnFaceUp top cards

        }

        for (int i = 0; i< 7; i++) {
            Card card = this.table_pile.get(i).pop();
            card.turnFaceUp();
            this.table_pile.get(i).anarchyPush(card);
        }

    }

    /**
     * @return Message describing next possible move. Returns null if there is not a possible move to be played.
     */
    public String help_move() {
        Card c;
        boolean res = false;
        String help_message = "";
        //table
        for(int from=0; from<=6; from++) {
            Pile p = getPile(from);

            for(int i = 0; i < p.size(); i++) {
                c = p.get(i);
                if (c.isTurnedFaceUp()) {

                    for(int to=0; to<=12; to++) {
                        if (from != to) {
                            res = isLegalMove(c, from, to);
                            if (res) {
                                help_message += "Move ";

                                switch (getPile(from).get(i).getValue()) {
                                    case 1:
                                        help_message += "Ace";
                                        break;
                                    case 11:
                                        help_message += "Jack";
                                        break;
                                    case 12:
                                        help_message += "Queen";
                                        break;
                                    case 13:
                                        help_message += "King";
                                        break;
                                    default:
                                        help_message += String.valueOf(c.getValue());

                                }

                                help_message += " of ";
                                help_message += c.getSuit().toString();
                                help_message += " to ";

                                if (getPile(to).size() == 0) {
                                    if (to <= 6) {
                                        help_message += "empty table pile.";
                                    }
                                    if (to >= 9) {
                                        help_message += "empty foundation pile.";
                                    }
                                }

                                else {
                                    switch (getPile(to).get().getValue()) {
                                        case 1:
                                            help_message += "Ace";
                                            break;
                                        case 11:
                                            help_message += "Jack";
                                            break;
                                        case 12:
                                            help_message += "Queen";
                                            break;
                                        case 13:
                                            help_message += "King";
                                            break;
                                        default:
                                            help_message += String.valueOf(getPile(to).get().getValue());

                                    }
                                    help_message += " of ";
                                    help_message += getPile(to).get().getSuit().toString();
                                    help_message += ".";
                                }

                                return help_message;
                            }
                        }

                    }
                }
            }
        }

        int from = 8;

        Pile p = getPile(from);
        c = p.get();
        if (c.isTurnedFaceUp()) {
            for(int to=0; to<=12; to++) {
                if (from != to) {
                    res = isLegalMove(c, from, to);
                    if (res) {
                        help_message += "Move ";

                        switch (getPile(to).get().getValue()) {
                            case 1:
                                help_message += "Ace";
                                break;
                            case 11:
                                help_message += "Jack";
                                break;
                            case 12:
                                help_message += "Queen";
                                break;
                            case 13:
                                help_message += "King";
                                break;
                            default:
                                help_message += String.valueOf(c.getValue());

                        }

                        help_message += " of ";
                        help_message += c.getSuit().toString();
                        help_message += " to ";

                        if (getPile(to).size() == 0) {
                            if (to <= 6) {
                                help_message += "empty table pile.";
                            }
                            if (to >= 9) {
                                help_message += "empty foundation pile.";
                            }
                        }

                        else {
                            switch (getPile(to).get().getValue()) {
                                case 1:
                                    help_message += "Ace";
                                    break;
                                case 11:
                                    help_message += "Jack";
                                    break;
                                case 12:
                                    help_message += "Queen";
                                    break;
                                case 13:
                                    help_message += "King";
                                    break;
                                default:
                                    help_message += String.valueOf(getPile(to).get().getValue());

                            }
                            help_message += " of ";
                            help_message += getPile(to).get().getSuit().toString();
                            help_message += ".";
                        }

                        return help_message;
                    }
                }
            }
        }

        if (!this.stock_pile.isEmpty()) {
            help_message = "Get new card from stock pile.";
            return help_message;
        }
        else if (!this.waste_pile.isEmpty()) {
            help_message = "Refill your stock pile.";
            return help_message;
        }
        else
            return null;

    }


    /**
     * Adds new listeners.
     * @param listener Listener to add.
     */
    public void register_listener(GameListener listener) {
        listeners.add(listener);
    }


    /**
     * Notifies all listeners that the game changed.
     */
    public void notifyListeners() {
        for(GameListener listener: listeners) { listener.gameModelChanged(); }
    }


    /**
     * Pops (removes) a card from the top of a pile.
     * @param pile Pile from which we want to pop the card.
     */
    public void pilePop(Pile pile) {
        while(!pile.isEmpty()) {
            pile.pop();
        }
    }


    /**
     * Generates Card instance from String s. Card is always facing down.
     * @param s Contains string representation of a playing card.
     * @return Card instance generated from s.
     */
    public Card str2card(String s) {
        Card.Suit suit = null;
        switch (s.substring(2)) {
            case "H":
                suit = Card.Suit.HEARTS;
                break;
            case "D":
                suit = Card.Suit.DIAMONDS;
                break;
            case "C":
                suit = Card.Suit.CLUBS;
                break;
            case "S":
                suit = Card.Suit.SPADES;
                break;
        }

        int value = Integer.parseInt(s.substring(0,2));
        return (new SolitaireCard(value, suit));
    }

    /**
     * Saves current game to file given by filename.
     * @param filename Tells in which file the game is ought to be saved.
     */
    // simplifed fileio handling
    public void toFile(String filename) { this.loader.toFile(filename); }

    /**
     * Loads and prepares the game instance from file identified by filename.
     * @param filename Tells from which file to load the game info.
     * @return Returns whether load was successful or not.
     */
    public boolean fromFile(String filename) {
        this.performed_moves = new ArrayList<>();
        return this.loader.fromFile(filename);
    }




    /**************************
     *  UNDO and MOVE section *
     **************************/


    /**
     * Generates ComplexMove move instance. This instance representes player moves
     * and contains list of basic move to be performed. This list varies according to
     * from and to pile indexes.
     * @param c Card instance to be moved.
     * @param from Index of pile on the board, from which the card comes from.
     * @param to Index of pile on the board, which we want to move the card onto.
     * @return ComplexMove move instance.
     */
    private Move generateComplexMove (Card c, int from, int to) {
        List<Move> list_of_moves = new ArrayList<>();
        Pile fromPile = getPile(from);
        Pile toPile = getPile(to);

        // from table
        if (from <= 6) {

            // presun jedne karty
            if (isTop(c, from)) {
                list_of_moves.add(generateBasicMove(c, from, to));
                if (fromPile.size()-2 >= 0) {
                    if (!fromPile.get(fromPile.size()-2).isTurnedFaceUp()) {
                        list_of_moves.add(generateCardTurn(fromPile.get(fromPile.size()-2)));
                    }
                }
                // return complex move
                return (new ComplexMove(list_of_moves));
            }

            // presun vice karet
            else {
                list_of_moves.add(generatePileMove(c, from, to));
                int cardIndexInPile = findCardInPile(c, from);
                if (cardIndexInPile > 0 && !fromPile.get(cardIndexInPile - 1).isTurnedFaceUp()) {
                    list_of_moves.add(generateCardTurn(fromPile.get(cardIndexInPile - 1)));
                }
                return (new ComplexMove(list_of_moves));
            }
        }

        // from stock
        else if (from == 7) {
            list_of_moves.add(generateBasicMove(c, from, to));
            list_of_moves.add(generateCardTurn(c));
            return (new ComplexMove(list_of_moves));
        }

        // vse ostatni
        else {
            list_of_moves.add(generateBasicMove(c, from, to));
            return (new ComplexMove(list_of_moves));
        }

    }

    /**
     * Generates PileMove move instance.
     * @param c Card instance starting the card sequence to be moved.
     * @param from Index of pile on the board, from which the card comes from.
     * @param to Index of pile on the board, which we want to move the card onto.
     * @return PileMove move instance.
     */
    private Move generatePileMove (Card c, int from, int to) {
        return (new PileMove(c, from, to, this));
    }

    /**
     * Generates BasicMove move instance.
     * @param c Card instance to be moved.
     * @param from Index of pile on the board, from which the card comes from.
     * @param to Index of pile on the board, which we want to move the card onto.
     * @return BasicMove move instance.
     */
    private Move generateBasicMove (Card c, int from, int to) {
        return (new BasicMove(c, from, to, this));
    }

    /**
     * Generates CardTurn move instance.
     * @param c Card instance to be turned.
     * @return CardTurn move instance.
     */
    private Move generateCardTurn (Card c) {
        return (new CardTurn(c));
    }

    /**
     * Adds ComplexMove instance to list of performed moves, which exists for undo to be possible.
     * @param m Move to be added to the list.
     */
    private void addMove (Move m) {
        if (performed_moves.size() < 5) {
            performed_moves.add(m);
        }
        else {
            performed_moves.remove(0);

            //pridam prvek
            performed_moves.add(m);
        }
    }

    /**
     * Determines whether is legal to move Card c from pile indentified by from_id to pile identified by to_id.
     * @param c Card instance to be moved.
     * @param from_id Index of pile on the board, from which the card comes from.
     * @param to_id Index of pile on the board, which we want to move the card onto.
     * @return Returns whether is possible to move Card c from from_id pile to to_id pile.
     */
    private boolean isLegalMove(Card c, int from_id, int to_id) {
        Card temp = new SolitaireCard(c.getValue(), c.getSuit());

        // from table
        if (from_id <= 6 && from_id >= 0) {

            // table to table
            if (to_id <= 6 && to_id >= 0) {
                return (tryLegal(temp, to_id) ); //&& c.isTurnedFaceUp());
            }

            // table to foundation
            else if (to_id >= 9) {

                return isTop(c, from_id) && (tryLegal(temp, to_id) );//&& c.isTurnedFaceUp());
            }
            else
                return false;
        }

        // from waste
        else if (from_id == 8) {

            // waste to table
            if (to_id <= 6 && to_id >= 0) {
                return isTop(c, from_id) && (tryLegal(temp, to_id)  ); //&& c.isTurnedFaceUp());
            }

            // waste to foundation
            else if (to_id >= 9) {

                return isTop(c, from_id) && (tryLegal(temp, to_id)  );//&& c.isTurnedFaceUp());
            }
            else
                return false;
        }

        // from foundation
        else if (from_id >= 9) {

            // foundation to table
            if (to_id <= 6 && to_id >= 0) {

                return isTop(c, from_id) && (tryLegal(temp, to_id) );//&& c.isTurnedFaceUp());
            }
            else
                return false;
        }

        // from stock
        else if (from_id == 7) {

            // to waste
            return to_id == 8 && isTop(c, from_id);
        }
        return false;
    }

    /**
     * Tries to perform a push onto certain pile given by index.
     * @param c Card to be pushed onto the pile given by index.
     * @param index Identifies certain pile on the board.
     * @return Returns true if the push of the card is possible, false otherwise.
     */
    private boolean tryLegal (Card c, int index) {
        boolean legal = getPile(index).push(c);

        if (legal) {
            getPile(index).pop();
            return true;
        }
        else
            return false;
    }

    /**
     * Performs move made by player. Recognizes if the move is legal (in which case does nothing).
     * Also saves the move (as ComplexMove instance), so player can undo his move.
     * @param c Card which we want to move.
     * @param to_id Index of destination pile we want to move the card to.
     */
    public void move_card(Card c, int to_id) {

        // stock refill
        if (c == null && to_id == 8) {

            // create stockrefill move and add to list
            Move m = new StockRefill(this);
            List<Move> list_of_moves = new ArrayList<>();
            list_of_moves.add(m);

            // create complex move
            Move complex = new ComplexMove(list_of_moves);

            // save complex move to performed_moves
            addMove(complex);

            // execute complex move
            complex.execute();

            // let the game know that something happened
            notifyListeners();

            return;
        }

        int from_id = findCard(c);

        // is move legal?

        if (!isLegalMove(c, from_id, to_id)) {
            // not legal move
            notifyListeners();
            return;
        }

        // generate complex move
        Move complex = generateComplexMove(c, from_id, to_id);

        // save complex move to performed_moves
        addMove(complex);

        // execute complex move
        complex.execute();

        // let the game know that something happened
        notifyListeners();

    }

    /**
     * Performs undo of the last move made by player
     */
    public void undo_move() {
        if (!performed_moves.isEmpty()) {
            this.performed_moves.get(performed_moves.size() - 1).undo();
            this.performed_moves.remove(performed_moves.size() - 1);
        }

        notifyListeners();
    }

    /**
     * Method searches for card on the board.
     * @param c Card to find on the board.
     * @return Returns index of certain pile which contains Card.
     */
    private int findCard (Card c) {
        for(int i = 0; i < 13; i++) {
            for(int j = 0; j < getPile(i).size(); j++) {
                if (getPile(i).get(j).getValue() == c.getValue() && getPile(i).get(j).getSuit() == c.getSuit()) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Method searches for position of Card in pile given by index.
     * @param c Card instance, which we try to find in certain pile given by index.
     * @param index Indetifies a certain pile via getPile(index).
     * @return Returns on which position we can find given card in a pile given by int index. Returns -1 if not found in pile.
     */
    public int findCardInPile(Card c, int index) {
        Pile p = getPile(index);
        for(int i = 0; i < p.size(); i++) {
            if (p.get(i).getValue() == c.getValue() && p.get(i).getSuit() == c.getSuit()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method returns if Card is on top of pile given by index.
     * @param c Card instance, which we search in certain pile given by index.
     * @param index Indetifies a certain pile via getPile(index).
     * @return Method returns if Card is on top of pile given by index.
     */
    private boolean isTop(Card c, int index) {
        return getPile(index).get().getValue() == c.getValue() && getPile(index).get().getSuit() == c.getSuit();
    }


}
