package com.ncr.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncr.common.ApiConstants;
import com.ncr.dao.TreeDao;
import com.ncr.exception.ExceedsMaxAllowedChildrenException;
import com.ncr.model.Node;
import com.ncr.model.TreeResponse;
import com.ncr.util.TreeHelper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ncr.common.ApiConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Produces(APPLICATION_JSON_VALUE)
@Consumes(APPLICATION_JSON_VALUE)
@RequestMapping(ApiConstants.BASE_URL)
public class HelloController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    TreeHelper treeHelper;

    @Autowired
    TreeDao treeDao;

    @Autowired
    ObjectMapper objectMapper;

    //Not exposing this method as rest endpoint, but can be exposed later if required
    //This method is used by the application internally
    //@RequestMapping(method = RequestMethod.GET, value = "/createRootNode")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<Node>> createRootNode() {
        try {
            return treeDao.createRootNode();
        } catch (Exception e) {
            LOG.error(ROOT_CREATE_ERROR, e);
            return treeHelper.createErrorResponse(null, ROOT_CREATE_ERROR);
        }
    }

    /**
     * prints the tree structure in the log
     *
     * @return json representation of the tree
     */
    @ApiOperation(value = ApiConstants.API_PRINT_TREE_VALUE, notes = ApiConstants.API_PRINT_TREE_NOTES)
    @RequestMapping(method = RequestMethod.GET, value = "/printTree")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<String>> printTree() {
        try {
            Node rootNode = nodePool.get(ROOT_KEY);

            //This will print the tree in the log
            treeHelper.printTree(rootNode, " ");

            return treeHelper.createSuccessResponse(PRINT_TREE_MSG);

        } catch (Exception e) {
            LOG.error(PRINT_TREE_ERROR_MSG, e);
            return treeHelper.createErrorResponse(null, PRINT_TREE_ERROR_MSG);
        }
    }

    /**
     * @param parentName
     * @param description
     * @return the newly created node object
     */
    @ApiOperation(value = ApiConstants.API_CREATE_NODE_VALUE, notes = ApiConstants.API_CREATE_NODE_NOTES)
    @RequestMapping(method = RequestMethod.POST, value = "/createChildNode")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<Node>> addChildNode(@RequestParam String parentName, @RequestParam String description) {
        try {
            if (StringUtils.isBlank(parentName) || !nodePool.containsKey(parentName)) {
                return treeHelper.createResponse(null, HttpStatus.NOT_FOUND, FAILURE, INVALID_PARENT_ERROR);
            }
            return treeHelper.createResponse(treeDao.addChild(parentName, description), HttpStatus.CREATED, SUCCESS, SUCCESS_DESCRIPTION);
        } catch (ExceedsMaxAllowedChildrenException e) {
            LOG.error(EXCEEDS_MAX_CHILDREN_MSG, e);
            return treeHelper.createErrorResponse(null, EXCEEDS_MAX_CHILDREN_MSG);
        } catch (Exception e) {
            LOG.error(ADD_CHILD_ERROR, e);
            return treeHelper.createErrorResponse(null, ADD_CHILD_ERROR);
        }
    }

    /**
     * @param name
     * @return the node object
     */
    @ApiOperation(value = ApiConstants.API_GET_NODE_VALUE, notes = ApiConstants.API_GET_NODE_NOTES)
    @RequestMapping(method = RequestMethod.GET, value = "/getNode")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<Node>> getNode(@RequestParam String name) {
        try {
            if (StringUtils.isBlank(name) || !nodePool.containsKey(name)) {
                return treeHelper.createResponse(null, HttpStatus.NOT_FOUND, FAILURE, INVALID_NODE_ERROR);
            }
            return treeHelper.createSuccessResponse(nodePool.get(name));
        } catch (Exception e) {
            LOG.error(GET_NODE_ERROR, e);
            return treeHelper.createErrorResponse(null, GET_NODE_ERROR);
        }
    }


    /**
     * @param name
     * @return the list of all immediate children node objects
     */
    @ApiOperation(value = ApiConstants.API_IMM_CHILD_VALUE, notes = ApiConstants.API_GET_NODE_NOTES)
    @RequestMapping(method = RequestMethod.GET, value = "/getImmediateChildren")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<List<Node>>> getImmediateChildren(@RequestParam String name) {
        try {
            if (StringUtils.isBlank(name) || !nodePool.containsKey(name)) {
                return treeHelper.createResponse(null, HttpStatus.NOT_FOUND, FAILURE, INVALID_NODE_ERROR);
            }
            return treeHelper.createSuccessResponse(nodePool.get(name).getChildren());
        } catch (Exception e) {
            LOG.error(GET_CHILDREN_ERROR, e);
            return treeHelper.createErrorResponse(null, GET_CHILDREN_ERROR);
        }
    }

    /**
     * @param name
     * @return all the immediate and nested children
     * <p>
     * It returns nested json object
     * Also prints tree structure in the log
     */
    @ApiOperation(value = ApiConstants.API_ALL_CHILD_VALUE, notes = ApiConstants.API_GET_NODE_NOTES)
    @RequestMapping(method = RequestMethod.GET, value = "/getAllChildren")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<List<Map<String, Object>>>> getAllChildren(@RequestParam String name) {
        try {
            if (StringUtils.isBlank(name) || !nodePool.containsKey(name)) {
                return treeHelper.createResponse(null, HttpStatus.NOT_FOUND, FAILURE, INVALID_NODE_ERROR);
            }
            Node node = nodePool.get(name);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            List<Node> listOfChildren = node.getChildren();
            for (Node childNode : listOfChildren) {
                getChildren(childNode, list);
            }
            treeHelper.printTree(node, " ");
            return treeHelper.createSuccessResponse(list);
        } catch (Exception e) {
            LOG.error(GET_CHILDREN_ERROR, e);
            return treeHelper.createErrorResponse(null, GET_CHILDREN_ERROR);
        }
    }

    /**
     *
     * @param name
     * @return all ancestors, that is the path from the root node to the specific node
     */
    @ApiOperation(value = ApiConstants.API_ALL_ANCESTORS_VALUE, notes = ApiConstants.API_GET_NODE_NOTES)
    @RequestMapping(method = RequestMethod.GET, value = "/getAllAncestors")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<Map<String, Object>>> getAllAncestors(@RequestParam String name) {
        try {
            if (StringUtils.isBlank(name) || !nodePool.containsKey(name)) {
                return treeHelper.createResponse(null, HttpStatus.NOT_FOUND, FAILURE, INVALID_NODE_ERROR);
            }
            Node node = nodePool.get(name);
            Map<String, Object> resultMap = getAncestors(node, null);
            return treeHelper.createSuccessResponse(resultMap);
        } catch (Exception e) {
            LOG.error(GET_ANCESTOR_ERROR, e);
            return treeHelper.createErrorResponse(null, GET_ANCESTOR_ERROR);
        }
    }

    @ApiOperation(value = ApiConstants.API_DELETE_NODE_VALUE, notes = ApiConstants.API_DELETE_NODE_NOTES)
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteNode")
    public
    @ResponseBody
    ResponseEntity<TreeResponse<Node>> deleteNode(@RequestParam String name) {
        try {
            if (StringUtils.isBlank(name) || !nodePool.containsKey(name)) {
                return treeHelper.createResponse(null, HttpStatus.NOT_FOUND, FAILURE, INVALID_NODE_ERROR);
            }
            if(name.equals(ROOT_KEY)) {
                return treeHelper.createResponse(null, HttpStatus.BAD_REQUEST, FAILURE, DELETE_ROOT_ERROR);
            }
            return treeHelper.createSuccessResponse(treeDao.deleteNode(name));
        } catch (Exception e) {
            LOG.error(DELETE_NODE_ERROR, e);
            return treeHelper.createErrorResponse(null, DELETE_NODE_ERROR);
        }
    }

    private void getChildren(Node node, List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap();
        map.put(NAME, node.getName());
        map.put(DESCRIPTION, node.getDescription());
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
        map.put(CHILDREN, childList);
        List<Node> listOfChildren = node.getChildren();
        for (Node childNode : listOfChildren) {
            getChildren(childNode, childList);
        }
        list.add(map);
    }

    private Map<String, Object> getAncestors(Node node, Map<String, Object> oldMap) {
        Map<String, Object> newMap = new HashMap();
        newMap.put(NAME, node.getName());
        newMap.put(DESCRIPTION, node.getDescription());
        if (null != oldMap) {
            newMap.put(CHILD, oldMap);
        }
        Node parent = node.getParent();
        Map<String, Object> result = null;
        if (null != parent) {
            result = getAncestors(parent, newMap);
        }
        if (null != result) {
            return result;
        } else {
            return newMap;
        }
    }


}
