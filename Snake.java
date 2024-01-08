import java.util.ArrayList;
import java.util.InputMismatchException;

public class Snake {
    private Direction direction;
    private ArrayList<Point> body;
    private int size;

    public Snake(Point tail, Point head) {
        body = new ArrayList<Point>();
        body.add(tail);
        body.add(head);
        direction = Direction.LEFT;
        size = 2;
    }

    public void setDir(Direction dir) {
        if (dir.getDx() + direction.getDx() == 0 && dir.getDy() + direction.getDy() == 0) {
            throw new InputMismatchException("cannot reverse direction 180 degrees");
        }
        direction = dir;
    }

    public Point getNextPoint() {
        Point head = getHead();
        int x = head.getX() + direction.getDx();
        int y = head.getY() + direction.getDy();
        return new Point(x,y);
    }

    public void move(boolean extend) {
        body.add(getNextPoint());
        if (extend) {
            size++;
        } else {
            body.remove(0);
        }
    }

    public ArrayList<Point> getBody() {
        return body;
    }

    public Point setHead(Point newHead) {
        return body.set(size - 1, newHead);
    }

    public Point getHead() {
        return body.get(size - 1);
    }

    public Point getTail() {
        return body.get(0);
    }

    public String toString() {
        String str = "";
        for (Point point : body) {
            str += " " + point;
        }
        return str;
    }

    public int getSize() {
        return size;
    }

}
