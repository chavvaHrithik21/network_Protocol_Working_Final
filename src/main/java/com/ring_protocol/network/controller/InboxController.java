package com.ring_protocol.network.controller;

import com.ring_protocol.network.model.Inbox;
import com.ring_protocol.network.service.InboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inbox")
public class InboxController {

    private final InboxService inboxService;

    @Autowired
    public InboxController(InboxService inboxService) {
        this.inboxService = inboxService;
    }

    /**
     * Stores a message in the inbox of a specified node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the stored Inbox object
     */
    @PostMapping("/{nodeId}/messages/{messageId}")
    public ResponseEntity<Inbox> storeMessageInInbox(@PathVariable String nodeId, @PathVariable String messageId) {
        try {
            Inbox inbox = inboxService.storeMessageInInbox(nodeId, messageId);
            return ResponseEntity.ok(inbox);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves all messages from a specified node's inbox.
     *
     * @param nodeId the ID of the node
     * @return list of messages in the node's inbox
     */
    @GetMapping("/{nodeId}/messages")
    public ResponseEntity<List<Inbox>> getMessagesFromInbox(@PathVariable String nodeId) {
        List<Inbox> inboxMessages = inboxService.getMessagesFromInbox(nodeId);
        return ResponseEntity.ok(inboxMessages);
    }

    /**
     * Retrieves a specific message from a node's inbox.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the Inbox object if found
     */
    @GetMapping("/{nodeId}/messages/{messageId}")
    public ResponseEntity<Inbox> getMessageFromInbox(@PathVariable String nodeId, @PathVariable String messageId) {
        Optional<Inbox> inbox = inboxService.getMessageFromInbox(nodeId, messageId);
        return inbox.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a specific message from the inbox of a node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return a ResponseEntity indicating the result
     */
    @DeleteMapping("/{nodeId}/messages/{messageId}")
    public ResponseEntity<Void> deleteMessageFromInbox(@PathVariable String nodeId, @PathVariable String messageId) {
        try {
            inboxService.deleteMessageFromInbox(nodeId, messageId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
