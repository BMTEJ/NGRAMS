package ngordnet.Wordnet;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

import static java.lang.Integer.parseInt;

public class WordNet {
    private final TreeMap<String, Set<Integer>> wordID;
    private final Graph graph;

    public WordNet(String fileSynset, String fileHyponyms) {
        In inSynset = new In(fileSynset);
        In inHyponyms = new In(fileHyponyms);
        wordID = new TreeMap<>();
        graph = new Graph();
        synsetInitializer(inSynset);
        hyponymInitializer(inHyponyms);
    }

    private void synsetInitializer(In fileIn) {
        while (fileIn.hasNextLine() && !fileIn.isEmpty()) {
            String line = fileIn.readLine();
            String[] concat = line.split(",");
            String iD = concat[0];
            int iDint = parseInt(iD);
            String words = concat[1];
            String[] a = words.split(" ");
            graph.addInt(iDint, a);
            for (int i = 0; i < a.length; i++) {
                String word = a[i];
                if (wordID != null && wordID.containsKey(word)) {
                    wordID.get(word).add(iDint);
                } else {
                    Set<Integer> idLst = new TreeSet<>();
                    idLst.add(iDint);
                    wordID.put(word, idLst);
                }
            }
        }
    }

    private void hyponymInitializer(In fileIn) {
        while (fileIn.hasNextLine() && !fileIn.isEmpty()) {
            String[] a = fileIn.readLine().split(",");
            int hyper = parseInt(a[0]);
            Set<Integer> hypos = new TreeSet<>();
            for (int i = 1; i < a.length; i++) {
                hypos.add(parseInt(a[i]));
            }
            graph.addHypo(hyper, hypos);
        }
    }

    public List<String> getHyponyms(List<String> query) {
        List<String> hyporesults = new ArrayList<>();
        List<Set<String>> curr = new ArrayList<>();
        for (String s : query) {
            if (!wordID.containsKey(s)) {
            } else {
                curr.add(graph.hyponymfinder(wordID.get(s)));
            }
        }
        int size = curr.size();
        if (size == 1) {
            for (String s : curr.get(0)) {
                hyporesults.add(s);
            }
            return hyporesults;
        }
        Map<String, Integer> weightMap = new TreeMap<>();
        for (Set<String> lst : curr) {
            for (String word : lst) {
                if (weightMap.containsKey(word)) {
                    int plusOne = weightMap.get(word) + 1;
                    weightMap.put(word, plusOne);
                } else {
                    weightMap.put(word, 1);
                }
            }
        }
        for (String word : weightMap.keySet()) {
            if (weightMap.get(word) >= size) {
                hyporesults.add(word);
            }
        }
        return hyporesults;
    }

    public List<String> getK(NGramMap ngm, List<String> query, int startYear, int endYear, int k) {
        List<String> hypo = getHyponyms(query);
        Map<Double, String> weightedMap = new TreeMap<>();
        List<String> result = new ArrayList<>();
        for (String word : hypo) {
            TimeSeries wordTS = ngm.countHistory(word, startYear, endYear);
            double totalCount = 0;
            for (int year : wordTS.keySet()) {
                totalCount += wordTS.get(year);
            }
            if (!wordTS.isEmpty()) {
                weightedMap.put(totalCount, word);
            }
        }
        int size = weightedMap.size();
        int kSize = size - k;
        for (Double key : weightedMap.keySet()) {
            kSize--;
            if (kSize < 0 && key >= 0) {
                result.add(weightedMap.get(key));
            }
        }
        Collections.sort(result);
        return result;
    }
}

