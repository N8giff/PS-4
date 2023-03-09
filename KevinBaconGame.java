/**
 * @title Kevin Bacon Game
 * @subtitle Assignment: PS-4
 * @Author Nathan Giffard
 * @class Dartmouth CS 10, Winter 2023
 * @date February 20th, 2023
 * @description Uses GraphLibrary and GraphBuilder to create an interface for the Kevin Bacon Game
 */

import java.util.*;
public class KevinBaconGame {
    /**
     * Interface for the Kevin Bacon game using Scanner
     * @param myMap graph of actors
     * @param <V>   vertex
     * @param <E>   edge
     */
    public static <V,E> void getList(Graph<V,E> myMap){
        Graph<V,E> universe = null;

        System.out.println("""
                
                The Kevin Bacon Game by Nathan Giffard CS10 W23 \s
                
                Description:
                This game tests the connections between movie actors
                Use the following commands to test connections...\s
                
                Commands:
                c: list top (positive number) or bottom (negative) centers of the universe, sorted by average separation
                d: list actors sorted by degree, with degree between low and high
                i: list actors with infinite separation from the current center
                p: find path from <name> to current center of the universe
                s: list actors sorted separation from the current center, with separation between low and high
                u: make <name> the center of the universe
                q: quit game\s
                """);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String command = in.nextLine();
            String[] key = command.split(" ");

            //Pressing c with a positive integer calls the rankActor method to return sorted items
            if(key[0].equals("c")) {
                System.out.print("Enter a positive or negative integer>");
                int input = Integer.parseInt(in.nextLine());
                rankActors(myMap, input);
            }
            //Returns actors sorted by their number of neighbors
            else if(key[0].equals("d")){
                System.out.print("Enter 'low' or 'high'>");
                String input = in.nextLine();
                degree(myMap,input);
            }
            //Returns actors with infinite separation from the center actor
            else if(key[0].equals("i")) {
                infinite(myMap,universe);
            }
            //Returns the path from enterd actor to the current center
            else if(key[0].equals("p")) {
                System.out.print("Enter Actor Name>");
                String input = in.nextLine();
                V inputs = (V) input;
                path(universe,myMap, inputs);
            }
            //Sets the center to the input vertex
            else if(key[0].equals("u")) {
                System.out.print("Enter Actor Name>");
                String input = in.nextLine();
                V inputs = (V) input;
                universe = center(myMap,inputs);
            }
            //Prints the actors sorted by distance from the center
            else if (key[0].equals("s")) {
                System.out.print("Enter 'low' or 'high'>");
                String input = in.nextLine();
                separation(universe,input);
            }
            //Quits the game
            else if (key[0].equals("q")) {
                System.out.println("Thanks for playing! Goodbye!");
                quit();
            }
        }
    }

    /**
     * ranks the actors by their average separation and returns either the top or the top x number
     * @param myMap    graph to search
     * @param num       number of actors to list
     * @param <V>   vertex
     * @param <E>   edge
     */
    public static <V,E> void rankActors( Graph<V,E> myMap, int num) {
        ArrayList<V> allActors = new ArrayList<>();
        for(V current:myMap.vertices()) {
            allActors.add(current);
        }
        //Sorts actors by average separation and print either top or bottom ranked
        allActors.sort( (n1,n2)-> (int) GraphLibrary.averageSeparation(GraphLibrary.bfs(myMap,n2),n2)-(int)GraphLibrary.averageSeparation(GraphLibrary.bfs(myMap,n1),n1));
        if(num > 0) {
           System.out.println("Top " + num + " actors:");
           for (int i = 0; i < num; i++) {
               System.out.println(allActors.get(i));
           }
       }
       else if (num < 0){
            allActors.sort(Comparator.comparingInt(n -> (int) GraphLibrary.averageSeparation(GraphLibrary.bfs(myMap, n), n)));
           System.out.println("Bottom " + Math.abs(num) + " actors:");
           for(int i = 0; i > num; i--) {
               System.out.println(allActors.get(i));
           }
       }
    }

    /**
     * ranks the myMap by their degree
     * @param myMap    graph to search
     * @param degree   degree (low or high)
     * @param <V>   vertex
     * @param <E>   edge
     */
    public static <V, E> void degree(Graph<V,E> myMap,String degree) {
        ArrayList<V> allActors = new ArrayList<>();
        for(V current:myMap.vertices()) {
            allActors.add(current);
        }
        //Returns myMap sorted from lowest to highest
        if(degree.equals("low")) {
            System.out.println("Actors sorted from lowest to highest: \n");
            allActors.sort(Comparator.comparingInt(myMap::inDegree));
            System.out.println(allActors);
        }
        //Returns myMap sorted from lowest to highest
        else if (degree.equals("high")) {
            allActors.sort( (n1,n2)-> myMap.inDegree(n2) - myMap.inDegree(n1));
            System.out.println("Actors sorted from highest to lowest: \n");
            System.out.println(allActors);
        }
    }

    /**
     * Returns the actors that have no connections to the current center
     *@param myMap    graph to search
     * @param input   graph to compare
     * @param <V>   vertex
     * @param <E>   edge
     */
    public static <V, E> void infinite(Graph<V,E> myMap , Graph<V,E> input) {
        //Checks if input is null to prevent errors
        if(input==null) {
            System.out.println("Set center first by using the command u");
        }
        //Uses sets to remove items which are in one set but not the other
        else {
            System.out.println("Actors with no connections to the current center:");
            Set<V> infinite = GraphLibrary.missingVertices(myMap, input);

            System.out.println(infinite);
            System.out.println("There are " + infinite.size() + " actors with no connection to the center.");
        }
    }

    /**
     * Return the path from an actor to the current center of the map
     * @param universe     graph to search
     * @param input     starting actor
     * @param <V>   vertex
     * @param <E>   edge
     */
    public static <V, E> void path(Graph<V,E> universe , Graph<V,E> myMap, V input) {
        if(universe==null) {
            System.out.println("Set center first by using the command u");
        }
        else {
            ArrayList<V> thisPath = (ArrayList<V>) GraphLibrary.getPath(universe,input);
            System.out.println(input + "'s number is "+(thisPath.size()-1));
            for(int x = 0; x < thisPath.size()-1 ; x++){
                V actor = thisPath.get(x);
                V nextActor = thisPath.get(x+1);
                System.out.println(actor + " appeared in " + myMap.getLabel(actor, nextActor) + " with " + nextActor);
            }
        }
    }

    /**
     * Sets a new center using a given actor and returns a new tree
     * @param currMap original map
     * @param input new center
     * @return  new map with the new center
     * @param <V>   vertex
     * @param <E>   edge
     */
    public static <V, E> Graph<V, E> center( Graph<V,E> currMap,V input) {
        Graph<V,E> newMap=null ;

        if(currMap.hasVertex(input)) {
            newMap = GraphLibrary.bfs(currMap,input);
            System.out.println("Center set to "+input);
            System.out.println("There are " + (newMap.numVertices()-1) + "/9235 connections"
                    + " and an average separation of " + GraphLibrary.averageSeparation(newMap, input));
            return newMap;
        }
        else {
            System.out.println("Please try again and add a valid center actor");
            return newMap;
        }
    }

    /**
     *
     * @param currMap   current graph
     * @param input     sorting low or high
     * @param <V>   vertex
     * @param <E>   edge
     */
    //Calclates the average separation between vertices and returns them sorted in this order.
    public static <V,E> void separation(Graph<V,E> currMap,String input){

        ArrayList<V> listofvertices = new ArrayList<>();
        if(currMap==null) {
            System.out.println("Add the center of the universe first using the command u");
        }

        else if(input.equals("low")) {
            for(V current:currMap.vertices()) {
                listofvertices.add(current);
            }
            listofvertices.sort(Comparator.comparingInt(n -> (int) GraphLibrary.averageSeparation(GraphLibrary.bfs(currMap, n), n)));
            System.out.println(listofvertices);
        }
        else if(input.equals("high")) {
            for(V current:currMap.vertices()) {
                listofvertices.add(current);
            }
            listofvertices.sort( (n1,n2)-> (int) GraphLibrary.averageSeparation(GraphLibrary.bfs(currMap,n2),n2)-(int)GraphLibrary.averageSeparation(GraphLibrary.bfs(currMap,n1),n1));
            System.out.println(listofvertices);
        }
    }
    //Quits the aplication
    public static void quit() {
        Runtime.getRuntime().exit(1);

    }


}