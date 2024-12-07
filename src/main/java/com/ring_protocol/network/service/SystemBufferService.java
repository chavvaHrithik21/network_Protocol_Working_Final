package com.ring_protocol.network.service;

import com.ring_protocol.network.model.SystemBuffer;
import com.ring_protocol.network.repository.SystemBufferRepository;
import com.ring_protocol.network.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemBufferService {

    private final SystemBufferRepository systemBufferRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public SystemBufferService(SystemBufferRepository systemBufferRepository, MessageRepository messageRepository) {
        this.systemBufferRepository = systemBufferRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Stores a message in the system buffer with a specified buffer size.
     *
     * @param messageId the ID of the message to store in the buffer
     * @param bufferSize the size of the buffer to allocate
     * @return the stored SystemBuffer object
     * @throws IllegalArgumentException if the message doesn't exist
     */
    public SystemBuffer storeMessageInBuffer(String messageId, Integer bufferSize) {
        // Check if the message exists
        if (!messageRepository.existsById(messageId)) {
            throw new IllegalArgumentException("Message with ID " + messageId + " does not exist.");
        }

        // Create and save the system buffer entry
        SystemBuffer systemBuffer = new SystemBuffer();
        systemBuffer.setMessageId(messageId);
        systemBuffer.setBufferSize(bufferSize);
        return systemBufferRepository.save(systemBuffer);
    }

    /**
     * Retrieves a message from the system buffer by message ID.
     *
     * @param messageId the ID of the message to retrieve from the buffer
     * @return an Optional containing the SystemBuffer object if found
     */
    public Optional<SystemBuffer> getMessageFromBuffer(String messageId) {
        return systemBufferRepository.findById(messageId);
    }

    /**
     * Deletes a message from the system buffer by message ID.
     *
     * @param messageId the ID of the message to delete from the buffer
     */
    public void deleteMessageFromBuffer(String messageId) {
        systemBufferRepository.deleteById(messageId);
    }
}
