package org.example;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Obstacles {


    private int size;
    private int posX;
    private int posY;
    private Terminal terminal;
    Position[] obstacles;
    boolean continueReadingInput;

    public Obstacles(int size, int posX, int posY, Terminal terminal, boolean continueReadingInput) {
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        this.terminal = terminal;
        this.continueReadingInput = continueReadingInput;
    }

    public Obstacles(int size, int posX, int posY, Terminal terminal) {
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        this.terminal = terminal;
    }


    public void createObstacles () throws IOException {
        obstacles = new Position[size];
        for (int i = 0; i < size; i++) {
            obstacles[i] = new Position(posX, posY + i);
        }

        for (Position p : obstacles) {
            terminal.setCursorPosition(p.x, p.y);
            terminal.putCharacter('\u2588');

        }
    }

    public void collisionObstacles (int playerx, int playery) throws IOException {
        for (Position ob : obstacles) {
            if (ob.x == playerx && ob.y == playery) {
                terminal.bell();
                System.out.println("GAME OVER!");
                continueReadingInput = false;
            }
        }

    }





}
