package com.example.appproject.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece{
    private Point currPos;
    private int points;
    private int color;
    public Queen(Point currPos,int color){
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
        Point c;
        allowedMoves.clear();

        for(int i=(coordinates.getX()+1) ; i<8 ;i++){
            if(board[i][coordinates.getY()] instanceof Blank){
                c = new Point(i , coordinates.getY());
                allowedMoves.add(c);
            }else{
                if(board[i][coordinates.getY()].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(i , coordinates.getY());
                    allowedMoves.add(c);
                }
                break;
            }
        }

        for(int i=(coordinates.getX()-1) ; i>=0 ; i--){
            if(board[i][coordinates.getY()] instanceof Blank){
                c = new Point(i , coordinates.getY());
                allowedMoves.add(c);
            }else{
                if(board[i][coordinates.getY()].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(i , coordinates.getY());
                    allowedMoves.add(c);
                }
                break;
            }
        }

        for(int i=(coordinates.getY()-1) ; i>=0 ; i--){
            if(board[coordinates.getX()][i] instanceof Blank){
                c = new Point( coordinates.getX() , i);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()][i].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point( coordinates.getX() , i);
                    allowedMoves.add(c);
                }
                break;
            }
        }

        for(int i=(coordinates.getY()+1) ; i<8 ;i++){
            if(board[coordinates.getX()][i] instanceof Blank){
                c = new Point(coordinates.getX() , i);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()][i].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX() , i);
                    allowedMoves.add(c);
                }
                break;
            }
        }

        /*............................*/
        for(int i=1 ; i<8 ; i++){
            if((coordinates.getX()+i)<8 && (coordinates.getY()+i)<8){
                if(board[coordinates.getX()+i][coordinates.getY()+i] instanceof Blank){
                    c = new Point(coordinates.getX()+i , coordinates.getY()+i);
                    allowedMoves.add(c);
                }else{
                    if(board[coordinates.getX()+i][coordinates.getY()+i].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX()+i , coordinates.getY()+i);
                        allowedMoves.add(c);
                    }
                    break;
                }
            }
        }

        for(int i=1 ; i<8 ; i++){
            if((coordinates.getX()-i)>=0 && (coordinates.getY()+i)<8){
                if(board[coordinates.getX()-i][coordinates.getY()+i] instanceof Blank){
                    c = new Point(coordinates.getX()-i , coordinates.getY()+i);
                    allowedMoves.add(c);
                }else{
                    if(board[coordinates.getX()-i][coordinates.getY()+i].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX()-i , coordinates.getY()+i);
                        allowedMoves.add(c);
                    }
                    break;
                }

            }
        }

        for(int i=1 ; i<8 ; i++){
            if((coordinates.getX()-i)>=0 && (coordinates.getY()-i)>=0){
                if(board[coordinates.getX()-i][coordinates.getY()-i] instanceof Blank){
                    c = new Point(coordinates.getX()-i , coordinates.getY()-i);
                    allowedMoves.add(c);
                }else{
                    if(board[coordinates.getX()-i][coordinates.getY()-i].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX()-i , coordinates.getY()-i);
                        allowedMoves.add(c);
                    }
                    break;
                }

            }
        }

        for(int i=1 ; i<8 ; i++){
            if((coordinates.getX()+i)<8 && (coordinates.getY()-i)>=0){
                if(board[coordinates.getX()+i][coordinates.getY()-i] instanceof Blank){
                    c = new Point(coordinates.getX()+i , coordinates.getY()-i);
                    allowedMoves.add(c);
                }else{
                    if(board[coordinates.getX()+i][coordinates.getY()-i].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                        c = new Point(coordinates.getX()+i , coordinates.getY()-i);
                        allowedMoves.add(c);
                    }
                    break;
                }

            }
        }

        //check locations at board
        //work on the coordinates and return the allowed moves
        return allowedMoves;
    }
    @Override
    public void setPoints(int points) {
this.points=9;
    }

    @Override
    public void setCurrPos(Point currPos) {

    }

    @Override
    public int getColor() {
        return this.color;
    }
}
