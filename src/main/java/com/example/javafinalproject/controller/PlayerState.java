package com.example.javafinalproject.controller;

import java.util.ArrayList;
import java.util.List;

/** Decides if the players has lost or there are still moves available.
 * @author Ayush
 * @version 1.0
 */

public class PlayerState {

    /**
     * Checks of the user has lost or if there are further moves available
     * @param i Int value which specifies the row number of the grid
     * @param j Int vale which specifies the column number of the grid
     * @param chars A character to check if there are any possible moves.
     * @param arr Array of characters of the current board state.
     * @param characterList ArrayList of Characters which the player currently has
     * @param placedPieces ArrayList of placed pieces in string which is in the form rowNumberColumnNumber
     * @return True if the player has lost otherwise false
     */
    public boolean checkIfLost(int i, int j, char chars, char arr[][], List<Character> characterList, ArrayList<String> placedPieces) {
        boolean check = false;
        if (checkPlaced(i,j, placedPieces)) {
            //00
            if (i == 0 && j == 0) {
                if (arr[i][j] == chars || arr[i + 1][j] == chars || arr[i][j + 1] == chars || arr[i + 1][j + 1] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //01,02
            if (i == 0 && j > 0 && j < 3) {
                if (arr[i][j] == chars || arr[i][j - 1] == chars || arr[i][j + 1] == chars || arr[i + 1][j] == chars || arr[i + 1][j - 1] == chars || arr[i + 1][j + 1] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //03
            if (i == 0 && j == 3) {
                if (arr[i][j] == chars || arr[i][j - 1] == chars || arr[i + 1][j - 1] == chars || arr[i + 1][j] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //10,20
            if (i > 0 && j == 0 && i < 3) {
                if (arr[i][j] == chars || arr[i - 1][j] == chars || arr[i + 1][j] == chars || arr[i - 1][j + 1] == chars || arr[i + 1][j + 1] == chars || arr[i][j + 1] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //30
            if (i == 3 && j == 0) {
                if (arr[i][j] == chars || arr[i][j + 1] == chars || arr[i - 1][j] == chars || arr[i - 1][j + 1] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //31,32
            if (i == 3 && j > 0 && j < 3) {
                if (arr[i][j] == chars || arr[i][j - 1] == chars || arr[i][j + 1] == chars || arr[i - 1][j - 1] == chars || arr[i - 1][j + 1] == chars || arr[i - 1][j] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //13,23
            if (i > 0 && i < 3 && j == 3) {
                if (arr[i][j] == chars || arr[i - 1][j] == chars || arr[i + 1][j] == chars || arr[i][j - 1] == chars || arr[i - 1][j - 1] == chars || arr[i + 1][j - 1] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //11,12,21,22
            if (i > 0 && i < 3 && j > 0 && j < 3) {
                if (arr[i][j] == chars || arr[i - 1][j] == chars || arr[i + 1][j] == chars || arr[i][j + 1] == chars || arr[i][j - 1] == chars || arr[i - 1][j - 1] == chars || arr[i + 1][j - 1] == chars || arr[i + 1][j + 1] == chars || arr[i - 1][j + 1] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
            //33
            if (i == 3 && j == 3) {
                if (arr[i][j] == chars || arr[i][j - 1] == chars || arr[i - 1][j - 1] == chars || arr[i - 1][j] == chars || (!characterList.contains(chars))) {
                    check = true;
                }
            }
        } else {
            check = true;
        }
        return check;
    }

    /**
     * Checks the placed position of the pieces on the board.
     * @param i Int value which specifies the row number of the grid.
     * @param j Int vale which specifies the column number of the grid.
     * @param placedPieces ArrayList of placed pieces in string which is in the form rowNumberColumnNumber.
     * @return False if the rowColumn has a piece and True if the rowColumn doesn't have a placed piece.
     */
    public boolean checkPlaced(int i, int j, ArrayList<String> placedPieces) {
        String check = i+""+j;
        return !placedPieces.contains(check);
    }
}
