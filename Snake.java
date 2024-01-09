import java.util.ArrayList;

public class Snake {
    private Direction direction;
    private ArrayList<Point> body;
    private int size;
    private Torus torus;
    private Point validNextPoint;

    public Snake(Point tail, Point head, Torus torus) {
        body = new ArrayList<Point>();
        body.add(tail);
        body.add(head);
        direction = Direction.LEFT;
        size = body.size();
        this.torus = torus;
    }

    public Snake(Point tail, Point head) {
        this(tail,head,null);
    }

    public boolean isDirValid(Direction dir) {
        Point nextPoint = getNextPoint(dir);
        if (nextPoint.equals(getHead()) || nextPoint.equals(body.get(size - 2))) {
            return false;
        }
        validNextPoint = nextPoint;
        return true;
    }

    public void setDir(Direction dir) {
        if (!isDirValid(dir)) {
            throw new IllegalArgumentException("Invalid direction");
        }
        direction = dir;
    }

    public void setDirIfValid(Direction dir) {
        if (dir != direction && isDirValid(dir)) {
            direction = dir;
        }
    }

    public Point getNextPoint() {
        return getNextPoint(direction);
    }

    private Point getNextPoint(Direction dir) {
        Point head = getHead();
        int x = head.getX() + dir.getDx();
        int y = head.getY() + dir.getDy();
        Point nexPoint = new Point(x, y);
        if (torus != null) {
            torus.restrictPoint(nexPoint);
        }
        return nexPoint;
    }

    public void extend() {
        move(true);
    }

    public void move() {
        move(false);
    }

    private void move(boolean extend) {
        if (validNextPoint == null) {
            body.add(getNextPoint());
        } else {
            body.add(validNextPoint);
            validNextPoint = null;
        }

        if (extend) {
            size++;
        } else {
            body.remove(0);
        }
    }

    public ArrayList<Point> getBody() {
        return body;
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
