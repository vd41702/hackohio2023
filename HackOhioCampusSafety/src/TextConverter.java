import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.Gson;

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
     * Log class for current crime log.
     */
    private static class Log {

        @SuppressWarnings("unused")
        private String date;

        @SuppressWarnings("unused")
        private String crime;

        @SuppressWarnings("unused")
        private String location;

    }

    private static final Set<String> STARTING_CHARS = new HashSet<>(
            Set.of("EXT", " EX", "CSA", " CS", "P20", " P2"));
    private static final Set<String> LOCATIONS = new HashSet<>(Set.of(
            "carmack lot 5", "union", "baker", "hitchcock", "maintenance",
            "e 11th", "morrill", "schottenstein", "denney", "torres",
            "osu hospital east", "rpac", "emergency", "northwest garage",
            "doan", "smith", "unknown", "wexneer", "agricultural", "physis",
            "french", "indianola and lane", "e 17th", "ethyl", "scott house",
            "canfield", "heffner", "lane ave", "1121 kinnear", "2180 n",
            "park-stradley", "blackburn", "busch", "drackett", "bowen",
            "n. high st. and e. 15th", "udf", "lawrence", "neil ave", "panera",
            "enarson", "residence on ten", "martha", "carmack lot 3",
            "medical center", "thompson", "rhodes", "james", "east chiller",
            "houston", "carmack lot 2", "east hospital", "park stradley",
            "hopkins", "arc", "chittenden", "ross", "worthington", "waterman",
            "wexner", "fry", "blankenship", "e woodruf and tuller", "healthy",
            "arps", "jones", "w 18th ave. at college", "osu campus", "pomerene",
            "18th ave", "timachev", "pressey", "old cannon", "lincoln", "hayes",
            "mershon", "harding", "47 e frambes", "younkin", "veterinary",
            "168 e 17", "browning", "ohio stadium", "e 12",
            "college rd. and woodruff"));
    private static final Set<String> CRIMES = new HashSet<>(
            Set.of("domestic violence", "theft"));

    /**
     * Determines if a given line is formatted as a crime log, if so returns the
     * line otherwise returns an empty string.
     */
    private static String getCrimeLog(String potentialLog) {
        String returnLog = "";

        if (potentialLog.length() >= 3
                && STARTING_CHARS.contains(potentialLog.substring(0, 3))) {
            returnLog = potentialLog;
        }

        return returnLog;
    }

    /**
     * Gets and returns string of data and time of current crime log.
     */
    private static String getDate(String crimeLog) {
        final int dateLength = 13;

        // Find 20th digit (start of date)
        int digitCount = 0;
        int currentChar = 0;
        while (digitCount < 20) {
            if (Character.isDigit(crimeLog.charAt(currentChar))) {
                digitCount++;
            }
            currentChar++;
        }

        // Get the date
        return crimeLog.substring(currentChar - 1, currentChar + dateLength);
    }

    /**
     * Gets and returns the location of the crime log.
     */
    private static String getLocation(String crimeLog) {
        String location = "unknown";

        for (String currentLocation : LOCATIONS) {
            if (crimeLog.contains(currentLocation)) {
                location = currentLocation;
            }
        }

        return location;
    }

    /**
     * Gets and returns the crime of the crime log.
     */
    private static String getCrime(String crimeLog) {
        String crime = "unknown";

        for (String currentCrime : CRIMES) {
            if (crimeLog.contains(currentCrime)) {
                crime = currentCrime;
            }
        }

        return crime;
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
        LinkedList<Log> fullCrimeLog = new LinkedList<>();

        // Get each crime log into fullCrimeLog
        while (sc.hasNext()) {
            String crimeLog = getCrimeLog(sc.nextLine());
            if (!crimeLog.equals("")) {
                Log currentLog = new Log();
                currentLog.crime = getCrime(crimeLog.toLowerCase());
                currentLog.date = getDate(crimeLog);
                currentLog.location = getLocation(crimeLog.toLowerCase());
                fullCrimeLog.addLast(currentLog);
            }
        }

        // Put fullCrimeLog in a json file
        Gson gson = new Gson();
        PrintStream output = new PrintStream("data/output.json");
        output.println(gson.toJson(fullCrimeLog));
        output.close();
    }

}