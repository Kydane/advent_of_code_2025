import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day11Solution {

    private static MutableGraph<Node> graph;

    public static class Pair<U, V> {

        private U first;

        private V second;

        public Pair(U first, V second) {

            this.first = first;
            this.second = second;
        }
    }

    private static Map<String, Node> nameToNodeLookup = new HashMap<>();


    private static Stack<Node> connectionPath = new Stack();
    private static List<Stack> connectionPaths = new ArrayList<>();

    /**
     * I/O
     *
     * @return
     * @throws IOException
     */
    private static Map<Node, List<String>> readInput() throws IOException {
        Map<Node,List<String>> nodesAndEdges = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("day11/servers.txt"))) {
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

    private static void findPaths(String node, String targetNode) {
        for (Node nextNode : graph.successors(nameToNodeLookup.get(node))) {
            if (nextNode.isGoesToOut()) {
                Stack<Node> temp = new Stack();
                for (Node node1 : connectionPath)
                    temp.add(node1);
                connectionPaths.add(temp);
            } else if (!connectionPath.contains(nextNode)) {
                connectionPath.push(nextNode);
                findPaths(nextNode.getName(), targetNode);
                connectionPath.pop();
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
        Map<Node,List<String>> input = readInput();
        constructGraph(input);
        findPaths("you", "out");
        System.out.printf("%nOutput: %s%n ", connectionPaths.size());

    }


}