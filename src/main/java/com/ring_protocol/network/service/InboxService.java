package com.ring_protocol.network.service;

import com.ring_protocol.network.model.Inbox;
import com.ring_protocol.network.model.InboxId;
import com.ring_protocol.network.model.Message;
import com.ring_protocol.network.repository.InboxRepository;
import com.ring_protocol.network.repository.MessageRepository;
import com.ring_protocol.network.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InboxService {

    private final InboxRepository inboxRepository;
    private final MessageRepository messageRepository;
    private final NodeRepository nodeRepository;

    @Autowired
    public InboxService(InboxRepository inboxRepository, MessageRepository messageRepository, NodeRepository nodeRepository) {
        this.inboxRepository = inboxRepository;
        this.messageRepository = messageRepository;
        this.nodeRepository = nodeRepository;
    }

    /**
     * Stores a message in the inbox of a node.
     *
     * @param nodeId    the ID of the node where the message is to be stored
     * @param messageId the ID of the message to store in the inbox
     * @return the stored Inbox object
     * @throws IllegalArgumentException if the node or message doesn't exist
     */
    public Inbox storeMessageInInbox(String nodeId, String messageId) {
        // Check if node and message exist
        if (!nodeRepository.existsById(nodeId)) {
            throw new IllegalArgumentException("Node with ID " + nodeId + " does not exist.");
        }
        if (!messageRepository.existsById(messageId)) {
            throw new IllegalArgumentException("Message with ID " + messageId + " does not exist.");
        }

        // Create and save the inbox entry
        Inbox inbox = new Inbox(nodeId, messageId);
        return inboxRepository.save(inbox);
    }

    /**
     * Retrieves all messages from a node's inbox.
     *
     * @param nodeId the ID of the node
     * @return list of messages in the node's inbox
     */
    public List<Inbox> getMessagesFromInbox(String nodeId) {
        return inboxRepository.findByNodeId(nodeId);
    }

    /**
     * Retrieves a specific message from a node's inbox by message ID.
     *
     * @param nodeId    the ID of the node
     * @param messageId the ID of the message to retrieve
     * @return the Inbox object if found
     */
    public Optional<Inbox> getMessageFromInbox(String nodeId, String messageId) {
        InboxId inboxId = new InboxId(nodeId, messageId);
        return inboxRepository.findById(inboxId);
    }

    /**
     * Deletes a message from the inbox of a node.
     *
     * @param nodeId    the ID of the node
     * @param messageId the ID of the message to delete
     */
    public void deleteMessageFromInbox(String nodeId, String messageId) {
        InboxId inboxId = new InboxId(nodeId, messageId);
        inboxRepository.deleteById(inboxId);
    }
}
