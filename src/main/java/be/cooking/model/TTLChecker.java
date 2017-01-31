package be.cooking.model;


import be.cooking.model.messages.MessageBase;

public class TTLChecker<T extends MessageBase> implements Handler<T> {

    private Handler<T> handler;

    public TTLChecker(Handler<T> handler) {
        this.handler = handler;
    }

    @Override
    public void handle(T object) {

        if (object instanceof Expirable) {
            Expirable expirableObject = (Expirable) object;
            if (expirableObject.isExpired()) {
                System.out.println("Drop!");
                expirableObject.drop();
            } else
                handler.handle(object);
        }
        else
            handler.handle(object);
    }
}
