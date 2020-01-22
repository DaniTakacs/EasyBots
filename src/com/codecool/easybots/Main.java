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


        //get robot coordinates from ArrayList
        int robotCounter = listOfRobots.size();
        int[][] allRobotsPos = new int[robotCounter][2];
        for (int x = 0; x < robotCounter; x++) {
            for (int y = 0; y < 2; y++) {
                allRobotsPos[x][y] = listOfRobots.get(x)[y];
            }
        }
        //
        printMap(map);
        System.out.println(Arrays.toString(allRobotsPos[0]) + 1 + ".robot cord");
        System.out.println(Arrays.toString(allRobotsPos[1]) + 2 + ".robot cord");
        System.out.println(Arrays.toString(allRobotsPos[2]) + 3 + ".robot cord");


        do {
            int[] playerCoordinates = getCharacterPosition(map, player);
            System.out.println(Arrays.toString(playerCoordinates) + " player coord");
            Scanner in = new Scanner(System.in);
            char input = in.next().charAt(0);

            //Player movement
            int[] newPlayerCoordinates = movePlayer(playerCoordinates, input, map);
            char[][] newMap = modifyMap(map, newPlayerCoordinates);
            /*System.out.println(Arrays.toString(newPlayerCoordinates));
            System.out.println("playercoord UP: ");*/
            for (int x = 0; x < robotCounter; x++) {
                //System.out.println(Arrays.toString(allRobotsPos[x]));
                allRobotsPos = moveRobot(allRobotsPos, newMap, x);
            }
            //System.out.println(Arrays.toString(allRobotsPos));
            int counter = -1;
            while (counter < 2) {
                counter += 1;
                System.out.println(Arrays.toString(allRobotsPos[counter]) + counter + ".robot cord");
                for (int x = 0; x < 30; x++) {
                    for (int y = 0; y < 50; y++) {
                        if (x == allRobotsPos[counter][0] && y == allRobotsPos[counter][1]) {
                            newMap[x][y] = '#';
                        }
                    }
                }
            }
            //System.out.println(Arrays.toString(newRobotPosition));
            printMap(newMap);


            //Robot movement


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
            Integer[] pos = {x, y};
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
        if (input == 't'){
            playerPlace =teleport(map);
        }
        return playerPlace;
    }

    public static int[][] moveRobot(int[][] robotCoordinates, char[][] map, int robotNum) {

        int[] playerPosition = getCharacterPosition(map, '@');
        int counter = robotNum;
        int[] robotNewCoordinates = new int[2];

        // Checks the absolute x and y distance
        int distanceFromX = Math.abs(robotCoordinates[counter][0] - playerPosition[0]);
        int distanceFromY = Math.abs(robotCoordinates[counter][1] - playerPosition[1]);

        // Checks for game over


        //moves on the X axis
        robotLogic(robotCoordinates, playerPosition, counter, robotNewCoordinates, distanceFromX, distanceFromY);

        robotCoordinates[counter] = robotNewCoordinates;
        return robotCoordinates;
    }

    private static void robotLogic(int[][] robotCoordinates, int[] playerPosition, int counter, int[] robotNewCoordinates, int distanceFromX, int distanceFromY) {
        if (distanceFromX == 0 || distanceFromY == 0) {
            if (distanceFromX == 0 && distanceFromY == 0) {
                System.out.println("Game over");
            } else {
                if (distanceFromX == 0) {
                    if (robotCoordinates[counter][1] - playerPosition[1] > 0) {
                        robotNewCoordinates[0] = robotCoordinates[counter][0];
                        robotNewCoordinates[1] = (robotCoordinates[counter][1] - 1);
                    } else {
                        robotNewCoordinates[0] = robotCoordinates[counter][0];
                        robotNewCoordinates[1] = (robotCoordinates[counter][1] + 1);
                    }
                } else {
                    if (robotCoordinates[counter][0] - playerPosition[0] > 0) {

                        robotNewCoordinates[0] = (robotCoordinates[counter][0] - 1);
                        robotNewCoordinates[1] = robotCoordinates[counter][1];
                    } else {
                        robotNewCoordinates[0] = (robotCoordinates[counter][0] + 1);
                        robotNewCoordinates[1] = robotCoordinates[counter][1];
                    }
                }
            }
        } else {
            if (distanceFromX < distanceFromY) {
                if (robotCoordinates[counter][0] - playerPosition[0] > 0) {
                    robotNewCoordinates[0] = (robotCoordinates[counter][0] - 1);
                    robotNewCoordinates[1] = robotCoordinates[counter][1];
                } else {
                    robotNewCoordinates[0] = (robotCoordinates[counter][0] + 1);
                    robotNewCoordinates[1] = robotCoordinates[counter][1];
                }
            } else {
                if (robotCoordinates[counter][1] - playerPosition[1] > 0) {
                    robotNewCoordinates[0] = robotCoordinates[counter][0];
                    robotNewCoordinates[1] = (robotCoordinates[counter][1] - 1);
                } else {
                    robotNewCoordinates[0] = robotCoordinates[counter][0];
                    robotNewCoordinates[1] = (robotCoordinates[counter][1] + 1);
                }
            }
        }
    }


    public static char[][] modifyMap(char[][] map, int[] newPlayerCoordinates) {
        int playerX = newPlayerCoordinates[0];
        int playerY = newPlayerCoordinates[1];
        for (int x = 0; x < 30; x++) {
            for (int y = 0; y < 50; y++) {
                if (map[x][y] == '@' || map[x][y] == '#') {
                    map[x][y] = ' ';
                }
            }
        }
        map[playerX][playerY] = '@';
        return map;
    }


    public static int[] teleport(char[][] map){
        int[] newPlace = new int[2];
        Random rand = new Random();
        int rowMax = 29;
        int min = 1;
        int columnMax = 49;
        int x = rand.nextInt(rowMax) + min;
        int y = rand.nextInt(columnMax) + min;
        while (map[x][y] != ' ') {
            x = rand.nextInt(rowMax) + min;
            y = rand.nextInt(columnMax) + min;
        }
        newPlace[0] = x;
        newPlace[1] = y;
        return newPlace;
    }


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}



