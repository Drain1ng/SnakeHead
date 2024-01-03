public class SnakeTest {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Point p1 = new Point(i, i);
            Point p2 = new Point(i, i+1);
            Snake snake = new Snake(p1, p2);
            System.out.println(snake);
        }

        for (Direction dir : Direction.values()) {
            System.out.println(dir + " = "  + dir.getDx() + " , " + dir.getDy());
        }

        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0+1);
        Snake snake = new Snake(p1, p2);
        System.out.println(snake);
        for (int i = 0; i < 5; i++) {
            snake.move(false);
            System.out.println(snake);
        }

    }
}
