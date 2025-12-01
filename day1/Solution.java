import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    static int[] dial = new int[100];
    static int pointer = 50;
    static int totalClickPastZero = 0;

    private static List<Instruction> readInput() throws IOException {
        List<Instruction> instuctionList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("day1/combo.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String direction = line.startsWith("R") ? "R" : "L";
                int rotation = Integer.parseInt(line.substring(1));
                instuctionList.add(new Instruction(direction,rotation));
                line = br.readLine();
            }
        }
        return instuctionList;
    }

    private static int solveForZero(List<Instruction> input) {
        for (Instruction instruction: input) {
            if (instruction.getDirection().equals("R")){
                for (int i = 0; i < instruction.getRotation(); i++){
                    if (pointer+1 == 100){
                        pointer = 0;
                        totalClickPastZero++;
                        continue;
                    }
                    pointer++;
                    if (pointer == 0) totalClickPastZero++;
                }
            }
            else{
                for (int i = 0; i < instruction.getRotation(); i++){
                    if (pointer-1 == -1){
                        pointer = 99;
                        continue;
                    }
                    pointer--;
                    if (pointer == 0) totalClickPastZero++;
                }
            }
            dial[pointer]++;
        }
        return dial[0];
    }

    public static void main(String[] args) throws IOException {
        List<Instruction> input = readInput();
        int combination = solveForZero(input);
        System.out.printf("The solution hit zero %s times for a total of %s clicks past 0%n", combination, totalClickPastZero);
    }




}