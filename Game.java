public class Game {
    private int n,m;
    private Torus map;

    public Game(int n, int m) {
        map = new Torus(n,m);
        this.n = n;
        this.m = m;
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

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

}
