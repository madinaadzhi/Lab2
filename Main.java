import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Cell[][] maze = generateMaze(10);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j].isWallTop) {
                    maze[i][j].setWall(Wall.TOP);
                } else if (maze[i][j].isWallLeft) {
                    maze[i][j].setWall(Wall.LEFT);
                }
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    private static Cell[][] generateMaze(int size) {
        Random random = new Random();
        Cell[][] cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(random.nextBoolean(), random.nextBoolean(), Wall.TOP);
            }
        }
        return cells;
    }
}
