import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
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
        private LinkedList<String> crimes;

        @SuppressWarnings("unused")
        private String location;

        @SuppressWarnings("unused")
        private String disposition;

    }

    private static final Set<String> STARTING_CHARS = new HashSet<>(
            Set.of("EXT", " EX", "CSA", " CS", "P20", " P2"));
    private static final Set<String> LOCATIONS = new HashSet<>(Set.of(
            "carmack lot 5", "union", "baker hall", "hitchcock", "maintenance",
            "e 11th", "morrill", "schottenstein", "denney", "torres",
            "osu hospital east", "rpac", "emergency", "northwest garage",
            "doan", "smith-steeb", "unknown", "wexneer", "agricultural",
            "physics", "french", "indianola and lane", "e 17th", "ethyl",
            "scott house", "canfield", "heffner", "lane ave", "1121 kinnear",
            "2180 n", "park-stradley", "blackburn", "busch", "drackett",
            "bowen", "n. high st. and e. 15th", "udf", "lawrence", "neil ave",
            "panera", "enarson", "residence on ten", "martha", "carmack lot 3",
            "medical center", "thompson", "rhodes", "james", "east chiller",
            "houston", "carmack lot 2", "east hospital", "park stradley", "arc",
            "chittenden", "ross", "worthington", "waterman", "wexner", "fry",
            "blankenship", "e woodruf and tuller", "healthy", "arps", "jones",
            "w 18th ave. at college", "pomerene", "18th ave", "timachev",
            "pressey", "old cannon", "lincoln", "hayes", "mershon", "harding",
            "47 e frambes", "younkin", "veterinary", "168 e 17", "browning",
            "ohio stadium", "e 12", "college rd. and woodruff", "alpha ep",
            "hughes", "dreese", "compliance", "knowlton", "siebert", "drinko",
            "1670 neil", "metropolitan", "components", "e 14", "brain",
            "carepoint", "northeast stadium", "e 13th", "66 w 10th", "scholars",
            "safe auto", "ramseyer", "lane ave and carmack", "mcdonald",
            "227 e 15th", "south oval", "11th ave garage", "stores",
            "delta tau", "bradley", "mendenhall", "animal science", "goco",
            "highland st. & w. 11th", "smith lab", "applewood",
            "osu medical center", "west parking", "postle", "scarlet",
            "fawcett", "blackwell", "townshend", "wilce", "indianola",
            "12th and indianola", "150 w lane", "1928 n", "pearl st and 13th",
            "raney", "nosker", "w 12th ave & college", "fontana", "meiling",
            "osu obstetrics", "research admin", "united dairy", "ives drive",
            "west woodruff", "mack", "buckeye village", "metro high",
            "indianola middle", "paterson", "baker systems and dreese",
            "hopkins", "pearl st", "w 11th ave & neil", "marketplace", "cvs",
            "traditions at scott", "osu human", "scott lab", "osu main ed",
            "bistro", "medcical center", "mirror lake", "vet med", "walgreens",
            "w 5th @ olentangy", "bricker", "gromwell", "kennedy",
            "w llth ave @ neil", "houck house", "university square",
            "gateway B", "fisher commons", "osu hospital", "union market",
            "interdisciplinary research", "target", "jesse owens memorial",
            "morill", "summit bridge", "9th ave", "student academic",
            "transit hub", "kunz", "pi lambda", "hale hall", "jennings",
            "biomedical research", "atwell", "n high street at e frambes",
            "bolz", "kottman", "llth ave parking", "cockins",
            "bradley-paterson", "the veteran's", "archer", "neil building",
            "north recreation", "veterinary medicine"));
    private static final Set<String> CRIMES = new HashSet<>(Set.of("theft",
            "stalking", "public indecency", "domestic violence",
            "gross sexual imposition", "warrant arrest", "aggravated menacing",
            "criminal mischief", "rape", "illegal use or possession",
            "disorderly conduct", "harassment", "assault", "criminal trespass",
            "drugs", "obstructing official business", "endangering",
            "dating violence", "vandalism", "burglary", "extortion",
            "graffitism", "fondling", "strangulation", "identity fraud",
            "public urination", "robbery", "joy riding",
            "receiving stolen property", "falsification", "marijuana", "hazing",
            "telecommunications fraud", "breaking and entering", "obscenity",
            "open liquor", "possessing criminal tools", "resisting arrest",
            "misuse of credit card", "unlawful restraint", "prohibitions",
            "aggravated robbery", "obstructing justice", "counterfeiting"));

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
        String location = "";

        for (String currentLocation : LOCATIONS) {
            if (crimeLog.contains(currentLocation)
                    && currentLocation.length() > location.length()) {
                location = currentLocation;
            }
        }

        if (location.equals("")) {
            location = "unknown";
        }

        return location;
    }

    /**
     * Gets and returns the crime of the crime log.
     */
    private static LinkedList<String> getCrimes(String crimeLog) {
        LinkedList<String> crimes = new LinkedList<>();

        for (String currentCrime : CRIMES) {
            if (crimeLog.contains(currentCrime)) {
                crimes.addLast(currentCrime);
            }
        }

        if (crimes.size() == 0) {
            crimes.addLast("unknown");
        }

        return crimes;
    }

    /**
     * Gets and returns the disposition of the crime log.
     */
    private static String getDisposition(String crimeLog) {
        // Assume open, change to closed if needed
        String disposition = "open";

        if (crimeLog.contains("closed")) {
            disposition = "closed";
        }

        return disposition;
    }

    /**
     * Takes a well-formatted date and returns the hour
     */
    private static String getHour(String date) {
        return date.substring(9, 11);
    }

    /**
     * Takes a well-formatted date and time and returns the date without the
     * year
     */
    private static String parseDate(String date) {
        return date.substring(0, 5);
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
        Map<String, Integer> hourFrequency = new HashMap<>(
                Map.of("00", 0, "01", 0, "02", 0, "03", 0, "04", 0, "05", 0,
                        "06", 0, "07", 0, "08", 0, "09", 0));
        hourFrequency.putAll(new HashMap<String, Integer>(Map.of("10", 0, "11",
                0, "12", 0, "13", 0, "14", 0, "15", 0, "16", 0)));
        hourFrequency.putAll(new HashMap<String, Integer>(Map.of("17", 0, "18",
                0, "19", 0, "20", 0, "21", 0, "22", 0, "23", 0)));
        LinkedList<Log> fullCrimeLog = new LinkedList<>();
        Map<String, Integer> dayFrequency = new HashMap<>();

        // Get each crime log into fullCrimeLog
        while (sc.hasNext()) {
            String crimeLog = getCrimeLog(sc.nextLine());
            if (!crimeLog.equals("")) {
                Log currentLog = new Log();
                currentLog.crimes = getCrimes(crimeLog.toLowerCase());
                currentLog.date = getDate(crimeLog);
                currentLog.location = getLocation(crimeLog.toLowerCase());
                currentLog.disposition = getDisposition(crimeLog.toLowerCase());

                String hour = getHour(currentLog.date);
                if (hourFrequency.containsKey(hour)) {
                    int frequency = hourFrequency.remove(hour);
                    hourFrequency.put(hour, frequency + 1);
                }

                String date = parseDate(currentLog.date);
                if (dayFrequency.containsKey(date)) {
                    int frequency = dayFrequency.remove(date);
                    dayFrequency.put(date, frequency + 1);
                } else {
                    dayFrequency.put(date, 1);
                }

                fullCrimeLog.addLast(currentLog);
            }
        }

        // Put fullCrimeLog, hourFrequency, dateFrequency in a json file
        Gson gson = new Gson();
        PrintStream outputLog = new PrintStream("data/output.json");
        PrintStream outputHours = new PrintStream("data/hours.json");
        PrintStream outputDates = new PrintStream("data/dates.json");

        outputLog.println(gson.toJson(fullCrimeLog));
        outputHours.println(gson.toJson(hourFrequency));
        outputDates.println(gson.toJson(dayFrequency));

        outputLog.close();
        outputHours.close();
        outputDates.close();
    }

}