public class Puzzle {

    // Attributes
    private final String puzzleID;
    private final String name;
    private final String location;
    private final String description;
    private final String answer;
    private final int maxAttempts;
    private int attempts = 0;
    private boolean solved = false;

    // 🔹 Constructor (includes puzzleID)
    public Puzzle(String puzzleID, String name, String location, String description, String answer, int maxAttempts) {
        this.puzzleID = puzzleID;
        this.name = name;
        this.location = location;
        this.description = description;
        this.answer = answer.toLowerCase().trim();
        this.maxAttempts = maxAttempts;
    }

    // 🔹 Getter methods
    public String getPuzzleID() { return puzzleID; }

    public String getName() { return name; }

    public String getLocation() { return location; }

    public String getDescription() { return description; }

    public boolean isSolved() { return solved; }

    // 🔹 Method to try solving the puzzle
    public String tryAnswer(String input) {
        if (solved) return "Already solved.";
        attempts++;
        if (input.toLowerCase().trim().equals(answer)) {
            solved = true;
            return "✅ Correct! Puzzle solved.";
        }
        if (attempts >= maxAttempts) return "❌ No attempts left. Puzzle locked.";
        return "Wrong answer. Try again (" + (maxAttempts - attempts) + " attempts left).";
    }

    // 🔹 Main method for testing
    // public static void main(String[] args) {
//     Puzzle test = new Puzzle("P-01", "Backdoor Keypad", "R01",
//         "You see a keypad by the backdoor...", "5711", 3);
//     System.out.println("Puzzle ID: " + test.getPuzzleID());
//     System.out.println("Description: " + test.getDescription());
//     System.out.println(test.tryAnswer("0800"));
//     System.out.println(test.tryAnswer("5711"));
//     System.out.println(test.tryAnswer("5711"));
// }

}


