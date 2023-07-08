package com.example.appproject.Model;

import java.util.Set;

public class Blank extends Piece{
    private int points;
    @Override
    public void setOptionalMoves() {

    }



    @Override
    public void setPoints(int points) {
this.points=0;
    }

    @Override
    public void setCurrPos(Point currPos) {

    }

    @Override
    public int getColor() {
        return 0;
    }
}
