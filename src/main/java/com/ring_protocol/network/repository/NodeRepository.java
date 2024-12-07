package com.ring_protocol.network.repository;

import com.ring_protocol.network.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, String> {}

