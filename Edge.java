/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 5 (Traveling Salesman Problem)
 */

/** Edge contains the information needed to implement directed and undirected
 *  edges in a graph.
 */
public class Edge implements Comparable {
    Vertex to; // The destination Vertex to which this Edge connects a Vertex.
    
    /** Constructor that creates a new Edge using the passed-in vertex.
     * 
     * @param vertex The destination Vertex to store in this Edge.
     * @throw IllegalArgumentException If the vertex parameter is null.
     */
    Edge(Vertex vertex) {
        // First check that the vertex parameter is not null.
        if (vertex == null)
            throw new IllegalArgumentException("Error while constructing a new Edge(Vertex): The vertex parameter is null!");
        
        to = vertex;
    }

    /** getTo returns this Edge's destination Vertex.
     * 
     * @return The to attribute.
     */
    public Vertex getTo() {
        return to;
    }
    
    /** setTo sets this Edge's destination Vertex.
     * 
     * @param newTo The new destination Vertex to set for this Edge.
     * @throws IllegalArgumentException If the newTo parameter is null.
     */
    public void setTo(Vertex newTo) throws IllegalArgumentException {
        // First check that the newTo parameter is not null.
        if (newTo == null)
            throw new IllegalArgumentException("Error while executing setTo(Vertex) in Edge: The newTo parameter is null!");
        
        to = newTo;
    }
    
    /** equals compares this Edge with the passed-in Object for equality.
     * 
     * @param o The other Object to compare to this Edge.
     * @return True if this Edge's to attribute equals the passed-in Edge.
     * @throws IllegalArgumentException If the passed-in Object is not an Edge
     * or is null.
     */
    @Override
    public boolean equals(Object o) throws IllegalArgumentException {
        if (o == this)
            return true;
        else if (o == null)
            throw new IllegalArgumentException("Error while executing equals(Object) in Edge: The o parameter is null!");
        else if (o.getClass() != this.getClass())
            throw new IllegalArgumentException("Error while executing equals(Object) in Edge: Cannot compare an Edge to a " + o.getClass() + "!");
        
        Edge edge = (Edge) o;
        return to.equals(edge.getTo());
    }
    
    /** hashCode returns the hash code for the stored destination Vertex.
     * 
     * @return The integer hash code for the stored destination Vertex of this Edge.
     */
    @Override
    public int hashCode() {
        return to.hashCode();
    }
    
    /** compareTo compares this Edge with the passed-in Object.
     * 
     * @param o The object to compare with this Edge.
     * @return {@literal <} 0 if this Edge is less than the passed-in Edge,
     * 0 if they are equal, and {@literal >} 0 if this Edge is greater than
     * the passed-in Edge.
     * @throws IllegalArgumentException If the passed-in Object is not an
     * Edge or is null.
     */
    @Override
    public int compareTo(Object o) throws IllegalArgumentException {
        if (o == this)
            return 0;
        else if (o == null)
            throw new IllegalArgumentException("Error while executing compareTo(Object) in Edge: The o parameter is null!");
        else if (o.getClass() != this.getClass())
            throw new IllegalArgumentException("Error while executing compareTo(Object) in Edge: Cannot compare this Edge object to " + o.getClass() + "!");
        
        Edge edge = (Edge) o;
        return to.compareTo(edge.getTo());
    }
    
    /** toString outputs this Edge's to Vertex.
     * 
     * @return This Edge's to Vertex.
     */
    @Override
    public String toString() {
        return to.toString();
    }
}