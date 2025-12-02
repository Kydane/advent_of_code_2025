import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2Solution {

    /**
     * I/O
     * @return
     * @throws IOException
     */
    private static List<String[]> readInput() throws IOException {
        List<String[]> idList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("day2/ids.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String[] allLines = line.split(",");
                for (String singleIdRange : allLines) {
                    idList.add(singleIdRange.split("-"));
                }
                line = br.readLine();
            }
        }
        return idList;
    }

    /**
     * Calculates if a number is valid.
     * Starts at the max length/2 (must be even since it requires the whole number to be exactly the silly pattern)
     * Then calculates the appropriate indexes for substringing and checks the sub. If its not valid, returns it.
     * @param testNumber
     * @return
     */
    private static boolean isValid(long testNumber) {
        String testString = String.valueOf(testNumber);
        int maxLength = testString.length() / 2;
        if (testString.length() % 2 != 0)
            return true;
        int numberOfDigits = maxLength;
        int leftIndexStart = 0;
        int leftIndexEnd = 0;

        int rightIndexStart = 0;
        int rightIndexEnd = 0;

        while (leftIndexStart < maxLength){
            leftIndexEnd = numberOfDigits+leftIndexStart;
            rightIndexStart = leftIndexEnd;
            rightIndexEnd = rightIndexStart+numberOfDigits;

            if (rightIndexStart > testString.length() || rightIndexEnd > testString.length()){
                break;
            }

            String sub1 = testString.substring(leftIndexStart, leftIndexEnd);
            String sub2 = testString.substring(rightIndexStart, rightIndexEnd);
            if (sub1.equals(sub2)){
                return false;
            }

            leftIndexStart++;
        }


        return true;

    }


    /**
     * Main method for going through each number in a range and checking if its valid. If its not valid,
     * go ahead and add it to the count
     * @param input
     * @return
     */
    private static long findInvalidIds(List<String[]> input) {
        long totalInvalidIds = 0;
        for (String[] idRange : input){
            String lowerRange = idRange[0];
            String upperRange = idRange[1];

            long testNumber = Long.parseLong(lowerRange);
            while (testNumber != Long.parseLong((upperRange))+1){
                if (!isValid(testNumber)){
                    System.out.printf("Invalid: %s%n", testNumber);
                    totalInvalidIds += testNumber;
                }
                testNumber++;
            }
        }
        return totalInvalidIds;
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<String[]> input = readInput();
        long output = findInvalidIds(input);
        System.out.printf("Output: %s%n ", output);

    }

}