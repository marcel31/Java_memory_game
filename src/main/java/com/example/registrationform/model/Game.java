package com.example.registrationform.model;

public class Game {
    private int playCounter;
    private char[][] board;
    private char useles;
    String winner ="";

    public Game() {
        this.playCounter = 0;
        this.board = new char[3][3];
    }

    public void makeMove(int col, int row) throws Exception {
        checkMove(col,row);
        if(this.playCounter % 2 == 0)
            this.board[col][row] = 'X';
        else
            this.board[col][row] = 'O';
        this.playCounter++;
    }


    private void checkMove(int col, int row) throws Exception {
        if (this.board[col][row] != 0) {
            throw new Exception("The cell is not empty!!");
        }
    }

    public char[][] getBoard() {
        return this.board;
    }

    public boolean winner(){
        if (checkWinner(board[0][0],board[0][1],board[0][2])) return true;
        if (checkWinner(board[0][0],board[1][0],board[2][0])) return true;
        if (checkWinner(board[0][0],board[1][1],board[2][2])) return true;
        if (checkWinner(board[1][0],board[1][1],board[1][2])) return true;
        if (checkWinner(board[0][1],board[1][1],board[2][1])) return true;
        if (checkWinner(board[0][2],board[1][2],board[2][2])) return true;
        if (checkWinner(board[2][0],board[2][1],board[2][2])) return true;
        if (checkWinner(board[0][2],board[1][1],board[2][0])) return true;

        return false;
    }

    private boolean checkWinner(char a, char b, char c) {
        if ((a == b) && (a == c) && a !=0) {
            winner = String.valueOf(a);
            return true;
        }
        else return false;
    }

    public String getWinner() throws Exception {
        if (winner.equals(""))
            throw  new Exception("No winner yet!!");
        else
            return winner;
    }
}
