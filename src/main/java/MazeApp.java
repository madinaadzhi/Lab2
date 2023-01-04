import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MazeApp extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        Cell[][] maze = generateMaze(100);


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

        VBox mazeBox = new VBox();
        for (Cell[] cells : maze) {
            HBox row = new HBox();
            for (int j = 0; j < maze.length; j++) {
                HBox pane = buildCell(cells[j]);
                row.getChildren().addAll(pane);
            }
            mazeBox.getChildren().addAll(row);
        }

        primaryStage.setScene(new Scene(mazeBox, 1000, 1000));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Cell getNextCell(Cell currentCell, Cell[][] maze) {
        Random random = new Random();
        List<Cell> possibleCells = new ArrayList<Cell>();
        Cell right = getCell(maze, possibleCells, currentCell.getRow(), currentCell.getCol() + 1);
        Cell left = getCell(maze, possibleCells, currentCell.getRow(), currentCell.getCol() - 1);
        ;
        Cell top = getCell(maze, possibleCells, currentCell.getRow() - 1, currentCell.getCol());
        ;
        Cell bottom = getCell(maze, possibleCells, currentCell.getRow() + 1, currentCell.getCol());
        ;

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
        System.out.println("" + currentCell.getRow() + ';' + currentCell.getCol() + " -> " + nextCell.getRow() + ';' + nextCell.getCol());
        return nextCell;
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

    private static Cell[][] generateMaze(int size) {
        Random random = new Random();
        Cell[][] cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(true, true, j, i);
            }
        }
        return cells;
    }

    private HBox buildCell(Cell cell) {
        HBox pane = new HBox();
        String borderConfig = (cell.isWallTop ? "3" : "0") + " 0 0 " + (cell.isWallLeft ? "3" : "0");
        pane.setStyle("-fx-background-color: yellow; -fx-border-color: black; -fx-border-width: " + borderConfig);
        pane.setMaxWidth(10);
        pane.setMaxHeight(10);
        pane.setMinWidth(10);
        pane.setMinHeight(10);
        return pane;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
