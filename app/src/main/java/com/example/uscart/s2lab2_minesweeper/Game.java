package com.example.uscart.s2lab2_minesweeper;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Scanner;

/**
 * Created by uscart on 3/6/18.
 */

public class Game {


    Board board;

    public Game(String diff, int rows, int cols) {
        board = new Board(diff, rows, cols);
    } // game constructor



    public void showAround(Button[][] btnList, int[][] boardFilled, int x, int y, Game g) { //recursive 0 - fill

        btnList[x][y].setEnabled(false); //make it so you can't click it again

        if(boardFilled[x][y] == 0) { //if the button's value is a zero
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) { // the i and j in these for loops are the values you add to the coordinates to check the eight spaces around it.

                    if (x + i < boardFilled.length && y + j < boardFilled[0].length && x + i >= 0 && y + j >= 0) { //if it's inbounds

                        if ((btnList[x + i][y + j].getText().toString()).equals(" ")) { //if its a button that hasn't been revealed
                            btnList[x + i][y + j].setText(String.valueOf(boardFilled[x + i][y + j]));
                            btnList[x + i][y + j].setEnabled(false);


                            if ((btnList[x + i][y + j].getText().toString()).equals("0")) { //recursive case
                                g.showAround(btnList, boardFilled, x + i, y + j, g);
                            }

                        }
                    }
                }
            }
        }

        else{return;} //base case

    }


    public int[][] checkAround(Board board) { //this sets the values at the start
        int[][] zeroBoard = new int[board.getWidth()+2][board.getLength()+2];
        for(int i = 1; i < zeroBoard[0].length-1; i++) { //it is two values larger on either side because I am padding this array with zeros.
            for(int j = 1; j < zeroBoard.length-1; j++) {
                zeroBoard[j][i] = board.getBoard()[i-1][j-1];
            }
        }


        int numBombs = 0;
        int[][] newBoard = new int[board.getWidth()][board.getLength()]; //the one I am returning

        for(int rows = 1; rows < zeroBoard.length-1; rows++) {
            for(int cols = 1; cols < zeroBoard[0].length-1; cols++) {
                numBombs = 0;
                if (zeroBoard[rows][cols] == -1) {
                    newBoard[rows - 1][cols - 1] = -1; //if it's a bomb, set the value to -1
                }
                else {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (zeroBoard[rows + i][cols + j] == -1) {numBombs++;}
                        }
                    }
                    //check the 8 values around the button and add to the number of bombs around the button
                    newBoard[rows-1][cols-1] = numBombs; //
                }
            }
        }

        return newBoard;
    }


}
