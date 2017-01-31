package be.cooking.model;

import be.cooking.model.messages.MessageBase;

public interface Handler<T extends MessageBase>
{
    void handle(T value);
}
