package com.ncr.dao;

import com.ncr.exception.ExceedsMaxAllowedChildrenException;
import com.ncr.model.Node;
import com.ncr.model.TreeResponse;
import com.ncr.util.TreeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ncr.common.ApiConstants.*;

/**
 * Created by SINHAVI on 12/31/2016.
 */

@Repository
public class TreeDaoImpl implements TreeDao {

    private static final Logger LOG = LoggerFactory.getLogger(TreeDaoImpl.class);

    @Autowired
    TreeHelper treeHelper;

    /** {@link TreeDao#createRootNode()} */
    @Override
    public ResponseEntity<TreeResponse<Node>> createRootNode() {

        if (!nodePool.containsKey(ROOT_KEY)) {
            Node treeRootNode = new Node(null);
            treeRootNode.setName("root");
            treeRootNode.setDescription("This is the root node");
            addNodeToPool(treeRootNode);
            return treeHelper.createResponse(treeRootNode, HttpStatus.CREATED, SUCCESS, SUCCESS_DESCRIPTION);
        } else {
            LOG.error(ROOT_EXISTS_MSG);
            return treeHelper.createResponse(null, HttpStatus.CONFLICT, FAILURE, ROOT_EXISTS_MSG);
        }

    }

    /** {@link TreeDao#addChild(String, String)} */
    @Override
    public Node addChild(String parentId, String description) throws ExceedsMaxAllowedChildrenException {
        Node parent = nodePool.get(parentId);
        int numberOfChildren = parent.getChildren().size();
        if(numberOfChildren == MAX_CHILDREN) {
            throw new ExceedsMaxAllowedChildrenException(EXCEEDS_MAX_CHILDREN_MSG);
        }

        Node node = new Node(parent);
        node.setName("child"+ counter.getAndIncrement());
        node.setDescription(description);
        parent.getChildren().add(node);
        addNodeToPool(node);
        return node;
    }

    /** {@link TreeDao#deleteNode(String)} */
    @Override
    public Node deleteNode(String name) {
        Node node = nodePool.get(name);
        Node parent = node.getParent();
        if (parent != null) {
            parent.getChildren().remove(node);
            deleteNodeFromPool(node);
        }
        return node;
    }

    private void addNodeToPool(Node node) {
        nodePool.put(node.getName(), node);
    }

    private void deleteNodeFromPool(Node node) {
        nodePool.remove(node.getName());
        for(Node childNode: node.getChildren()) {
            deleteNodeFromPool(childNode);
        }
    }

}
