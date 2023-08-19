package ngordnet.proj2b_testing;

import ngordnet.Wordnet.WordNet;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.browser.NgordnetServer;
import ngordnet.main.HistoryHandler;
import ngordnet.main.HistoryTextHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        return new HyponymsHandler(ngm, wn);
    }
}
