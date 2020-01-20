package com.codecool.easybots;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // write your code here
        char player = '@';
        char robot = '#';
        char[][] map = generateMap();
        map = setStartingPoint(map, player);
        for (int i = 0; i < 3; i++) {
            map = setStartingRobot(map, robot);
        }
        int[] playerCoordinates = getPlayerPosition(map, player);
        printMap(map);
        Scanner in = new Scanner(System.in);
        char input = in.next().charAt(0);
        if (input == 'd'){
            System.out.println("moved right!");
        }
        int[] newPlayerCoordinates = movePlayer(playerCoordinates, input, map);
        System.out.println(Arrays.toString(playerCoordinates));
        System.out.println(Arrays.toString(newPlayerCoordinates));
        System.out.println("Your input: " + input);
        char[][] newMap = modifyMap(map, newPlayerCoordinates);
        printMap(newMap);
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


    public static char[][] setStartingPoint(char[][] map, char player) {
        Random rand = new Random();
        int rowMax = 29;
        int min = 1;
        int columnMax = 49;
        int x = rand.nextInt(rowMax) + min;
        int y = rand.nextInt(columnMax) + min;
        if (map[x][y] == ' ') {
            map[x][y] = player;
        } else {
            setStartingPoint(map, player);
        }
        return map;
    }

    public static char[][] setStartingRobot(char[][] map, char robot) {
        Random rand = new Random();
        int rowMax = 29;
        int min = 1;
        int columnMax = 49;
        int x = rand.nextInt(rowMax) + min;
        int y = rand.nextInt(columnMax) + min;
        if (map[x][y] == ' ') {
            map[x][y] = robot;
        } else {
            setStartingRobot(map, robot);
        }
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

    public static int[] movePlayer(int[] playerCoordinates, char input, char[][] map){
        int baseX = playerCoordinates[0];
        int baseY = playerCoordinates[1];
        int[] playerPlace = {baseX, baseY};
        char[][] currentMap = map;
        int upX = playerPlace[0] + 1;
        int downX = playerPlace[0] - 1;
        int rightY = playerPlace[1] + 1;
        int leftY = playerPlace[1] - 1;

        if(input == 'w' && currentMap[upX][baseY] != '_'){
            playerPlace[0] = upX;
        }
        if(input == 's' && currentMap[downX][baseY] != '_'){
            playerPlace[0] = downX;
        }
        if(input == 'd' && currentMap[baseX][rightY] != '|'){
            playerPlace[1] = rightY;
        }
        if(input == 'a' && currentMap[baseX][leftY] != '|'){
            playerPlace[1] = leftY;
        }
        return playerPlace;
    }

    public static char[][] modifyMap(char[][] map, int[] newPlayerCoordinates){
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
}

