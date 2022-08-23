package org.example;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(60,15);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();

        terminal.setCursorVisible(false);


        final char block = '\u2588';
        char playerCharacter = '\u263a';
        Position player = new Position(13,13);
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(playerCharacter);

       // printNewObstacles
        // Create obstacles array
        Position[] obstacles = new Position[10];
        for(int i = 0;i<10;i++){
            obstacles[i] = new Position(10+i, 10);
        }

        // Use obstacles array to print to lanterna
        for (Position p : obstacles) {
            terminal.setCursorPosition(p.x, p.y);
            terminal.putCharacter(block);
        }

        terminal.flush();


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
                    player.y += 1;
                    break;
                case ArrowUp:
                    player.y -= 1;
                    break;
                case ArrowRight:
                    player.x += 1;
                    break;
                case ArrowLeft:
                    player.x -= 1;
                    break;
            }

            // Draw player
            terminal.setCursorPosition(oldPosition.x, oldPosition.y);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(player.x, player.y);
            terminal.putCharacter(playerCharacter);



            // Is the player alive? obstacles for lvl 1
            for (Position ob : obstacles) {
                if (ob.x == player.x && ob.y == player.y) {
                    continueReadingInput = false;
                    terminal.bell();
                    System.out.println("GAME OVER!");
                }
            }

            terminal.flush();
        }
    }

}
