/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 5 (Traveling Salesman Problem)
 */

import java.util.TreeMap;
import java.util.TreeSet;

/** An UndirectedUnweightedGraph stores Edge objects without costs pointing to
 *  both Vertex objects.
 * 
 * @param <K> The variable type stored in this Graph's vertices.
 */
public class UndirectedUnweightedGraph<K extends Comparable<? super K>> extends Graph<K> implements Cloneable {
    
    /** Constructor that creates a new empty UndirectedUnweightedGraph.
     */
    public UndirectedUnweightedGraph() {
        graph = new TreeMap<>();
    }

    /** addEdge adds a new edge to both the from and to Vertex objects.
     *  If either Vertex does not exist in the graph, it is added.
     * 
     * @param from The Vertex that this Edge comes from.
     * @param to The destination Vertex of the from Edge.
     * @throws IllegalArgumentException If either from or to is null.
     */
    public void addEdge(Vertex<K> from, Vertex<K> to) throws IllegalArgumentException {
        // First check that neither from nor to are null.
        if (from == null)
            throw new IllegalArgumentException("Error while executing addEdge(Vertex<K>, Vertex<K>) in UndirectedUnweightedGraph: The from parameter is null!");
        else if (to == null)
            throw new IllegalArgumentException("Error while executing addEdge(Vertex<K>, Vertex<K>) in UndirectedUnweightedGraph: The to parameter is null!");
        
        
        // Add edges to both Vertex objects pointing to the other Vertex.
        if (!graph.containsKey(from)) {
            addVertex(from);
            TreeSet<Edge> fromList = new TreeSet<>();
            fromList.add(new Edge(to));
            graph.put(from, fromList);
        } else
            graph.get(from).add(new Edge(to));
        
        if (!graph.containsKey(to)) {
            addVertex(to);
            TreeSet<Edge> toList = new TreeSet<>();
            toList.add(new Edge(from));
            graph.put(to, toList);
        } else
            graph.get(to).add(new Edge(from));
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
    
    /** clone creates a deep copy of this UndirectedUnweightedGraph.
     * 
     * @return A deep copy of this UndirectedUnweightedGraph.
     * @throws CloneNotSupportedException If cloning is not supported by this class.
     */
    @Override
    public UndirectedUnweightedGraph<K> clone() throws CloneNotSupportedException {
        UndirectedUnweightedGraph<K> result = (UndirectedUnweightedGraph<K>) super.clone();
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