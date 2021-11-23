import java.util.ArrayList;
import java.util.List;

// the runner class for the algorithm
public class Main {

  public static Pair<List<Integer>, List<Integer>> dijkstra(Graph graph,
      int startingNode) {
    List<Integer> distances = new ArrayList<Integer>(graph.getNumberOfNodes());
    List<Integer> parent = new ArrayList<Integer>(graph.getNumberOfNodes());
    List<Boolean> seen = new ArrayList<Boolean>(graph.getNumberOfNodes());
    PriorityQueue queue = new PriorityQueue(graph.getNumberOfNodes());
    for (int index = 0; index < graph.getNumberOfNodes(); index++) {
      int currentNode = index;
      seen.add(false);
      if (currentNode == startingNode) {
        distances.add(0);
        queue.insert(currentNode, 0);
        parent.add(startingNode);
      } else {
        // this is hacky, but i don't want to deal with the max integer value
        distances.add(9999);
        queue.insert(currentNode, 9999);
        // since this is not a possible node, it indicate an absence of parent
        parent.add(-1);
      }
    }

    while (!queue.isEmpty()) {
      Pair<Integer, Integer> closestNodeAndDistance = queue.extractMin();
      int closestNode = closestNodeAndDistance.getFirst();
      int closestDistance = closestNodeAndDistance.getSecond();
      seen.set(closestNode, true);

      List<Pair<Integer, Integer>> outEdges = graph.getOutgoingEdges(closestNode);
      for (int index = 0; index < outEdges.size(); index++) {
        Pair<Integer, Integer> currentEdge = outEdges.get(index);
        int currentOutNeighbor = currentEdge.getFirst();
        int currentEdgeWeight = currentEdge.getSecond();
        if (!seen.get(currentOutNeighbor)) {
          int distanceToNeighbor = queue.lookup(currentOutNeighbor);
          if (distanceToNeighbor > closestDistance + currentEdgeWeight) {
            distances.set(currentOutNeighbor, closestDistance + currentEdgeWeight);
            queue.decreaseKey(currentOutNeighbor, closestDistance + currentEdgeWeight);
            parent.set(currentOutNeighbor, closestNode);
          }
        }
      }
    }

    return new Pair<List<Integer>, List<Integer>>(distances, parent);
  }

  public static void main(String[] args) {
    System.out.println("Hello world!");
    Graph exampleGraph = new Graph(6);

    exampleGraph.addEdge(0, 2, 5);
    exampleGraph.addEdge(0, 4, 9);
    exampleGraph.addEdge(0, 5, 3);
    exampleGraph.addEdge(4, 1, 4);
    exampleGraph.addEdge(1, 5, 6);
    exampleGraph.addEdge(2, 3, 8);
    exampleGraph.addEdge(2, 4, 10);
    exampleGraph.addEdge(4, 5, 2);
    exampleGraph.addEdge(1, 3, 3);
    exampleGraph.addEdge(4, 3, 11);

    Pair<List<Integer>, List<Integer>> distancesAndParents = dijkstra(exampleGraph, 0);
    List<Integer> distances = distancesAndParents.getFirst();
    List<Integer> parents = distancesAndParents.getSecond();
    System.out.println("distances: " + distances);
    System.out.println("parents: " + parents);
  }
}

