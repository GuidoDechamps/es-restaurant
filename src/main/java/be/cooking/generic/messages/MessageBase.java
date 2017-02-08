package be.cooking.generic.messages;

import java.util.Optional;
import java.util.UUID;

public abstract class MessageBase<T> {
    private final UUID messageUUID = UUID.randomUUID();
    private final UUID correlationUUID;
    private final UUID causeUUID;

    public MessageBase(UUID correlationUUID, UUID causeUUID) {
        checkNotNull(correlationUUID);
        checkNotNull(causeUUID);
        this.correlationUUID = correlationUUID;
        this.causeUUID = causeUUID;
    }

    public MessageBase(MessageBase<?> causationMessage) {
        this(causationMessage.correlationUUID, causationMessage.messageUUID);
    }

    public MessageBase(UUID correlationUUID) {
        checkNotNull(correlationUUID);
        this.correlationUUID = correlationUUID;
        this.causeUUID = null;
    }

    public abstract T getContent();

    private void checkNotNull(UUID uuid) {
        if (uuid == null)
            throw new RuntimeException("Correlation may not be null");
    }

    public UUID getMessageUUID() {
        return messageUUID;
    }

    public UUID getCorrelationUUID() {
        return correlationUUID;
    }

    public Optional<UUID> getCauseUUID() {
        return Optional.of(causeUUID);
    }
}
