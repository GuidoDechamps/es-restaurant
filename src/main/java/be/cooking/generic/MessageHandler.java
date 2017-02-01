package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

public class MessageHandler<T extends MessageBase> implements Handler<T>
{
    @Override
    public void handle(T value)
    {
        System.out.println("Message (cor:"+value.getCorrelationUUID()+") :" + value.toString());
    }
}
