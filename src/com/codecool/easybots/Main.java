package com.codecool.easybots;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // write your code here
        char player = '@';
        char[][] map = generateMap();
        map = setStartingPoint(map, player);
        int[] playerCoordinates = getPlayerPosition(map, player);
        printMap(map);
    }

    public static char[][] generateMap() {
        int rows = 30;
        int columns = 50;
        int lastRowElement = rows - 1;
        int lastColumnElement = columns - 1;
        char[][] map = new char[rows][columns];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (x == 0 || x == (rows - 1)) {
                    map[x][y] = '_';

                } else {
                    map[x][y] = ' ';
                }
            }
        }
        map[0][0] = '_';
        map[0][lastColumnElement] = '_';
        map[lastRowElement][0] = '+';
        map[lastRowElement][lastColumnElement] = '+';
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (x > 0 && x < (rows - 1)) {
                    map[x][0] = '|';
                    map[x][lastColumnElement] = '|';
                    map[lastRowElement][0] = '|';
                    map[lastRowElement][lastColumnElement] = '|';

                }
            }

        }
        return map;
    }


    public static char[][] setStartingPoint(char[][] map, char player) {
        Random rand = new Random();
        int rowMax = 29;
        int min = 1;
        int columnMax = 49;
        int x = rand.nextInt(rowMax) + min;
        int y = rand.nextInt(columnMax) + min;

        map[x][y] = player;
        return map;
    }


    public static int[] getPlayerPosition(char[][] map, char player) {
        int rows = 30;
        int columns = 50;
        int playerX = 0;
        int playerY = 0;
        int[] playerXY = new int[2];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (map[x][y] == '@') {
                    playerX = x;
                    playerY = y;
                }
            }
        }
        playerXY[0] = playerX;
        playerXY[1] = playerY;
        return playerXY;
    }

    public static void printMap(char[][] map) {
        for (int y = 0; y < 30; y++) {
            StringBuilder strBuilder = new StringBuilder("");
            System.out.println(strBuilder.append(map[y]));

        }
    }
}

