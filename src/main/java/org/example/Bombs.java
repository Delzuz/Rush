package org.example;

import com.googlecode.lanterna.terminal.Terminal;
import java.util.Random;
import java.io.IOException;

public class Bombs {
    public static int x;
    public static int y;
    public Terminal terminal;

    public Bombs(int x, int y, Terminal terminal){
        this.x = x;
        this.y = y;
        this.terminal = terminal;
    }

    public void createBombs(Position player) throws IOException, InterruptedException {

            Random r1 = new Random();
            x = r1.nextInt(15);
            y = r1.nextInt(15);

            final char bomb = 'O';
            terminal.setCursorPosition(x, y);
            terminal.putCharacter(bomb);

            if (player.x == x && player.y == y) {
                terminal.clearScreen();
                String stringToText = "You hit a bomb sucker!";
                for (int i = 0; i < stringToText.length(); i++) {
                    terminal.setCursorPosition(i, 3);
                    terminal.putCharacter(stringToText.charAt(i));

                }
                terminal.flush();
                Thread.sleep(4000);
                terminal.close();
            }
            terminal.flush();

    }
}
