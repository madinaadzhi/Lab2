import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;


public class MazeApp extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Cell[][] maze = generateMaze(20);
        buildMaze(maze);

        Maze m = new Maze(maze);
        final AstarAlgorithm astarAlgorithm = new AstarAlgorithm(m);

        Button btn = new Button("NextStep");
        Button displayPath = new Button("DisplayPath");
        final VBox[] mazeBox = {buildMazeUI(maze, maze[0][0])};
        final VBox box = new VBox();
        box.getChildren().addAll(btn, mazeBox[0]);
        displayPath.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Cell currentCell = astarAlgorithm.getCurrentCell();
                box.getChildren().remove(mazeBox[0]);
                mazeBox[0] = buildMazeUI(maze, currentCell);
                box.getChildren().addAll(mazeBox[0]);
            }
        });
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                System.out.println("we are here!");
                astarAlgorithm.nextStep();
                Cell currentCell = astarAlgorithm.getCurrentCell();
                box.getChildren().remove(mazeBox[0]);
                mazeBox[0] = buildMazeUI(maze, currentCell);
                box.getChildren().addAll(mazeBox[0]);
            }
        });

        primaryStage.setScene(new Scene(box, 570, 570));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private VBox buildMazeUI(Cell[][] maze, Cell currentCell) {
        VBox mazeBox = new VBox();
        for (int i = 0; i < maze.length; i++) {
            Cell[] cells = maze[i];
            HBox row = new HBox();
            for (int j = 0; j < maze.length; j++) {
                boolean currCell = i == currentCell.getRow() && j == currentCell.getCol();
                boolean pathCell = isPathCell(i, j, currentCell);
                HBox pane = buildCell(cells[j], currCell, pathCell);
                row.getChildren().addAll(pane);
            }
            mazeBox.getChildren().addAll(row);
        }
        return mazeBox;
    }

    private boolean isPathCell(int i, int j, Cell currCell) {
        List<Cell> pathCells = getCellsInPath(currCell);
        for (Cell pathCell : pathCells) {
            if (i == pathCell.getRow() && j == pathCell.getCol()) {
                return true;
            }
        }
        return false;
    }

    private List<Cell> getCellsInPath(Cell currentCell) {
        List<Cell> pathCells = new ArrayList<Cell>();
        while (currentCell.getParent() != null) {
            pathCells.add(currentCell.getParent());
            currentCell = currentCell.getParent();
        }

        return pathCells;
    }

    private void buildMaze(Cell[][] maze) {
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

    private Cell getNextCell(Cell currentCell, Cell[][] maze) {
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

    private HBox buildCell(Cell cell, boolean currCell, boolean pathCell) {
        HBox pane = new HBox();
        String borderConfig = (cell.isWallTop ? "3" : "0") + " 0 0 " + (cell.isWallLeft ? "3" : "0");
        String cellColor;
        if (pathCell) {
            cellColor = "green";
        } else if (currCell) {
            cellColor = "red";
        } else {
            if (cell.isVisited()) {
                cellColor = "grey";
            } else {
                cellColor = "yellow";
            }
        }
        pane.setStyle("-fx-background-color: " + cellColor + "; -fx-border-color: black; -fx-border-width: " + borderConfig);
        pane.setMaxWidth(25);
        pane.setMaxHeight(25);
        pane.setMinWidth(25);
        pane.setMinHeight(25);
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
