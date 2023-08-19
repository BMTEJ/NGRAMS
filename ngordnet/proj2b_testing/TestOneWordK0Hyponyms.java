package ngordnet.proj2b_testing;
import ngordnet.Wordnet.WordNet;
import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.ngrams.NGramMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void basicTest() {
        WordNet wn = new WordNet("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
        List<String> query = new ArrayList<>();
        query.add("transition");
        query.add("alteration");
        assertThat(wn.getHyponyms(query)).containsExactly("jump", "leap", "saltation", "transition");

        WordNet wn1 = new WordNet("./data/wordnet/synsets.txt", "./data/wordnet/hyponyms.txt");
        String wordFile = "./data/ngrams/top_49887_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        List<String> query1 = new ArrayList<>();
        query1.add("food");
        query1.add("cake");
        NGramMap ngm = new NGramMap(wordFile, countFile);
        List<String> test = wn1.getK(ngm, query1, 1950, 1990, 5);
        assertThat(test).containsExactly("biscuit", "cake", "kiss", "snap", "wafer");

    }

    @Test
    public void elevenTest() {
        WordNet wn = new WordNet("./data/wordnet/synsets11.txt", "./data/wordnet/hyponyms11.txt");
        List<String> query = new ArrayList<>();
        query.add("antihistamine");
        assertThat(wn.getHyponyms(query)).containsExactly("actifed", "antihistamine");
    }

    // TODO: Add more unit tests (including edge case tests) here.
}
