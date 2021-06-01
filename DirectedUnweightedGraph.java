/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 5 (Traveling Salesman Problem)
 */

import java.util.TreeMap;
import java.util.TreeSet;

/** A DirectedUnweightedGraph stores Edge objects without costs and whose Edges
 *  point to just the destination Vertex object.
 * 
 * @param <K> The variable type stored in this Graph's vertices.
 */
public class DirectedUnweightedGraph<K extends Comparable<? super K>> extends Graph<K> implements Cloneable {
    
    /** Constructor that creates a new empty DirectedUnweightedGraph.
     */
    public DirectedUnweightedGraph() {
        graph = new TreeMap<>();
    }

    /** addEdge adds a new edge to the from Vertex object. If either Vertex does
     *  not exist in the graph, it is added.
     * 
     * @param from The Vertex that this Edge comes from.
     * @param to The destination Vertex of the from Edge.
     * @throws IllegalArgumentException If either from or to is null.
     */
    public void addEdge(Vertex<K> from, Vertex<K> to) throws IllegalArgumentException {
        // First check that neither from nor to are null.
        if (from == null)
            throw new IllegalArgumentException("Error while executing addEdge(Vertex<K>, Vertex<K>) in DirectedUnweightedGraph: The from parameter is null!");
        else if (to == null)
            throw new IllegalArgumentException("Error while executing addEdge(Vertex<K>, Vertex<K>) in DirectedUnweightedGraph: The to parameter is null!");
        
        
        // Add Edge to the from Vertex that points to the other Vertex.
        if (!graph.containsKey(from)) {
            addVertex(from);
            TreeSet<Edge> fromList = new TreeSet<>();
            fromList.add(new Edge(to));
            graph.put(from, fromList);
        } else
            graph.get(from).add(new Edge(to));
        
        if (!graph.containsKey(to))
            addVertex(to);
    }
    
    /** toString outputs the Vertex and associated Edges in a readable format.
     * 
     * @return This Graph's list of Vertex and Edge objects.
     */
    @Override
    public String toString() {
        String result = "{" + System.lineSeparator();
        
        for (Vertex v : getVertices()) {
            result += "\t" + v + ": [";
            for(Edge e : (TreeSet<Edge>) getEdges(v))
                if (e.equals(graph.get(v).last()))
                    result += e;
                else
                    result += e + ", ";
            
            
            if (v.equals(graph.lastKey()))
                result += "]" + System.lineSeparator();
            else
                result += "]," + System.lineSeparator();
        }
        
        result += "}";
        
        return result;
    }
    
    /** clone creates a deep copy of this DirectedUnweightedGraph.
     * 
     * @return A deep copy of this DirectedUnweightedGraph.
     * @throws CloneNotSupportedException If cloning is not supported by this class.
     */
    @Override
    public DirectedUnweightedGraph<K> clone() throws CloneNotSupportedException {
        DirectedUnweightedGraph<K> result = (DirectedUnweightedGraph<K>) super.clone();
        result.graph = new TreeMap<>();
        for (Vertex<K> vertex : graph.keySet()) {
            result.addVertex(vertex);
            for (Edge edge : (TreeSet<Edge>) getEdges(vertex)) {
                result.addEdge(vertex, edge.getTo());
            }
        }
        
        return result;
    }
}