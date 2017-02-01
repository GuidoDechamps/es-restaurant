package be.cooking.model.messages;

import be.cooking.generic.messages.MessageBase;

import java.util.UUID;

public class PublishAt extends MessageBase {
    private final MessageBase messageToPublish;
    private final long timeToPublish;

    public PublishAt(MessageBase messageToPublish, UUID correlationUUID, UUID causeUUID, long timeToPublish) {
        super(correlationUUID, causeUUID);
        this.messageToPublish = messageToPublish;
        this.timeToPublish = timeToPublish;
    }

    public MessageBase getMessageToPublish() {
        return messageToPublish;
    }

    public long getTimeToPublish() {
        return timeToPublish;
    }
}
