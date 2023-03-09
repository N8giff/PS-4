import java.util.*;

public class GraphLibrary {
    /**
     * Performs a breadth first search on a tree and returns a graph with the path
     * @param g     graph that we want to search
     * @param source    source vertex used to build shortest paths
     * @return
     *
     */
    public static <V, E> Graph<V, E> bfs(Graph<V, E> g, V source) {
        Graph<V, E> backTrack = new AdjacencyMapGraph<>();
        backTrack.insertVertex(source); //insert source vertex
        Set<V> visited = new HashSet<>(); //which vertices have been visited
        Queue<V> queue = new LinkedList<>(); //queue for BFS

        queue.add(source); //enqueue start vertex
        visited.add(source); //add start to visited
        while (!queue.isEmpty()) { //loop until no more vertices
            V u = queue.remove(); //dequeue
            for (V v : g.outNeighbors(u)) { //loop over out neighbors
                if (!visited.contains(v)) { //if neighbor not visited, then neighbor is discovered from this vertex
                    visited.add(v); //add neighbor to visited Set
                    queue.add(v); //enqueue neighbor
                    backTrack.insertVertex(v);
                    backTrack.insertDirected(v, u, null);
                }
            }
        }
        return backTrack;
    }

    /**
     * Build a path from a given node
     * @param tree  graph we want to search
     * @param v     node we want to build a path from
     * @return
     * @param <V>
     * @param <E>
     */
    public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
        if(tree.numVertices()==0 || !tree.hasVertex(v)){
            System.out.println("Vertex " + v + " does not exist.");
            return new ArrayList<V>();
        }
        ArrayList<V> path = new ArrayList<>(); // holds path for vertices
        V current = v;
        path.add(0,current);
        //loop over each vertex in the tree to create a path
        while(tree.outDegree(current)!= 0){
            for(V vertex : tree.outNeighbors(current)){
                current = vertex; //get
            }
            path.add(0,current);
        }
        return path;
    }

    /**
     * Returns a list of vertices missing from teh subgraph
     * @param graph     original graph
     * @param subgraph  graph to compare to original
     * @return
     *
     */
    public static<V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
        Set<V> vertices = new HashSet<>(); //set of vertexes
        //Add all vertices from graph into the set
        for(V vertex : graph.vertices()){
            vertices.add(vertex);
        }
        //Remove vertices that were also in the subgraph
        for(V vertex : subgraph.vertices()){
            vertices.remove(vertex);
        }
        return vertices; //return remaining vertices that weren't in subgraph
    }

    /**
     * Returns the average separation in the tree
     * @param tree  graph to search
     * @param root  root of the graph
     * @return
     *
     */
    public static <V,E> double averageSeparation(Graph<V,E> tree, V root){
        double sum = 0;

        for(V vertex : tree.vertices()){
            if(vertex != root){
                List<V> path = getPath(tree,vertex);
                sum += path.size();
            }
        }
        return sum/tree.numVertices();
    }
}