package com.example.javafinalproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Class for representing the intial state of the game.
 * @author Ayush
 * @version 1.0
 */

public class Puzzle {

    private List<Character> characterList;

    /**
     * Initialises the characterList which is an ArrayList of Characters.
     */
    public Puzzle() {
        characterList = new ArrayList<>();
    }

    /**
     * Acessor method to get the contents of the characterList.
     * @return List of characters
     */
    public List<Character> getCharacterList() {
        return characterList;
    }

    /**
     * This method creates the initial board of the game.
     * @param arr An empty array of characters which represents the board state.
     * @param n Int value which represents the number of rows or columns. Consider this value same for both row and column.
     * @param pieces Int value to denote the total number of pieces available to the player in the game.
     * @return Array of characters to set the initial board state when the game starts.
     */
    public char[][] createBoard(char[][] arr, int n, int pieces) {
        for(int i = 0; i < n; i++)  {
            for(int j = 0; j < n; j++)  {
                if(i == 0 && j == 0) // for [00]
                {
                    char y = getChar(pieces);
                    while(arr[i+1][j] == y || arr[i][j+1] == y || arr[i+1][j+1] == y ) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i == 0 && j > 0 && j < n-1) //for 01,02
                {
                    char y = getChar(pieces);
                    while(arr[i][j-1] == y || arr[i][j+1] == y || arr[i+1][j] == y || arr[i+1][j-1] == y || arr[i+1][j+1] == y)  {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i == 0 && j == n-1) // for [03]
                {
                    char y = getChar(pieces);
                    while(arr[i][j-1] == y || arr[i+1][j-1] == y || arr[i+1][j] == y || arr[i][j-2] == y || arr[i][j-3] == y) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i > 0 && j == 0 && i < n-1) //for 10,20,
                {
                    char y = getChar(pieces);
                    while(arr[i-1][j] == y || arr[i+1][j] == y || arr[i-1][j+1] == y || arr[i+1][j+1] == y || arr[i][j+1] == y) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i == n-1 && j == 0) //for 30
                {
                    char y = getChar(pieces);
                    while(arr[i][j+1] == y || arr[i-1][j] == y || arr[i-1][j+1] == y) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i == n-1 && j > 0 && j < n-1) //for 31,32
                {
                    char y = getChar(pieces);
                    while(arr[i][j-1] == y || arr[i][j+1] == y || arr[i-1][j-1] == y || arr[i-1][j+1] == y || arr[i-1][j] == y) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i > 0  && i < n-1 && j == n-1) // for 13,23
                {
                    char y = getChar(pieces);
                    while(arr[i-1][j] == y || arr[i+1][j] == y || arr[i][j-1] == y || arr[i-1][j-1] == y || arr[i+1][j-1] == y) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i > 0  && i < n-1 && j > 0 && j < n-1) // for 11,12,21,22
                {
                    char y = getChar(pieces);
                    while(arr[i-1][j] == y || arr[i+1][j] == y || arr[i][j+1] == y || arr[i][j-1] == y || arr[i-1][j-1] == y || arr[i+1][j-1] == y|| arr[i+1][j+1] == y || arr[i-1][j+1] == y) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
                if(i == n-1 && j == n-1) // for 33
                {
                    char y = getChar(pieces);
                    while(arr[i][j-1] == y || arr[i-1][j-1] == y || arr[i-1][j] == y) {
                        y = getChar(pieces);
                    }
                    arr[i][j]=y;
                }
            }
        }
        return arr;
    }

    /**
     * Initialises the characterList and adds all the characters that are with the player at the start of the game.
     */
    public void initialiseCharacterList() {
        characterList.add('A');
        characterList.add('A');
        characterList.add('B');
        characterList.add('B');
        characterList.add('B');
        characterList.add('C');
        characterList.add('C');
        characterList.add('C');
        characterList.add('D');
        characterList.add('D');
        characterList.add('D');
        characterList.add('D');
        characterList.add('E');
        characterList.add('E');
        characterList.add('E');
        characterList.add('E');
    }

    /**
     * This method returns the characters to create the board. Here a random number is chosen and then selects a character.
     * @param pieces Indicates the total number of pieces in the game.
     * @return The character depending on the ASCII value.
     */
    private char getChar(int pieces) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(pieces);
        char ascii = (char)(65+randomIndex);
        return ascii;
    }
}
