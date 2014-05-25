package org.cytoscape.psfc.logic.structures;

import org.cytoscape.psfc.ExceptionMessages;

/**
 * PUBLIC CLASS Node
 * The Node class represents a node in the graph.
 * Node a non-negative int index associated with it. The index of Node cannot be changed after it is created.
 * Node may have a double value for gene expression, expression ratios or other metrics.
 *
 * Unresolved: If not initialized, the value is set to 0.0. Should this be null?
 * Unresolved: Should it keep the reference of the graph it belongs to?
 */

public class Node {
    private int index;
    private double value;

    /**
     * Creates a Node with given index and 0.0 initial value.
     *
     * @param index non-negative integer: should be unique identifier for the node in a graph.
     */
    public Node(int index){
        if (index < 0)
            throw new IllegalArgumentException(ExceptionMessages.NodeWithNegativeIndex);
        this.index = index;
    }

    /**
     * Creates a Node with given index and value.
     *
     * @param index non-negative integer: should be unique identifier for the node in a graph.
     * @param value double value: may stand for expression, ratio, rank score, etc.
     */
    public Node(int index, double value) {
        new Node(index);
        setValue(value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }
}
