package com.ncr.dao;

import com.ncr.ApplicationTests;
import com.ncr.model.Node;
import com.ncr.model.TreeResponse;
import com.ncr.util.TreeHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static com.ncr.common.ApiConstants.ROOT_KEY;
import static com.ncr.common.ApiConstants.nodePool;
import static org.junit.Assert.*;

/**
 * Created by SINHAVI on 12/31/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class TreeDaoImplTest {

    TreeDao target = new TreeDaoImpl();

    @Before
    public void setUp() throws Exception {
        TreeHelper treeHelper = new TreeHelper();
        ReflectionTestUtils.setField(target, "treeHelper", treeHelper, TreeHelper.class);
    }

    @BeforeClass
    public static void beforeClass() {
        if(!nodePool.containsKey(ROOT_KEY)) {
            Node treeRootNode = new Node(null);
            treeRootNode.setName("root");
            treeRootNode.setDescription("This is the root node");
            nodePool.put(ROOT_KEY, treeRootNode);
        }
    }

    public void test_createRootNode() {
        ResponseEntity<TreeResponse<Node>> result = target.createRootNode();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().getData().getName(), ROOT_KEY);
    }


    @Test
    public void test_addChild() throws Exception {
        Node result = target.addChild("root", "test");
        assertEquals(result.getDescription(), "test");
        assertEquals(result.getParent().getName(), ROOT_KEY);
    }

    @Test
    public void test_deleteNode() throws Exception {
        //To make sure atleast one child is present
        test_addChild();
        Node node = target.deleteNode("child1");
        assertEquals(node.getName(), "child1");
        assertEquals(node.getParent().getName(), ROOT_KEY);
    }

}