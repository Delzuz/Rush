import com.googlecode.lanterna.terminal.Terminal;
import java.util.Random;
import java.io.IOException;

public class Bombs {
    public static int x;
    public static int y;

    public Bombs(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void createBombs(int Bombs, int x, int y) throws IOException {
         Random r1 = new Random();
         x = r1.nextInt(15);
         y = r1.nextInt(15);

        final char bomb = 'O';
        Terminal terminal = null;
        terminal.setCursorPosition(x, y);
        terminal.putCharacter(bomb);
        terminal.flush();
    }
}
