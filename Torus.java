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
        int x = m/2, y = n/2;
        Point tail = new Point(x + 1,y);
        Point head = new Point(x,y);
        Snake snake = new Snake(tail, head, this);
        for (Point p : snake.getBody()) {
            setCell(p, snake);
        }
        this.snake = snake;
        isSnakeAlive = true;
        food = new Point(0,0);
        spawnFood();
    }

    public void update() {
        Point nextPoint = snake.getNextPoint();
        Object cell = getCell(nextPoint);
        if (cell == null || nextPoint.equals(snake.getTail())) {
            setCell(snake.getTail(),null);
            snake.move();
        } else if (cell == food) {
            snake.extend();
            if (snake.getSize() < getSize()) {
                spawnFood();
            }
        } else if (cell == snake) {
            snake.move();
            isSnakeAlive = false;
        }
        Point head = snake.getHead();
        setCell(head, snake);

    }

    public boolean isSnakeAlive() {
        return isSnakeAlive;
    }

    public void restrictPoint(Point p) {
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
        Random random = new Random();
        do {
            food.setX(random.nextInt(m));
            food.setY(random.nextInt(n));
        } while (getCell(food) != null);
        setCell(food,food);
    }

    public Point getFood() {
        return food;
    }

    public Snake getSnake() {
        return snake;
    }

    public Object[][] getState() {
        return state;
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

    public int getSize() {
        return n * m;
    }
}
