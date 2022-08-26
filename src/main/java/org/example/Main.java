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
        Position player = new Position(2,7);
        terminal.setCursorPosition(player.x, player.y);

        List<Obstacles> obstacles1 = new ArrayList<>();
        obstacles1.add(new Obstacles(5,60,4,terminal));
        obstacles1.add(new Obstacles(5,60,6,terminal));
        obstacles1.add(new Obstacles(5,60,8,terminal));

        List<Obstacles> obstacles2 = new ArrayList<>();
        obstacles2.add(new Obstacles(3,75,6,terminal));
        obstacles2.add(new Obstacles(3,75,12,terminal));
        obstacles2.add(new Obstacles(3,75,10,terminal));

        List<Position> monsters = new ArrayList<>();
        monsters.add(new Position(50, 13));
        monsters.add(new Position(44, 14));


        ArrayList<Bombs> bombList = new ArrayList<>();
        bombList.add(new Bombs(15,5,terminal));
        bombList.add(new Bombs(19,9,terminal));
        bombList.add(new Bombs(35,7,terminal));
        bombList.add(new Bombs(48,10,terminal));
        bombList.add(new Bombs(55,13,terminal));

        terminal.flush();

        boolean continueReadingInput = true;
        boolean continueReadingInput2 = true;
        boolean continueReadingInput3 = true;

        terminal.flush();
        Thread.sleep(500);

        String welcome = "WELCOME TO RUSH";
        String die = "GET READY TO DIE :)";
        for (int i = 0; i < welcome.length(); i++) {
            terminal.setCursorPosition(i+20, 4);
            terminal.putCharacter(welcome.charAt(i));
        }
        for (int i = 0; i < die.length(); i++) {
            terminal.setCursorPosition(i+18, 6);
            terminal.putCharacter(die.charAt(i));
        }
        terminal.flush();
        Thread.sleep(2000);


        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            int index = 0;
            int bombIndex = 0;

            Random r = new Random();

            do {
                terminal.setForegroundColor(TextColor.ANSI.YELLOW);
                index+=5;
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

            }
            //borders
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

        String passedLvl1 = "YOU PASSED LEVEL 1!";
        for (int i = 0; i < passedLvl1.length(); i++) {
            terminal.setCursorPosition(i+20, 4);
            terminal.putCharacter(passedLvl1.charAt(i));
        }
        String getReady = "GET READY FOR LEVEL 2!";
        for (int i = 0; i < getReady.length(); i++) {
            terminal.setCursorPosition(i+18, 5);
            terminal.putCharacter(getReady.charAt(i));

        }
        terminal.flush();
        Thread.sleep(2000);
        terminal.clearScreen();
        String stringToText = "LEVEL 2";
        for (int i = 0; i < stringToText.length(); i++) {
            terminal.setCursorPosition(i+25, 4);
            terminal.putCharacter(stringToText.charAt(i));

        }
        terminal.flush();
        Thread.sleep(2000);
        terminal.flush();
        player = new Position(2,7);
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(playerCharacter);

        //start på lvl 2
        while (continueReadingInput2) {
            KeyStroke keyStroke = null;
            int index = 0;
            int bombIndex = 0;
            int bombcount = 0;
            do {
                terminal.setForegroundColor(TextColor.ANSI.GREEN);
                index+=5;

                // Bomb
                bombIndex+=5;

                if (bombIndex % 180 == 0) {
                    bombcount++;
                    if (bombcount >= 1) {
                        terminal.setCursorPosition(bombList.get(0).x, bombList.get(0).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 2) {
                        terminal.setCursorPosition(bombList.get(1).x, bombList.get(1).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 3) {
                        terminal.setCursorPosition(bombList.get(2).x, bombList.get(2).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 4) {
                        terminal.setCursorPosition(bombList.get(3).x, bombList.get(3).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 5) {
                        terminal.setCursorPosition(bombList.get(4).x, bombList.get(4).y);
                        terminal.putCharacter('\u2622');
                    }

                }
                for (Bombs bomb1: bombList) {
                    bomb1.createBombs(player,bomb1);
                }
                //First set of moving obstacles
                if (index % 100==0) {
                    continueReadingInput2 = handleObstacles1(obstacles1, player, terminal);
                    if (!continueReadingInput2) {
                        terminal.close();
                        break;
                    }
                }
                //Second set of moving obstacles
                if (index % 100==0) {
                    continueReadingInput2 = handleObstacles2(obstacles2, player, terminal);
                    if (!continueReadingInput2) {
                        terminal.close();
                        break;
                    }
                }
                // put wall method here
                continueReadingInput2 = printWalls(player,terminal);

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
                continueReadingInput2 = false;
                System.out.println("Quit");
                terminal.close();

                //borders
            }

            if(player.x == 0)
            {
                continueReadingInput2 = false;
                System.out.println("Quit2");
                terminal.close();
            }
            if(player.y == 0)
            {
                continueReadingInput2 = false;
                System.out.println("Quit3");
                terminal.close();
            }
            if(player.y == 14)
            {
                continueReadingInput2 = false;
                System.out.println("Quit4");
                terminal.close();
            }
            if(player.x == 59 && player.y ==7)
            {

                continueReadingInput2 = false;
                System.out.println("Win!");

            }
            if(player.x == 60)
            {
                continueReadingInput2 = false;
                System.out.println("Quit6");
                terminal.close();
            }

        }
        terminal.clearScreen();

        String passedLvl2 = "YOU PASSED LEVEL 2!";
        for (int i = 0; i < passedLvl2.length(); i++) {
            terminal.setCursorPosition(i+20, 4);
            terminal.putCharacter(passedLvl2.charAt(i));
        }
        String getReady3 = "GET READY FOR LEVEL 3!";
        for (int i = 0; i < getReady3.length(); i++) {
            terminal.setCursorPosition(i+18, 5);
            terminal.putCharacter(getReady3.charAt(i));

        }
        terminal.flush();
        Thread.sleep(2000);

        terminal.clearScreen();
        String stringToText3 = "LEVEL 3";
        for (int i = 0; i < stringToText3.length(); i++) {
            terminal.setCursorPosition(i+25, 3);
            terminal.putCharacter(stringToText3.charAt(i));

        }
        terminal.flush();
        player = new Position(2,7);
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(playerCharacter);

        // start på lvl 3
        while (continueReadingInput3) {
            KeyStroke keyStroke = null;
            int index = 0;
            int bombIndex = 0;
            int bombcount = 0;
            int monsterIndex = 0;
            Random r = new Random();

            do {
                terminal.setForegroundColor(TextColor.ANSI.RED);
                index+=5;

                // Bomb
                bombIndex+=5;

                if (bombIndex % 180 == 0) {
                    bombcount++;
                    if (bombcount >= 1) {
                        terminal.setCursorPosition(bombList.get(0).x, bombList.get(0).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 2) {
                        terminal.setCursorPosition(bombList.get(1).x, bombList.get(1).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 3) {
                        terminal.setCursorPosition(bombList.get(2).x, bombList.get(2).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 4) {
                        terminal.setCursorPosition(bombList.get(3).x, bombList.get(3).y);
                        terminal.putCharacter('\u2622');
                    }
                    if (bombcount >= 5) {
                        terminal.setCursorPosition(bombList.get(4).x, bombList.get(4).y);
                        terminal.putCharacter('\u2622');
                    }

                }
                for (Bombs bomb1: bombList) {
                    bomb1.createBombs(player,bomb1);
                }

                // Monster
                monsterIndex+=4;

                if (monsterIndex % 100 == 0) {
                    continueReadingInput = handleMonsters(monsters,player, terminal);
                    if (!continueReadingInput) {
                        terminal.close();
                        break;
                    }
                }
                //First set of moving obstacles
                if (index % 100==0) {
                    continueReadingInput3 = handleObstacles1(obstacles1, player, terminal);
                    if (!continueReadingInput3) {
                        terminal.close();
                        break;
                    }
                }
                //Second set of moving obstacles
                if (index % 100==0) {
                    continueReadingInput3 = handleObstacles2(obstacles2, player, terminal);
                    if (!continueReadingInput3) {
                        terminal.close();
                        break;
                    }
                }
                // put wall method here
                continueReadingInput3 = printWalls(player,terminal);

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
                continueReadingInput3 = false;
                System.out.println("Quit");
                terminal.close();

                //borders
            }

            if(player.x == 0)
            {
                continueReadingInput3 = false;
                System.out.println("Quit2");
                terminal.close();
            }
            if(player.y == 0)
            {
                continueReadingInput3 = false;
                System.out.println("Quit3");
                terminal.close();
            }
            if(player.y == 14)
            {
                continueReadingInput3 = false;
                System.out.println("Quit4");
                terminal.close();
            }
            if(player.x == 59 && player.y ==7)
            {
                terminal.clearScreen();
                String youWon = "YOU WON! :D";
                for (int i = 0; i < youWon.length(); i++) {
                    terminal.setCursorPosition(i+25, 3);
                    terminal.putCharacter(youWon.charAt(i));
                }
                terminal.flush();
                Thread.sleep(4000);
                terminal.close();
                continueReadingInput3 = false;
                System.out.println("Win!");
                terminal.clearScreen();


            }
            if(player.x == 60)
            {
                continueReadingInput3 = false;
                System.out.println("Quit6");
                terminal.close();
            }

        }


    }

    private static boolean printWalls(Position player, Terminal terminal) throws Exception {
        final char wallL = '\u2344';
        final char wallRight = '\u2343';
        final char wallT = '\u234c';
        final char wallB = '\u2353';
        Position[] wallR = new Position[60];
        for (int i = 0; i < 60; i++) {
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

    private static boolean handleObstacles1(List<Obstacles> obstacles, Position player, Terminal terminal) throws Exception {

        for (Obstacles obstacle : obstacles) {
            Random r = new Random();
            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter(' ');


            if (obstacle.posX > -1) {
                obstacle.posX--;

                if (obstacle.posX == -1) {

                    obstacle.posX = 60;
                    obstacles.get(0).setPosY(r.nextInt(14));
                    obstacles.get(1).setPosY(r.nextInt(14));
                    obstacles.get(2).setPosY(r.nextInt(14));
                    //terminal.clearScreen();

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
                String stringToText = "YOU HIT THE OBSTACLE! :(";
                for (int i = 0; i < stringToText.length(); i++) {
                    terminal.setCursorPosition(i+20, 3);
                    terminal.putCharacter(stringToText.charAt(i));

                }
                terminal.flush();
                Thread.sleep(4000);
                terminal.close();
                return false;
            }
        }
        return true;
    }
    private static boolean handleObstacles2 (List<Obstacles> obstacles, Position player, Terminal terminal) throws Exception {
        for (Obstacles obstacle: obstacles) {
            Random r = new Random();
            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter(' ');

            if (obstacle.posX > -1) {
                obstacle.posX--;
                if (obstacle.posX == -1) {
                    obstacle.posX = 60;
                    obstacles.get(0).setPosY(r.nextInt(15));
                    obstacles.get(1).setPosY(r.nextInt(15));
                    obstacles.get(2).setPosY(r.nextInt(15));
                    //terminal.clearScreen();



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
                String stringToText = "YOU HIT THE OBSTACLE! :(";
                for (int i = 0; i < stringToText.length(); i++) {
                    terminal.setCursorPosition(i+20, 3);
                    terminal.putCharacter(stringToText.charAt(i));

                }
                terminal.flush();
                Thread.sleep(4000);
                terminal.close();
                return false;
            }
        }
        return true;
    }

    public static boolean handleMonsters(List<Position> monsters, Position player, Terminal terminal) throws Exception {

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
            terminal.setForegroundColor(TextColor.ANSI.RED);
            terminal.putCharacter('\u046A');

        }
        for (Position monster : monsters) {
            if (monster.x == player.x && monster.y == player.y) {
                terminal.bell();
                System.out.println("GAME OVER!");
                String stringToText = "YOU GOT EATEN! :(";
                for (int i = 0; i < stringToText.length(); i++) {
                    terminal.setCursorPosition(i+20, 3);
                    terminal.putCharacter(stringToText.charAt(i));

                }
                terminal.flush();
                Thread.sleep(4000);
                terminal.close();
                return false;

            }

        } return true;
    }
}
