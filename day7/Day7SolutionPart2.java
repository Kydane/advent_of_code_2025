import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day7SolutionPart2 {


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
        protected Object clone() throws CloneNotSupportedException {
            return new Place(character);
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

    private static Long[] solveTeleportation(List<Place[]> input, Long[] quantumState) {
        //Just some printing
        System.out.println();
        Place[] currentFrame = input.removeFirst();
        for (Place place : currentFrame){
            System.out.printf(place.toString());
        }
        System.out.println();
        if (quantumState != null){
            for (Long lon : quantumState){
                System.out.printf(String.valueOf(lon));
            }
        }

        if (input.isEmpty()){
            // We've arrived at the base case, go ahead and just pump out the answer.
            return quantumState;
        }

        // Lets look at the next frame and figure out what we have to do.
        Place[] nextFrame = input.get(0);

        //If my quantum state is empty, just fill it with zeros
        if (quantumState == null){
            quantumState = new Long[nextFrame.length];
            Arrays.fill(quantumState, Long.valueOf(0));
        }
        Long[] oldState = Arrays.copyOf(quantumState, quantumState.length);

        // Okay go through this frame and look at the next frame.
        for (int i = 0; i < currentFrame.length; i++) {

            Place place = currentFrame[i];
            //If we see an emitter, we got to start making some beams.
            if (place.isEmitter()){
                if (nextFrame[i].isBlank()){
                    nextFrame[i].setBeam();
                }
                else if (nextFrame[i].isSplitter()){
                    nextFrame[i-1].setBeam();
                    nextFrame[i+1].setBeam();
                    quantumState[i] = 0L;
                    quantumState[i-1] = (quantumState[i-1] == 0 && oldState[i] == 0) ? 1 : quantumState[i-1]+oldState[i];
                    quantumState[i+1] = (quantumState[i+1] == 0 && oldState[i] == 0) ? 1 : quantumState[i+1]+oldState[i];

                }
            }
            // If we see a beam, move it down if we can otherwise start splitting.
            if (place.isBeam()){
                if (nextFrame[i].isBlank()){
                    nextFrame[i].setBeam();
                }
                else if (nextFrame[i].isSplitter()){
                    // The two side beams get beams (not really needed for part 2)
                    nextFrame[i-1].setBeam();
                    nextFrame[i+1].setBeam();

                    //Keep track of multiple universes.
                    quantumState[i] = 0L;
                    quantumState[i-1] = (quantumState[i-1] == 0 && oldState[i] == 0) ? 1 : quantumState[i-1]+oldState[i];
                    quantumState[i+1] = (quantumState[i+1] == 0 && oldState[i] == 0) ? 1 : quantumState[i+1]+oldState[i];
                }
            }
        }

        return solveTeleportation(input,quantumState);
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<Place[]> input = readInput();
        Long[] beams = solveTeleportation(input, null);
        AtomicLong grandTotal = new AtomicLong(0);
        Arrays.stream(beams).forEach(aLong -> {
            grandTotal.addAndGet(aLong);
        });
        System.out.printf("%nOutput: %s%n ", grandTotal);

    }


}