package ngordnet.main;

import ngordnet.Wordnet.WordNet;
import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.ArrayList;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wn;
    private NGramMap ngm;
    public HyponymsHandler(NGramMap ngm, WordNet net) {
        wn = net;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        List<String> a = new ArrayList<>();
        if (k != 0) {
            a = wn.getK(ngm, words, startYear, endYear, k);
        } else {
            a = wn.getHyponyms(words);
        }

        return a.toString();

    }
}
