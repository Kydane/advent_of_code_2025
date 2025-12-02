import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2SolutionPart2 {

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
     * Given a number and a segment, check if the number is made up of the segment.
     * @param testNumber
     * @param segment
     * @return
     */
    private static boolean fullNumberValid(String testNumber, String segment){
        int segmentLength = segment.length();

        if (testNumber.length() % segmentLength != 0){
            return true;
        }
        int leftIndexStart = 0;
        int leftIndexEnd = 0;


        for (int i = 0; i < (testNumber.length() / segmentLength); i++){
            leftIndexEnd=leftIndexEnd+segmentLength;
            String sub1 = testNumber.substring(leftIndexStart, leftIndexEnd);
            if (!segment.equals(sub1)){
                return true;
            }
            leftIndexStart=leftIndexEnd;
        }

        return false;
    }

    /**
     * Calculates if a number is valid.
     * Starts at one number and incrementally adds another digit to see if any pattern can exist.
     * If something does emerge as a pattern, the whole number is checked to see if that pattern exists throughout the entire number
     * @param testNumber
     * @return
     */
    private static boolean isValid(long testNumber) {
        String testString = String.valueOf(testNumber);
        int maxLength = testString.length() / 2;

        for (int numberOfDigits = 1; numberOfDigits <= maxLength; numberOfDigits++){
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
                if (!(sub1.startsWith("0") || sub2.startsWith("0")) && sub1.equals(sub2)){
                    if (!fullNumberValid(testString, sub1))
                        return false;
                }

                leftIndexStart++;
            }

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