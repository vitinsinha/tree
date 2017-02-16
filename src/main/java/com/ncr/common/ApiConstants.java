package com.ncr.common;

import com.ncr.model.Node;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ApiConstants {

	public static final String BASE_URL = "/svc/v1";
    public static final String ROOT_KEY = "root";

    //Maximum number of children a node can have
    public static final Integer MAX_CHILDREN = 15;

    //Messages
    public static final String ROOT_EXISTS_MSG = "Root node already exists, so cannot create a new root node";
    public static final String PRINT_TREE_ERROR_MSG = "Exception while printing tree";
    public static final String EXCEEDS_MAX_CHILDREN_MSG = "The specified parent node already has " + MAX_CHILDREN + " children and that is the max limit";
    public static final String ROOT_CREATE_ERROR = "Exception while creating root node";
    public static final String ADD_CHILD_ERROR = "Exception while adding a child node";
    public static final String GET_NODE_ERROR = "Exception while retrieving a node";
    public static final String GET_CHILDREN_ERROR = "Exception while retrieving the children of the node";
    public static final String INVALID_PARENT_ERROR = "Specified parent node does not exist";
    public static final String GET_ANCESTOR_ERROR = "Exception while retrieving the ancestors of the node";
    public static final String INVALID_NODE_ERROR = "Specified node does not exist";
    public static final String DELETE_NODE_ERROR = "Exception while deleting the node";
    public static final String DELETE_ROOT_ERROR = "Root Node can not be deleted";
    public static final String PRINT_TREE_MSG = "Please check the log to view the tree";
    public static final String SUCCESS_DESCRIPTION = "Operation has been successful";

    //Map used for storing all nodes
    //Thread safe
	public static final ConcurrentHashMap<String, Node> nodePool = new ConcurrentHashMap<String, Node>();

    //Counter
    //Thread safe
    public static AtomicInteger counter = new AtomicInteger(1);

    //Response Status
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";

    //Node properties
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PARENT = "parent";
    public static final String CHILDREN = "children";
    public static final String CHILD = "child";

    //Swagger Notes
    public static final String API_GET_NODE_VALUE = "Retrieve a node from the tree";
    public static final String API_GET_NODE_NOTES = "<ul><li>Provide the name of the node.</li>" +
            "<li>Name is case-sensitive.</li></ul>";
    public static final String API_CREATE_NODE_VALUE = "Create/Add a node";
    public static final String API_CREATE_NODE_NOTES = "<ul><li>Provide the name of the parent node.</li>" +
            "<li>Provide the description of the node.</li>" +
            "<li>Name is case-sensitive.</li></ul>";
    public static final String API_IMM_CHILD_VALUE = "Retrieve the immediate children of a node";
    public static final String API_ALL_CHILD_VALUE = "Retrieve all children of a node";
    public static final String API_ALL_ANCESTORS_VALUE = "Retrieve all ancestors of a node";
    public static final String API_DELETE_NODE_VALUE = "Delete a node from the tree";
    public static final String API_DELETE_NODE_NOTES = "<ul><li>Provide the name of the node.</li>" +
            "<li>Name is case-sensitive.</li>" +
            "<li>This operation will delete the specified node and all its children.</li></ul>";
    public static final String API_PRINT_TREE_VALUE = "Print the tree in the log";
    public static final String API_PRINT_TREE_NOTES = "<ul><li>This operation will print the tree in the log.</li>" +
            "<li>Please check the log.</li></ul>";

	public ApiConstants() {
	}

}
