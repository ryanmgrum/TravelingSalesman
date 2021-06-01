/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 5 (Traveling Salesman Problem)
 */

/** The WeightedEdge extends the Edge class by adding a weight cost to its edge.
 * 
 * @param <V> The type of numerical value stored in this WeightedEdge.
 */
public class WeightedEdge<V extends Comparable<? super V>> extends Edge {
    V weight; // The weight value assigned to this WeightedEdge.
    
    /** Constructor that creates a new WeightedEdge using the passed-in
     *  destination Vertex and edge cost.
     * 
     * @param to The destination Vertex of this WeightedEdge.
     * @param newWeight The new weight value/cost to assign to this
     * WeightedEdge.
     */
    public WeightedEdge(Vertex to, V newWeight) {
        super(to);
        weight = newWeight;
    }
    
    /** getWeight returns the cost of this WeightedEdge.
     * 
     * @return The weight attribute of this WeightedEdge.
     */
    public V getWeight() {
        return weight;
    }
    
    /** setWeight sets a new edge cost to this WeightedEdge.
     * 
     * @param newWeight The new edge cost to set to this WeightedEdge.
     */
    public void setWeight(V newWeight) {
        weight = newWeight;
    }
    
    /** equals compares this WeightedEdge with the passed-in Object for
     *  equality; in particular, it compares their weight, and then their
     *  destination Vertex, for equality.
     * 
     * @param o The Object to compare to this WeightedEdge.
     * @return True if this WeightedEdge equals the passed-in Object.
     * @throws IllegalArgumentException If the passed-in Object is not a
     * WeightedEdge or is null.
     */
    @Override
    public boolean equals(Object o) throws IllegalArgumentException {
        if (o == this)
            return true;
        else if (o == null)
            throw new IllegalArgumentException("Error while executing equals(Object) in WeightedEdge: The o parameter is null!");
        else if (o.getClass() != getClass())
            throw new IllegalArgumentException("Error while executing equals(Object) in WeightedEdge: Cannot compare a WeightedEdge to a " + o.getClass() + "!");
        
        WeightedEdge edge = (WeightedEdge) o;
        if (edge.getWeight() == weight)
            return getTo().equals(edge.getTo());
        else
            return false;
    }
    
    /** hashCode returns the combined hashCode value of this WeightedEdge's weight
     *  and destination Vertex.
     * 
     * @return The combined hashCode of this WeightedEdge's weight and to Vertex.
     */
    @Override
    public int hashCode() {
        return weight.hashCode() + getTo().hashCode();
    }
    
    /** compareTo compares this WeightedEdge with the passed-in Object;
     *  in particular, it compares their weight, and then their
     *  destination Vertex.
     * 
     * @param o The Object to compare to this WeightedEdge.
     * @return {@literal <} 0 if this WeightedEdge is less than the passed-in
     * Object, 0 if equal, and {@literal >} 0 if this WeightedEdge is greater
     * than the passed-in Object.
     * @throws IllegalArgumentException If the passed-in Object is not a
     * WeightedEdge or is null.
     */
    @Override
    public int compareTo(Object o) throws IllegalArgumentException {
        if (o == this)
            return 0;
        else if (o == null)
            throw new IllegalArgumentException("Error while executing compareTo(Object) in WeightedEdge: The o parameter is null!");
        else if (o.getClass() != getClass())
            throw new IllegalArgumentException("Error while executing compareTo(Object) in WeightedEdge: Cannot compare a WeightedEdge to a " + o.getClass() + "!");
        
        WeightedEdge<V> edge = (WeightedEdge<V>) o;
        if (edge.getWeight() == weight)
            return getTo().compareTo(edge.getTo());
        else
            return weight.compareTo(edge.getWeight());
    }
    
    /** toString outputs this WeightedEdge's Vertex, followed by its weight,
     *  in a set of parenthesis.
     * 
     * @return This WeightedEdge's Vertex, followed by its weight, in a set
     * of parenthesis.
     */
    @Override
    public String toString() {
        return "(" + getTo() + ", " + weight + ")";
    }
}