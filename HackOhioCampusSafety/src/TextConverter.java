import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Converts well-formatted txt file of campus safety to json data.
 */
public final class TextConverter {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TextConverter() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(
                new File("data/Daily_Crime_Log_-_Columbus.txt"));

        while (sc.hasNext()) {
            System.out.println(sc.nextLine());
        }
    }

}