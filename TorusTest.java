public class TorusTest {
    public static void main(String[] args) {
        Torus torus = new Torus(4,7);
        for (int i = 0; i < 10; i++) {
            torus.move();
            System.out.println(torus);
        }

    }
}
