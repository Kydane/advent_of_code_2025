import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day3Solution {

    /**
     * I/O
     * @return
     * @throws IOException
     */
    private static List<List<Integer>> readInput() throws IOException {
        List<List<Integer>> idList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("day3/batteries.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String[] allLines = line.split("");
                List<Integer> tempList = new ArrayList<>();
                Arrays.stream(allLines).forEach(s -> tempList.add(Integer.parseInt(s)));
                idList.add(tempList);
                line = br.readLine();
            }
        }
        return idList;
    }

    private static int findVoltages(List<List<Integer>> input) {
        int addedVoltages = 0;
        for (List<Integer> batteryLine : input) {
            int highestIndex = 0;
            int highestVoltage = 0;
            for (int i = 0; i < batteryLine.size()-1; i++){
                Integer index = batteryLine.get(i);
                List<Integer> subList = batteryLine.subList(i+1, batteryLine.size());
                Integer highestNext = Collections.max(subList);
                int potentialVoltage = Integer.parseInt(index.toString()+highestNext.toString());
                if (potentialVoltage > highestVoltage){
                    highestVoltage = potentialVoltage;
                    highestIndex = index;
                }
                if (highestIndex > highestNext) {
                    break;
                }
            }
            System.out.printf("In %s: the highest voltage is %s%n", batteryLine.toString(),highestVoltage);
            addedVoltages+=highestVoltage;
        }
        return addedVoltages;
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<List<Integer>> input = readInput();
        int output = findVoltages(input);
        System.out.printf("Output: %s%n ", output);

    }



}