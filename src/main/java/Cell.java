public class Cell {
    boolean isWallTop;
    boolean isWallLeft;
    private boolean visited;
    private double f = Double.MAX_VALUE;
    private double g, h;
    private Cell parent;
    private int col;
    private int row;

    public Cell(boolean isWallTop, boolean isWallLeft, int col, int row) {
        this.isWallTop = isWallTop;
        this.isWallLeft = isWallLeft;
        this.col = col;
        this.row = row;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }
    public void setH(double h) {
        this.h = h;
    }

    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setWallTop(boolean wallTop) {
        isWallTop = wallTop;
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
