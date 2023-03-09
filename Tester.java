import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @title Kevin Bacon Game Tester
 * @subtitle Assignment: PS-4
 * @Author Nathan Giffard
 * @class Dartmouth CS 10, Winter 2023
 * @date February 20th, 2023
 * @description Tests GraphBuilder, GraphLibrary, and KevinBaconGame
 */
public class Tester {
    /**
     * Test program for graph
     */
    public static void main(String[] args) throws Exception {
        //HARD-CODED GRAPH
        AdjacencyMapGraph<String, Set<String>> testGraph = new AdjacencyMapGraph<>();
        testGraph.insertVertex("Kevin Bacon");
        testGraph.insertVertex("Alice");
        testGraph.insertVertex("Bob");
        testGraph.insertVertex("Charlie");
        testGraph.insertVertex("Dartmouth");
        testGraph.insertVertex("Nobody");
        testGraph.insertVertex("Nobody's Friend");
        HashSet<String> aMovie = new HashSet<>();
        aMovie.add("A Movie");
        HashSet<String> bMovie = new HashSet<>();
        bMovie.add("B Movie");
        HashSet<String> cMovie = new HashSet<>();
        cMovie.add("C Movie");
        HashSet<String> dMovie = new HashSet<>();
        dMovie.add("D Movie");
        HashSet<String> fMovie = new HashSet<>();
        fMovie.add("F Movie");
        HashSet<String> aeMovie = new HashSet<>();
        aeMovie.add("A Movie");
        aeMovie.add("E Movie");
        testGraph.insertUndirected("Kevin Bacon","Bob", aMovie);
        testGraph.insertUndirected("Kevin Bacon","Alice",aeMovie);
        testGraph.insertUndirected("Bob","Alice",aMovie);
        testGraph.insertUndirected("Alice","Charlie",dMovie);
        testGraph.insertUndirected("Bob","Charlie",cMovie);
        testGraph.insertUndirected("Charlie","Dartmouth",bMovie);
        testGraph.insertUndirected("Nobody","Nobody's Friend",fMovie);
        System.out.println("\n" + "Hard-Coded Graph:");
        System.out.println(testGraph);

        //TEST GRAPH-BUILDER CLASS
        GraphBuilder test = new GraphBuilder();
        test.buildMap();
        AdjacencyMapGraph<String, Set<String>> myMap = test.getMap();

        System.out.println("\n" + "GraphBuilder Graph:");
        System.out.println(myMap); //View map

        //TEST CASE 1
        System.out.println("\n" + "TEST #1: Kevin Bacon at the center...");
        Graph<String,Set<String>> current = GraphLibrary.bfs(myMap,"Kevin Bacon"); //build map with Kevin at the center
        //System.out.println(current);
        System.out.println("Expected path to Dartmouth: [Kevin Bacon, Bob, Charlie, Dartmouth (Earl thereof)]");
        System.out.println("Result: " + GraphLibrary.getPath(current, "Dartmouth (Earl thereof)")); //get path
        System.out.println("Expected missing: [Nobody, Nobody's Friend]");
        System.out.println("Result: " + GraphLibrary.missingVertices(myMap,current));
        System.out.println("Expected average separation: 2.2");
        System.out.println("Result: " + GraphLibrary.averageSeparation(current, "Kevin Bacon"));

        //TEST CASE 2
        System.out.println("\n" + "TEST #2: Charlie at the center...");
        current = GraphLibrary.bfs(myMap,"Charlie"); //build map with Kevin at the center
        //System.out.println(current);
        System.out.println("Expected path to Alice: [Charlie, Dartmouth (Earl thereof)]");
        System.out.println("Result: " + GraphLibrary.getPath(current, "Alice")); //get path
        System.out.println("Expected missing: [Nobody, Nobody's Friend]");
        System.out.println("Result: " + GraphLibrary.missingVertices(myMap,current));
        System.out.println("Expected average separation: 2.0");
        System.out.println("Result: " + GraphLibrary.averageSeparation(current, "Kevin Bacon"));

        //TEST CASE 3
        System.out.println("\n" + "TEST #3: Nobody at the center...");
        current = GraphLibrary.bfs(myMap,"Nobody"); //build map with Kevin at the center
        //System.out.println(current);
        System.out.println("Expected path to Alice: []");
        System.out.println("Result: " + GraphLibrary.getPath(current, "Alice")); //get path
        System.out.println("Expected missing: [Bob, Alice, Charlie, Kevin Bacon, Dartmouth (Earl thereof)]");
        System.out.println("Result: " + GraphLibrary.missingVertices(myMap,current));
        System.out.println("Expected average separation: 1.0");
        System.out.println("Result: " + GraphLibrary.averageSeparation(current, "Nobody"));


        //TEST GAME
        KevinBaconGame.getList(myMap);
    }
}