package org.cytoscape.psfc.logic.structures;

import org.cytoscape.psfc.ExceptionMessages;

/**
 * PUBLIC CLASS Edge
 * Edge represents a directed relation between two Nodes: source and target.
 * There can be only one directed edge between two nodes.
 *
 * Unresolved: Should  an Edge keeps an integer index as an identifier?
 * Unresolved: Are self-loops allowed?
 * Unresolved: Should it keep the reference of the Graph it belongs to?
 */
public class Edge {
    public static int ACTIVATION = 1;
    public static int INHIBITION = 0;

    private Node source;
    private Node target;
    private int type;

    public Edge(Node target, Node source) {
        if (target == null || source == null)
            throw new NullPointerException(ExceptionMessages.EdgeWithNullNode);
        this.target = target;
        this.source = source;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }
}
