import java.util.ArrayList;

public class Game {
    private Torus map;

    public Game(int n, int m) {
        map = new Torus(n,m);
    }

    public void update() {
        map.update();
    }

    public Object[][] getState() {
        return map.getState();
    }

    public void setDir(Direction newDir) {
        map.getSnake().setDir(newDir);
    }

    public void setDirIfValid(Direction newDir) {
        map.getSnake().setDirIfValid(newDir);
    }

    public boolean isLost() {
        return !map.isSnakeAlive();
    }

    public int getScore() {
        return map.getSnake().getBody().size() - 2;
    }

    public boolean isWon() {
        return map.getSnake().getSize() == map.getSize();
    }

    public Point getSnakeHead() {
        return map.getSnake().getHead();
    }

    public Point getSnakeTail() {
        return map.getSnake().getTail();
    }

    public ArrayList<Point> getBody() {
        return map.getSnake().getBody();
    }

    public Point getFood() {
        return map.getFood();
    }

}
