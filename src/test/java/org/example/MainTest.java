package org.example;


import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private final String CSV_FILE = "test-results.csv";

    @Test
    public void testMainOutput_ValidInput() throws Exception {
        String testName = "testMainOutput_ValidInput";
        String input = String.join("\n",
                "3",            // number of students
                "Alice", "95",  // A
                "Bob", "67",    // D
                "Charlie", "82" // B
        ) + "\n";

        String expected = "Top Student(s): Alice (95)";
        String output = runMainWithInput(input);
        boolean passed = output.contains("Alice got grade: A")
                && output.contains("Bob got grade: D")
                && output.contains("Charlie got grade: B")
                && output.contains("Average Score: 81.33")
                && output.contains(expected);

        logToCSV(testName, input, expected, output, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }

    @Test
    public void testInvalidScoreHandling() throws Exception {
        String testName = "testInvalidScoreHandling";
        String input = String.join("\n",
                "1",
                "TestUser",
                "85"     // valid
        ) + "\n";

        String expected = "TestUser got grade: B";
        String output = runMainWithInput(input);

        boolean passed = output.contains("Enter score for TestUser:")
                && output.contains(expected);

        logToCSV(testName, input, expected, output, passed ? "PASS" : "FAIL");
        assertTrue(passed);
    }

    // 🔧 Helper: run main with input and capture output
    private String runMainWithInput(String simulatedInput) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();

        System.setIn(testIn);
        System.setOut(new PrintStream(testOut));

        try {
            Main.main(new String[]{});
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        return testOut.toString();
    }

    // 🔧 Helper: log test results to CSV
    private void logToCSV(String testName, String input, String expected, String actual,
                          String status) throws IOException {
        File file = new File(CSV_FILE);
        boolean isNew = file.createNewFile();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (isNew) {
                writer.println("Test Name,Input,Expected Output,Actual Output,Status,Timestamp");
            }
            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",%s,%s%n",
                    testName,
                    input.replace("\n", "\\n"),
                    expected,
                    actual.replace("\n", "\\n"),
                    status,
                    time);
        }
    }
}