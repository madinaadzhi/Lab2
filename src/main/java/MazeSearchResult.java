public class MazeSearchResult {
    private String algorithm;
    private long searchTimeInMs;
    private int stepCnt;
    private int impasseCnt;

    private int pathCnt;

    public int getPathCnt() {
        return pathCnt;
    }

    public MazeSearchResult(String algorithm, long searchTimeInMs, int stepCnt, int impasseCnt, int pathCnt) {
        this.algorithm = algorithm;
        this.searchTimeInMs = searchTimeInMs;
        this.stepCnt = stepCnt;
        this.impasseCnt = impasseCnt;
        this.pathCnt = pathCnt;
    }

    public long getSearchTimeInMs() {
        return searchTimeInMs;
    }

    public int getStepCnt() {
        return stepCnt;
    }

    public int getImpasseCnt() {
        return impasseCnt;
    }


    @Override
    public String toString() {
        return "MazeSearchResult{" +
                "algorithm='" + algorithm + '\'' +
                ", searchTimeInMs=" + searchTimeInMs +
                ", stepCnt=" + stepCnt +
                ", impasseCnt=" + impasseCnt +
                ", pathCnt=" + pathCnt +
                '}';
    }
}
