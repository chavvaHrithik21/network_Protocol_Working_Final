package com.ring_protocol.network.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SystemBuffer")
public class SystemBuffer {

    @Id
    @Column(name = "MessageID", nullable = false)
    private String messageId; // FK to Message

    @Column(name = "BufferSize")
    private Integer bufferSize;

    // Default constructor required by JPA
    public SystemBuffer() {}

    // Constructor for easy instantiation
    public SystemBuffer(String messageId, Integer bufferSize) {
        this.messageId = messageId;
        this.bufferSize = bufferSize;
    }

    // Getters and Setters
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    // Override equals and hashCode for entity equality based on messageId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemBuffer that = (SystemBuffer) o;
        return messageId.equals(that.messageId);
    }

    @Override
    public int hashCode() {
        return messageId.hashCode();
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "SystemBuffer{" +
                "messageId='" + messageId + '\'' +
                ", bufferSize=" + bufferSize +
                '}';
    }
}
