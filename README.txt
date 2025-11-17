README - Master Jewel Thief (Text-Based Game)
=============================================

This project is a Java-based text adventure game that includes:
- Room navigation across multiple floors
- Inventory management
- Monsters and combat
- Puzzles with lockout mechanics
- Saving/loading game progress
- Checkpoints and item interactions

Project Files
-------------
The following Java classes are included in the project:

- Game.java         : Main game loop and command handling
- Player.java       : Player data, inventory, stats, puzzle state
- Room.java         : Room structure, exits, items, puzzles
- Puzzle.java       : Puzzle logic, attempts, success/failure
- Item.java         : Base item class
- Useable.java      : Usable (consumable/key) item logic
- Weapon.java       : Weapon items
- Character.java    : NPC/Monster class
- Monster.java      : Monster subclass logic
- FileLoader.java   : Loads Room, Item, Puzzle, Character data from text files

Data Files
----------
These text files must remain in the project directory:

- Room.txt       : Room definitions, exits, locked states
- item.txt       : Item definitions and locations
- puzzles.txt    : Puzzle definitions
- Character.txt  : NPC & Monster definitions

How to Run
----------
1. Compile all .java files.
2. Ensure external text files (Room.txt, item.txt, puzzles.txt, Character.txt)
   are in the same directory as your compiled classes.
3. Run:

    java Game

Gameplay Commands
-----------------
- go <direction>       : Move between rooms
- explore              : View room details
- inventory            : View items you're carrying
- inspect <item>       : Inspect an item in inventory
- use <item>           : Use a consumable or key item
- pickup <item>        : Pick up an item in the room
- drop <item>          : Drop an item into the room
- attack <monster>     : Attack a hostile NPC
- flee <direction>     : Flee to another room (if allowed)
- save <filename>      : Save your current game
- load <filename>      : Load a saved game
- load checkpoint      : Load the automated checkpoint
- read map             : Opens the map image for current floor
- help                 : Shows all commands
- quit                 : Exit the game

Puzzle System
-------------
When a puzzle begins:
- Player is locked in place (cannot move)
- Player must enter puzzle input (code, word, item name, etc.)
- When solved or failed, puzzleMode unlocks and player may continue

Checkpoint System
-----------------
Reaching certain rooms automatically:
- Refills HP
- Saves game to "checkpoint.txt"

Notes
-----
Make sure puzzle mode resets when loading a save or checkpoint:

    player.setInPuzzleMode(false);

You may also reset all puzzles by calling resetPuzzle() on each puzzle.

Enjoy the heist!

