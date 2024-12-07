package com.ring_protocol.network.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InboxId implements Serializable {

    private String nodeId;
    private String messageId;

    // Default constructor
    public InboxId() {}

    // Constructor
    public InboxId(String nodeId, String messageId) {
        this.nodeId = nodeId;
        this.messageId = messageId;
    }

    // Getters and Setters
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    // Override equals and hashCode for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboxId inboxId = (InboxId) o;
        return Objects.equals(nodeId, inboxId.nodeId) && Objects.equals(messageId, inboxId.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, messageId);
    }
}
