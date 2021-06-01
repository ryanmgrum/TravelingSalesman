/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 5 (Traveling Salesman Problem)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/** GLParser parses a .gl file and stores it in the appropriate Graph
 *  (held in a Graph superclass for convenience).
 */
public class GLParser {
    Graph<String> graph; // The resulting Graph created by this GLParser.
    boolean directed; // Boolean used to indicate to the user whether the resulting Graph is directed so they can type-cast appropriately.
    boolean weighted; // Boolean used to indicate to the user whether the resulting Graph is weighted so they can type-cast appropriately.
    
    /** Constructor that takes in a .gl file and parses it, ultimately storing
     *  it in one of the following actual Graph types:
     *  DirectedUnweightedGraph{@literal <}K{@literal >}
     *  DirectedWeightedGraph{@literal <}K{@literal >}
     *  UndirectedUnweightedGraph{@literal <}K{@literal >}
     *  UndirectedWeightedGraph{@literal <}K{@literal >}
     * 
     * @param filePath The .gl file to parse and load into a Graph.
     * @throws IllegalArgumentException If the filePath is null, empty,
     * or is not a .gl file.
     * @throws IOException If there is an issue reading the .gl file.
     * @throws SecurityException If GLParser is unable to read the file.
     */
    public GLParser(String filePath) throws IllegalArgumentException, IOException, SecurityException {
        // First check that filePath is not null.
        if (filePath == null)
            throw new IllegalArgumentException("Error while constructing a new GLParser(String): The filePath parameter is null!");
        else if (filePath.isEmpty())
            throw new IllegalArgumentException("Error while constructing a new GLParser(String): The filePath parameter is empty!");
        else if (filePath.isBlank())
            throw new IllegalArgumentException("Error while constructing a new GLParser(String): The filePath parameter is only spaces!");
        
        // Create a new File object to further verify that filePath is actually a .gl file.
        File file = new File(filePath);
        
        if (!file.canRead())
            throw new SecurityException("Error while constructing a new GLParser(String): The filePath parameter \"" + filePath + "\" cannot be opened for reading!");
        else if (file.isDirectory())
            throw new IllegalArgumentException("Error while constructing a new GLParser(String): The filePath parameter \"" + filePath + "\" is a directory, not a .gl file!");
        else if (file.isFile()) { // Verify that filePath is actually a .gl file.
            String extension = "";
            int i = filePath.lastIndexOf(".");
            if (i >= 0 && i < filePath.length() - 1)
                extension = filePath.substring(filePath.lastIndexOf(".") + 1);
            
            if (!extension.toLowerCase().equals("gl"))
                throw new IllegalArgumentException("Error while constructing a new GLParser(String): The filePath parameter \"" + filePath + "\" is not a .gl file!");
        }
        
        // Read the .gl file line-by-line.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            boolean firstLine = true;
            for(String line = reader.readLine(); ; line = reader.readLine()) {
                if (firstLine) { // Heading.
                    directed = line.split(" ")[0].equals("directed");
                    weighted = line.split(" ")[1].equals("weighted");
                    
                    if (directed & weighted)
                        graph = new DirectedWeightedGraph<>();
                    else if (!directed & weighted)
                        graph = new UndirectedWeightedGraph<>();
                    else if (directed & !weighted)
                        graph = new DirectedUnweightedGraph<>();
                    else if (!directed & !weighted)
                        graph = new UndirectedUnweightedGraph<>();
                    
                    firstLine = false;
                } else if (!line.isEmpty() && !line.isBlank()) { // Read the Vertices and Edges into the Graph.
                    String[] lines = line.split("=");
                    
                    if (directed & weighted)
                        ((DirectedWeightedGraph<String, Integer>)(graph)).addEdge(new Vertex<>(lines[0]), new Vertex<>(lines[1]), Integer.parseInt(lines[2]));
                    else if (!directed & weighted)
                        ((UndirectedWeightedGraph<String, Integer>)(graph)).addEdge(new Vertex<>(lines[0]), new Vertex<>(lines[1]), Integer.parseInt(lines[2]));
                    else if (directed & !weighted)
                        ((DirectedUnweightedGraph<String>)(graph)).addEdge(new Vertex<>(lines[0]), new Vertex<>(lines[1]));
                    else if (!directed & !weighted)
                        ((UndirectedUnweightedGraph<String>)(graph)).addEdge(new Vertex<>(lines[0]), new Vertex<>(lines[1]));
                }
                
                if (!reader.ready())
                    break;
            }
        }
        
    }

    /** getGraph returns the Graph stored in this GLParser.
     * 
     * @return The graph attribute.
     */
    public Graph<String> getGraph() {
        return graph;
    }
    
    /** isDirected returns whether the Graph stored in this GLParser is directed.
     * 
     * @return True if the Graph is directed.
     */
    public boolean isDirected() {
        return directed;
    }
    
    /** isWeighted returns whether the Graph stored in this GLParser is weighted.
     * 
     * @return True if the Graph is weighted.
     */
    public boolean isWeighted() {
        return weighted;
    }
    
    /** toString returns the String output of the stored Graph using the
     *  appropriate subclass graph based on it being weighted and directed.
     * 
     * @return The appropriate String representation of the stored Graph object.
     */
    @Override
    public String toString() {
        String result = "";
        
        if (directed & weighted)
            result = "DirectedWeightedGraph " + ((DirectedWeightedGraph<String, Integer>)(graph)).toString();
        else if (!directed & weighted)
            result = "UndirectedWeightedGraph " + ((UndirectedWeightedGraph<String, Integer>)(graph)).toString();
        else if (directed & !weighted)
            result = "DirectedUnweightedGraph " + ((DirectedUnweightedGraph<String>)(graph)).toString();
        else if (!directed & !weighted)
            result = "UndirectedUnweightedGraph " + ((UndirectedUnweightedGraph<String>)(graph)).toString();
        
        return result;
    }
}