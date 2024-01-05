public class Game {
    private Torus map;

    public Game(int n, int m) {
        map = new Torus(n,m);
    }

    public void update() {
        map.move();
    }

    public Object[][] getState() {
        return map.getState();
    }

    public void setDir(Direction newDir) {
        map.getSnake().setDir(newDir);
    }

    public boolean isGameOver() {
        return !map.isSnakeAlive();
    }

    public int getScore() {
        return map.getSnake().getBody().size() - 2; //bruger snakelength til at vurdere score
    }

    public boolean isGameWon() {
        return map.getSnake().getSize() == map.getSize();
    }

}
