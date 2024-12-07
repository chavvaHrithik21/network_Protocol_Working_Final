package com.ring_protocol.network.model;

import jakarta.persistence.*;

@Entity
@Table(name = "node")  // Ensure the table name is correct
public class Node {
    @Id
    @Column(name = "NodeID")  // Map to the correct column in the database
    private String nodeId;

    @Column(name = "left_neighborid")  // Map to the correct column
    private String leftNeighbor;

    @Column(name = "right_neighborid")  // Map to the correct column
    private String rightNeighbor;

    @Column(name = "status")  // This column name matches directly
    private String status;

    @Column(name = "inbox_size")  // Use the correct column name
    private Integer inboxSize;

    // Getters and Setters
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getLeftNeighbor() {
        return leftNeighbor;
    }

    public void setLeftNeighbor(String leftNeighbor) {
        this.leftNeighbor = leftNeighbor;
    }

    public String getRightNeighbor() {
        return rightNeighbor;
    }

    public void setRightNeighbor(String rightNeighbor) {
        this.rightNeighbor = rightNeighbor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getInboxSize() {
        return inboxSize;
    }

    public void setInboxSize(Integer inboxSize) {
        this.inboxSize = inboxSize;
    }
}
