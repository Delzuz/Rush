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

    public void createBombs() throws IOException {
         Random r1 = new Random();
         this.x = r1.nextInt(15);
         this.y = r1.nextInt(15);

        final char bomb = 'O';
        terminal.setCursorPosition(x, y);
        terminal.putCharacter(bomb);
        terminal.flush();
    }
}
