package com.example.appproject.Model;

import java.util.ArrayList;

public class Move {
    private int srcI;
    private int srcJ;
    private int dstI;
    private int dstJ;
    private ArrayList list;

    public Move(int srcI,int srcJ,int dstI,int dstJ){
        this.srcI=srcI;
        this.srcJ=srcJ;
        this.dstI=dstI;
        this.dstJ=dstJ;

        list=new ArrayList<Integer>();
        list.add(srcI);
        list.add(srcJ);
        list.add(dstI);
        list.add(dstJ);
    }

    public ArrayList getList() {
        return list;
    }
}
