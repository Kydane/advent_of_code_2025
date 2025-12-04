import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4SolutionPart2 {

    private static class Position{
        private boolean containsPaper = false;

        public Position() {
        }

        public Position(char parseAbleCharacter){
            if(parseAbleCharacter == '@'){
                containsPaper = true;
            }

        }

        public boolean isContainsPaper() {
            return containsPaper;
        }

        public void setContainsPaper(boolean containsPaper) {
            this.containsPaper = containsPaper;
        }


    }

    public static class Pair<U, V> {

        private U first;

        private V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    /**
     * I/O
     * @return
     * @throws IOException
     */
    private static Position[][] readInput() throws IOException {
        List<List<Position>> positionStream = new ArrayList<>();
        int maxLength = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("day4/grid.txt"))) {
            String line = br.readLine();
            while (line != null) {
                List<Position> lineStream = new ArrayList<>();
                for(char c: line.toCharArray()){
                    lineStream.add(new Position(c));
                }
                if (lineStream.size() > maxLength)
                    maxLength = lineStream.size();
                positionStream.add(lineStream);
                line = br.readLine();
            }
        }
        Position[][] positionArr = new Position[positionStream.size()][maxLength];
        for (int i = 0; i < positionStream.size(); i++){
            List<Position> currLine = positionStream.get(i);
            for (int j = 0; j < currLine.size(); j++){
                positionArr[i][j] = currLine.get(j);
            }
        }

        return positionArr;
    }

    private static Pair<Integer, List<Pair<Integer, Integer>>> optimizeForklift(Position[][] input) {
        int totalPaperRolls = 0;
        List<Pair<Integer, Integer>> removalCoordinates = new ArrayList<>();
        for (int i = 0; i < input.length; i++){
            Position[] row = input[i];
            System.out.println("");
            for (int j = 0; j < row.length; j++){
                boolean candidateContainsPaper = input[i][j].containsPaper;
                System.out.print((candidateContainsPaper)? "@": ".");
                if (candidateContainsPaper){
                    //Check Top row
                    //Lets check the boundaries first, get an idea of where we are.
                    int totalAdjacent = 0;
                    boolean topRow = (i == 0);
                    boolean bottomRow = (i == input.length-1);

                    boolean firstElementInRow = (j == 0);
                    boolean lastElementInRow = (j == input.length-1);

                    //Lets check if anything is above
                    if (!topRow){
                        //Top Left
                        if (!firstElementInRow){
                            if (input[i-1][j-1].containsPaper) totalAdjacent++;
                        }
                        //Top Middle
                        if (input[i-1][j].containsPaper) totalAdjacent++;
                        //Top Right
                        if (!lastElementInRow){
                            if (input[i-1][j+1].containsPaper) totalAdjacent++;
                        }
                    }

                    //Lets check if anything is on the sides
                    if (!firstElementInRow){
                        if (input[i][j-1].containsPaper) totalAdjacent++;
                    }
                    if (!lastElementInRow){
                        if (input[i][j+1].containsPaper) totalAdjacent++;
                    }

                    //Lets check if anything is below
                    if (!bottomRow){
                        //Bottom Left
                        if (!firstElementInRow){
                            if (input[i+1][j-1].containsPaper) totalAdjacent++;
                        }
                        //Top Middle
                        if (input[i+1][j].containsPaper) totalAdjacent++;
                        //Top Right
                        if (!lastElementInRow){
                            if (input[i+1][j+1].containsPaper) totalAdjacent++;
                        }
                    }
                    if (totalAdjacent < 4){
                        totalPaperRolls++;
                        removalCoordinates.add(new Pair<>(i,j));
                    }
                }
            }
        }
        System.out.println("");
        return new Pair<>(totalPaperRolls, removalCoordinates);
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Position[][] input = readInput();

        Pair<Integer, List<Pair<Integer, Integer>>> outputOfOptimization = optimizeForklift(input);
        Integer rollsToRemove = outputOfOptimization.first;
        int counter = 1;
        int totalRollsRemoved = 0;
        while(rollsToRemove != 0){
            System.out.printf("After %s round of optimization. Removing %s rolls", counter, rollsToRemove);
            totalRollsRemoved+=rollsToRemove;
            for (Pair<Integer,Integer> pair: outputOfOptimization.second){
                input[pair.first][pair.second].setContainsPaper(false);
            }
            outputOfOptimization = optimizeForklift(input);
            rollsToRemove = outputOfOptimization.first;
            counter ++;
        }
        System.out.printf("Output: %s%n ", totalRollsRemoved);

    }




}