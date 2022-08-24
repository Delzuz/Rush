package org.example;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

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
        final char wall = '\u2588';


        char playerCharacter = '\u263a';
        Position player = new Position(13,13);
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(playerCharacter);






        List<Obstacles> obstacles1 = new ArrayList<>();
        obstacles1.add(new Obstacles(5,60,4,terminal));
        obstacles1.add(new Obstacles(5,60,2,terminal));
        obstacles1.add(new Obstacles(5,60,8,terminal));

        List<Obstacles> obstacles2 = new ArrayList<>();
        obstacles2.add(new Obstacles(3,75,4,terminal));
        obstacles2.add(new Obstacles(3,75,2,terminal));
        obstacles2.add(new Obstacles(3,75,8,terminal));



        terminal.flush();


        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            int index = 0;
            int bombIndex = 0;

            do {
                index+=5;

                // Bomb
                bombIndex++;
                if (bombIndex % 100 == 0) {
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

                Position[] wallR = new Position[60];
                for(int i = 0;i<60;i++){
                    wallR[i] = new Position(60, 0);
                }
                // wall array to print
                for (Position p : wallR) {
                    for (int column = 0; column < 60; column++) {
                        terminal.setCursorPosition(column, 0);
                        terminal.putCharacter(wall);
                    }
                    for (int column = 60; column > 0; column--) {
                        terminal.setCursorPosition(column, 60);
                        terminal.putCharacter(wall);

                    }
                    for (int row = 0; row < 15; row++) {
                        terminal.setCursorPosition(0, row);
                        terminal.putCharacter(wall);
                    }
                    for (int row = 15; row > 0; row--) {
                        terminal.setCursorPosition(60, row);
                        terminal.putCharacter(wall);
                    }
                }

                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            }
            while (keyStroke == null);

                if (wall == player.x)
                {
                    continueReadingInput = false;
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



            //Exit button
            if (keyStroke.getKeyType() == Escape) {
                continueReadingInput = false;
                System.out.println("Quit");
                terminal.close();
            }


        }

        /*terminal.clearScreen();
        String stringToText = "LEVEL 2";
        for (int i = 0; i < stringToText.length(); i++) {
            terminal.setCursorPosition(i, 3);
            terminal.putCharacter(stringToText.charAt(i));

        }
        terminal.flush();*/

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
                    obstacles.get(0).setPosY(r.nextInt(15));
                    obstacles.get(1).setPosY(r.nextInt(15));
                    obstacles.get(2).setPosY(r.nextInt(15));

                    terminal.clearScreen();


                }
            }

            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter('\u2588');

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


                }
            }

            terminal.setCursorPosition(obstacle.posX, obstacle.posY);
            terminal.putCharacter('\u2588');

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
