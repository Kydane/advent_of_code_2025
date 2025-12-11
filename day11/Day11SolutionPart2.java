import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day11SolutionPart2 {

    private static MutableGraph<Node> graph;

    public static class Pair<U, V> {

        private final U first;

        private final V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    private static final Map<String, Node> nameToNodeLookup = new HashMap<>();

    /**
     * I/O
     *
     * @return
     * @throws IOException
     */
    private static Map<Node, List<String>> readInput() throws IOException {
        Map<Node,List<String>> nodesAndEdges = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("day11/serversPart2.txt"))) {
            String line = br.readLine();
            while (line != null) {
                String[] machineLine = line.split(":");
                Node newNode = new Node(machineLine[0].trim());
                List<String> newEdges = new ArrayList<>();
                for (String edge : machineLine[1].split(" ")){
                    if (edge.trim()!=""){
                        if(edge.trim().equals("out")){
                            newNode.setGoesToOut(true);
                            continue;
                        }
                        newEdges.add(edge.trim());
                    }
                }
                nodesAndEdges.put(newNode, newEdges);
                nameToNodeLookup.put(newNode.getName(), newNode);
                line = br.readLine();
            }
        }
        return nodesAndEdges;
    }

    private static void constructGraph(Map<Node, List<String>> input) {
        graph = GraphBuilder.directed().build();

        for (Node node : input.keySet()){
            graph.addNode(node);
        }

        for (Map.Entry<Node,List<String>> nodeEntry : input.entrySet()){
            for (String edge : nodeEntry.getValue()){
                graph.putEdge(nodeEntry.getKey(), nameToNodeLookup.get(edge));
            }
        }
    }

    private static long countPaths(Node start, Node target) {
        Map<Node, Long> memo = new HashMap<>();
        Set<Node> stack = new HashSet<>();
        return dfs(start, target, memo, stack);
    }

    private static long dfs(Node node,
                            Node target,
                            Map<Node, Long> memo,
                            Set<Node> stack) {

        if (node.equals(target) || (target == null && node.isGoesToOut())) {
            return 1;
        }

        if (memo.containsKey(node)) {
            return memo.get(node);
        }

        if (stack.contains(node)) {
            // A cycle was detected on the current path
            return 0;
        }

        stack.add(node);

        long total = 0;
        for (Node succ : graph.successors(node)) {
            total += dfs(succ, target, memo, stack);
        }

        stack.remove(node);
        memo.put(node, total);
        return total;
    }

    private static long countPathsThrough(Node start, Node end, Node x, Node y) {

        long s_x = countPaths(start, x);
        long x_y = countPaths(x, y);
        long y_e = countPaths(y, end);

        long s_y = countPaths(start, y);
        long y_x = countPaths(y, x);
        long x_e = countPaths(x, end);

        // (start → X → Y → end) + (start → Y → X → end)
        return (s_x * x_y * y_e) + (s_y * y_x * x_e);
    }

    /**
     * Main Method for just starting it up and spitting out the answer
     *
     * @param args
     * @throws IOException
     */
    static void main(String[] args) throws IOException {
        Map<Node,List<String>> input = readInput();
        constructGraph(input);
        long paths = countPathsThrough(nameToNodeLookup.get("svr"), nameToNodeLookup.get("out"), nameToNodeLookup.get("dac"), nameToNodeLookup.get("fft"));

        System.out.printf("%nOutput: %s%n ", paths);

    }


}