package com.ring_protocol.network.repository;

import com.ring_protocol.network.model.Store;
import com.ring_protocol.network.model.StoreId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StoreRepository extends JpaRepository<Store, StoreId> {


    /**
     * Finds all Store entries for a given node ID.
     *
     * @param nodeId the ID of the node
     * @return list of Store entries associated with the specified node ID
     */
    List<Store> findByNodeId(String nodeId);
}
