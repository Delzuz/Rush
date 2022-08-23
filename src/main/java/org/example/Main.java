package org.example;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        char playerCharacter = '\u263a';
        Position player = new Position(13,13);
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(playerCharacter);

        List<Position> monsters = new ArrayList<>();
        monsters.add(new Position(3, 3));
        monsters.add(new Position(23, 23));
        monsters.add(new Position(23, 3));
        monsters.add(new Position(3, 23));

        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;

            do {
                keyStroke = terminal.pollInput();
            }
            while (keyStroke == null);

            Character c = keyStroke.getCharacter(); // used Character instead of char because it might be null
            if (c == Character.valueOf('q')) { continueReadingInput = false;
                System.out.println("quit");
            }

            Position oldPosition = new Position(player.x, player.y);

            switch (keyStroke.getKeyType()) {
                case ArrowDown:
                    player.y += 2;
                    break;
                case ArrowUp:
                    player.y -= 2;
                    break;
                case ArrowRight:
                    player.x += 2;
                    break;
                case ArrowLeft:
                    player.x -= 2;
                    break;
            }

            // Draw player
            terminal.setCursorPosition(oldPosition.x, oldPosition.y);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(player.x, player.y);
            terminal.putCharacter(playerCharacter);

            // Handle monsters
            for (Position monster : monsters) {
                terminal.setCursorPosition(monster.x, monster.y);
                terminal.putCharacter(' ');

                if (player.x > monster.x) {
                    monster.x++;
                }
                else if (player.x < monster.x) {
                    monster.x--;
                }
                if (player.y > monster.y) {
                    monster.y++;
                }
                else if (player.y < monster.y) {
                    monster.y--;
                }

                terminal.setCursorPosition(monster.x, monster.y);
                terminal.putCharacter('X');
            }

            // Is the player alive?
            for (Position monster : monsters) {
                if (monster.x == player.x && monster.y == player.y) {
                    continueReadingInput = false;
                    terminal.bell();
                    System.out.println("GAME OVER!");
                }
            }

            terminal.flush();
        }
    }

}
