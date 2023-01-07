import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LdfsAlgorithm {
    private Cell[][] cells;
    private Stack<Cell> stack = new Stack<Cell>();
    private Cell endCell;
    private Cell currCell;
    private boolean completed = false;
    private boolean solutionNotFound = false;
    private int depth;
    private int stepCnt = 0;
    private int impasseCnt = 0;
    private boolean lastStepWasBack = false;

    public Stack<Cell> getStack() {
        return stack;
    }

    public LdfsAlgorithm(Maze maze, int depth) {
        this.cells = maze.getCells();
        this.endCell = cells[cells.length - 1][cells.length - 1];
        this.currCell = cells[0][0];
        this.depth = depth;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].setVisited(false);
                cells[i][j].setG(0);

            }
        }
        this.currCell.setVisited(true);
    }

    public boolean isCompleted() {
        return completed;
    }

    public Cell getCurrCell() {
        return currCell;
    }

    public MazeSearchResult doSearch() {
        long startTime = System.currentTimeMillis();
        while (!completed && !solutionNotFound) {
            nextStep();
        }
        long endTime = System.currentTimeMillis();
        int pathCnt = 0;
        while (currCell.getParent() != null) {
            pathCnt++;
            currCell = currCell.getParent();
        }

        return new MazeSearchResult("LDFS", endTime - startTime, stepCnt, impasseCnt, pathCnt);
    }

    public boolean nextStep() {
        if (!completed && !solutionNotFound) {
            stepCnt++;
            lastStepWasBack = false;
            List<Cell> neighborCells = getNeighborCells(cells, currCell.getRow(), currCell.getCol());
            if (!neighborCells.isEmpty() && currCell.getG() != depth) {
                neighborCells.get(0).setParent(currCell);
                neighborCells.get(0).setG(currCell.getG() + 1);
                currCell = neighborCells.get(0);
                stack.add(currCell);
                currCell.setVisited(true);
                if (currCell == endCell) {
                    completed = true;
                }
            } else {
                if (stack.isEmpty()) {
                    solutionNotFound = true;
                    currCell = cells[0][0];
                } else {
                    currCell = stack.peek();
                    stack.pop();
                    // we are going back
                    if (!lastStepWasBack) {
                        impasseCnt++;
                    }
                    lastStepWasBack = true;
                }

            }
        }
        return completed;
    }

    private List<Cell> getNeighborCells(Cell[][] maze, int row, int col) {
        List<Cell> neighborCell = new ArrayList<Cell>();
        if (!maze[row][col].isWallLeft && !maze[row][col - 1].isVisited()) {
            neighborCell.add(maze[row][col - 1]);
        }
        if (!maze[row][col].isWallTop && !maze[row - 1][col].isVisited()) {
            neighborCell.add(maze[row - 1][col]);
        }
        boolean notLastRow = row < maze.length - 1;
        if (notLastRow && !maze[row + 1][col].isWallTop && !maze[row + 1][col].isVisited()) {
            neighborCell.add(maze[row + 1][col]);
        }
        boolean notLastCol = col < maze.length - 1;
        if (notLastCol && !maze[row][col + 1].isWallLeft && !maze[row][col + 1].isVisited()) {
            neighborCell.add(maze[row][col + 1]);
        }
        return neighborCell;
    }
}
