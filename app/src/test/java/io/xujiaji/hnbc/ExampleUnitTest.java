package io.xujiaji.hnbc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJiSuan() {
        String testStr = "  1. dsafdasf";
        System.out.println(testStr.matches("\\s*[1-9]+[0-9]*\\.\\s.+"));
        System.out.println(testStr.substring(0, testStr.indexOf('.')).trim());
    }
}