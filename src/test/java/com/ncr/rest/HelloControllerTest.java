package com.ncr.rest;

import com.ncr.ApplicationTests;
import com.ncr.model.Node;
import com.ncr.model.TreeResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ncr.common.ApiConstants.PRINT_TREE_MSG;
import static com.ncr.common.ApiConstants.ROOT_KEY;
import static com.ncr.common.ApiConstants.nodePool;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by SINHAVI on 12/31/2016.
 */

public class HelloControllerTest extends ApplicationTests {

    @Autowired
    HelloController target;

    @BeforeClass
    public static void beforeClass() {
        if(!nodePool.containsKey(ROOT_KEY)) {
            Node treeRootNode = new Node(null);
            treeRootNode.setName("root");
            treeRootNode.setDescription("This is the root node");
            nodePool.put(ROOT_KEY, treeRootNode);
        }
    }

    @Test
    public void test_createRootNode() throws Exception {
        assertNotNull(nodePool.get(ROOT_KEY));
    }

    @Test
    public void test_printTree() throws Exception {
        ResponseEntity<TreeResponse<String>> response = target.printTree();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getData(), PRINT_TREE_MSG);
    }

    @Test
    public void test_addChildNode() throws Exception {
        ResponseEntity<TreeResponse<Node>> response = target.addChildNode(ROOT_KEY, "test");
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getData().getDescription(), "test");
    }

    @Test
    public void test_getNode() throws Exception {
        ResponseEntity<TreeResponse<Node>> response = target.getNode(ROOT_KEY);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getData().getName(), ROOT_KEY);
    }

    @Test
    public void test_deleteNode() throws Exception {
        //To make sure atleast two children are present
        target.addChildNode(ROOT_KEY, "test");
        target.addChildNode(ROOT_KEY, "test");
        ResponseEntity<TreeResponse<Node>> response = target.deleteNode("child2");
        System.out.print(nodePool.toString());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getData().getName(), "child2");
    }

}