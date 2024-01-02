package gui;

import model.cards.Card;

/**
 * Stores information about card being transferred.
 * @author Marek Dolezel
 */
public class CardTransfer {

    private Card card;
    private int from;
    private int to;

    enum TransferStatus { PENDING, NOT_STARTED};

    private TransferStatus transfer_status;

    public CardTransfer() {
        transfer_status = TransferStatus.NOT_STARTED;

    }

    /**
     * Set card being transferred
     * @param c card being transferred
     */
    void setCard(Card c) { card = c; }

    /**
     * Change state to transfer done.
     */
    void setTransferStatus(TransferStatus stat) { transfer_status = stat; }

    TransferStatus getTransferStatus() { return transfer_status; }

    /**
     * Returns card to transfer.
     * @return If card is null then no transfer is pending.
     */
    Card getCard() { return card; }

}
