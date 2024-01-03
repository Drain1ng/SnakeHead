import java.util.Random;

public class Torus {
    private int n;
    private int m;
    private Object[][] state;
    private Snake snake;
    private Point food;
    private boolean isSnakeAlive;

    public Torus(int n, int m) {
        state = new Object[n][m];
        this.n = n;
        this.m = m;
        int y = n/2, x = m/2;
        Point tail = new Point(x,y);
        Point head = new Point(x,y + 1);
        Snake snake = new Snake(tail, head);
        for (Point p : snake.getBody()) {
            setCell(p, snake);
        }
        this.snake = snake;
        isSnakeAlive = true;
        spawnFood();
    }

    public void move() {
        Point nextPoint = snake.getNextPoint();
        warpPoint(nextPoint);
        Object cell = getCell(nextPoint);
        if (cell == null || cell == snake.getTail()) {
            setCell(snake.getTail(),null);
            snake.move(false);
            Point head = snake.getHead();
            warpPoint(head);
            setCell(head, snake);
        } else if (cell == food) {
            snake.move(true);
            Point head = snake.getHead();
            warpPoint(head);
            setCell(head, snake);
            spawnFood();
        } else if (cell == snake) {
            snake.move(false);
            Point head = snake.getHead();
            warpPoint(head);
            setCell(head, "DEAD");
            isSnakeAlive = false;
        }

    }

    public boolean isSnakeAlive() {
        return isSnakeAlive;
    }

    //ikke helt testet
    public void warpPoint(Point p) {
        p.setX((p.getX() % m + m) % m);
        p.setY((p.getY() % n + n) % n);
    }

    public void setCell(Point point,Object newValue) {
        state[point.getY()][point.getX()] = newValue;
    }

    public Object getCell(Point point) {
        return state[point.getY()][point.getX()];
    }

    public void spawnFood() {
        // this is wrong. kan spawne i kroppen.
        //https://codereview.stackexchange.com/questions/151800/snake-in-javafx
        Random random = new Random();
        Point food;
        do {
            food = new Point(random.nextInt(n), random.nextInt(m));
        } while (food.equals(snake.getHead()));
        setCell(food,food);
        this.food = food;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Snake getSnake() {
        return snake;
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < n; i++) {
            for(int k = 0; k < m; k++) {
                Object elem = state[i][k];
                if (elem == null) {
                    str += "-";
                } else if (elem == snake) {
                    str += "s";
                } else if (elem == food) {
                    str += "u";
                } else {
                    str += elem;
                }
            }
            str += "\n";
        }
        return str;
    }
}
