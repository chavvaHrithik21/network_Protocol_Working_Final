package com.ring_protocol.network.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(InboxId.class)
@Table(name = "inbox")
public class Inbox {

    @Id
    @Column(name = "NodeID", nullable = false)
    private String nodeId;

    @Id
    @Column(name = "MessageID", nullable = false)
    private String messageId;

    // Constructors
    public Inbox() {}

    public Inbox(String nodeId, String messageId) {
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

    // Override equals and hashCode to match the composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inbox inbox = (Inbox) o;
        return Objects.equals(nodeId, inbox.nodeId) && Objects.equals(messageId, inbox.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, messageId);
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Inbox{" +
                "nodeId='" + nodeId + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
