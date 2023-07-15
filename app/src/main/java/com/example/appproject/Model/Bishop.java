package com.example.appproject.Model;

import com.example.appproject.Logic.GameManager;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece{
    private int points;
    private int color;

    public Bishop( Point currPos,int color){
        super();
        setCurrPosition(currPos);
        this.points=points;
        this.color=color;
        setOptionalMoves();
    }
    @Override
    public void setOptionalMoves() {
        int tempX=0;
        int tempY=0;
        int minAxis=0;
        Point currPos=getCurrPosition();
        //offset of main diagonal
        minAxis =Math.min(currPos.getX(), currPos.getY());
        tempX-=minAxis;
        tempY-=minAxis;
        //main diagonal
        while(tempX+1<GameManager.getROWS()&& tempY+1<GameManager.getCOLS()){
            optionalMoves.add(new Point(tempX,tempY));
            tempX++;
            tempY++;
        }
        //upperleft diagonal

        //bottomright diagonal

    }



    @Override
    public void setPoints(int points) {
        this.points=3;
    }
    public Set<Point> AllowedMoves(Point coordinates , Piece[][] board){
        Set<Point> allowedMoves = new HashSet<>();
        Point c;

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
        return allowedMoves;
    }

    @Override
    public void setCurrPos(Point currPos) {


    }

    @Override
    public int getColor() {
        return this.color;
    }
}
