import java.util.ArrayList;

public class Game {
    private Torus map;
    private HighScores highScores;

    public Game(int n, int m) {
        map = new Torus(n,m);
        highScores = new HighScores();
    }

    public void restartGame(int n, int m) {
        map = new Torus(n, m);
    }

    public void restartGame() {
        restartGame(map.getN(),map.getM());
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

    public Direction getDirection() {
        return map.getSnake().getDirection();
    }
    public Point getFood() {
        return map.getFood();
    }

    public void updateLeaderBoard(int newScore) {
        highScores.updateLeaderBoard(newScore);
    }

    public ArrayList<Integer> getLeaderBoardScores() {
        return highScores.getLeaderBoard();
    }
}
