import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class MazeApp extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        MazeGenerator mazeGenerator = new MazeGenerator();
        final Maze maze = mazeGenerator.generateMaze(10);

        final AstarAlgorithm astarAlgorithm = new AstarAlgorithm(maze);
        final LdfsAlgorithm ldfsAlgorithm = new LdfsAlgorithm(maze, 11000);

        Button aStarBtn = new Button("A* Run");
        Button ldfsBtn = new Button("LDFS Run");
        final VBox[] mazeBox = {buildMazeUI(maze.getCells(), maze.getCells()[0][0])};
        final VBox box = new VBox();
        box.getChildren().addAll(aStarBtn, ldfsBtn, mazeBox[0]);
        aStarBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                new Thread(new Runnable() {
                    public void run() {
                        while (!astarAlgorithm.isCompleted()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }
                            astarAlgorithm.nextStep();
                            final Cell currentCell = astarAlgorithm.getCurrentCell();
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    box.getChildren().remove(mazeBox[0]);
                                    mazeBox[0] = buildMazeUI(maze.getCells(), currentCell);
                                    box.getChildren().addAll(mazeBox[0]);

                                }
                            });
                        }
                    }
                }).start();
            }
        });

        ldfsBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                ldfsAlgorithm.nextStep();
                Cell currentCell = ldfsAlgorithm.getCurrCell();
                box.getChildren().remove(mazeBox[0]);
                mazeBox[0] = buildMazeUI(maze.getCells(), currentCell);
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

        String gText = "";
        if (cell.getG() != 0) {
            gText = String.valueOf((int)cell.getG());
        }
        pane.getChildren().add(new Label(gText));
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
