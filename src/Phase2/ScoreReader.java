package Phase2;

/**
 * @author Group 8 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.FileWriter;

// class for reading score data from a file
public class ScoreReader {
    // method to read scores from a given file path
    public static List<Integer> readScores(String filePath) {
        List<Integer> scores = new ArrayList<>(); // list to hold the scores
        File file = new File(filePath); // create a File object for the provided file path

        // check if the file exists
        if (!file.exists()) {
            // if the file does not exist, create it with five zeros
            try {
                file.createNewFile(); // create the file.
                try (FileWriter writer = new FileWriter(file)) { // try - catch
                    // write five zeros to the new file, one on each line
                    for (int i = 0; i < 5; i++) {
                        writer.write("0\n"); // write a zero followed by a newline
                        scores.add(0); // add zero to the scores list
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scores; // return the scores list with five zeros
        }

        // if the file exists, read the scores from it
        try (BufferedReader br = new BufferedReader(new FileReader(file))) { // try - catch
            String line;
            // read each line from the file until there are no more lines
            while ((line = br.readLine()) != null) {
                // Convert each line to an integer and add it to the scores list
                scores.add(Integer.parseInt(line.trim())); // trim whitespace and convert/parse to integer
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores; // Return the list of scores read from the file
    }

}

// class to manage score data
class ScoreManager {
    // method to get the top scores from the list scores.csv
    public static List<Integer> getTopScores(List<Integer> scores, int topCount) {
        // sort the scores in descending order.
        Collections.sort(scores, Collections.reverseOrder());
        // return a liar containing only the top # scores
        return scores.subList(0, Math.min(scores.size(), topCount));
    }
}

// class for writing score data to a file
class ScoreWriter {
    // method to write a list of scores to a specified file path
    public static void writeScores(List<Integer> scores, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) { // try - catch
            // loop through each score in the list.
            for (Integer score : scores) {
                // write each score followed by a newline to the file
                writer.write(score + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}