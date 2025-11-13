public class Puzzle {

    // Attributes
    private final String puzzleID;
    private final String name;
    private final String location;
    private final String description;
    private final String answer;
    private final int maxAttempts;

    private String passMessage;   // NEW
    private String failMessage;   // NEW

    private int attempts;         // now counts DOWN
    private boolean solved = false;
    private boolean failed = false;

    // Constructor (includes PASS/FAIL messages)
    public Puzzle(String puzzleID,
                  String name,
                  String location,
                  String description,
                  String answer,
                  int maxAttempts,
                  String passMessage,
                  String failMessage) {

        this.puzzleID = puzzleID;
        this.name = name;
        this.location = location;
        this.description = description;
        this.answer = answer.toLowerCase().trim();
        this.maxAttempts = maxAttempts;

        this.passMessage = passMessage;
        this.failMessage = failMessage;

        this.attempts = maxAttempts;  // start full: 5 → 4 → 3 → 2 → 1 → 0
        this.solved = false;
        this.failed = false;
    }

    // Getter methods
    public String getPuzzleID() { return puzzleID; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public boolean isSolved() { return solved; }
    public boolean isFailed() { return failed; }

    // Method to try solving the puzzle
    public String tryAnswer(String input) {

        if (solved) {
            return "Already solved.";
        }

        // normalize
        String playerAnswer = input.toLowerCase().trim();

        // Correct answer
        if (playerAnswer.equals(answer)) {
            solved = true;
            return passMessage;  // Comes from puzzles.txt
        }

        // Wrong answer → subtract attempt
        attempts--;

        // No attempts left
        if (attempts <= 0) {
            failed = true;
            return failMessage + " (No attempts left. Puzzle locked.)";
        }

        // Still available attempts
        return failMessage + " (" + attempts + " attempts left)";
    }

    // Reset puzzle (optional)
    public void resetPuzzle() {
        this.attempts = this.maxAttempts;
        this.failed = false;
        this.solved = false;
    }
}



