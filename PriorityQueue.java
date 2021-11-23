import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A representation for a minheap to be used in an improved implementation of Dijkstra's shortest
 * path algorithm
 */
public class PriorityQueue {

  Map<Integer, Integer> nodeIndices; // maps from node id to index in distances array
  Map<Integer, Integer> reverseIndices; // maps from index in distances array to node id
  List<Integer> nodeDistances;

  /**
   * Constructs a new Priority Queue with an initial capacity
   *
   * @param capacity The capacity of the queue
   */
  public PriorityQueue(int capacity) {
    this.nodeIndices = new HashMap<Integer, Integer>(capacity);
    this.reverseIndices = new HashMap<Integer, Integer>(capacity);
    this.nodeDistances = new ArrayList<Integer>(capacity);
  }

  /**
   * Returns a copy of the list of values in this priority queue
   *
   * @return A copy of the values of in this priority queue
   */
  public List<Integer> getValues() {
    List<Integer> valuesCopy = new ArrayList<Integer>(this.nodeDistances.size());
    for (int index = 0; index < this.nodeDistances.size(); index++) {
      valuesCopy.add(this.nodeDistances.get(index));
    }
    return valuesCopy;
  }

  /**
   * Places the new key at the last location and upheaps the value until heap order is restored
   *
   * @param node     The ID of the new node being added to the heap
   * @param distance The distance associated with the node
   */
  public void insert(int node, int distance) {
    if (this.nodeDistances.size() > 0) {
      int newIndex = this.nodeDistances.size();
      this.nodeDistances.add(distance);
      int parentIndex = (int) Math.floor(newIndex / 2);
      int parentDistance = this.nodeDistances.get(parentIndex);
      // the node id of the parent
      int parentNode = this.reverseIndices.get(parentIndex);

      while (parentDistance > distance) {
        // swapping the two distances in the distances array
        Collections.swap(this.nodeDistances, parentIndex, newIndex);

        // fixing the index the parent node points to
        this.nodeIndices.put(parentNode, newIndex);
        this.reverseIndices.put(newIndex, parentNode);

        // calculating the new index values
        newIndex = parentIndex;
        parentIndex = (int) Math.floor(newIndex / 2);
        parentDistance = this.nodeDistances.get(parentIndex);
        parentNode = this.reverseIndices.get(parentIndex);

        // breaking out of the loop if the new index is at the root
        if (newIndex == 0) {
          break;
        }
      }

      // assigning the index the new node points to
      this.nodeIndices.put(node, newIndex);
      this.reverseIndices.put(newIndex, node);
    } else {
      this.nodeDistances.add(distance);
      this.nodeIndices.put(node, 0);
      this.reverseIndices.put(0, node);
    }
  }

  /**
   * Finds the distance associated with the given node
   *
   * @param node The node whose distance is desired
   * @return The distance associated with the given node
   * @throws IllegalArgumentException if the node is not contained the map
   */
  public int lookup(int node) throws IllegalArgumentException {
    if (!this.nodeIndices.containsKey(node)) {
      throw new IllegalArgumentException("Node not contained in heap");
    }
    int nodeIndex = this.nodeIndices.get(node);
    int nodeDistance = this.nodeDistances.get(nodeIndex);
    return nodeDistance;
  }

