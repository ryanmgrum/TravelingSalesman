/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 5 (Traveling Salesman Problem)
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/** TravelingSalesman contains the logic necessary to solve the Traveling
 *  Salesman Problem (TSP). In particular, this class solves undirected weighted
 *  graphs using permutation through all Vertex combinations, starting and ending
 *  at the same Vertex, to find the lowest possible trip cost.
 */
public class TravelingSalesman {
    /** minCost contains the final cost for the lowest-cost cycle in the TSP graph.
     */
    private Integer minCost;
    /** minPath contains the minimum graph that make up the lowest-cost cycle
     *  in the TSP graph.
     */
    private UndirectedWeightedGraph<String, Integer> minPath;
    
    /** Constructor that takes in a GLParser and solves its TSP.
     * 
     * @param parser The GLParser from which to extract its underlying graph use
     * for TSP.
     * @throws IllegalArgumentException If parser is null, its underlying graph
     * is not an UndirectedWeightedGraph<String, Integer>, or if its underlying
     * graph is empty.
     */
    TravelingSalesman(GLParser parser) throws IllegalArgumentException {
        // First check that parser is not null.
        if (parser == null)
            throw new IllegalArgumentException("Error while constructing new TravelingSalesman(GLParser): The parser parameter is null!");
        // Next check that it is undirected.
        else if (parser.isDirected())
            throw new IllegalArgumentException("Error while constructing new TravelingSalesman(GLParser): The parser's underlying graph is directed (this class only works with undirected graphs)!");
        else if (!parser.isWeighted())
            throw new IllegalArgumentException("Error while constructing new TravelingSalesman(GLParser): The parser's underlying graph is unweighted (Traveling Salesman Problem involves weighted graphs)!");
        // Finally, check that it is not empty.
        else if (parser.getGraph().getVertices().isEmpty())
            throw new IllegalArgumentException("Error while constructing new TravelingSalesman(GLParser): The underlying graph is empty!");
        
        // Initialize minCost and minPath.
        minCost = Integer.MAX_VALUE;
        minPath = new UndirectedWeightedGraph<>();

        // Call the TSP function that starts the recursive searching process.
        TSP((UndirectedWeightedGraph<String, Integer>) parser.getGraph());
    }
    
    /** Constructor that takes in an UndirectedWeightedGraph<String, Integer> and
     *  solves its TSP.
     * 
     * @param graph The new UndirectedWeightedGraph<String, Integer> to solve.
     * @throws IllegalArgumentException If graph is null or contains zero vertices.
     */
    TravelingSalesman(UndirectedWeightedGraph<String, Integer> graph) throws IllegalArgumentException {
        // First check that graph is not null.
        if (graph == null)
            throw new IllegalArgumentException("Error while constructing new TravelingSalesman(UndirectedWeightedGraph<String, Integer>): The graph parameter is null!");
        // Next, make sure that the graph is not empty.
        else if (graph.getVertices().isEmpty())
            throw new IllegalArgumentException("Error while constructing new TravelingSalesman(UndirectedWeightedGraph<String, Integer>): The graph parameter is empty!");
        
        // Initialize minCost and minPath.
        minCost = Integer.MAX_VALUE;
        minPath = new UndirectedWeightedGraph<>();
        
        // Call the TSP function that starts the recursive searching process.
        TSP(graph);
    }
    
    /** TSP sets up and starts the recursive searching process for the least-cost complete cycle.
     * 
     * @param graph The graph from which to find the answer to the Traveling Salesman Problem.
     */
    private void TSP(UndirectedWeightedGraph<String, Integer> graph) {
        // startVertex tracks the current Vertex being considered for searching.
        Vertex<String> startVertex = (Vertex<String>) graph.getVertices().toArray()[0];
        // currentPath keeps track of the current path around the graph.
        UndirectedWeightedGraph<String, Integer> currentPath = new UndirectedWeightedGraph<>();
        currentPath.addVertex(startVertex);
        // pathCost tracks currentPath's trip cost.
        int pathCost = 0;
        // visited tracks the vertices we have visited while searching.
        HashMap<String, Boolean> visited = new HashMap<>();
        for(Vertex vertex : graph.getVertices())
            visited.put(vertex.toString(), vertex.equals(startVertex));
        
        // Call the recursive version of TSP.
        TSP(graph, startVertex, startVertex, currentPath, pathCost, visited);
    }
    
