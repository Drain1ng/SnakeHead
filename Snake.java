import java.util.ArrayList;

public class Snake {
    private Direction direction;
    private ArrayList<Point> body;
    private int size;
    private Torus torus;

    public Snake(Point tail, Point head) {
        body = new ArrayList<Point>();
        body.add(tail);
        body.add(head);
        direction = Direction.LEFT;
        size = 2;
    }

    public void setTorus(Torus torus) {
        this.torus = torus;
    }

    public boolean isDirValid(Direction dir) {
        Point nextPoint = getNextPoint(dir);
        if (nextPoint.equals(getHead()) || nextPoint.equals(body.get(size - 2))) {
            return false;
        }
        return true;
    }

    public void setDir(Direction dir) {
        if (!isDirValid(dir)) {
            throw new IllegalArgumentException("Invalid direction");
        }
        direction = dir;
    }

    public Point getNextPoint() {
        return getNextPoint(direction);
    }

    private Point getNextPoint(Direction dir) {
        Point head = getHead();
        int x = head.getX() + dir.getDx();
        int y = head.getY() + dir.getDy();
        Point nexPoint = new Point(x, y);
        torus.restrictPoint(nexPoint);
        return nexPoint;
    }

    public void extend() {
        move(true);
    }

    public void move() {
        move(false);
    }

    private void move(boolean extend) {
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
