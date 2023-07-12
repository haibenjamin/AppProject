package com.example.appproject.Logic;

import com.example.appproject.Model.Bishop;
import com.example.appproject.Model.Blank;
import com.example.appproject.Model.King;
import com.example.appproject.Model.Knight;
import com.example.appproject.Model.Pawn;
import com.example.appproject.Model.Piece;
import com.example.appproject.Model.Point;
import com.example.appproject.Model.Queen;
import com.example.appproject.Model.Rook;
import com.example.appproject.R;

import java.util.HashSet;
import java.util.Set;

public class GameManager {
   //private pieces board[][];
   private Piece[][] board;
   private Set<Point> legalMoves = new HashSet<>();
   private Set<Point> optionalMoves = new HashSet<>();
    private final static int ROWS=8;
    private final static int COLS=8;
    public enum color{BLACK,WHITE};
    public enum pieces{BLACK_PAWN,BLACK_KNIGHT,BLACK_BISHOP,BLACK_ROOK,
        BLACK_QUEEN,BLACK_KING,BLANK,
    WHITE_PAWN,WHITE_KNIGHT,WHITE_BISHOP,WHITE_QUEEN,WHITE_KING,WHITE_ROOK};


    public GameManager() {
        this.board = new Piece[ROWS][COLS];
        fillBoard();
    }
    public Piece[][] getBoard(){
        return board;
    }

    public static int getCOLS() {
        return COLS;
    }

    public static int getROWS() {
        return ROWS;
    }

    private void fillBoard() {
        for (int i = 0; i < ROWS; i++) {
            //board[1][i]=pieces.WHITE_PAWN;
            board[1][i]=new Pawn(new Point(1,i),color.WHITE.ordinal());
          //  board[ROWS-2][i]=pieces.BLACK_PAWN;
            board[ROWS-2][i]=new Pawn(new Point(ROWS-2,i),color.BLACK.ordinal());
        }
        for (int i = 2; i <ROWS-2; i++) {
            for (int j =0; j < COLS; j++) {
               // board[i][j]=pieces.BLANK;
                board[i][j]=new Blank();

            }

        }
        //board[0][0]=pieces.WHITE_ROOK;
        board[0][0]=new Rook(new Point(0,0),color.WHITE.ordinal());
       // board[0][1]=pieces.WHITE_KNIGHT;
        board[0][1]=new Knight(new Point(0,1),color.WHITE.ordinal());
        //board[0][2]=pieces.WHITE_BISHOP;
        board[0][2]=new Bishop(new Point(0,2),color.WHITE.ordinal());
       // board[0][3]=pieces.WHITE_QUEEN;
        board[0][3]=new Queen(new Point(0,3),color.WHITE.ordinal());
       // board[0][4]=pieces.WHITE_KING;
        board[0][4]=new King(new Point(0,4),color.WHITE.ordinal());
     //   board[0][5]=pieces.WHITE_BISHOP;
        board[0][5]=new Bishop(new Point(0,5),color.WHITE.ordinal());
    //    board[0][6]=pieces.WHITE_KNIGHT;
        board[0][6]=new Knight(new Point(0,6),color.WHITE.ordinal());
    //    board[0][7]=pieces.WHITE_ROOK;
        board[0][7]=new Rook(new Point(0,7),color.WHITE.ordinal());

    /*    board[ROWS-1][0]=pieces.BLACK_ROOK;
        board[ROWS-1][1]=pieces.BLACK_KNIGHT;
        board[ROWS-1][2]=pieces.BLACK_BISHOP;
        board[ROWS-1][3]=pieces.BLACK_QUEEN;
        board[ROWS-1][4]=pieces.BLACK_KING;
        board[ROWS-1][5]=pieces.BLACK_BISHOP;
        board[ROWS-1][6]=pieces.BLACK_KNIGHT;
        board[ROWS-1][7]=pieces.BLACK_ROOK;*/

        board[ROWS-1][0]=new Rook(new Point(0,0),color.BLACK.ordinal());

        board[ROWS-1][1]=new Knight(new Point(0,1),color.BLACK.ordinal());

        board[ROWS-1][2]=new Bishop(new Point(0,2),color.BLACK.ordinal());

        board[ROWS-1][3]=new Queen(new Point(0,3),color.BLACK.ordinal());

        board[ROWS-1][4]=new King(new Point(0,4),color.BLACK.ordinal());

        board[ROWS-1][5]=new Bishop(new Point(0,5),color.BLACK.ordinal());

        board[ROWS-1][6]=new Knight(new Point(0,6),color.BLACK.ordinal());

        board[ROWS-1][7]=new Rook(new Point(0,7),color.BLACK.ordinal());


    }
    public void flipBoard(){
        Piece[][] newBoard = new Piece[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                newBoard[i][j]=board[ROWS-1-i][COLS-1-j];

            }

        }
        board=newBoard;
    }

    public void move(int srcI,int srcJ,int dstI,int dstJ){
        board[dstI][dstJ]=board[srcI][srcJ];
        board[srcI][srcJ]=new Blank();

    }

    public void setOptionalMoves(Point p) {
        Piece selectedPiece;
        selectedPiece=board[p.getX()][p.getY()];


        //Rook
        if (selectedPiece instanceof Rook){
            for (int i = 0; i < ROWS; i++) {
                this.optionalMoves.add(new Point(i,p.getY()));
                this.optionalMoves.add(new Point(p.getX(),i));
            }

        }
        //Bishop
        if (selectedPiece instanceof Bishop){

        }
        //Knight
        if (selectedPiece instanceof Knight){

        }
        //Queen
        if (selectedPiece instanceof Queen){

        }
        //King
        if (selectedPiece instanceof King){

        }
        //Pawn
        if (selectedPiece instanceof Pawn){

        }

    }

    public Set<Point> getLegalMoves(Point point){
        legalMoves.clear();
        optionalMoves.clear();
        setOptionalMoves(point);
        for(Point p :optionalMoves){
            if (board[p.getX()][p.getY()] instanceof Blank || board[p.getX()][p.getY()].getColor()!=board[p.getX()][p.getY()].getColor()){
                this.legalMoves.add(p);
            }
        }
        return legalMoves;
    }

}
