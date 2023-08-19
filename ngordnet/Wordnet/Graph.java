package ngordnet.Wordnet;

import java.util.*;

public class Graph {
    private TreeMap<Integer, Node> graph;

    public Graph() {
        graph = new TreeMap<>();
    }

    public void addInt(int iD, String[] words) {
        Node node = new Node(words);
        graph.put(iD, node);
    }

    public void addHypo(int iD, Set<Integer> hypo) {
        Node node = graph.get(iD);
        node.hyponyms = hypo;
        if (hypo.size() > 0) {
            node.hypernym = true;
        }
    }

    public String[] getWord(int iD) {
        return graph.get(iD).words;
    }

    public Set<Integer> getHypo(int iD) {
        return graph.get(iD).hyponyms;
    }

    public boolean isHyper(int iD) {
        return graph.get(iD).hypernym;
    }

    private void hyponymfinderhelper(Set<String> resultlst, int id) {
        Node hyponode = graph.get(id);
        String[] hypowords = hyponode.words();
        for (int i = 0; i < hypowords.length; i++) {
            resultlst.add(hypowords[i]);
        }
        if (isHyper(id)) {
            Set<Integer> hyponyms = hyponode.hyponyms();
            for (Integer x : hyponyms) {
                hyponymfinderhelper(resultlst, x);
            }
        }
    }

    public Set<String> hyponymfinder(Set<Integer> querried) {
        if (querried.isEmpty()) {
            return null;
        } else {
            Set<String> resultlst = new TreeSet<>();
            for (Integer i : querried) {
                Node node = graph.get(i);
                String[] nodewords = node.words();
                for (int v = 0; v < nodewords.length; v++) {
                    resultlst.add(nodewords[v]);
                }
                if (isHyper(i)) {
                    Set<Integer> hyponyms = node.hyponyms();
                    for (Integer x : hyponyms) {
                        hyponymfinderhelper(resultlst, x);
                    }
                }
            }
            return resultlst;
        }
    }

    public class Node {
        private final String[] words;
        private Set<Integer> hyponyms;
        private boolean hypernym;

        private Node(String[] word) {
            this.words = word;
            hyponyms = new TreeSet<>();
            hypernym = false;
        }

        public String[] words() {
            return words;
        }

        public Set<Integer> hyponyms() {
            return hyponyms;
        }
    }
}
