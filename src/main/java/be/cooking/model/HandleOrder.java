package be.cooking.model;

/**
 * Handles<T>
 *
 *     MsgBase
 *     OrderPlaced: MsgBase
 *     OrderCooked: MsgBase
 *     OrderPriced: MsgBase
 *     OrderPaid: MsgBase
 *
 *     order not in msgBase but in MSG
 *
 *     Topic generic arguments, topic itself not generic
 *
 *     Define messages
 */
public interface HandleOrder {

    void handle(Order order);
}
