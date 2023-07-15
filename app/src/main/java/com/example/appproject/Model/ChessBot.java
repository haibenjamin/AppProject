package com.example.appproject.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ChessBot {
    Queue<ArrayList> moves;


    public ChessBot(){
       moves=new LinkedList<>();
        /*Move move1 = new Move(6,3,4,3);
        moves.add(move1.getList());
        Move move2 = new Move(6,4,4,4);
        moves.add(move2.getList());
        Move move3 = new Move(7,6,5,5);
        moves.add(move3.getList());
        Move move4 = new Move(7,1,5,2);
        moves.add(move4.getList());
        Move move5 = new Move(7,5,4,2);
        moves.add(move5.getList());*/



        }

    public Queue<ArrayList> getMoves() {
        return moves;
    }
}

