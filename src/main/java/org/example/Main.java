package org.example;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.googlecode.lanterna.input.KeyType.Escape;

public class Main {

    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(60,15);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();


        terminal.setCursorVisible(false);

        char playerCharacter = '\u2603';
        Position player = new Position(13,13);
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(playerCharacter);

        List<Obstacles> obstacles1 = new ArrayList<>();
        obstacles1.add(new Obstacles(5,60,4,terminal));
        obstacles1.add(new Obstacles(5,60,2,terminal));
        obstacles1.add(new Obstacles(5,60,8,terminal));

        List<Obstacles> obstacles2 = new ArrayList<>();
        obstacles2.add(new Obstacles(3,75,6,terminal));
        obstacles2.add(new Obstacles(3,75,12,terminal));
        obstacles2.add(new Obstacles(3,75,10,terminal));

        terminal.flush();

        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            int index = 0;
            int bombIndex = 0;

            do {
                index+=5;

                // Bomb
                bombIndex+=2;
                if (bombIndex % 180 == 0) {
                    Bombs bomb1 = new Bombs(10,10,terminal);
                    bomb1.createBombs(player);
                }
                //First set of moving obstacles
                if (index % 100==0) {
                    continueReadingInput = handleObstacles1(obstacles1, player, terminal);
                    if (!continueReadingInput) {
                        terminal.close();
                        break;
                    }
                }
                //Second set of moving obstacles
                if (index % 100==0) {
                    continueReadingInput = handleObstacles2(obstacles2, player, terminal);
                    if (!continueReadingInput) {
                        terminal.close();
                        break;
                    }
                }
                // put wall method here
                continueReadingInput = printWalls(player,terminal);

                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            }
            while (keyStroke == null);


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



            //Exit button
            if (keyStroke.getKeyType() == Escape) {
                continueReadingInput = false;
                System.out.println("Quit");
                terminal.close();

            //borders
            }
            if(player.x == 30 && player.x ==8)
            {
                continueReadingInput = false;
                System.out.println("Quit1");
                terminal.close();
            }
            if(player.x == 0)
            {
                continueReadingInput = false;
                System.out.println("Quit2");
                terminal.close();
            }
            if(player.y == 0)
            {
                continueReadingInput = false;
                System.out.println("Quit3");
                terminal.close();
            }
            if(player.y == 14)
            {
                continueReadingInput = false;
                System.out.println("Quit4");
                terminal.close();
            }
            if(player.x == 59 && player.y ==7)
            {

                continueReadingInput = false;
                System.out.println("Win!");

            }
            if(player.x == 60)
            {
                continueReadingInput = false;
                System.out.println("Quit6");
                terminal.close();
            }

        }
        terminal.clearScreen();
        String stringToText = "LEVEL 2";
        for (int i = 0; i < stringToText.length(); i++) {
            terminal.setCursorPosition(i, 3);
            terminal.putCharacter(stringToText.charAt(i));

        }
        terminal.flush();

    }
    private static boolean printWalls(Position player,Terminal terminal)throws Exception
    {
        final char wallL = '\u2344';
        final char wallRight = '\u2343';
        final char wallT = '\u234c';
        final char wallB = '\u2353';
        Position[] wallR = new Position[60];
        for(int i = 0;i<60;i++){
            wallR[i] = new Position(60, 0);
        }
        // wall array to print
        for (Position p : wallR) {
            for (int column = 0; column < 60; column++) {
                terminal.setCursorPosition(column, 0);
                terminal.putCharacter(wallT);
            }
            for (int column = 60; column > 0; column--) {
                terminal.setCursorPosition(column, 60);
                terminal.putCharacter(wallB);

            }
            for (int row = 0; row < 15; row++) {
                terminal.setCursorPosition(0, row);
                terminal.putCharacter(wallL);
            }
            for (int row = 6; row > 0; row--) {
                terminal.setCursorPosition(60, row);
                terminal.putCharacter(wallRight);
            }
            for (int row = 8; row < 15; row++) {
                terminal.setCursorPosition(60, row);
                terminal.putCharacter(wallRight);
            }
                }
        for (Position walls: wallR) {
            if (walls.x == player.x && walls.y == player.y) {
                terminal.bell();
                System.out.println("GAME OVER!");
                return false;
            }
        }

        terminal.flush();

        return true;
    }
    private static boolean handleObstacles1 (List<Obstacles> obstacles, Position player, Terminal terminal) throws Exception {

        for (Obstacles obstacle: obstacles) {
            Random r = new Random();
            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter(' ');


            if (obstacle.posX > 0) {
                obstacle.posX--;
                if (obstacle.posX == 0) {
                    obstacle.posX = 60;
                    obstacles.get(0).setPosY(r.nextInt(14));
                    obstacles.get(1).setPosY(r.nextInt(14));
                    obstacles.get(2).setPosY(r.nextInt(14));
                    terminal.clearScreen();




                }
            }


            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter('\u26dd');

        }



        terminal.flush();
        for (Obstacles obs: obstacles) {
            if (obs.posX == player.x && obs.posY == player.y) {
                terminal.bell();
                System.out.println("GAME OVER!");
                return false;
            }
        }
        return true;
    }
    private static boolean handleObstacles2 (List<Obstacles> obstacles, Position player, Terminal terminal) throws Exception {
        int lvlClear = 0;
        for (Obstacles obstacle: obstacles) {
            Random r = new Random();
            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter(' ');

            if (obstacle.posX > 0) {
                obstacle.posX--;
                if (obstacle.posX == 0) {
                    obstacle.posX = 60;
                    obstacles.get(0).setPosY(r.nextInt(15));
                    obstacles.get(1).setPosY(r.nextInt(15));
                    obstacles.get(2).setPosY(r.nextInt(15));
                    terminal.clearScreen();
                    lvlClear++;



                }
                if (lvlClear == 3) {
                    return false;
                }
            }

            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter('\u26dd');

        }

        terminal.flush();
        for (Obstacles obs: obstacles) {
            if (obs.posX == player.x && obs.posY == player.y) {
                terminal.bell();
                //terminal.setForegroundColor(TextColor.ANSI.RED);
                System.out.println("GAME OVER!");
                return false;
            }
        }
        return true;
    }

    public static void handleMonsters (Position player, Terminal terminal) throws IOException {
        List<Position> monsters = new ArrayList<>();
        monsters.add(new Position(3, 3));
        monsters.add(new Position(23, 23));
        monsters.add(new Position(23, 3));
        monsters.add(new Position(3, 23));

        for (Position monster : monsters) {
            terminal.setCursorPosition(monster.x, monster.y);
            terminal.putCharacter(' ');

            if (player.x > monster.x) {
                monster.x++;
            } else if (player.x < monster.x) {
                monster.x--;
            }
            if (player.y > monster.y) {
                monster.y++;
            } else if (player.y < monster.y) {
                monster.y--;
            }

            terminal.setCursorPosition(monster.x, monster.y);
            terminal.putCharacter('X');
        }

    }

}
