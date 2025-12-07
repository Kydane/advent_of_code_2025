import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day6SolutionPart2 {


    public static class Pair<U, V> {

        private U first;

        private V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    public static class SignificantInteger{
        char[] integerArr;
        public SignificantInteger(String integerArr) {
            this.integerArr = integerArr.toCharArray();
        }
    }

    private static class Operator {
        private int sigDigits = 0;
        Operations operation;
        enum Operations {
            ADDITION,
            MULTIPLICATION;
        }

        public Operator(Operations operation) {
            this.operation = operation;
        }

        public int getSigDigits() {
            return sigDigits;
        }

        public void setSigDigits(int sigDigits) {
            this.sigDigits = sigDigits;
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
    private static Pair<SignificantInteger[][], List<Operator>> readInput() throws IOException {
        List<List<SignificantInteger>> integerStream = new ArrayList<>();
        List<Operator> operatorStream = new ArrayList<>();
        int maxLength = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("day6/smallmath.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String nextLine = br.readLine();
                if (nextLine == null){
                    int currentSignficantDigitsLength = 0;
                    Operator currentOperator = null;
                    char[] operatorLine = line.toCharArray();
                    for (int i = 0; i < operatorLine.length; i++) {
                        if (operatorLine[i] == '*'){
                            if (currentOperator != null){
                                currentOperator.setSigDigits(currentSignficantDigitsLength);
                                operatorStream.add(currentOperator);
                            }
                            currentSignficantDigitsLength = 0;
                            currentOperator = new Operator(Operator.Operations.MULTIPLICATION);

                        }
                        else if (operatorLine[i] == '+'){
                            if (currentOperator != null){
                                currentOperator.setSigDigits(currentSignficantDigitsLength);
                                operatorStream.add(currentOperator);
                            }
                            currentSignficantDigitsLength = 0;
                            currentOperator = new Operator(Operator.Operations.ADDITION);
                        }
                        else {
                            currentSignficantDigitsLength++;
                        }
                    }
                    currentOperator.setSigDigits(currentSignficantDigitsLength+1);
                    operatorStream.add(currentOperator);
                }
                line = nextLine;
            }
        }

        List<Integer> masterSigDigits = new ArrayList<>();
        operatorStream.forEach(operator -> masterSigDigits.add(operator.getSigDigits()));


        try(BufferedReader br = new BufferedReader(new FileReader("day6/smallmath.txt"))) {
            String line = br.readLine();
            while (line != null) {
                List<SignificantInteger> lineStream = new ArrayList<>();
                char[] currentLine = line.toCharArray();
                int head = 0;
                for (Integer numberOfDigitsToCount : masterSigDigits){
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < numberOfDigitsToCount; i++) {
                        builder.append(currentLine[head+i]);
                    }
                    head = head+numberOfDigitsToCount+1;
                    lineStream.add(new SignificantInteger(builder.toString()));
                }
                if (lineStream.size() > maxLength)
                    maxLength = lineStream.size();

                integerStream.add(lineStream);
                line = br.readLine();
            }
        }
        SignificantInteger[][] integerArr = new SignificantInteger[integerStream.size()-1][maxLength];
        for (int i = 0; i < integerStream.size()-1; i++){
            List<SignificantInteger> currLine = integerStream.get(i);
            for (int j = 0; j < currLine.size(); j++){
                integerArr[i][j] = currLine.get(j);
            }
        }

        return new Pair(integerArr, operatorStream);
    }

    private static List<Integer> doStupidSquidMath(List<SignificantInteger> columnNumbers, Operator operation) {
        List<Integer> fixedNumbers = new ArrayList<>();
        for (int i = 0; i < operation.getSigDigits(); i++) {
            StringBuilder builder = new StringBuilder();
            for (SignificantInteger stringInteger : columnNumbers){
                char potentialDigit = stringInteger.integerArr[i];
                if (potentialDigit != ' '){
                    builder.append(potentialDigit);
                }
            }
            if (!builder.toString().equals("")){
                fixedNumbers.add(Integer.parseInt(builder.toString()));
            }
        }
        return fixedNumbers;
    }

    private static long calculateColumns(Pair<SignificantInteger[][], List<Operator>> input) {
        long runningTotal = 0;
        SignificantInteger[][] mathArray = input.first;
        //Lets hope I have something int his array lol
        int maxLength = mathArray.length;
        List<Operator> operators = input.second;

        for (int i = 0; i < operators.size(); i++){
            List<SignificantInteger> columnNumbers = new ArrayList<>();
            long tempTotal =0;
            for (int j = 0; j < maxLength; j++){
                columnNumbers.add(mathArray[j][i]);
            }
            List<Integer> correctedNumbers = doStupidSquidMath(columnNumbers, operators.get(i));

            for (Integer columnNumber : correctedNumbers){
                switch (operators.get(i).operation){
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
        Pair<SignificantInteger[][], List<Operator>> input = readInput();
        long output = calculateColumns(input);

        System.out.printf("Output: %s%n ", output);

    }



}