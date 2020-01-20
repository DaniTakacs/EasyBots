package com.codecool.easybots;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // write your code here
        char player = '@';
        char[][] map = generateMap();
        map = setStartingPoint(map, player);
        System.out.println(Arrays.deepToString(map));
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
                    map[x][y] = '-';

                } else {
                    map[x][y] = ' ';
                }
            }
        }
        map[0][0] = '+';
        map[0][lastColumnElement] = '+';
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


    public static char[][] setStartingPoint(char[][] map, char player){
        Random rand = new Random();
        int rowMax = 29;
        int min = 1;
        int columnMax = 49;
        int x = rand.nextInt(rowMax) + min;
        int y = rand.nextInt(columnMax) + min;

        map[x][y] = player;
        return map;
    }
}
