//package com.ring_protocol.network.controller;
//
//import com.ring_protocol.network.model.Node;
//import com.ring_protocol.network.service.NodeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/nodes")
//public class NodeController {
//
//    private final NodeService nodeService;
//
//    @Autowired
//    public NodeController(NodeService nodeService) {
//        this.nodeService = nodeService;
//    }
//
//    /**
//     * Retrieves all nodes in the network.
//     *
//     * @return list of all Node objects
//     */
//    @GetMapping
//    public ResponseEntity<List<Node>> getAllNodes() {
//        List<Node> nodes = nodeService.getAllNodes();
//        return ResponseEntity.ok(nodes);
//    }
//
//    /**
//     * Retrieves details of a specific node by nodeId.
//     *
//     * @param nodeId the ID of the node to retrieve
//     * @return the Node object if found
//     */
//    @GetMapping("/{nodeId}")
//    public ResponseEntity<Node> getNodeById(@PathVariable String nodeId) {
//        Optional<Node> node = nodeService.getNodeById(nodeId);
//        return node.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    /**
//     * Creates or updates a node in the network.
//     *
//     * @param node the Node object to create or update
//     * @return the saved Node object
//     */
//    @PostMapping
//    public ResponseEntity<Node> saveNode(@RequestBody Node node) {
//        Node savedNode = nodeService.saveNode(node);
//        return ResponseEntity.ok(savedNode);
//    }
//
//    /**
//     * Updates the left and right neighbors of a node.
//     *
//     * @param nodeId the ID of the node to update
//     * @param leftNeighborId the ID of the left neighbor
//     * @param rightNeighborId the ID of the right neighbor
//     * @return the updated Node object
//     */
//    @PutMapping("/{nodeId}/neighbors")
//    public ResponseEntity<Node> updateNeighbors(
//            @PathVariable String nodeId,
//            @RequestParam String leftNeighborId,
//            @RequestParam String rightNeighborId) {
//        try {
//            Node updatedNode = nodeService.updateNeighbors(nodeId, leftNeighborId, rightNeighborId);
//            return ResponseEntity.ok(updatedNode);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    /**
//     * Updates the status of a node.
//     *
//     * @param nodeId the ID of the node
//     * @param status the new status of the node (e.g., ACTIVE or INACTIVE)
//     * @return the updated Node object
//     */
//    @PutMapping("/{nodeId}/status")
//    public ResponseEntity<Node> updateStatus(@PathVariable String nodeId, @RequestParam String status) {
//        try {
//            Node updatedNode = nodeService.updateStatus(nodeId, status);
//            return ResponseEntity.ok(updatedNode);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> bulkDeleteNotAllowed() {
//        return ResponseEntity.status(405).build(); // 405 Method Not Allowed
//    }
//
//
//    /**
//     * Deletes a node from the network by nodeId.
//     *
//     * @param nodeId the ID of the node to delete
//     * @return a ResponseEntity indicating the result
//     */
//    @DeleteMapping("/{nodeId}")
//    public ResponseEntity<Void> deleteNode(@PathVariable String nodeId) {
//        try {
//            nodeService.deleteNode(nodeId);
//            return ResponseEntity.noContent().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
package com.ring_protocol.network.controller;

import com.ring_protocol.network.model.Node;
import com.ring_protocol.network.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    private final NodeService nodeService;

    @Autowired
    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    /**
     * Retrieves all nodes in the network.
     *
     * @return list of all Node objects
     */
    @GetMapping
    public ResponseEntity<List<Node>> getAllNodes() {
        List<Node> nodes = nodeService.getAllNodes();
        if (nodes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(nodes);
    }

    /**
     * Retrieves details of a specific node by nodeId.
     *
     * @param nodeId the ID of the node to retrieve
     * @return the Node object if found
     */
    @GetMapping("/{nodeId}")
    public ResponseEntity<Node> getNodeById(@PathVariable String nodeId) {
        Optional<Node> node = nodeService.getNodeById(nodeId);
        if (node.isPresent()) {
            return ResponseEntity.ok(node.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Creates or updates a node in the network.
     *
     * @param node the Node object to create or update
     * @return the saved Node object
     */
    @PostMapping
    public ResponseEntity<Node> saveNode(@RequestBody Node node) {
        try {
            // Ensure neighbors exist before saving
            if (node.getLeftNeighbor() != null && !nodeService.nodeExists(node.getLeftNeighbor())) {
                throw new IllegalArgumentException("Left neighbor does not exist.");
            }
            if (node.getRightNeighbor() != null && !nodeService.nodeExists(node.getRightNeighbor())) {
                throw new IllegalArgumentException("Right neighbor does not exist.");
            }

            Node savedNode = nodeService.saveNode(node);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the left and right neighbors of a node.
     *
     * @param nodeId the ID of the node to update
     * @param leftNeighborId the ID of the left neighbor
     * @param rightNeighborId the ID of the right neighbor
     * @return the updated Node object
     */
    @PutMapping("/{nodeId}/neighbors")
    public ResponseEntity<Node> updateNeighbors(
            @PathVariable String nodeId,
            @RequestParam String leftNeighborId,
            @RequestParam String rightNeighborId) {
        try {
            Node updatedNode = nodeService.updateNeighbors(nodeId, leftNeighborId, rightNeighborId);
            return ResponseEntity.ok(updatedNode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the status of a node.
     *
     * @param nodeId the ID of the node
     * @param status the new status of the node (e.g., ACTIVE or INACTIVE)
     * @return the updated Node object
     */
    @PutMapping("/{nodeId}/status")
    public ResponseEntity<Node> updateStatus(@PathVariable String nodeId, @RequestParam String status) {
        try {
            Node updatedNode = nodeService.updateStatus(nodeId, status);
            return ResponseEntity.ok(updatedNode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Prevents bulk delete operation.
     *
     * @return a ResponseEntity indicating that the operation is not allowed.
     */
    @DeleteMapping
    public ResponseEntity<Void> bulkDeleteNotAllowed() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    /**
     * Deletes a node from the network by nodeId.
     *
     * @param nodeId the ID of the node to delete
     * @return a ResponseEntity indicating the result
     */
    @DeleteMapping("/{nodeId}")
    public ResponseEntity<Void> deleteNode(@PathVariable String nodeId) {
        try {
            nodeService.deleteNode(nodeId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
