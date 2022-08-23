package org.example;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Obstacles {


    private int size;
    private int posX;
    private int posY;
    private Terminal terminal;

    public Obstacles(int size, int posX, int posY, Terminal terminal) {
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        this.terminal = terminal;
    }


    public void createObstacles () throws IOException {
        Position[] obstacles = new Position[size];
        for (int i = 0; i < size; i++) {
            obstacles[i] = new Position(posX, posY + i);
        }

        for (Position p : obstacles) {
            terminal.setCursorPosition(p.x, p.y);
            terminal.putCharacter('\u2588');

        }
    }





}
