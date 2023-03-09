import java.util.Set;

public class GameRunner {
    public static void main(String[] args) throws Exception {
        String actorFile = "PS4/bacon/actors.txt";
        String movieFile = "PS4/bacon/movies.txt";
        String movieActorFile = "PS4/bacon/movie-actors.txt";

        GraphBuilder gb = new GraphBuilder(actorFile, movieFile, movieActorFile);
        gb.buildMap();
        AdjacencyMapGraph<String, Set<String>> myMap = gb.getMap();

        //PLAY GAME
        KevinBaconGame.getList(myMap);
    }
}
