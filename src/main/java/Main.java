import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        long seriasCnt = 3;
        int stepSum = 0;
        int impasseSum = 0;
        long totalTime = 0;
        for (int i = 0; i < seriasCnt; i++) {
            System.out.println("=====================Test #" + i + " ===================");
            System.out.println("Maze generating...");
            Maze maze = MazeGenerator.generateMaze(10);
            final AstarAlgorithm astarAlgorithm = new AstarAlgorithm(maze);
            System.out.println("Maze searching...");
            MazeSearchResult mazeSearchResult = astarAlgorithm.doSearch();
            System.out.println(mazeSearchResult);
            stepSum = stepSum + mazeSearchResult.getStepCnt();
            impasseSum = impasseSum + mazeSearchResult.getImpasseCnt();
            totalTime = totalTime + mazeSearchResult.getSearchTimeInMs();
        }
        double avgStep = (double) stepSum / seriasCnt;
        double avgImpasse = (double) impasseSum / seriasCnt;
        double avgTime = (double) totalTime / seriasCnt;
        System.out.println();
        System.out.println("=====================Summary===================");
        System.out.println("Serias count: "+ seriasCnt);
        System.out.println("Average steps count: "+ avgStep);
        System.out.println("Average impasse count: "+ avgImpasse);
        System.out.println("Average search time: " + avgTime);
    }

}
