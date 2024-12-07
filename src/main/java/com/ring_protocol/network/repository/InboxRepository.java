package com.ring_protocol.network.repository;

import com.ring_protocol.network.model.Inbox;
import com.ring_protocol.network.model.InboxId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InboxRepository extends JpaRepository<Inbox, InboxId> {

    List<Inbox> findByNodeId(String nodeId);
}

