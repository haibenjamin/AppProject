package com.example.appproject.Model;

import com.example.appproject.Logic.GameManager;

import java.util.Set;

public class Rook extends Piece {
    private Point currPos;
    private int points;
    private int color;
    public Rook(Point currPos,int color){
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
        this.points=5;

    }

    public void setCurrPos(Point currPos) {
        this.currPos = currPos;
    }

    @Override
    public int getColor() {
        return this.color;
    }
}
