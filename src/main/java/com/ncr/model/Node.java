package com.ncr.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SINHAVI on 12/31/2016.
 */

//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="name")
public class Node {
    //name field is unique
    private String name;
    @JsonIgnore
    private final List<Node> children = new ArrayList<Node>();
    @JsonIgnore
    private final Node parent;
    private String description;

    public Node(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
