package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;

import java.util.List;

interface MidgetStrategy {

    List<MessageBase> handleEvent(MessageBase m);
}
