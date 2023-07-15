package com.example.appproject.Model;

import com.example.appproject.Logic.GameManager;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece{
    private Point currPos;
    private int points;
    private int color;
    public King(Point currPos,int color){
        super();
        this.currPos=currPos;
        this.points=points;
        this.color=color;
        setOptionalMoves();


    }


    @Override
    public void setOptionalMoves() {
        //upper bound
        if(currPos.getX()+1<= GameManager.getROWS()){
            optionalMoves.add(new Point(currPos.getX()+1, currPos.getY()));
            if (currPos.getY()-1>0)
                optionalMoves.add(new Point(currPos.getX()+1, currPos.getY()-1));
             if(currPos.getY()+1<=GameManager.getCOLS())
                 optionalMoves.add(new Point(currPos.getX()+1, currPos.getY()+1));
        }
        //lower bound
        if(currPos.getX()-1>=0){
            optionalMoves.add(new Point(currPos.getX()-1, currPos.getY()));
            if (currPos.getY()-1>=0)
                optionalMoves.add(new Point(currPos.getX()-1, currPos.getY()-1));
            if(currPos.getY()+1<=GameManager.getCOLS())
                optionalMoves.add(new Point(currPos.getX()-1, currPos.getY()+1));
        }
        //left bound
        if(currPos.getY()-1>=0){
            optionalMoves.add(new Point(currPos.getX(), currPos.getY()-1));
            if (currPos.getX()-1>=0)
                optionalMoves.add(new Point(currPos.getX()-1, currPos.getY()-1));
            if(currPos.getX()+1<=GameManager.getCOLS())
                optionalMoves.add(new Point(currPos.getX()+1, currPos.getY()-1));
        }
        //right bound
        if(currPos.getY()+1<= GameManager.getCOLS()){
            optionalMoves.add(new Point(currPos.getX(), currPos.getY()+1));
            if (currPos.getX()-1>=0)
                optionalMoves.add(new Point(currPos.getX()-1, currPos.getY()+1));
            if(currPos.getX()+1<=GameManager.getCOLS())
                optionalMoves.add(new Point(currPos.getX()+1, currPos.getY()+1));
        }

    }
    public Set<Point> AllowedMoves(Point coordinates , Piece[][] board){
        Set<Point> allowedMoves = new HashSet<>();
        allowedMoves.clear();
        Point c;

        if((coordinates.getX()+1) <8 && (coordinates.getY()+1)<8){
            if(board[coordinates.getX()+1][coordinates.getY()+1] instanceof Blank){
                c = new Point(coordinates.getX()+1 , coordinates.getY()+1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()+1][coordinates.getY()+1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()+1 , coordinates.getY()+1);
                    allowedMoves.add(c);
                }
            }
        }

        if((coordinates.getY()+1)<8){
            if(board[coordinates.getX()][coordinates.getY()+1] instanceof Blank){
                c = new Point(coordinates.getX() , coordinates.getY()+1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()][coordinates.getY()+1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX() , coordinates.getY()+1);
                    allowedMoves.add(c);
                }
            }
        }

        if((coordinates.getX()-1) >=0 && (coordinates.getY()+1)<8){
            if(board[coordinates.getX()-1][coordinates.getY()+1] instanceof Blank){
                c = new Point(coordinates.getX()-1 , coordinates.getY()+1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()-1][coordinates.getY()+1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()-1 , coordinates.getY()+1);
                    allowedMoves.add(c);
                }
            }
        }

        if((coordinates.getX()+1) <8 ){
            if(board[coordinates.getX()+1][coordinates.getY()] instanceof Blank){
                c = new Point(coordinates.getX()+1 , coordinates.getY());
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()+1][coordinates.getY()].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()+1 , coordinates.getY());
                    allowedMoves.add(c);
                }
            }
        }

        if((coordinates.getX()-1) <8 ){
            if(board[coordinates.getX()-1][coordinates.getY()] instanceof Blank){
                c = new Point(coordinates.getX()-1 , coordinates.getY());
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()-1][coordinates.getY()].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()-1 , coordinates.getY());
                    allowedMoves.add(c);
                }
            }
        }

        if((coordinates.getX()+1) <8 && (coordinates.getY()-1)>=0){
            if(board[coordinates.getX()+1][coordinates.getY()-1] instanceof Blank){
                c = new Point(coordinates.getX()+1 , coordinates.getY()-1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()+1][coordinates.getY()-1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()+1 , coordinates.getY()-1);
                    allowedMoves.add(c);
                }
            }
        }

        if((coordinates.getY()-1)>=0){
            if(board[coordinates.getX()][coordinates.getY()-1] instanceof Blank){
                c = new Point(coordinates.getX() , coordinates.getY()-1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()][coordinates.getY()-1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX() , coordinates.getY()-1);
                    allowedMoves.add(c);
                }
            }
        }

        if((coordinates.getX()-1) <8 && (coordinates.getY()-1)>=0){
            if(board[coordinates.getX()-1][coordinates.getY()-1] instanceof Blank){
                c = new Point(coordinates.getX()-1 , coordinates.getY()-1);
                allowedMoves.add(c);
            }else{
                if(board[coordinates.getX()-1][coordinates.getY()-1].getColor() != board[coordinates.getX()][coordinates.getY()].getColor()){
                    c = new Point(coordinates.getX()-1 , coordinates.getY()-1);
                    allowedMoves.add(c);
                }
            }
        }



        return allowedMoves;
    }



    @Override
    public void setPoints(int points) {
        this.points=10;

    }

    @Override
    public void setCurrPos(Point currPos) {

    }

    @Override
    public int getColor() {
        return this.color;
    }
}
