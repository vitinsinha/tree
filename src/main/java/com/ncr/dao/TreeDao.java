package com.ncr.dao;

import com.ncr.exception.ExceedsMaxAllowedChildrenException;
import com.ncr.model.Node;
import com.ncr.model.TreeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by SINHAVI on 12/31/2016.
 */

public interface TreeDao {

    /**
     *
     * @return success or failure
     */
    ResponseEntity<TreeResponse<Node>> createRootNode();

    /**
     *
     * @param parentId
     * @param description
     * @return newly created node
     */
    Node addChild(String parentId, String description) throws ExceedsMaxAllowedChildrenException;

    /**
     *
     * @param name
     * @return deleted node
     */
    Node deleteNode(String name);

}
