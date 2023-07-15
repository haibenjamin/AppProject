package com.example.appproject.Model;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece{
    private Point currPos;
    private int points;
    private int color;
    public Knight(Point currPos,int color){
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

        if (coordinates.getX()+2 <8 && coordinates.getY()-1 >=0){
            if(board[coordinates.getX()+2][coordinates.getY()-1] instanceof Blank){
                c = new Point(coordinates.getX()+2 , coordinates.getY()-1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()+2][coordinates.getY()-1].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()+2 , coordinates.getY()-1);
                    allowedMoves.add(c);
                }
            }
        }

        if (coordinates.getX()+1 <8 && coordinates.getY()-2 >=0){
            if(board[coordinates.getX()+1][coordinates.getY()-2] instanceof Blank){
                c = new Point(coordinates.getX()+1 , coordinates.getY()-2);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()+1][coordinates.getY()-2].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()+1 , coordinates.getY()-2);
                    allowedMoves.add(c);
                }
            }
        }

        if (coordinates.getX()-2 >= 0 && coordinates.getY()-1 >=0){
            if(board[coordinates.getX()-2][coordinates.getY()-1] instanceof Blank){
                c = new Point(coordinates.getX()-2 , coordinates.getY()-1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()-2][coordinates.getY()-1].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()-2 , coordinates.getY()-1);
                    allowedMoves.add(c);
                }
            }
        }

        if (coordinates.getX()-1 >=0  && coordinates.getY()-2 >=0){
            if(board[coordinates.getX()-1][coordinates.getY()-2] instanceof Blank){
                c = new Point(coordinates.getX()-1 , coordinates.getY()-2);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()-1][coordinates.getY()-2].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()-1 , coordinates.getY()-2);
                    allowedMoves.add(c);
                }
            }
        }

        if (coordinates.getX()+2 <8 && coordinates.getY()+1 <8){
            if(board[coordinates.getX()+2][coordinates.getY()+1] instanceof Blank){
                c = new Point(coordinates.getX()+2 , coordinates.getY()+1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()+2][coordinates.getY()+1].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()+2 , coordinates.getY()+1);
                    allowedMoves.add(c);
                }
            }
        }

        if (coordinates.getX()+1 <8 && coordinates.getY()+2 <8){
            if(board[coordinates.getX()+1][coordinates.getY()+2] instanceof Blank){
                c = new Point(coordinates.getX()+1 , coordinates.getY()+2);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()+1][coordinates.getY()+2].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()+1 , coordinates.getY()+2);
                    allowedMoves.add(c);
                }
            }
        }

        if (coordinates.getX()-2 >=0 && coordinates.getY()+1 <8){
            if(board[coordinates.getX()-2][coordinates.getY()+1] instanceof Blank){
                c = new Point(coordinates.getX()-2 , coordinates.getY()+1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()-2][coordinates.getY()+1].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()-2 , coordinates.getY()+1);
                    allowedMoves.add(c);
                }
            }
        }

        if (coordinates.getX()-1 >=0 && coordinates.getY()+2 <8){
            if(board[coordinates.getX()-1][coordinates.getY()+2] instanceof Blank){
                c = new Point(coordinates.getX()-1 , coordinates.getY()+2);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()-1][coordinates.getY()+2].getColor()!= board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()-1 , coordinates.getY()+2);
                    allowedMoves.add(c);
                }
            }
        }

        return allowedMoves;
    }




    @Override
    public void setPoints(int points) {
        this.points=3;

    }

    @Override
    public void setCurrPos(Point currPos) {

    }

    @Override
    public int getColor() {
        return this.color;
    }
}
