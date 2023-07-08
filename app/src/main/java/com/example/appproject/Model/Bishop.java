package com.example.appproject.Model;

import com.example.appproject.Logic.GameManager;

import java.util.Set;

public class Bishop extends Piece{
    private Point currPos;
    private int points;
    private int color;

    public Bishop( Point currPos,int color){
        super();
        this.currPos=currPos;
        this.points=points;
        this.color=color;
        setOptionalMoves();
    }
    @Override
    public void setOptionalMoves() {
        int tempX=0;
        int tempY=0;
        int minAxis=0;

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

    @Override
    public void setCurrPos(Point currPos) {
        this.currPos=currPos;

    }

    @Override
    public int getColor() {
        return this.color;
    }
}
