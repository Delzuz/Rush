package org.example;

import com.googlecode.lanterna.terminal.Terminal;
import java.util.Random;
import java.io.IOException;

public class Bombs {
    public int x;
    public int y;
    public Terminal terminal;

    public Bombs(int x, int y, Terminal terminal){
        this.x = x;
        this.y = y;
        this.terminal = terminal;
    }

    public void createBombs(Position player, Bombs bomb) throws IOException, InterruptedException {

          /*  Random r1 = new Random();
            x = r1.nextInt(58);
            y = r1.nextInt(13);

            //final char bomb = 'O';
            terminal.setCursorPosition(x, y);
            terminal.putCharacter('\u2622');;

           */

            if (player.x == bomb.x && player.y == bomb.y) {
                terminal.clearScreen();
                String stringToText = "YOU HIT A BOMB & EXPLODED SUCKER :(!";
                for (int i = 0; i < stringToText.length(); i++) {
                    terminal.setCursorPosition(i+15, 3);
                    terminal.putCharacter(stringToText.charAt(i));

                }
                terminal.flush();
                Thread.sleep(4000);
                terminal.close();
            }
            terminal.flush();

    }
}
