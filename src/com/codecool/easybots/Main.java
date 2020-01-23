package com.codecool.easybots;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {

    static int turn = 3;

    public static void main(String[] args) {
        Random rng = new Random();
        boolean endOfGame = false;
        char player = '@';
        char robot = '#';
        int score = 0;
        int numberOfRobots = rng.nextInt(50) + 3; //Random number of Robots between 3-8
        ArrayList<Integer[]> listOfRobots = new ArrayList<>();

        char[][] map = generateMap();


        //Set coordinates for Robots
        map = setStartingPoint(map, player, listOfRobots);
        for (int i = 0; i < numberOfRobots; i++) {
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
        startGame(endOfGame, player, score, numberOfRobots, listOfRobots, map, robotCounter, allRobotsPos);


    }

    private static void startGame(boolean endOfGame, char player, int score, int numberOfRobots, ArrayList<Integer[]> listOfRobots, char[][] map, int robotCounter, int[][] allRobotsPos) {
        printStartMenu();
        Scanner scanner = new Scanner(System.in);
        char startChar = scanner.next().charAt(0);

        if (startChar == 's') {
            clearScreen();
            printMap(map);
            gameLoop(endOfGame, player, score, numberOfRobots, listOfRobots, map, robotCounter, allRobotsPos);
        } else {
            clearScreen();
            startGame(endOfGame, player, score, numberOfRobots, listOfRobots, map, robotCounter, allRobotsPos);

        }
    }


    public static void gameLoop(boolean endOfGame, char player, int score, int numberOfRobots, ArrayList<Integer[]> listOfRobots, char[][] map, int robotCounter, int[][] allRobotsPos) {
        while (!endOfGame) {
            int[] playerCoordinates = getCharacterPosition(map, player);

            robotCounter = listOfRobots.size();
            System.out.println("Your player is at " + playerCoordinates[0] + ", " + playerCoordinates[1] + "." + "\n");



            Scanner in = new Scanner(System.in);

            System.out.println('\n' + "  w" + "\n" + "a s d   or t for teleport (usable every 3 turns)" + "\n");
            System.out.println(" @ <-- Your character" + "\n" + " # <-- Enemy robots" + "\n");
            System.out.println("Your score: "+ score + "\n" );
            if (turn >= 3) {
                System.out.println("You can use teleport! (t) ");
            } else {
                System.out.println("You can teleport in " + (3 - turn) + " turns." + "\n");
            }
            System.out.println("Your turn to move!");

            char input = in.next().charAt(0);


            //Player movement
            int[] newPlayerCoordinates = movePlayer(playerCoordinates, input, map);
            turn++;
            //if caught, break, game over
            if (newPlayerCoordinates[0] == 0 || newPlayerCoordinates[1] == 0) {
                System.out.println("BIG LOSER");
                endOfGame = true;
            }

            map = modifyMap(map, newPlayerCoordinates);
            //move Robots
            for (int x = 0; x < robotCounter; x++) {
                allRobotsPos = moveRobot(allRobotsPos, map, x);
            }
            int counter = -1;
            while (counter < listOfRobots.size() - 1) {
                counter += 1;
                for (int x = 0; x < 30; x++) {
                    for (int y = 0; y < 50; y++) {
                        if (x == allRobotsPos[counter][0] && y == allRobotsPos[counter][1]) {
                            map[x][y] = '#';
                        }
                    }
                }
            }

            printMap(map);
            System.out.println(score + " <-- your score");
            System.out.println("starting robots " + numberOfRobots);
            System.out.println("sum current robots " + (score/100));
            clearScreen();
            printMap(map);
        }
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
                    map[x][0] = '+';
                    map[x][lastColumnElement] = '+';
                    map[lastRowElement][0] = '+';
                    map[lastRowElement][lastColumnElement] = '+';

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

    public static void printStartMenu() {
        System.out.println("𝘞𝘦𝘭𝘤𝘰𝘮𝘦 𝘵𝘰 𝘵𝘩𝘦 𝘙𝘖𝘉𝘖𝘛𝘚 © 𝘨𝘢𝘮𝘦!" + "\n" + "Press (s) to start.");

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
        if (input == 'd' && currentMap[baseX][rightY] != '+') {
            playerPlace[1] = rightY;
        }
        if (input == 'a' && currentMap[baseX][leftY] != '+') {
            playerPlace[1] = leftY;
        }
        if (input == 't' && turn >= 3) {
            playerPlace = teleport(map);
            turn = 0;
        }
        System.out.print("\033[H\033[2J");
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
        boolean sameRowAsPlayer = distanceFromX == 0;
        boolean sameColumnAsPlayer = distanceFromY == 0;
        boolean matchPlayerAxis = sameRowAsPlayer || sameColumnAsPlayer;
        boolean rowIsCloser = distanceFromX < distanceFromY;

        if (matchPlayerAxis) {
            if (sameRowAsPlayer && sameColumnAsPlayer) {
                gameOver();
            }
            else {
                if (sameRowAsPlayer) {
                    changeColumn(robotCoordinates, playerPosition, counter, robotNewCoordinates);
                } else {
                    changeRow(robotCoordinates, playerPosition, counter, robotNewCoordinates);
                }
            }
        } else {
            if (rowIsCloser) {
                changeRow(robotCoordinates, playerPosition, counter, robotNewCoordinates);
            } else {
                changeColumn(robotCoordinates, playerPosition, counter, robotNewCoordinates);
            }
        }
    }

    private static void changeRow(int[][] robotCoordinates, int[] playerPosition, int counter, int[] robotNewCoordinates) {
        if (robotCoordinates[counter][0] - playerPosition[0] > 0) {

            robotNewCoordinates[0] = (robotCoordinates[counter][0] - 1);
            robotNewCoordinates[1] = robotCoordinates[counter][1];
        } else {
            robotNewCoordinates[0] = (robotCoordinates[counter][0] + 1);
            robotNewCoordinates[1] = robotCoordinates[counter][1];
        }
    }

    private static void changeColumn(int[][] robotCoordinates, int[] playerPosition, int counter, int[] robotNewCoordinates) {
        if (robotCoordinates[counter][1] - playerPosition[1] > 0) {
            robotNewCoordinates[0] = robotCoordinates[counter][0];
            robotNewCoordinates[1] = (robotCoordinates[counter][1] - 1);
        } else {
            robotNewCoordinates[0] = robotCoordinates[counter][0];
            robotNewCoordinates[1] = (robotCoordinates[counter][1] + 1);
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


    public static int[] teleport(char[][] map) {
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



    public static int robotCollide(int[][] allRobots, int numberOfRobots) {
        int differentRobotsNum = 1;
        int score = 0;
        for (int x = 0; x < numberOfRobots - 1; x++){
            boolean robotDifferent = true;
            for (int y = x+1; y < numberOfRobots; y++){
                if (allRobots[x] == allRobots[y]){
                    robotDifferent = false;
                }
            }
            if (robotDifferent == true){
                differentRobotsNum += 1;
            }
        }
        score = (numberOfRobots - differentRobotsNum) * 100;
        return score;
    }


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void gameOver(){

    }

}



