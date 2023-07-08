package com.example.appproject.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Piece implements Moveable {
    private int points;
    private Point currPosition;
    public enum color{BLACK,WHITE};
    public Set<Point> optionalMoves= new HashSet<>();



    public int getPoints() {
        return 0;

    }
    public Set<Point> getOptionalMoves(){
        return optionalMoves;
    }
    public void setOptionalMoves(Set<Point> legalMoves){
        this.optionalMoves=optionalMoves;

    }



}