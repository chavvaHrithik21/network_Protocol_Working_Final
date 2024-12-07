package com.ring_protocol.network.controller;

import com.ring_protocol.network.model.Inbox;
import com.ring_protocol.network.model.Message;
import com.ring_protocol.network.model.Store;
import com.ring_protocol.network.model.SystemBuffer;
import com.ring_protocol.network.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Creates a new message.
     *
     * @param message the Message object to create
     * @return the created Message object
     */
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId the ID of the message to retrieve
     * @return the Message object if found
     */
    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable String messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Stores a message in the inbox of a specific node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the stored Inbox object
     */
    @PostMapping("/{nodeId}/inbox/{messageId}")
    public ResponseEntity<Inbox> storeMessageInInbox(@PathVariable String nodeId, @PathVariable String messageId) {
        try {
            Inbox inbox = messageService.storeMessageInInbox(nodeId, messageId);
            return ResponseEntity.ok(inbox);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Stores a message in the store of a specific node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the stored Store object
     */
    @PostMapping("/{nodeId}/store/{messageId}")
    public ResponseEntity<Store> storeMessageInStore(@PathVariable String nodeId, @PathVariable String messageId) {
        try {
            Store store = messageService.storeMessageInStore(nodeId, messageId);
            return ResponseEntity.ok(store);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Stores a message in the system buffer.
     *
     * @param messageId the ID of the message
     * @param bufferSize the buffer size to allocate
     * @return the stored SystemBuffer object
     */
    @PostMapping("/system-buffer/{messageId}")
    public ResponseEntity<SystemBuffer> storeMessageInSystemBuffer(
            @PathVariable String messageId, @RequestParam Integer bufferSize) {
        try {
            SystemBuffer systemBuffer = messageService.storeMessageInSystemBuffer(messageId, bufferSize);
            return ResponseEntity.ok(systemBuffer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves a message from the inbox by node ID and message ID.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the Inbox object if found
     */
    @GetMapping("/{nodeId}/inbox/{messageId}")
    public ResponseEntity<Inbox> getMessageFromInbox(@PathVariable String nodeId, @PathVariable String messageId) {
        Optional<Inbox> inbox = messageService.getMessageFromInbox(nodeId, messageId);
        return inbox.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a message from the store by node ID and message ID.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the Store object if found
     */
    @GetMapping("/{nodeId}/store/{messageId}")
    public ResponseEntity<Store> getMessageFromStore(@PathVariable String nodeId, @PathVariable String messageId) {
        Optional<Store> store = messageService.getMessageFromStore(nodeId, messageId);
        return store.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a message from the system buffer by message ID.
     *
     * @param messageId the ID of the message to retrieve
     * @return the SystemBuffer object if found
     */
    @GetMapping("/system-buffer/{messageId}")
    public ResponseEntity<SystemBuffer> getMessageFromSystemBuffer(@PathVariable String messageId) {
        Optional<SystemBuffer> systemBuffer = messageService.getMessageFromSystemBuffer(messageId);
        return systemBuffer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
