import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class AstarAlgorithm {
    private Cell[][] cells;
    private boolean completed = false;
    private List<Cell> openList = new ArrayList<Cell>();
    private List<Cell> closedList = new ArrayList<Cell>();
    private Cell startCell;
    private Cell endCell;
    private Cell currentCell;
    private int stepCnt = 0;
    private int impasseCnt = 0; // тупик
    private int timeLimitInMs;

    public AstarAlgorithm(Maze maze, int timeLimitInMs) {
        this.cells = maze.getCells();
        this.startCell = cells[0][0];
        this.endCell = cells[cells.length - 1][cells.length - 1];
        this.timeLimitInMs = timeLimitInMs;
        startCell.setF(0);
        startCell.setG(0);
        openList.add(startCell);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].setVisited(false);
            }
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public MazeSearchResult doSearch() {
        long startTime = System.currentTimeMillis();
        while (!isCompleted()) {
            nextStep();
            if (System.currentTimeMillis() - startTime >= timeLimitInMs) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        int pathCnt = 0;
        while (currentCell.getParent() != null) {
            pathCnt++;
            currentCell = currentCell.getParent();
        }
        return new MazeSearchResult("A*", endTime - startTime, stepCnt, impasseCnt, completed, pathCnt);
    }

    public boolean nextStep() {
        if (!completed && !openList.isEmpty()) {
            stepCnt++;
            currentCell = getCellWithMinF();
            openList.remove(currentCell);
            List<Cell> nextCellList = getNeighborCells(cells, currentCell.getRow(), currentCell.getCol());
            if (nextCellList.size() == 1) {
                impasseCnt++;
            }

            for (Cell nextCell : nextCellList) {
                if (!closedList.contains(nextCell)) {
                    if (nextCell == endCell) {
                        nextCell.setParent(currentCell);
                        completed = true;
                        currentCell = endCell;
                    } else {
                        double f, g, h;
                        g = currentCell.getG() + 1;
                        // Manhattan distance
                        h = abs(nextCell.getRow() - endCell.getRow()) + abs(nextCell.getCol() - endCell.getCol());
                        f = g + h;
                        if (nextCell.getF() == Double.MAX_VALUE || nextCell.getF() > f) {
                            openList.add(nextCell);
                            nextCell.setG(g);
                            nextCell.setH(h);
                            nextCell.setF(f);
                            nextCell.setParent(currentCell);
                        }
                    }
                }
            }
            currentCell.setVisited(true);
            closedList.add(currentCell);
        } else {
            completed = true;
        }
        if (!completed && !openList.isEmpty()) {
            currentCell = getCellWithMinF();
        }
        return completed;
    }

    private Cell getCellWithMinF() {
        Cell cellWithMinF = openList.get(0);
        for (Cell cell : openList) {
            if (cell.getF() < cellWithMinF.getF()) {
                cellWithMinF = cell;
            }
        }
        return cellWithMinF;
    }

    private List<Cell> getNeighborCells(Cell[][] maze, int row, int col) {
        List<Cell> neighborCell = new ArrayList<Cell>();
        if (!maze[row][col].isWallLeft) {
            neighborCell.add(maze[row][col - 1]);
        }
        if (!maze[row][col].isWallTop) {
            neighborCell.add(maze[row - 1][col]);
        }
        boolean notLastRow = row < maze.length - 1;
        if (notLastRow && !maze[row + 1][col].isWallTop) {
            neighborCell.add(maze[row + 1][col]);
        }
        boolean notLastCol = col < maze.length - 1;
        if (notLastCol && !maze[row][col + 1].isWallLeft) {
            neighborCell.add(maze[row][col + 1]);
        }
        return neighborCell;
    }
}
