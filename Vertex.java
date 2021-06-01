/* Author:     Ryan McAllister-Grum
 * UIN:        661880584
 * Class:      20FA - Algorithms & Computation (20FA-OL-CSC482A-15037)
 * Assignment: 5 (Traveling Salesman Problem)
 */

/** Vertex implements the functionality for vertices in a graph.
 * 
 * @param <K> The type of item stored in this Vertex.
 */
public class Vertex<K extends Comparable<? super K>> implements Comparable {
    private K item; // The item stored in this Vertex.
    
    /** Constructor that takes a new item and stores it in this Vertex.
     * 
     * @param newItem The item to store in this Vertex.
     * @throws IllegalArgumentException If the newItem parameter is null.
     */
    public Vertex(K newItem) throws IllegalArgumentException {
        // Check that the newItem parameter is not null.
        if (newItem == null)
            throw new IllegalArgumentException("Error while creating new Vertex(K): The newItem parameter is null!");
        
        item = newItem;
    }
    
    /** get returns the item stored in this Vertex.
     * 
     * @return The item attribute.
     */
    public K get() {
        return item;
    }
    
    /** set sets a new item into this Vertex and returns its previous item.
     * 
     * @param newItem The new item to store in this Vertex.
     * @return The previous item stored in this Vertex.
     * @throws IllegalArgumentException If the newItem parameter is null.
     */
    public K set(K newItem) throws IllegalArgumentException {
        // First check that newItem is not null.
        if (newItem == null)
            throw new IllegalArgumentException("Error while executing set(K) in Vertex: The newItem parameter is null!");
        
        K result = item;
        item  = newItem;
        return result;
    }
    
    /** equals checks whether this Vertex is equal to the passed-in vertex.
     * 
     * @param vertex The vertex to check for equality against this one.
     * @return True if this vertex is equal to the passed-in vertex.
     * @throws IllegalArgumentException If the passed-in vertex is null.
     */
    public boolean equals(Vertex<K> vertex) throws IllegalArgumentException {
        // First check that the passed-in vertex is not null.
        if (vertex == null)
            throw new IllegalArgumentException("Error while executing equals(Vertex<K>) in Vertex: The vertex parameter is null!");
        
        return item.equals(vertex.get());
    }
    
    /** equals checks whether this Vertex's item is equal to the passed-in item.
     * 
     * @param otherItem The other item to compare to this Vertex's item.
     * @return True if this Vertex's item equals otherItem.
     * @throws IllegalArgumentException If otherItem is null.
     */
    public boolean equals(K otherItem) throws IllegalArgumentException {
        // First check that otherItem is not null.
        if (otherItem == null)
            throw new IllegalArgumentException("Error while executing equals(K) in Vertex: The otherItem parameter is null!");
        
        return item.equals(otherItem);
    }

    /** compareTo compares this Vertex with the passed-in Object; in particular,
     *  it compares their stored items.
     * 
     * @param o The other Object to compare to this Vertex.
     * @return {@literal <} 0 if this Vertex is less than the passed-in Object,
     * 0 if equal, and {@literal >} 0 if this Vertex is greater than the
     * passed-in Object.
     * @throws IllegalArgumentException If the passed-in vertex is null or
     * cannot be compared to a Vertex or its object.
     */
    @Override
    public int compareTo(Object o) throws IllegalArgumentException {
        if (this == o)
            return 0;
        else if (o == null)
            throw new IllegalArgumentException("Error while executing compareTo(Object) in Vertex: The o parameter is null!");
        else if (o.getClass() == item.getClass())
            return item.compareTo((K) o);
        else if (o.getClass() == this.getClass()) {
            Vertex<K> vertex = (Vertex<K>) o;
            return item.compareTo(vertex.get());
        } else
            throw new IllegalArgumentException("Error while executing compareTo(Object) in Vertex: Cannot compare " + o.getClass() + " with either " + item.getClass() + " or " + this.getClass() + "!");
    }
    
    /** toString outputs this Vertex's item attribute.
     * 
     * @return The item attribute's toString() method.
     */
    @Override
    public String toString() {
        return item.toString();
    }
}