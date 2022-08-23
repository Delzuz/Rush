package org.example;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Obstacles {


    private int size;
    public int posX;
    public int posY;
    private Terminal terminal;
    Position[] obstacles;
    boolean continueReadingInput;

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
/*
    public Obstacles(int size, int posX, int posY, Terminal terminal, boolean continueReadingInput) {
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        this.terminal = terminal;
        this.continueReadingInput = continueReadingInput;
    }

     */

    public Obstacles(int size, int posX, int posY, Terminal terminal) {
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        this.terminal = terminal;
    }


    public void createObstacles () throws IOException, InterruptedException {
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
                System.exit(0);
            }
        }

    }

}
