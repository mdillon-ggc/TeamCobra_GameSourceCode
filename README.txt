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


-> Open file "Room.txt".

This file is structured into siz parts separated by "%".
The parts are: <roomID>%<roomFloorID>%<roomName>%<roomDescr>%<roomExits>&<isCheckpoint>

<roomID>
Room identification string given for each room.
<roomFloorID>
Room floor identification string given for each room.
<roomName>
The room name is the title of each room.
<roomDescr>
Description is a string of what the rooms are like.
<roomExits>
Exit numbers is a list of 4 string values separated by ",".
These represent the 4 connection exit points for each room.
The order for the list is <North,South,West,East>
-These are the commands the player inputs to navigate.
<isCheckpoint>
The room is set to be a checkpoint or not.


-> Open file "item.txt"

This file is structured into seven parts separated by "%".
The parts are: <itemID>%<itemName>%<itemDesc>%<itemType>%<itemValue>%<maxStack>%<itemRoomID>

<itemID>
The item identification string given to each item.
<itemName>
The item name is the title of each item.
<itemDesc>
Item description is the string of what the items are like.
<itemType>
The type of the item: either consumable or a weapon.
<itemValue>
The value of the item: either heal amount or damage amount.
<maxStack>
How many of each item can be stored in one slot of player inventory.
<itemRoomID>
The room number the item is located in.


-> Open file "puzzles.txt"

This file is structured into eight parts separated by "%".
The parts are: 
<puzzleID>%<name>%<location>%<description>%<answer>%<passMessage>%<failMessage>%<maxAttempts>

<puzzleID>
The puzzle identification string given to each puzzle.
<name>
The puzzle name is the title of each puzzle.
<location>
The room where the puzzle is located.
<description>
Puzzle question.
<answer>
The puzzle answer to the question.
<passMessage>
The puzzle message that prints out when the player passes the puzzle.
<failMessage>
The puzzle message that prints out when the player fails the puzzle.
<maxAttempts>
The max number of attempts per puzzle.


-> Open file "Character.txt"

This file is structured into eleven parts separated by "%".
The parts are: 
<iD>%<name>%<charType>%<preExisting>%<spawn>%
   <monsterDies>%<playerDies>%<damage>%<health>%<location>

<iD>
The character identification string given to each character.
<name>
The character name is the title of each character.
<charType>
The type of the character: either NPC or a monster.
<preExisting>
Message if monster is already in room.
<spawn>
Message if monster is spawned into the room.
<monsterDies>
Message when monster dies.
<playerDies>
Message when player dies.
<damage>
The character attack damage points.
<health>
The character health points.
<location>
The room where the character is located.


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

AI Usage:
Debugging errors involving typos and error messages like NullPointException.
Read map command and making a pop-up of the map appear.


Enjoy the heist!

