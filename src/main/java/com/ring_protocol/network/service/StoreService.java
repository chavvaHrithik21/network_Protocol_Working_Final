package com.ring_protocol.network.service;

import com.ring_protocol.network.model.Store;
import com.ring_protocol.network.model.StoreId;
import com.ring_protocol.network.repository.StoreRepository;
import com.ring_protocol.network.repository.NodeRepository;
import com.ring_protocol.network.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final NodeRepository nodeRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, NodeRepository nodeRepository, MessageRepository messageRepository) {
        this.storeRepository = storeRepository;
        this.nodeRepository = nodeRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Stores a message in the store of a specific node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the stored Store object
     * @throws IllegalArgumentException if the node or message doesn't exist
     */
    public Store storeMessage(String nodeId, String messageId) {
        // Check if node and message exist
        if (!nodeRepository.existsById(nodeId)) {
            throw new IllegalArgumentException("Node with ID " + nodeId + " does not exist.");
        }
        if (!messageRepository.existsById(messageId)) {
            throw new IllegalArgumentException("Message with ID " + messageId + " does not exist.");
        }

        // Create and save the store entry
        Store store = new Store(nodeId, messageId);
        return storeRepository.save(store);
    }

    /**
     * Retrieves all messages stored in a node's store.
     *
     * @param nodeId the ID of the node
     * @return list of Store objects related to the node's store
     */
    public List<Store> getMessagesByNodeId(String nodeId) {
        return storeRepository.findByNodeId(nodeId);
    }

    /**
     * Retrieves a specific message from the store by node ID and message ID.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message to retrieve
     * @return an Optional containing the Store object if found
     */
    public Optional<Store> getMessageFromStore(String nodeId, String messageId) {
        StoreId storeId = new StoreId(nodeId, messageId);
        return storeRepository.findById(storeId);
    }

    /**
     * Deletes a specific message from the store of a node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message to delete
     */
    public void deleteMessageFromStore(String nodeId, String messageId) {
        StoreId storeId = new StoreId(nodeId, messageId);
        storeRepository.deleteById(storeId);
    }
}
