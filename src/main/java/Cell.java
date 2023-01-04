public class Cell {

    boolean isWallTop;
    boolean isWallLeft;
    private boolean visited;

    private int col;
    private int row;

    public Cell(boolean isWallTop, boolean isWallLeft, int col, int row) {
        this.isWallTop = isWallTop;
        this.isWallLeft = isWallLeft;
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isWallTop() {
        return isWallTop;
    }

    public void setWallTop(boolean wallTop) {
        isWallTop = wallTop;
    }

    public boolean isWallLeft() {
        return isWallLeft;
    }

    public void setWallLeft(boolean wallLeft) {
        isWallLeft = wallLeft;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "isWallTop=" + isWallTop +
                ", isWallLeft=" + isWallLeft +
                '}';
    }
}
