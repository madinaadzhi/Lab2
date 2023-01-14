import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        long seriasCnt = 20;
        List<MazeSearchResult> astarResults = new ArrayList<MazeSearchResult>();
        List<MazeSearchResult> ldfsResults = new ArrayList<MazeSearchResult>();
        for (int i = 0; i < seriasCnt; i++) {
            System.out.println("===================== Test #" + (i + 1) + " =====================");
            System.out.println("Maze generating...");
            Maze maze = MazeGenerator.generateMaze(300);

            System.out.println("Maze searching...");
            final AstarAlgorithm astarAlgorithm = new AstarAlgorithm(maze, 50000);
            MazeSearchResult astartResult = astarAlgorithm.doSearch();
            astarResults.add(astartResult);

            final LdfsAlgorithm ldfsAlgorithm = new LdfsAlgorithm(maze, 150000);
            MazeSearchResult ldfsResult = ldfsAlgorithm.doSearch();
            ldfsResults.add(ldfsResult);
        }

        System.out.println("");
        System.out.println("===================== RESULTS =====================");
        for (int i = 0; i < seriasCnt; i++) {
            System.out.println("===================== Test #" + (i + 1) + " =====================");
            System.out.println(astarResults.get(i));
            System.out.println(ldfsResults.get(i));
        }

        printSummary(astarResults, "A*");
        printSummary(ldfsResults, "LDFS");
    }

    private static void printSummary(List<MazeSearchResult> results, String title) {
        int stepSum = 0;
        int impasseSum = 0;
        int totalTime = 0;
        int pathCnt = 0;

        for (MazeSearchResult result : results) {
            stepSum += result.getStepCnt();
            impasseSum += result.getImpasseCnt();
            totalTime += result.getSearchTimeInMs();
            pathCnt += result.getPathCnt();
        }

        double avgStep = (double) stepSum / results.size();
        double avgImpasse = (double) impasseSum / results.size();
        double avgTime = (double) totalTime / results.size();
        double avgPath = (double) pathCnt / results.size();
        System.out.println();
        System.out.println("===================== Summary for " + title + " =====================");
        System.out.println("Serias count: " + results.size());
        System.out.println("Average steps count: " + avgStep);
        System.out.println("Average impasse count: " + avgImpasse);
        System.out.println("Average search time: " + avgTime);
        System.out.println("Average path count: " + avgPath);
    }

}
