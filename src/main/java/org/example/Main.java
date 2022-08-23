package org.example;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
/*
        RUSH
        Lvl 1
        * Hinder kommer och går mot en

        Står lvl 2
        * Hinder
        * + 1 Monster jagar

        * Lvl 3
        * Hinder ( längre + snabbare)
        * 1 monster jagar

        Lvl 4
        * Hinder ( längre + bredare+ snabbare)
        * 2 monster jaguar
        * 5 bomber kommer långsamt

        Lvl 5
        * samma Hinder som lvl 3
        * 3 monster jagar
        * 10 bomber lite snabbare

        Extra finesser
        Dör av att röra taket och botten
        Hinder en färg (olika färg för lvl?)
        Bomber en färg
        Monster en färg eller symbol
        Spelare blir röd av död
        Game Over när man dör
        Spel meny med 2 val - starta eller exit*/
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

        /* Create obstacles array
        Position[] obstacles = new Position[10];
        for(int i = 0;i<10;i++){
            obstacles[i] = new Position(10+i, 10);
        }

        // Use obstacles array to print to lanterna
        for (Position p : obstacles) {
            terminal.setCursorPosition(p.x, p.y);
            terminal.putCharacter(block);
        }



         */


        Obstacles o = new Obstacles(2,30,4,terminal);
        o.createObstacles();
        Obstacles o2 = new Obstacles(2,40,2,terminal);
        o2.createObstacles();
        Obstacles o3 = new Obstacles(3,50,8,terminal);
        o3.createObstacles();



        terminal.flush();


        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;

            do {
                Thread.sleep(5);
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

            o.collisionObstacles(player.x,player.y);
            o2.collisionObstacles(player.x,player.y);
            o3.collisionObstacles(player.x,player.y);


            /* Is the player alive? obstacles for lvl 1
            for (Position ob : obstacles) {
                if (ob.x == player.x && ob.y == player.y) {
                    continueReadingInput = false;
                    terminal.bell();
                    System.out.println("GAME OVER!");
                }
            }

             */

            terminal.flush();
        }
    }

}
