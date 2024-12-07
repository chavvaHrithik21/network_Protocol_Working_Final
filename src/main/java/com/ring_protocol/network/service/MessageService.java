package com.ring_protocol.network.service;

import com.ring_protocol.network.model.*;
import com.ring_protocol.network.repository.MessageRepository;
import com.ring_protocol.network.repository.InboxRepository;
import com.ring_protocol.network.repository.StoreRepository;
import com.ring_protocol.network.repository.SystemBufferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final InboxRepository inboxRepository;
    private final StoreRepository storeRepository;
    private final SystemBufferRepository systemBufferRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          InboxRepository inboxRepository,
                          StoreRepository storeRepository,
                          SystemBufferRepository systemBufferRepository) {
        this.messageRepository = messageRepository;
        this.inboxRepository = inboxRepository;
        this.storeRepository = storeRepository;
        this.systemBufferRepository = systemBufferRepository;
    }

    /**
     * Stores a new message in the database.
     *
     * @param message the message to be stored
     * @return the saved Message object
     */
    public Message createMessage(Message message) {
        message.setTimeStampCreated(LocalDateTime.now());
        return messageRepository.save(message);
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId the ID of the message
     * @return an Optional containing the Message if found
     */
    public Optional<Message> getMessageById(String messageId) {
        return messageRepository.findById(messageId);
    }

    /**
     * Stores a message in the inbox of a specific node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the stored Inbox object
     * @throws IllegalArgumentException if the node or message doesn't exist
     */
    public Inbox storeMessageInInbox(String nodeId, String messageId) {
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
     * Stores a message in the store of a specific node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the stored Store object
     * @throws IllegalArgumentException if the node or message doesn't exist
     */
    public Store storeMessageInStore(String nodeId, String messageId) {
        Store store = new Store(nodeId, messageId);
        return storeRepository.save(store);
    }

    /**
     * Retrieves a message from the store by node ID and message ID.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return an Optional containing the Store object if found
     */
    public Optional<Store> getMessageFromStore(String nodeId, String messageId) {
        return storeRepository.findById(new StoreId(nodeId, messageId));
    }

    /**
     * Stores a message in the system buffer.
     *
     * @param messageId the ID of the message to store in the buffer
     * @param bufferSize the buffer size to allocate
     * @return the stored SystemBuffer object
     */
    public SystemBuffer storeMessageInSystemBuffer(String messageId, Integer bufferSize) {
        SystemBuffer systemBuffer = new SystemBuffer(messageId, bufferSize);
        return systemBufferRepository.save(systemBuffer);
    }

    /**
     * Retrieves a specific message from a node's inbox by node ID and message ID.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return an Optional containing the Inbox object if found
     */
    public Optional<Inbox> getMessageFromInbox(String nodeId, String messageId) {
        return inboxRepository.findById(new InboxId(nodeId, messageId));
    }


    /**
     * Retrieves a message from the system buffer by message ID.
     *
     * @param messageId the ID of the message
     * @return an Optional containing the SystemBuffer object if found
     */
    public Optional<SystemBuffer> getMessageFromSystemBuffer(String messageId) {
        return systemBufferRepository.findById(messageId);
    }
}
