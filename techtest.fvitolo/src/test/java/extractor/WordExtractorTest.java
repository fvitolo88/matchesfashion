package extractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class WordExtractorTest {

    private static final String VALID_CHARACTER_REGEX = "['aA-zZ]+";

    private WordsExtractor wordsExtractor;

    @Before
    public void init() {
        wordsExtractor = new WordsExtractor(VALID_CHARACTER_REGEX, 3);
    }

    @Test
    public void shouldReturnThreeMostRecurrentWords() {
        String text =
                "In a village of La Mancha, the name of which I have " +
                        "no desire to call to " +
                        "mind, there lived not long since one of those gentlemen that keep a lance " +
                        "in the lance-rack, an old buckler, a lean hack, and a greyhound for " +
                        "coursing. An olla of rather more beef than mutton, a salad on most " +
                        "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra " +
                        "on Sundays, made away with three-quarters of his income.";

        String[] expectedResult = {"a", "of", "on"};

        assertArrayEquals(wordsExtractor.extractMostRecurring(text), expectedResult);
    }

    @Test
    public void shouldReturnTwoMostRecurrentWords() {
        String text = "//wont won't won't";

        String[] expectedResult = {"won't", "wont"};

        assertArrayEquals(wordsExtractor.extractMostRecurring(text), expectedResult);
    }

    @Test
    public void shouldReturnOneMostRecurrentWords() {
        String text = "/x/x x";

        String[] expectedResult = {"x"};

        assertArrayEquals(wordsExtractor.extractMostRecurring(text), expectedResult);
    }

    @Test
    public void shouldNotReturnAnyWords() {
        String text = "//.- @";

        String[] expectedResult = {};

        assertArrayEquals(wordsExtractor.extractMostRecurring(text), expectedResult);
    }

    @Test
    public void shouldNotCrashWhenInputTextIsNull() {
        String text = null;

        String[] expectedResult = {};

        assertArrayEquals(wordsExtractor.extractMostRecurring(text), expectedResult);
    }

}
