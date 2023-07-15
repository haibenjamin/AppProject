package com.example.appproject.Model;

import com.example.appproject.Logic.GameManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
    private int points;
    private int color;
    private boolean isMoved;
    //private ArrayList<Point> allowedMoves;
    public Rook(Point currPos,int color){
        super();
        setCurrPosition( currPos);
        this.points=points;
        this.color=color;
        isMoved=false;
       // allowedMoves=new ArrayList<>();

        setOptionalMoves();



    }
    @Override
    public void setOptionalMoves() {

    }
    public Set<Point> AllowedMoves(Point point , Piece[][] board){

        Set<Point> allowedMoves = new HashSet<>();
        allowedMoves.clear();
        Point p ;

        for(int i=(point.getX()+1) ; i<8 ;i++){
            if(board[i][point.getY()] instanceof Blank){
                p = new Point(i , point.getY());
                allowedMoves.add(p);
            }else{
                if(board[i][point.getY()].getColor() != board[point.getX()][point.getY()].getColor()){
                    p = new Point(i , point.getY());
                    allowedMoves.add(p);
                }
                break;
            }
        }

        for(int i=(point.getX()-1) ; i>=0 ; i--){
            if(board[i][point.getY()] instanceof Blank){
                p = new Point(i , point.getY());
                allowedMoves.add(p);
            }else{
                if(board[i][point.getY()].getColor() != board[point.getX()][point.getY()].getColor()){
                    p = new Point(i , point.getY());
                    allowedMoves.add(p);
                }
                break;
            }
        }

        for(int i=(point.getY()-1) ; i>=0 ; i--){
            if(board[point.getX()][i] instanceof Blank){
                p = new Point( point.getX() , i);
                allowedMoves.add(p);
            }else{
                if(board[point.getX()][i].getColor() != board[point.getX()][point.getY()].getColor()){
                    p = new Point( point.getX() , i);
                    allowedMoves.add(p);
                }
                break;
            }
        }

        for(int i=(point.getY()+1) ; i<8 ;i++){
            if(board[point.getX()][i] instanceof Blank){
                p = new Point(point.getX() , i);
                allowedMoves.add(p);
            }else{
                if(board[point.getX()][i].getColor() != board[point.getX()][point.getY()].getColor()){
                    p = new Point(point.getX() , i);
                    allowedMoves.add(p);
                }
                break;
            }
        }
        //check locations at board
        //work on the Point and return the allowed moves
       // this.allowedMoves=allowedMoves;
        return allowedMoves;
    }




    @Override
    public void setPoints(int points) {
        this.points=5;

    }

    public void setCurrPos(Point currPos) {
    }

    @Override
    public int getColor() {
        return this.color;
    }
    public void setMoved(boolean status){
        this.isMoved=status;
    }
    public boolean getMoved()
    {return this.isMoved;}

}
