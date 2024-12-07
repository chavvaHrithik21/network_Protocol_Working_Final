package com.ring_protocol.network.controller;

import com.ring_protocol.network.model.SystemBuffer;
import com.ring_protocol.network.service.SystemBufferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/system-buffer")
public class SystemBufferController {

    private final SystemBufferService systemBufferService;

    @Autowired
    public SystemBufferController(SystemBufferService systemBufferService) {
        this.systemBufferService = systemBufferService;
    }

    /**
     * Stores a message in the system buffer with a specified buffer size.
     *
     * @param messageId the ID of the message to store in the buffer
     * @param bufferSize the size of the buffer to allocate
     * @return the stored SystemBuffer object
     */
    @PostMapping("/{messageId}")
    public ResponseEntity<SystemBuffer> storeMessageInBuffer(
            @PathVariable String messageId,
            @RequestParam Integer bufferSize) {
        try {
            SystemBuffer systemBuffer = systemBufferService.storeMessageInBuffer(messageId, bufferSize);
            return ResponseEntity.ok(systemBuffer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves a message from the system buffer by message ID.
     *
     * @param messageId the ID of the message to retrieve from the buffer
     * @return the SystemBuffer object if found
     */
    @GetMapping("/{messageId}")
    public ResponseEntity<SystemBuffer> getMessageFromBuffer(@PathVariable String messageId) {
        Optional<SystemBuffer> systemBuffer = systemBufferService.getMessageFromBuffer(messageId);
        return systemBuffer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a message from the system buffer by message ID.
     *
     * @param messageId the ID of the message to delete from the buffer
     * @return a ResponseEntity indicating the result
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessageFromBuffer(@PathVariable String messageId) {
        systemBufferService.deleteMessageFromBuffer(messageId);
        return ResponseEntity.noContent().build();
    }
}
