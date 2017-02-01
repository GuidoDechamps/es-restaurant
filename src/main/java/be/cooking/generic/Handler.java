package be.cooking.generic;

import be.cooking.generic.messages.MessageBase;

public interface Handler<T extends MessageBase>
{
    void handle(T value);
}
