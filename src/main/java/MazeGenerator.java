import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeGenerator {
    public static Maze generateMaze(int size) {
        Cell[][] cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(true, true, j, i);
            }
        }
         buildMaze(cells);
        return new Maze(cells);
    }
    private static void buildMaze(Cell[][] maze) {
        Cell currentCell = maze[0][0];
        currentCell.setVisited(true);
        List<Cell> visitedCells = new ArrayList<Cell>();
        visitedCells.add(currentCell);
        while (true) {
            Cell nextCell = getNextCell(currentCell, maze);
            if (nextCell == null) {
                if (visitedCells.isEmpty()) {
                    break;
                } else {
                    currentCell = visitedCells.get(visitedCells.size() - 1);
                    visitedCells.remove(visitedCells.size() - 1);
                    continue;
                }
            } else {
                visitedCells.add(nextCell);
            }
            currentCell = nextCell;
        }
    }
    private static Cell getNextCell(Cell currentCell, Cell[][] maze) {
        Random random = new Random();
        List<Cell> possibleCells = new ArrayList<Cell>();
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
        } else if (nextCell == bottom) {
            nextCell.setWallTop(false);
        } else if (nextCell == left) {
            currentCell.setWallLeft(false);
        } else {
            currentCell.setWallTop(false);
        }
        nextCell.setVisited(true);
        return nextCell;
    }
    private static Cell getCell(Cell[][] maze, List<Cell> possibleCells, int row, int col) {
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
}
