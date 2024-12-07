package com.ring_protocol.network.service;

import com.ring_protocol.network.model.Node;
import com.ring_protocol.network.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodeService {

    private final NodeRepository nodeRepository;

    @Autowired
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    /**
     * Retrieves all nodes in the network.
     *
     * @return list of all Node objects
     */
    public List<Node> getAllNodes() {
        return nodeRepository.findAll();
    }

    /**
     * Retrieves details of a specific node by nodeId.
     *
     * @param nodeId the ID of the node to retrieve
     * @return an Optional containing the Node if found
     */
    public Optional<Node> getNodeById(String nodeId) {
        return nodeRepository.findById(nodeId);
    }

    /**
     * Creates or updates a node in the network.
     *
     * @param node the Node object to create or update
     * @return the saved Node object

    public Node saveNode(Node node) {
        return nodeRepository.save(node);
    }*/

    public Node saveNode(Node node) {
        if (node.getLeftNeighbor() != null && !nodeExists(node.getLeftNeighbor())) {
            throw new IllegalArgumentException("Left neighbor does not exist.");
        }
        if (node.getRightNeighbor() != null && !nodeExists(node.getRightNeighbor())) {
            throw new IllegalArgumentException("Right neighbor does not exist.");
        }
        return nodeRepository.save(node);
    }


    /**
     * Deletes a node from the network by nodeId.
     *
     * @param nodeId the ID of the node to delete
     * @throws IllegalArgumentException if the node does not exist
     */
    public void deleteNode(String nodeId) {
        if (!nodeRepository.existsById(nodeId)) {
            throw new IllegalArgumentException(" does not exist.");
        }
        nodeRepository.deleteById(nodeId);
    }

    /**
     * Updates the left and right neighbors of a node.
     *
     * @param nodeId the ID of the node to update
     * @param leftNeighborId the ID of the left neighbor
     * @param rightNeighborId the ID of the right neighbor
     * @return the updated Node object if found and updated
     * @throws IllegalArgumentException if the node or its neighbors do not exist
     */
    public Node updateNeighbors(String nodeId, String leftNeighborId, String rightNeighborId) {
        if (!nodeRepository.existsById(nodeId)) {
            throw new IllegalArgumentException("Node with ID " + nodeId + " does not exist.");
        }
        if (!nodeRepository.existsById(leftNeighborId)) {
            throw new IllegalArgumentException("Left neighbor with ID " + leftNeighborId + " does not exist.");
        }
        if (!nodeRepository.existsById(rightNeighborId)) {
            throw new IllegalArgumentException("Right neighbor with ID " + rightNeighborId + " does not exist.");
        }

        Node node = nodeRepository.findById(nodeId).get();
        node.setLeftNeighbor(leftNeighborId);
        node.setRightNeighbor(rightNeighborId);
        return nodeRepository.save(node);
    }

    public boolean nodeExists(String nodeId) {
        return nodeRepository.existsById(nodeId);
    }

    /**
     * Updates the status of a node.
     *
     * @param nodeId the ID of the node
     * @param status the new status of the node
     * @return the updated Node object
     * @throws IllegalArgumentException if the node does not exist
     */
    public Node updateStatus(String nodeId, String status) {
        Optional<Node> optionalNode = nodeRepository.findById(nodeId);
        if (optionalNode.isEmpty()) {
            throw new IllegalArgumentException("Node with ID " + nodeId + " does not exist.");
        }

        Node node = optionalNode.get();
        node.setStatus(status);
        return nodeRepository.save(node);
    }
}
