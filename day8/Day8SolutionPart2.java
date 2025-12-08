import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day8SolutionPart2 {

    public static class Pair<U, V> {

        private U first;

        private V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    private static Map<Pair<JunctionBox,JunctionBox>,Double> distanceLookup = new HashMap<>();
    private static Map<JunctionBox, Circuit> circuitMap = new HashMap<>();

    /**
     * I/O
     *
     * @return
     * @throws IOException
     */
    private static List<JunctionBox> readInput() throws IOException {
        List<JunctionBox> junctionBoxes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("day8/junctionboxes.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String[] box = line.split(",");
                JunctionBox newBox = new JunctionBox(Double.parseDouble(box[0]), Double.parseDouble(box[1]), Double.parseDouble(box[2]));
                junctionBoxes.add(newBox);
                circuitMap.put(newBox, new Circuit(new HashSet<>(Collections.singleton(newBox))));
                line = br.readLine();
            }
        }
        return junctionBoxes;
    }

    private static Double calculateDistance(JunctionBox box, JunctionBox secondBox) {
        return Math.sqrt(Math.pow(box.getX() - secondBox.getX(), 2) + Math.pow(box.getY() - secondBox.getY(), 2) + Math.pow(box.getZ() - secondBox.getZ(), 2));
    }

    private static void buildDistanceMap(List<JunctionBox> junctionBoxes) {
        Set<String> cache = new HashSet<String>();
        for (JunctionBox box : junctionBoxes){
            for (JunctionBox secondBox : junctionBoxes){
                String keyStr = box.toString()+"+"+secondBox.toString();
                String reverseKeyStr = secondBox.toString()+"+"+box.toString();
                if (!box.equals(secondBox) && !cache.contains(keyStr) && !cache.contains(reverseKeyStr)){
                    distanceLookup.put(new Pair(box,secondBox), calculateDistance(box,secondBox));
                    cache.add(keyStr);
                }
            }
        }
    }


    private static Double connectJunctions() {
        List<Map.Entry<Pair<JunctionBox, JunctionBox>, Double>> sortedDistance = distanceLookup.entrySet().stream().sorted((o1, o2) -> Double.compare(o1.getValue(), o2.getValue())).collect(Collectors.toCollection(ArrayList::new));

        int newConnections = 0;
        while (true){
            Pair<JunctionBox, JunctionBox> candidateBoxes = sortedDistance.removeFirst().getKey();
            Circuit box1Circuit = circuitMap.get(candidateBoxes.first);
            Circuit box2Circuit = circuitMap.get(candidateBoxes.second);

            System.out.printf("Connecting %s and %s.", candidateBoxes.first.toString(), candidateBoxes.second.toString());

            // Both of the boxes are in different circuits.
            if (box1Circuit != null && box2Circuit != null && !box1Circuit.equals(box2Circuit)){
                System.out.printf(".... merging Circuits. %n");
                // Alright, they are going to be one circuit now!
                box1Circuit.addJunctionBox(box2Circuit.getJunctionBoxes());
                for (JunctionBox box : box2Circuit.getJunctionBoxes()){
                    circuitMap.put(box,box1Circuit);
                }
                newConnections++;
            }
            else {
                System.out.printf(".... already in the same circuit, no-op %n");
                newConnections++;
            }

            Set<Circuit> circuits = circuitMap.values().stream().collect(Collectors.toCollection(HashSet::new));
            if (circuits.size() == 1){
                return candidateBoxes.first.getX()*candidateBoxes.second.getX();
            }
        }
    }


    /**
     * Main Method for just starting it up and spitting out the answer
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        List<JunctionBox> input = readInput();
        buildDistanceMap(input);
        Double output = connectJunctions();
        System.out.printf("%nOutput: %.0f%n ", output);

    }


}