  /**
   * Extracts the minimum value of the heap and downheaps the new root node pulled from the back
   *
   * @return A pair that contains the closest node and its distance
   * @throws UnsupportedOperationException if the heap is empty
   */
  public Pair<Integer, Integer> extractMin() throws UnsupportedOperationException {
    // throw exception when heap is empty
    if (this.nodeDistances.isEmpty()) {
      throw new UnsupportedOperationException("Cannot extract min on an empty heap");
    } else {
      int closestNode = this.reverseIndices.get(0);
      int minimumDistance = this.nodeDistances.get(0);
      if (this.nodeDistances.size() == 1) {
        this.nodeDistances.remove(0);
        this.nodeIndices.remove(this.reverseIndices.remove(0));
      } else {
        // remove link for the first node, and adjust link for the last node
        int parentNode = this.reverseIndices.get(this.nodeDistances.size() - 1);
        this.nodeIndices.remove(this.reverseIndices.remove(0));
        this.nodeIndices.remove(this.reverseIndices.remove(this.nodeDistances.size() - 1));
        this.nodeIndices.put(parentNode, 0);
        this.reverseIndices.put(0, parentNode);
        // remove the node from the front of the list and replace with the last node in the list
        this.nodeDistances.set(0, this.nodeDistances.remove(this.nodeDistances.size() - 1));
        // calculate the parent and children indices
        int parentIndex = 0;
        int parentDistance = this.nodeDistances.get(parentIndex);
        int leftChildIndex = 1;
        int rightChildIndex = 2;

        while ((rightChildIndex < this.nodeDistances.size() && parentDistance > Math
            .min(this.nodeDistances.get(leftChildIndex), this.nodeDistances.get(rightChildIndex)))
            || (leftChildIndex < this.nodeDistances.size() && parentDistance > this.nodeDistances
            .get(leftChildIndex))) {
          // FIXME: ABSTRACT THIS CODE INTO A HELPER METHOD
          if (rightChildIndex < this.nodeDistances.size() && parentDistance > Math
              .min(this.nodeDistances.get(leftChildIndex),
                  this.nodeDistances.get(rightChildIndex))
              && this.nodeDistances.get(rightChildIndex) < this.nodeDistances.get(leftChildIndex)) {
            // the parent is to be replaced by the right node
            int rightChildNode = this.reverseIndices.get(rightChildIndex);
            this.nodeIndices.put(rightChildNode, parentIndex);
            this.reverseIndices.put(parentIndex, rightChildNode);
            this.nodeIndices.put(parentNode, rightChildIndex);
            this.reverseIndices.put(rightChildIndex, parentNode);
            Collections.swap(this.nodeDistances, parentIndex, rightChildIndex);
            parentIndex = rightChildIndex;
            leftChildIndex = 2 * parentIndex;
            rightChildIndex = (2 * parentIndex) + 1;
          } else {
            // the parent is to be replaced by the left node
            int leftChildNode = this.reverseIndices.get(leftChildIndex);
            this.nodeIndices.put(leftChildNode, parentIndex);
            this.reverseIndices.put(parentIndex, leftChildNode);
            this.nodeIndices.put(parentNode, leftChildIndex);
            this.reverseIndices.put(leftChildIndex, parentNode);
            Collections.swap(this.nodeDistances, parentIndex, leftChildIndex);
            parentIndex = leftChildIndex;
            leftChildIndex = 2 * parentIndex;
            rightChildIndex = (2 * parentIndex) + 1;
          }
        }
      }
      return new Pair<Integer, Integer>(closestNode, minimumDistance);
    }
  }

  /**
   * Decreases the distance associated with the given node the given _newValue_ and upheaps the new
   * distance
   *
   * @param node     The ID of the node whose distance we wish to decrease
   * @param distance The new distance associated with the given node
   * @throws IllegalArgumentException if the node is not contained in the heap or if the new value
   *                                  is not strictly less than the currently associate distance
   */
  public void decreaseKey(int node, int distance) throws IllegalArgumentException {
    if (!this.nodeIndices.containsKey(node)) {
      throw new IllegalArgumentException("Node is not contained in the heap");
    }

    int currentNodeIndex = this.nodeIndices.get(node);
    int currentNodeDistance = this.nodeDistances.get(currentNodeIndex);
    // ensure that new distance is less than or equal to current node distance
    if (!(distance <= currentNodeDistance)) {
      throw new IllegalArgumentException("New distance is greater than the current distance");
    }
    this.nodeDistances.set(currentNodeIndex, distance);

    int parentIndex = (int) Math.floor(currentNodeIndex / 2);
    int parentDistance = this.nodeDistances.get(parentIndex);
    // the node id of the parent
    int parentNode = this.reverseIndices.get(parentIndex);

    while (parentDistance > distance) {
      // swapping the two distances in the distances array
      Collections.swap(this.nodeDistances, parentIndex, currentNodeIndex);

      // fixing the index the parent node points to
      this.nodeIndices.put(parentNode, currentNodeIndex);
      this.reverseIndices.put(currentNodeIndex, parentNode);

      // calculating the new index values
      currentNodeIndex = parentIndex;
      parentIndex = (int) Math.floor(currentNodeIndex / 2);
      parentDistance = this.nodeDistances.get(parentIndex);
      parentNode = this.reverseIndices.get(parentIndex);

      // breaking out of the loop if the new index is at the root
      if (currentNodeIndex == 0) {
        break;
      }
    }

    // assigning the index the new node points to
    this.nodeIndices.put(node, currentNodeIndex);
    this.reverseIndices.put(currentNodeIndex, node);
  }

  /**
   * Determines whether the heap is empty
   * @return True if the heap is empty, False otherwise
   */
  public boolean isEmpty() {
    return this.nodeDistances.isEmpty();
  }
}
