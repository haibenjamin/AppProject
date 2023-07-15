package com.example.appproject.Model;

import com.example.appproject.Logic.GameManager;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece{
    private Point currPos;
    private int points;
    private int color;
    public Pawn(Point currPos,int color){
        super();
        this.currPos=currPos;
        this.points=points;
        this.color=color;
        setOptionalMoves();


    }
    @Override
    public void setOptionalMoves() {

    }
    public Set<Point> AllowedMoves(Point coordinates , Piece[][] board){

        Set<Point> allowedMoves = new HashSet<>();
        allowedMoves.clear();
        Point c;


        if(board[coordinates.getX()][coordinates.getY()].getColor()==GameManager.color.WHITE.ordinal()){

            if(coordinates.getY()<8 && coordinates.getY()>=0 && (coordinates.getX()-1)<8 && (coordinates.getX()-1)>=0){
                if(board[coordinates.getX()-1][coordinates.getY()] instanceof Blank){
                    c=new Point(coordinates.getX()-1 , coordinates.getY());
                    allowedMoves.add(c);

                    if((coordinates.getX() == 6) && (board[coordinates.getX()-2][coordinates.getY()] instanceof Blank)){
                        c = new Point(coordinates.getX()-2, coordinates.getY());
                        allowedMoves.add(c);
                    }
                }
            }

            if((coordinates.getX()-1)<8 && (coordinates.getX()-1)>=0 && (coordinates.getY()+1)<8 && (coordinates.getY()+1)>=0) {
                if (!(board[coordinates.getX() -1][coordinates.getY() + 1] instanceof Blank)) {
                    if(board[coordinates.getX() - 1][coordinates.getY() + 1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX() - 1, coordinates.getY() + 1);
                        allowedMoves.add(c);
                    }
                }

            }

            if((coordinates.getX()-1)<8 && (coordinates.getX()-1)>=0 && (coordinates.getY()-1)<8 && (coordinates.getY()-1)>=0) {
                if (!(board[coordinates.getX() - 1][coordinates.getY() - 1] instanceof Blank)) {
                    if(board[coordinates.getX() - 1][coordinates.getY() - 1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX() - 1, coordinates.getY() - 1);
                        allowedMoves.add(c);
                    }
                }
            }

        }else{

            if((coordinates.getY())<8 && (coordinates.getY())>=0 && (coordinates.getX()+1)<8 && (coordinates.getX()+1)>=0) {
                if (board[coordinates.getX()+1][coordinates.getY()] instanceof Blank) {
                    c = new Point(coordinates.getX()+1, coordinates.getY());
                    allowedMoves.add(c);

                    if(coordinates.getX() == 1 && (board[coordinates.getX()+2][coordinates.getY()] instanceof Blank)){
                        c = new Point(coordinates.getX()+2, coordinates.getY());
                        allowedMoves.add(c);
                    }
                }
            }

            if((coordinates.getX()+1)<8 && (coordinates.getX()+1)>=0 && (coordinates.getY()+1)<8 && (coordinates.getY()+1)>=0) {
                if (!(board[coordinates.getX() + 1][coordinates.getY() + 1] instanceof Blank)) {
                    if(board[coordinates.getX() + 1][coordinates.getY() + 1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX() + 1, coordinates.getY() + 1);
                        allowedMoves.add(c);
                    }
                }
            }

            if((coordinates.getX()+1)<8 && (coordinates.getX()+1)>=0 && (coordinates.getY()-1)<8 && (coordinates.getY()-1)>=0) {
                if (!(board[coordinates.getX() + 1][coordinates.getY() - 1] instanceof Blank)) {
                    if(board[coordinates.getX() + 1][coordinates.getY() - 1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX() + 1, coordinates.getY() - 1);
                        allowedMoves.add(c);
                    }
                }
            }

        }
        //check locations at board
        //work on the coordinates and return the allowed moves
        return allowedMoves;
    }



    @Override
    public void setPoints(int points) {
this.points=1;
    }

    @Override
    public void setCurrPos(Point currPos) {

    }

    @Override
    public int getColor() {
        return this.color;
    }
}
