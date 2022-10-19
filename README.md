# Monsters and Battles
This is the final project for the course Advanced Programming of Interactive Systems at Université Paris-Saclay. 

In this project, we implemented a digital card game "Monsters and Battles" that is inspired by the well-known card game called yugioh!

## Description on the project
In this version of the game, each player starts the match with 8000 life points, 7 cards in their hands and with a deck of 40 cards. 
And each player has a field with 5 monster zones in front of them.
They can position the card in the monster zone during the position phase.
Each player's turn contains four phases that take place in the following order:
* Draw Phase: The turn player draws one card from their Deck.
* Position Phase: The turn player may Normal Summon or set a monster or change
the battle position of a monster.
* Battle Phase: The turn player may choose to attack their opponent using any monsters on their field in Attack Position. 
Depending on the position of opposing monster, the attacking monster's ATK points are taken into account against the opposing monster's ATK or DEF points. 
If both monsters are in Attack Position, the monster with fewer ATK points is destroyed and its owner takes life point damage equal the difference between both monster's ATK points (if both monsters have equal ATK points, they are both destroyed and no damage is taken, unless both of their ATK points are 0, in which neither is destroyed). 
If the opposing monster is in Defense Position and has fewer DEF points than the attacking monster's ATK points, it is destroyed and the owner takes no damage.
However, if its DEF point is higher, the attacker takes life point damage equal to the difference between the two values. 
If the defending player has no monsters defending them, a Direct Attack can be performed, with the defending player receiving life point damage equal to the attacking monster's ATK points. 
The turn player can choose to not enter the battle phase and instead go to the End Phase.
* End phase: The turn ends.

When either player's life points become 0, the game ends.

## Implemented functionalities
* The graphical user interface of the game
* Interactivity: choose the card and put it in the field; the feature that enables user to change the position of a card already placed on the board;
user controlling the phase of the game; Notifications to help the user proceed in the game, especially when user gets stuck or makes inappropriate move
* The backend logical according to the rules of the game

## Run the project
Oracle OpenJDK 19 was used in this project.

IntelliJ IDEA was used as the IDE.

For the code to run properly, you need to choose the suitable version of SDK (preferably Oracle OpenJDK 19).

To run this project, go to the src/main/java/advUI.yugioh folder, then run Main.main()
