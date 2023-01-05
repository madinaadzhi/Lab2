import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public AstarAlgorithm(Maze maze) {
        this.cells = maze.getCells();
        this.startCell = cells[0][0];
        this.endCell = cells[cells.length - 1][cells.length - 1];
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

    public MazeSearchResult getResult() {

        return null;
    }

    public boolean nextStep() {

        if (!completed && !openList.isEmpty()) {
            stepCnt++;
            System.out.println("Current step is: " + stepCnt);
            currentCell = getCellWithMinF();
            openList.remove(currentCell);

            List<Cell> nextCellList = getCells(cells, currentCell.getRow(), currentCell.getCol());
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

    private List<Cell> getNextCell(Cell currentCell, Cell[][] maze) {
        Random random = new Random();
        List<Cell> possibleCells = new ArrayList<Cell>();
        List<Cell> nextCells = new ArrayList<Cell>();
        Cell right = getCell(maze, possibleCells, currentCell.getRow(), currentCell.getCol() + 1);
        Cell left = getCell(maze, possibleCells, currentCell.getRow(), currentCell.getCol() - 1);
        Cell top = getCell(maze, possibleCells, currentCell.getRow() - 1, currentCell.getCol());
        Cell bottom = getCell(maze, possibleCells, currentCell.getRow() + 1, currentCell.getCol());

        if (possibleCells.isEmpty()) {
            return null;
        }

        int index = random.nextInt(possibleCells.size());
        Cell nextCell = possibleCells.get(index);
        if (nextCell == right) {
            nextCell.setWallLeft(false);
            nextCells.add(nextCell);
        } else if (nextCell == bottom) {
            nextCell.setWallTop(false);
            nextCells.add(nextCell);
        } else if (nextCell == left) {
            currentCell.setWallLeft(false);
            nextCells.add(nextCell);
        } else {
            currentCell.setWallTop(false);
            nextCells.add(nextCell);
        }
        nextCell.setVisited(true);
        System.out.println("" + currentCell.getRow() + ';' + currentCell.getCol() + " -> " + nextCell.getRow() + ';' + nextCell.getCol());
        return nextCells;
    }

    private Cell getCell(Cell[][] maze, List<Cell> possibleCells, int row, int col) {
        Cell right = null;
        try {
            right = maze[row][col];
            if (!right.isVisited()) {
                possibleCells.add(right);
            }
        } catch (Exception e) {
        }
        return right;
    }

    private List<Cell> getCells(Cell[][] maze, int row, int col) {
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
