public class MazeSearchResult {
    private long searchTimeInMs;
    private int stepCnt;
    private int impasseCnt;

    public MazeSearchResult(long searchTimeInMs, int stepCnt, int impasseCnt) {
        this.searchTimeInMs = searchTimeInMs;
        this.stepCnt = stepCnt;
        this.impasseCnt = impasseCnt;
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
                "searchTimeInMs=" + searchTimeInMs +
                ", stepCnt=" + stepCnt +
                ", impasseCnt=" + impasseCnt +
                '}';
    }
}
