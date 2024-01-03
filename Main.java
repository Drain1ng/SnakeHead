public class Main {
    public static void main(String[] args) {
        Torus torus = new Torus(10,10);
        System.out.println(torus);
        for (int i = 0; i < 9; i++) {
            torus.move();
            System.out.println(torus);
        }
        torus.getSnake().setDir(Direction.UP);
        for (int i = 0; i < 7; i++) {
            torus.move();
            System.out.println(torus);
        }

    }
}
