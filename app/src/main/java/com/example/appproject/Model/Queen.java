package com.example.appproject.Model;

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
