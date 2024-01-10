import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScores {
    private File data;
    private Scanner reader;

    // ensure capacity documentation: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
    public void updateLeaderBoard(int score) {
        data = new File("Data.txt");
        try {
            reader = new Scanner(new File("Data.txt"));
            ArrayList<Integer> scores = new ArrayList<>();
            scores.ensureCapacity(6);
            String s = "";
            while(reader.hasNextInt()) {
                scores.add(reader.nextInt());
            }
            if(!scores.contains(score)) {
                for(int i = 0; i < 5; i++) {
                    if(score > scores.get(i)) {
                        scores.add(i, score);
                        break;
                    }
                }
            }
            for(int k = 0; k < 5; k++) {
                s += String.valueOf(scores.get(k)) + "\n";
            }
            FileWriter dataFW = new FileWriter(data, false);
            dataFW.write(s);
            dataFW.close();
            reader.close();
        } catch (IOException e) {
            System.out.println("file not found");
        }
    }

    public ArrayList<Integer> getLeaderBoard() {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.ensureCapacity(5);
        try {
            reader = new Scanner(new File("Data.txt"));
            while (reader.hasNextInt()) {
                scores.add(reader.nextInt());
            }
        } catch (IOException e) {
            System.out.println("file not found");
        }
        reader.close();
        return scores;
    }
}
