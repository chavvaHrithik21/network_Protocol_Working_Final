package com.ring_protocol.network.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Message")
public class Message {

    @Id
    @Column(name = "MessageID", nullable = false, length = 50)
    private String messageId;

    @ManyToOne
    @JoinColumn(name = "SenderNodeID")
    private Node senderNode;

    @ManyToOne
    @JoinColumn(name = "ReceiverNodeID")
    private Node receiverNode;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "TimeStampCreated")
    private LocalDateTime timeStampCreated;

    @Column(name = "TimeStampReceived")
    private LocalDateTime timeStampReceived;

    @Column(name = "Direction", length = 20)
    private String direction; // Possible values: LEFT or RIGHT

    @Column(name = "Path", length = 255)
    private String path;

    @Column(name = "Status", length = 20)
    private String status; // Possible values: SENT, DELIVERED, etc.

    // Default constructor required by JPA
    public Message() {}

    // Constructor
    public Message(String messageId, Node senderNode, Node receiverNode, String content,
                   LocalDateTime timeStampCreated, LocalDateTime timeStampReceived,
                   String direction, String path, String status) {
        this.messageId = messageId;
        this.senderNode = senderNode;
        this.receiverNode = receiverNode;
        this.content = content;
        this.timeStampCreated = timeStampCreated;
        this.timeStampReceived = timeStampReceived;
        this.direction = direction;
        this.path = path;
        this.status = status;
    }

    // Getters and Setters
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Node getSenderNode() {
        return senderNode;
    }

    public void setSenderNode(Node senderNode) {
        this.senderNode = senderNode;
    }

    public Node getReceiverNode() {
        return receiverNode;
    }

    public void setReceiverNode(Node receiverNode) {
        this.receiverNode = receiverNode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeStampCreated() {
        return timeStampCreated;
    }

    public void setTimeStampCreated(LocalDateTime timeStampCreated) {
        this.timeStampCreated = timeStampCreated;
    }

    public LocalDateTime getTimeStampReceived() {
        return timeStampReceived;
    }

    public void setTimeStampReceived(LocalDateTime timeStampReceived) {
        this.timeStampReceived = timeStampReceived;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override equals and hashCode for entity equality based on messageId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", senderNode=" + (senderNode != null ? senderNode.getNodeId() : null) +
                ", receiverNode=" + (receiverNode != null ? receiverNode.getNodeId() : null) +
                ", content='" + content + '\'' +
                ", timeStampCreated=" + timeStampCreated +
                ", timeStampReceived=" + timeStampReceived +
                ", direction='" + direction + '\'' +
                ", path='" + path + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
