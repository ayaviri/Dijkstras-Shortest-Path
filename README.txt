welcome to my first implementation of Dijkstra's Shortest Path Algorithm in java !
i decided to use priority queues rather than simple arrays for this implementation in order
to reduce the asymptotic running time from O(n^2) to m(log(n)). below are some of important design
choices that i hope to improve in the future

graph:
- the adjacency list representation for graphs was used for this program with two adjacency lists,
one corresponding to the outgoing edge from a given node, and one corresponding to the incoming
edges to a given node
- since this graph is weighted, a pair of numbers is stored in each entry of the adjacency list. the
first is neighboring node, and the second is the weight of the edge

priority queue / minheap:
- in order to represent this priority queue, i used a few data structures. the first is a map from
node to index in the values or distances array. for example, if node 10 has a distance of 1 which
sat at the root of the heap, then the key 10 would map to the index 0. the second is an identical
map in the reverse direction so that i can find a value in constant time. i believe there are
data structures which allowed this, but i decided to create two maps instead since the increase
in space usage is only linear so the asymptotic space usage does not change, and searching by value
is an operation i perform a lot. as such, the increase in performance from O(n) to O(1) is worth it.
the final data structure i used was a list which represents the binary tree itself. it is a common
technique to use lists rather to simulate binary trees for priority queues by using clever indexing.

what follows is a brief description of how to use this program. this algorithm runs on simple
directed graphs with positively weighted edges. the edges are not constrained to being distinct.
to create a graph, you begin by created a Graph object either without any nodes or with an initial
number of nodes. for a graph with n nodes, the nodes will be labeled 0, 1, ..., n-1. the addition
of new nodes will simply allocate more memory for the graph. to add an edge, you call the
Graph.addEdge() method which takes in the from node, the to node, and the weight of the edge. once
the desired graph has been constructed, you can run dijkstra's algorithm to obtain a pair of lists.
the first list in the pair will be a list of distances where the ith element in the list represents
the distance between the source node and the ith node. the second list in the pair is a list of
parent pointers where the ith element in the list represents the node from which the last edge in
the shortest path from source to the ith node is. this is used when reconstructing the path, as you
can follow the parent pointers until arriving at the source node. finally, in order to run the
algorithm, you call dijkstra(graph, startingNode).

future additions:
- scripting so that graphs can be constructed through command line arguments rather than altering
source code