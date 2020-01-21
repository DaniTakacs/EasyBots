package com.codecool.easybots;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        boolean endOfGame = false;
        char player = '@';
        char robot = '#';
        ArrayList<Integer[]> listOfRobots = new ArrayList<>();
        char[][] map = generateMap();
        map = setStartingPoint(map, player, listOfRobots);
        for (int i = 0; i < 3; i++) {
            map = setStartingPoint(map, robot, listOfRobots);
        }
        printMap(map);
        do {
            int[] playerCoordinates = getCharacterPosition(map, player);
            printMap(map);
            Scanner in = new Scanner(System.in);
            char input = in.next().charAt(0);

            int[] newPlayerCoordinates = movePlayer(playerCoordinates, input, map);
            char[][] newMap = modifyMap(map, newPlayerCoordinates);
            printMap(newMap);

            //get robot coordinates from ArrayList
            int robotCounter = listOfRobots.size();
            int[][] allRobotsPos = new int[robotCounter][2];
            for(int x = 0; x < robotCounter; x++){
                for(int y = 0; y < 2; y++){
                    allRobotsPos[x][y] = listOfRobots.get(x)[y];
                }
            }
            //
            System.out.println(Arrays.toString(allRobotsPos[0]));
        }
        while (!endOfGame);
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


    public static char[][] setStartingPoint(char[][] map, char character, ArrayList<Integer[]> listOfRobots) {
        Random rand = new Random();
        int rowMax = 29;
        int min = 1;
        int columnMax = 49;
        int x = rand.nextInt(rowMax) + min;
        int y = rand.nextInt(columnMax) + min;
        if (map[x][y] == ' ') {
            map[x][y] = character;
            Integer[] pos = {y, x};
            if (character == '#') {
                listOfRobots.add(pos);
            }
        } else {
            setStartingPoint(map, character, listOfRobots);
        }
        return map;
    }


    public static int[] getCharacterPosition(char[][] map, char ch) {
        int rows = 30;
        int columns = 50;
        int characterX = 0;
        int characterY = 0;
        int[] characterXY = new int[2];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (map[x][y] == ch) {
                    characterX = x;
                    characterY = y;
                }
            }
        }
        characterXY[0] = characterX;
        characterXY[1] = characterY;
        return characterXY;
    }


    public static void printMap(char[][] map) {
        for (int y = 0; y < 30; y++) {
            StringBuilder strBuilder = new StringBuilder("");
            System.out.println(strBuilder.append(map[y]));

        }
    }


    public static int[] movePlayer(int[] playerCoordinates, char input, char[][] map) {
        int baseX = playerCoordinates[0];
        int baseY = playerCoordinates[1];
        int[] playerPlace = {baseX, baseY};
        char[][] currentMap = map;
        int upX = playerPlace[0] - 1;
        int downX = playerPlace[0] + 1;
        int rightY = playerPlace[1] + 1;
        int leftY = playerPlace[1] - 1;

        if (input == 'w' && currentMap[upX][baseY] != '_') {
            playerPlace[0] = upX;
        }
        if (input == 's' && currentMap[downX][baseY] != '_') {
            playerPlace[0] = downX;
        }
        if (input == 'd' && currentMap[baseX][rightY] != '|') {
            playerPlace[1] = rightY;
        }
        if (input == 'a' && currentMap[baseX][leftY] != '|') {
            playerPlace[1] = leftY;
        }
        return playerPlace;
    }


    public static char[][] modifyMap(char[][] map, int[] newPlayerCoordinates) {
        int playerX = newPlayerCoordinates[0];
        int playerY = newPlayerCoordinates[1];
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 50; y++) {
                if (map[x][y] == '@') {
                    map[x][y] = ' ';
                }
            }
        }
        map[playerX][playerY] = '@';
        return map;
    }


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public static int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}

