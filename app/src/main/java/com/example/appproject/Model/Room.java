package com.example.appproject.Model;

public class Room {
    private boolean isPlayer1=false;
    private boolean isPlayer2=false;
    private int currPlayer1;
    private int currPlayer2;


    public void join(){
        if(isRoomFree()){
            if (!isPlayer1){
                //first slot free
                isPlayer1=true;
            }
            else{
                //second slot free
                isPlayer2=true;
            }
        }



    }
    private boolean isRoomFree(){
        return (!(isPlayer1||isPlayer2));



    }
    private boolean isRoomFull(){
        return (isPlayer1&&isPlayer2);
    }
}
