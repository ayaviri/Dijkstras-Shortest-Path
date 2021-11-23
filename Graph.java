import java.util.ArrayList;
import java.util.List;

/**
 * A representation for a directed graph with positively weighted edges
 */
public class Graph {

  // pair of (node, edge weight)
  private List<ArrayList<Pair<Integer, Integer>>> outgoingEdges;
  private List<ArrayList<Pair<Integer, Integer>>> incomingEdges;
  private int numberOfNodes;
  private int numberOfEdges;

  /**
   * Constructs a new empty Graph
   */
  public Graph() {
    this.outgoingEdges = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
    this.incomingEdges = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
    this.numberOfNodes = 0;
    this.numberOfEdges = 0;
  }

  /**
   * Constructs a new Graph with _numberOfNodes_ nodes and no edges
   *
   * @param numberOfNodes the number of nodes in the graph
   * @throws IllegalArgumentException if the number of nodes desired is negative
   */
  public Graph(int numberOfNodes) throws IllegalArgumentException {
    this.outgoingEdges = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
    this.incomingEdges = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
    this.numberOfNodes = InputValidation
        .ensureGreaterThan(numberOfNodes, 0, "Number of nodes must be positive");
    this.numberOfEdges = 0;

    // populate with appropriate number of nodes
    for (int index = 0; index < numberOfNodes; index++) {
      this.outgoingEdges.add(new ArrayList<Pair<Integer, Integer>>());
      this.incomingEdges.add(new ArrayList<Pair<Integer, Integer>>());
    }
  }

  /**
   * Adds one node to the graph
   */
  public void addNode() {
    this.numberOfNodes += 1;
    this.outgoingEdges.add(new ArrayList<Pair<Integer, Integer>>());
    this.incomingEdges.add(new ArrayList<Pair<Integer, Integer>>());
  }

  /**
   * Adds _numberToBeAdded_ many nodes to the graph
   *
   * @param numberToBeAdded The number of nodes to be added
   */
  public void addNodes(int numberToBeAdded) throws IllegalArgumentException {
    // input validation
    InputValidation.ensureGreaterThan(numberToBeAdded, 0, "Must add a positive number of nodes");

    this.numberOfNodes += numberToBeAdded;
    for (int index = 0; index < numberToBeAdded; index++) {
      this.outgoingEdges.add(new ArrayList<Pair<Integer, Integer>>());
      this.incomingEdges.add(new ArrayList<Pair<Integer, Integer>>());
    }
  }

  /**
   * Returns the number of nodes in the graph
   *
   * @return The number of nodes in the graph
   */
  public int getNumberOfNodes() {
    return this.numberOfNodes;
  }

  /**
   * Adds an edge from _from_ to _to_, ensuring that both id's are valid. This method will only
   * allow the addition of nodes that already exist in the graph. Duplicate edges are not allowed
   * and will be rejected.
   *
   * @param from   The id for the node the edge comes from
   * @param to     The id for the node the edge goes to
   * @param weight The weight of edge to be added
   * @throws IllegalArgumentException if either input is negative or more than 1 +
   *                                  _this.numberOfNodes_
   */
  public void addEdge(int from, int to, int weight) throws IllegalArgumentException {
    // input validation
    InputValidation.ensureWithin(from, -1, this.numberOfNodes,
        "From node cannot be less than zero or greater than the number of nodes - 1");
    InputValidation.ensureWithin(to, -1, this.numberOfNodes,
        "To node cannot be less than 0 or greater than the number of nodes - 1");
    InputValidation.ensureGreaterThan(weight, 0, "Edges must be positively weighted");

    List<Pair<Integer, Integer>> currentOutgoingEdges = this.outgoingEdges.get(from);
    List<Pair<Integer, Integer>> currentIncomingEdges = this.incomingEdges.get(to);
    List<Integer> currentOutNeighbors = ListUtilities
        .map(currentOutgoingEdges, (element) -> element.getFirst());
    List<Integer> currentInNeighbors = ListUtilities
        .map(currentIncomingEdges, (element) -> element.getFirst());
    if (currentOutNeighbors.contains(to) || currentInNeighbors.contains(from)) {
      throw new IllegalArgumentException("Duplicate edges are not allowed");
    } else {
      currentOutgoingEdges.add(new Pair<Integer, Integer>(to, weight));
      currentIncomingEdges.add(new Pair<Integer, Integer>(from, weight));
      this.numberOfEdges += 1;
    }
  }

  /**
   * Returns a copy of all of the out neighbors of the given node.
   *
   * @param from The id of the node whose out neighbors we want
   * @return A copy of the list of out neighbors
   * @throws IllegalArgumentException if the node's id is out of bounds
   */
  public List<Pair<Integer, Integer>> getOutgoingEdges(int from) throws IllegalArgumentException {
    // input validation
    InputValidation
        .ensureWithin(from, -1, this.numberOfNodes, "From node is not contained the graph");

    // creation of deep copy
    List<Pair<Integer, Integer>> outgoingEdgesCopy = new ArrayList<Pair<Integer, Integer>>();
    List<Pair<Integer, Integer>> currentOutgoingEdges = this.outgoingEdges.get(from);
    for (int index = 0; index < currentOutgoingEdges.size(); index++) {
      outgoingEdgesCopy.add(currentOutgoingEdges.get(index));
    }
    return outgoingEdgesCopy;
  }

  /**
   * Returns a copy of all of the in neighbors of the given node.
   *
   * @param from The id of the node whose in neighbors we want
   * @return A copy of the list of in neighbors
   * @throws IllegalArgumentException if the node's id is out of bounds
   */
  public List<Pair<Integer, Integer>> getIncomingEdges(int from) throws IllegalArgumentException {
    // input validation
    InputValidation
        .ensureWithin(from, -1, this.numberOfNodes, "From node is not contained the graph");

    // creation of deep copy
    List<Pair<Integer, Integer>> incomingEdgesCopy = new ArrayList<Pair<Integer, Integer>>();
    List<Pair<Integer, Integer>> currentIncomingEdges = this.incomingEdges.get(from);
    for (int index = 0; index < currentIncomingEdges.size(); index++) {
      incomingEdgesCopy.add(currentIncomingEdges.get(index));
    }
    return incomingEdgesCopy;
  }
}
