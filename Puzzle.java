public class Puzzle {
    private final String name;
    private final String location;
    private final String description;
    private final String answer;
    private final int maxAttempts;
    private int attempts = 0;
    private boolean solved = false;

    public Puzzle(String name, String location, String description, String answer, int maxAttempts) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.answer = answer.toLowerCase().trim();
        this.maxAttempts = maxAttempts;
    }

    public String getDescription() { return description; }

    public String tryAnswer(String input) {
        if (solved) return "Already solved.";
        attempts++;
        if (input.toLowerCase().trim().equals(answer)) {
            solved = true;
            return "✅ Correct! Puzzle solved.";
        }
        if (attempts >= maxAttempts) return "❌ No attempts left. Puzzle locked.";
        return "Wrong answer. Try again (" + (maxAttempts - attempts) + " left).";
    }

    // 🔹 AQUÍ ESTÁ EL MAIN 🔹
    public static void main(String[] args) {
        Puzzle test = new Puzzle("Backdoor Keypad", "R01",
                "You see a keypad by the backdoor...", "5711", 3);

        System.out.println(test.getDescription());
        System.out.println(test.tryAnswer("0000"));
        System.out.println(test.tryAnswer("5711"));
        System.out.println(test.tryAnswer("5711"));
    }
}

