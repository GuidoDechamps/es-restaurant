package be.cooking.model.actors;

import be.cooking.generic.messages.MessageBase;

import java.util.Optional;

interface MidgetStrategy {

    Optional<MessageBase> handleEvent(MessageBase m);
}
