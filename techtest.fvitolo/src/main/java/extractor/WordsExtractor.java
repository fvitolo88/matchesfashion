package extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordsExtractor {

    /**
     * The pattern used for match text words
     */
    private String regex;

    /**
     * The number of words to return
     */
    private int numberOfWords;

    /**
     * Constructor
     * @param regex the pattern used for match text words
     * @param numberOfWords the number of words to return
     * @throws IllegalArgumentException if regex is null or numberOfWords is less then 0
     */
    public WordsExtractor(String regex, int numberOfWords) {
        if(regex==null)
            throw new IllegalArgumentException("Regex couldn't be null");
        this.regex = regex;
        if(numberOfWords<0)
            throw new IllegalArgumentException("Number of words couldn't be less then 0");
        this.numberOfWords = numberOfWords;
    }

    /**
     * Given an input string return top {@code numberOfWords} words matching {@code regex} pattern
     * @param text the input text to analyze
     * @return the list of top {@code numberOfWords} matching words
     */
    public String[] extractMostOccurring(String text) {
        List<String> words = listOfMatchingWords(text)
                .stream()
                .collect(Collectors.toMap(w -> w, w -> 1, (v1, v2) -> v1 + 1))
                .entrySet()
                .stream()
                .sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return words.subList(0, calculateResultSize(words, numberOfWords)).toArray(new String[0]);
    }

    /**
     * Given an input string return all words matching {@code regex} pattern
     * @param text the input text to analyze
     * @return the list of all matching words
     */
    private List<String> listOfMatchingWords(String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Optional.ofNullable(text).orElse(""));
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words;
    }

    /**
     * Check if numberOfWords is greater then the size of matching list words
     * @param words words matching with regex
     * @param numberOfWords number of top recurring word
     * @return the minimum value between words size and numberOfWords
     */
    private int calculateResultSize(List<String> words, int numberOfWords) {
        return words.size() >= numberOfWords ? numberOfWords : words.size();
    }
}
