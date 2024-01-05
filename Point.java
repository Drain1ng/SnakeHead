public class Point {
    private int x,y;

    public Point(int x, int y) {
        set(x,y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    //boilerplate: https://www.sitepoint.com/implement-javas-equals-method-correctly/
    public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Point point = (Point) obj;
    return y == point.getY() && x == point.getX();
}
}