    /** Recursive version of TSP that exhaustively searches the graph for the least-
     *  cost complete cycle.
     * 
     * @param graph The graph to search.
     * @param startVertex The beginning Vertex on the path.
     * @param currentVertex The current Vertex under consideration whose edges we
     * wish to search for the least-cost.
     * @param currentPath The current path followed so far along the graph.
     * @param pathCost The current cost of currentPath.
     * @param visited The list of vertices that have been visited thus far.
     */
    private void TSP(UndirectedWeightedGraph<String, Integer> graph, Vertex startVertex,
            Vertex currentVertex, UndirectedWeightedGraph<String, Integer> currentPath,
            int pathCost, HashMap<String, Boolean> visited) {
        /* If we reach the end of searching the graph (we have searched every Vertex
         * once), check the resulting cycle and exit.
         */
        if (!visited.values().contains(false)) {
            /* Loop through the list of edges for currentVertex to find the one that references startVertex
             * to complete the cycle.
             */
            Iterator<WeightedEdge<Integer>> iter = graph.getEdges(currentVertex).iterator();
            while(iter.hasNext()) {
                WeightedEdge<Integer> edge = iter.next();
                if (edge.getTo().equals(startVertex)) {
                    // Once found, add WeightedEdge to currentPath and pathCost...
                    currentPath.addEdge(currentVertex, startVertex, edge.getWeight());
                    pathCost += edge.getWeight();
                    
                    // ...and if total is less than current global total, update
                    // minCost and minPath to this pathCost and currentPath, respectively.
                    if (pathCost < minCost) {
                        minCost = pathCost;
                        try {
                            minPath = currentPath.clone(); // Create clone because currentPath will be modified.
                        } catch (CloneNotSupportedException e) {
                            // Should not be an issue.
                        }
                    }
                    
                    // Remove final edge.
                    currentPath.getEdges(currentVertex).remove(new WeightedEdge(startVertex, edge.getWeight()));
                    currentPath.getEdges(startVertex).remove(new WeightedEdge(currentVertex, edge.getWeight()));
                    
                    // Exit loop.
                    break;
                }
            }
        } else { // Continue searching graph's vertices and their edges for a lower-cost path.
            // Loop through the list of edges for currentVertex
            Iterator<WeightedEdge<Integer>> iter = graph.getEdges(currentVertex).iterator();
            while(iter.hasNext()) {
                WeightedEdge<Integer> edge = iter.next();
                // If we have not already visited this Vertex, try moving to it next for the path.
                if (!visited.get(edge.getTo().toString())) {
                    visited.put(edge.getTo().toString(), true);
                    currentPath.addEdge(currentVertex, edge.getTo(), edge.getWeight());
                    TSP(graph, startVertex, edge.getTo(), currentPath, pathCost + edge.getWeight(), visited);
                    // Remove Vertex and edge from currentPath to try another Vertex and edge.
                    visited.put(edge.getTo().toString(), false);
                    currentPath.getEdges(currentVertex).remove(new WeightedEdge<>(edge.getTo(), edge.getWeight()));
                    currentPath.getEdges(edge.getTo()).remove(new WeightedEdge<>(currentVertex, edge.getWeight()));
                    if (currentPath.getEdges(edge.getTo()).isEmpty())
                        currentPath.getVertices().remove(edge.getTo());
                }
            }
        }
    }

    /** toString prints out the resulting least-cost tour for the provided Traveling Salesman Problem graph.
     * @return A String containing the graph details of the least-cost tour to the TSP graph.
     */
    @Override
    public String toString() {
        return "UndirectedWeightedGraph " + minPath.toString();
    }
    
    /** glFormat outputs the tour stored in this TravelingSalesman into the format found in .gl files.
     * 
     * @return The .gl format of the tour stored in this TravelingSalesman based on its vertex information.
     */
    public String glFormat() {
        String heading = String.format("%s %s%s", "undirected", "weighted", System.lineSeparator());
        int edgeCount = 0;
        for (Vertex<String> vertex : minPath.getVertices())
            edgeCount += minPath.getEdges(vertex).size();
        
        String result[] = new String[edgeCount];
        
        int i = 0; // i walks through the indexes of the result array.
        for (Vertex<String> vertex : minPath.getVertices())
            for (Edge edge : minPath.getEdges(vertex)) {
                result[i] = String.format( // Output in typical .gl format (start=end=cost)
                    "%s=%s=%d%s",
                    vertex, edge.getTo(), ((WeightedEdge<Integer>) edge).getWeight(), System.lineSeparator()
                );
                i++;
            }
        
        // Append entries.
        Arrays.sort(result);
        for (String line : result)
            heading += line;

        return heading;
    }
    
    /** save outputs the contents of the tour stored in this TravelingSalesman
     *  instance to the format used in our graph language (.gl) file.
     *  Specifically, it saves the contents to the provided complete file path.
     * 
     * @param filePath The file to write the contents of the tour.
     * @throws IllegalArgumentException If the filePath parameter is null.
     * @throws IOException If there is an issue writing the file.
     */
    public void save(String filePath) throws IllegalArgumentException, IOException {
        try (FileWriter writer = new FileWriter(new File(filePath))) {
            writer.write(glFormat());
        }
    }
    
    /** tourCost returns the cost of the tour stored in this TravelingSalesman instance.
     * 
     * @return The minCost attribute.
     */
    public int tourCost() {
        return minCost;
    }
}