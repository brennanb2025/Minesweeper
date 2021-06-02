package com.example.uscart.s2lab2_minesweeper;


import android.util.Log;

/**
 * Created by uscart on 3/6/18.
 */

public class Board {
    private int[][] board;
    private int length;
    private int width;
    private String difficulty;

    public int[][] getBoard() {
        return board;
    } //get methods
    public int getWidth() {
        return width;
    }
    public int getLength() {
        return length;
    }

    public String getDiff() { return difficulty; } //returns difficulty solely for impossible difficulty


    public Board(String difficulty, int length, int width) {
        board = new int[length][width];
        this.length = length;
        this.width = width;
        this.difficulty = difficulty;

        double percent = 0.0;

        if (difficulty.equals("Easy")) { //sets the percent of the amount of bombs based on the diff entered
            percent = 0.1;
        } else if (difficulty.equals("Medium")) {
            percent = 0.2;
        } else if (difficulty.equals("Hard")) {
            percent = 0.3;
        }



        if(!difficulty.equals("Impossible")) { //set the bombs unless the difficulty is Impossible in which case they are all 0s.
            for (int l = 0; l < board.length; l++) {
                for (int w = 0; w < board[0].length; w++) {
                    double rand = Math.random();
                    if (rand < percent) { //if the random value is less than the percent that you set, make it a bomb
                        board[l][w] = -1;
                    }
                }
            }
        }



    }

}

