public class Puzzle {

    // Attributes
    private final String puzzleID;
    private final String name;
    private final String location;
    private final String description;
    private final String answer;
    private final int maxAttempts;

    private String passMessage;  
    private String failMessage;  

    private int attempts;        
    private boolean solved = false;
    private boolean failed = false;

    // 🔒 nuevo: referencia al jugador que está haciendo este puzzle
    private Player puzzlePlayer;

    // Constructor
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

        this.attempts = maxAttempts;
        this.solved = false;
        this.failed = false;

        this.puzzlePlayer = null;
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

        String playerAnswer = input.toLowerCase().trim();

        if (playerAnswer.equals(answer)) {
            solved = true;

            // ✅ puzzle resuelto: liberar al jugador del modo puzzle
            if (puzzlePlayer != null) {
                puzzlePlayer.setInPuzzleMode(false);
            }

            return passMessage;
        }

        attempts--;

        if (attempts <= 0) {
            failed = true;

            // ❌ sin intentos: también liberar al jugador (puzzle terminado)
            if (puzzlePlayer != null) {
                puzzlePlayer.setInPuzzleMode(false);
            }

            return failMessage + " (No attempts left. Puzzle locked.)";
        }

        return failMessage + " (" + attempts + " attempts left)";
    }

    // Reset puzzle
    public void resetPuzzle() {
        this.attempts = this.maxAttempts;
        this.failed = false;
        this.solved = false;
    }

    // ⭐ Aquí es donde Hina quería el lock del jugador
    // Start puzzle when player enters the room
    public void startPuzzle(Player player) {
        // guardar referencia del jugador
        this.puzzlePlayer = player;

        // 🔒 BLOQUEAR MOVIMIENTO
        player.setInPuzzleMode(true);

        System.out.println("You encounter a puzzle: " + name);
        System.out.println(description);
    }

}  // ← LAST BRACKET
