import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day7Solution {

    private static class Place {
        char character;

        public Place(char character) {
            this.character = character;

        }

        public boolean isEmitter() {
            return character == 'S';
        }

        public boolean isSplitter() {
            return character == '^';
        }

        public boolean isBlank() {
            return character == '.';
        }

        public boolean isBeam() {
            return character == '|';
        }

        public void setBeam(){
            character = '|';
        }

        public void setSplitter(){
            character = '^';
        }

        public void setEmitter(){
            character = 'S';
        }

        @Override
        public String toString() {
            return String.valueOf(character);
        }
    }

    /**
     * I/O
     *
     * @return
     * @throws IOException
     */
    private static List<Place[]> readInput() throws IOException {
        List<Place[]> frameList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("day7/grid.txt"))) {
            String line = br.readLine();
            while (line != null) {
                Place[] placeArr = new Place[line.length()];
                for (int i = 0; i < line.toCharArray().length; i++) {
                    placeArr[i] = new Place(line.toCharArray()[i]);
                }
                frameList.add(placeArr);
                line = br.readLine();
            }
        }
        return frameList;
    }

    private static int solveTeleportation(List<Place[]> input, int totalSplit) {
        System.out.println();
        Place[] currentFrame = input.removeFirst();
        for (Place place : currentFrame){
            System.out.printf(place.toString());
        }
        if (input.size() == 0){
            // We've arrived at the base case, go ahead and just pump out the answer.
            return totalSplit;
        }

        Place[] nextFrame = input.get(0);

        for (int i = 0; i < currentFrame.length; i++) {
            Place place = currentFrame[i];
            if (place.isEmitter()){
                if (nextFrame[i].isBlank()){
                    nextFrame[i].setBeam();
                    continue;
                }
                else if (nextFrame[i].isSplitter()){
                    totalSplit++;
                    nextFrame[i-1].setBeam();
                    nextFrame[i+1].setBeam();
                    continue;
                }
            }

            if (place.isBeam()){
                if (nextFrame[i].isBlank()){
                    nextFrame[i].setBeam();
                    continue;
                }
                else if (nextFrame[i].isSplitter()){
                    totalSplit++;
                    nextFrame[i-1].setBeam();
                    nextFrame[i+1].setBeam();
                    continue;
                }
            }
        }

        return solveTeleportation(input,totalSplit);
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<Place[]> input = readInput();
        int beams = solveTeleportation(input, 0);

        System.out.printf("%nOutput: %s%n ", beams);

    }


}