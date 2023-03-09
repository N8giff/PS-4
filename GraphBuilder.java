/**
 * @title Kevin Bacon Game
 * @subtitle Assignment: PS-4
 * @Author Nathan Giffard
 * @class Dartmouth CS 10, Winter 2023
 * @date February 20th, 2023
 * @description Imports data files and returns an AdjacencyMapGraph
 * with actors as vertices and movies they costar in as edges
 */
import java.util.*;
import java.io.*;
public class GraphBuilder {
    static String actorPath = "";
    static String moviePath = "";
    static String movieActorPath = "";

    AdjacencyMapGraph<String, Set<String>> myMap = new AdjacencyMapGraph<>();

    public GraphBuilder(){
        actorPath = "PS4/bacon/actorsTest.txt";
        moviePath = "PS4/bacon/moviesTest.txt";
        movieActorPath = "PS4/bacon/movie-actorsTest.txt";
    }

    public GraphBuilder(String actorFile, String movieFile, String movieActorFile){
        actorPath = actorFile;
        moviePath = movieFile;
        movieActorPath = movieActorFile;
    }

    /**
     * Maps actor IDs to actor names
     *
     * @param pathName is the file containing the data
     * @return  map of actors and their movie connections
     * @throws Exception    files may not load
     */
    public static Map<String, String> loadActors(String pathName) throws Exception {
        Map<String, String> actorID = new HashMap<>();

        try {
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            String line;
            while ((line = input.readLine()) != null) {
                String[] ids = line.split("\\|");
                actorID.put(ids[0], ids[1]);
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Actor file not loaded properly");
        }
        return actorID;
    }

    /**
     * Maps movie IDs to movie names
     *
     * @param pathName is the file containing the data
     * @return
     * @throws Exception
     */
    public static Map<String, String> loadMovies(String pathName) throws Exception {
        Map<String, String> movieID = new HashMap<>();

        try {
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            String line;
            while ((line = input.readLine()) != null) {
                String[] ids = line.split("\\|");
                movieID.put(ids[0], ids[1]);
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Movie file not loaded properly");
        }
        return movieID;
    }

    /**
     * Maps movie IDs to actors IDs
     *
     * @param pathName is the file containing the data
     * @return
     * @throws Exception
     */
    public static Map<String, ArrayList<String>> loadMovieActors(String pathName) {
        Map<String, ArrayList<String>> movieActors = new HashMap<>();

        try {
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            String line;
            while ((line = input.readLine()) != null) {
                String[] ids = line.split("\\|");
                ArrayList<String> actors;
                if (!movieActors.containsKey(ids[0])) {
                    actors = new ArrayList<>();
                } else {
                    actors = movieActors.get(ids[0]);
                }
                actors.add(ids[1]);
                movieActors.put(ids[0], actors);
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Actor-Movie file not loaded properly");
        }
        return movieActors;
    }

    public AdjacencyMapGraph<String, Set<String>> getMap(){
        return myMap;
    }

    /**
     * Builds an adjacency map graph of actors with their movies on the edges
     */
    public void buildMap() throws Exception {
        Map<String, String> actorMap = loadActors(actorPath); //map of actor ID to actor name
        Map<String, String> movieMap = loadMovies(moviePath); // map of movie ID to movie name
        Map<String, ArrayList<String>> movieActorMap = loadMovieActors(movieActorPath); //map of movie ID to actor ID

        //Add actors to myMap as vertices
        for(String id : actorMap.keySet()){
            myMap.insertVertex(actorMap.get(id));
        }

        //Loop over each movie and insert an
        // undirected edge in the map to link every actor
        // to every other actor with the movie ID as the edge
        for (String movie : movieActorMap.keySet()) {
            ArrayList<String> allActors = movieActorMap.get(movie); //get the list of actors for that movie
            for (String thisActor : allActors) { //loop over all actors
                for (int x = 0; x < allActors.size(); x++) {
                    if(!thisActor.equals(allActors.get(x))) {
                        Set<String> movies = new HashSet<>();
                        if(myMap.hasEdge(actorMap.get(thisActor), actorMap.get((allActors.get(x))))){
                            movies = myMap.getLabel(actorMap.get(thisActor),actorMap.get(allActors.get(x)));
                        }
                        movies.add(movieMap.get(movie));
                        myMap.insertUndirected(actorMap.get(thisActor), actorMap.get((allActors.get(x))), movies);
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws Exception {
        GraphBuilder gb = new GraphBuilder();
        gb.buildMap();
        AdjacencyMapGraph<String, Set<String>> myMap = gb.getMap();
        System.out.println(myMap);
        //KevinBaconGame.getList(myMap);
    }

}
