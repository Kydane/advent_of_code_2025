import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day6Solution {


    public static class Pair<U, V> {

        private U first;

        private V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    private static enum Operator {
        ADDITION,
        MULTIPLICATION;

        Operator() {
        }

        public static Operator parseOperator(String parseable){
            if (parseable.trim().equals("+")){
                return Operator.ADDITION;
            }
            else if (parseable.trim().equals("*")){
                return Operator.MULTIPLICATION;
            }
            throw new RuntimeException("Found an unhandled operator. This should never happen.");
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str.trim());
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * I/O
     * @return
     * @throws IOException
     */
    private static Pair<Integer[][], List<Operator>> readInput() throws IOException {
        List<List<Integer>> IntegerStream = new ArrayList<>();
        List<Operator> operatorStream = new ArrayList<>();
        int maxLength = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("day6/math.txt"))) {
            String line = br.readLine();
            while (line != null) {
                boolean addedLine = false;
                List<Integer> lineStream = new ArrayList<>();
                for(String c: line.split(" ")){
                    if(c.trim()!= ""){
                        if (isNumeric(c)){
                            lineStream.add(Integer.parseInt(c));
                            addedLine = true;
                        }
                        else{
                            operatorStream.add(Operator.parseOperator(c));
                        }
                    }

                }
                if (lineStream.size() > maxLength)
                    maxLength = lineStream.size();
                if(addedLine)
                    IntegerStream.add(lineStream);
                line = br.readLine();
            }
        }
        Integer[][] integerArr = new Integer[IntegerStream.size()][maxLength];
        for (int i = 0; i < IntegerStream.size(); i++){
            List<Integer> currLine = IntegerStream.get(i);
            for (int j = 0; j < currLine.size(); j++){
                integerArr[i][j] = currLine.get(j);
            }
        }

        return new Pair(integerArr, operatorStream);
    }

    private static long calculateColumns(Pair<Integer[][], List<Operator>> input) {
        long runningTotal = 0;
        Integer[][] mathArray = input.first;
        //Lets hope I have something int his array lol
        int maxLength = mathArray.length;
        List<Operator> operators = input.second;

        for (int i = 0; i < operators.size(); i++){
            List<Integer> columnNumbers = new ArrayList<>();
            long tempTotal =0;
            for (int j = 0; j < maxLength; j++){
                columnNumbers.add(mathArray[j][i]);
            }

            for (Integer columnNumber : columnNumbers){
                switch (operators.get(i)){
                    case ADDITION -> {
                        tempTotal+=columnNumber;
                    }
                    case MULTIPLICATION -> {
                        if(tempTotal==0){
                            tempTotal=columnNumber;
                            continue;
                        }
                        tempTotal*=columnNumber;
                    }
                }
            }
            runningTotal+=tempTotal;
        }
        return runningTotal;
    }
    /**
     * Main Method for just starting it up and spitting out the answer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Pair<Integer[][], List<Operator>> input = readInput();
        long output = calculateColumns(input);

        System.out.printf("Output: %s%n ", output);

    }



}