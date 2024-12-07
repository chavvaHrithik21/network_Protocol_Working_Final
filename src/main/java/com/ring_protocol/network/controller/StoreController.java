package com.ring_protocol.network.controller;

import com.ring_protocol.network.model.Store;
import com.ring_protocol.network.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * Stores a message in the store of a specific node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the stored Store object
     */
    @PostMapping("/{nodeId}/messages/{messageId}")
    public ResponseEntity<Store> storeMessage(@PathVariable String nodeId, @PathVariable String messageId) {
        try {
            Store store = storeService.storeMessage(nodeId, messageId);
            return ResponseEntity.ok(store);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves all messages stored in a specific node's store.
     *
     * @param nodeId the ID of the node
     * @return list of messages in the node's store
     */
    @GetMapping("/{nodeId}/messages")
    public ResponseEntity<List<Store>> getMessagesByNodeId(@PathVariable String nodeId) {
        List<Store> messages = storeService.getMessagesByNodeId(nodeId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Retrieves a specific message from the store by node ID and message ID.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return the Store object if found
     */
    @GetMapping("/{nodeId}/messages/{messageId}")
    public ResponseEntity<Store> getMessageFromStore(@PathVariable String nodeId, @PathVariable String messageId) {
        Optional<Store> store = storeService.getMessageFromStore(nodeId, messageId);
        return store.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a specific message from the store of a node.
     *
     * @param nodeId the ID of the node
     * @param messageId the ID of the message
     * @return a ResponseEntity indicating the result
     */
    @DeleteMapping("/{nodeId}/messages/{messageId}")
    public ResponseEntity<Void> deleteMessageFromStore(@PathVariable String nodeId, @PathVariable String messageId) {
        try {
            storeService.deleteMessageFromStore(nodeId, messageId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
