package com.example.appproject.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appproject.R;
import com.example.appproject.Utillities.SignalGenerator;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //player unique id
    private String playerId = "0";
    private String playerName = "";
    private String opponentName = "";

    // true when opponent was found
    private boolean opponentFound = false;
    //unique id of opponent
    private String opponentId = "0";

    // value must be empty, matching or waiting, when a user create a new room and he is waiting for other to join value will be waiting
    private String status = "matching";
    private String playerTurn = "";
    //connection id in which player has joined to play
    public static String CONID = "KEY_CONID";
    public static final String CONNECTED="KEY_CONNECTED";
    private String connectionId="";
    private int playersCount = 0;
    private boolean gameLoaded = false;
    private boolean isWhite=false;
    //getting database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appproject-3c98f-default-rtdb.firebaseio.com/");
    //generating value event listener for database
    ValueEventListener turnsEventListener, wonEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn = findViewById(R.id.btnStartGame);
        Button loginBtn = findViewById(R.id.btnSignIn);
        Button signBtn = findViewById(R.id.btnSignUp);
        Button statsBtn = findViewById(R.id.btnStatistics);
        isConnected(statsBtn,startBtn);


        //generate uniqe id
        playerId=System.currentTimeMillis()+"";
        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StatsActivity.class);
                startActivity(intent);
                finish();
            }
        });





        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this,ColorActivity.class);
                    intent.putExtra(GameActivity.PLAYER_NAME,playerName);
                    startActivity(intent);
                    finish();


                //status="matching";
            //    opponentFound=false;

             /*   databaseReference.child("connection").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //look for opponent
                        if (!opponentFound) {
                            //checking for other players in dataBase
                            if (snapshot.hasChildren()) {
                                for (DataSnapshot connections : snapshot.getChildren()) {
                                    String conId = connections.getKey();
                                    connectionId = conId;
                                    playersCount = (int) connections.getChildrenCount();
                                    if (status.equals("waiting")) {
                                        if (playersCount == 2) {
                                            playerTurn = playerId;
                                            applyPlayTurn(playerTurn);
                                            boolean playerFound = false;
                                            for (DataSnapshot players : connections.getChildren()) {
                                                String getPlayerId = players.getKey();
                                                //check if playerid matches who created the lobby
                                                if (getPlayerId.equals(playerId)) {
                                                    playerFound = true;
                                                    isWhite=true;
                                                } else if (playerFound) {
                                                    String getOpponentName = players.child("player_name").getValue(String.class);
                                                    opponentName = getOpponentName;
                                                    //assigning connection id
                                                    opponentId = players.getKey();
                                                    opponentFound = true;
                                                    connectionId = conId;
                                                    //adding turns and won listener to the database reference
                                                    databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
                                                    databaseReference.child("won").child(connectionId).addValueEventListener(wonEventListener);

                                                    //once the connection removed connectionListener from database
                                                    databaseReference.child("connections").removeEventListener(this);


                                                }

                                            }

                                        }
                                        //in case user has not created the lobby because other rooms are avaiable to join
                                    } else {
                                        //checking if the connection has 1 player and need 1 more player to join
                                        if (playersCount == 1) {
                                            connections.child(playerId).child("player_name").getRef().setValue(playerName);
                                            for (DataSnapshot players : connections.getChildren()) {
                                                String getOpponentName = players.child("player_name").getValue(String.class);
                                                opponentName = getOpponentName;
                                                opponentId = players.getKey();
                                                //first turn will be of who created the connection
                                                playerTurn = opponentId;
                                                applyPlayTurn(playerTurn);
                                                //player2TV.setText(getOpponentName);

                                                connectionId = conId;
                                                opponentFound = true;
                                                //adding turns and won listener to the database reference
                                                databaseReference.child("turns").child(connectionId).addValueEventListener(turnsEventListener);
                                                databaseReference.child("won").child(connectionId).addValueEventListener(wonEventListener);


                                                //once the connection removed connectionListener from database
                                                databaseReference.child("connections").removeEventListener(this);

                                                break;
                                            }
                                        }

                                    }

                                }
                                //check if opponent is not found and user is not waiting for the opponent anymore
                                if (!opponentFound && !status.equals("waiting")) {
                                    //generate unique room id

                                    String connectionId = String.valueOf(System.currentTimeMillis());
                                    snapshot.child(connectionId).child(playerId).child(("player_name")).getRef().setValue(playerName);
                                    status = "waiting";
                                }
                            } else {
                                //generate unique room id
                                connectionId = String.valueOf(System.currentTimeMillis());
                                //adding first player
                                snapshot.child(connectionId).child(playerId).child(("player_name")).getRef().setValue(playerName);
                                status = "waiting";
                            }

                        }
                        Log.d("con id",connectionId+"");

                        playersCount = (int) snapshot.child(connectionId).getChildrenCount();
                        if (playersCount == 2 && !gameLoaded) {
                            gameLoaded = true;
                            Intent i = new Intent(MainActivity.this, GameActivity.class);
                            i.putExtra(GameActivity.START, true);
                            i.putExtra(GameActivity.CONID, connectionId);
                            i.putExtra(GameActivity.PLAYER2, opponentName);
                            i.putExtra(GameActivity.START, true);
                            i.putExtra(GameActivity.PLAYER1, playerName);
                            i.putExtra(GameActivity.COUNT, playersCount);
                            i.putExtra(GameActivity.COLOR,isWhite);
                            startActivity(i);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if (playersCount < 2) {
                    SignalGenerator.getInstance().toast("waiting for player", Toast.LENGTH_SHORT);
                }*/


            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        turnsEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //getting all turns of the connection
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getChildrenCount() == 2) {
                        //get box position selected by the user
                        //  final int getBoxPosition= Integer.parseInt(dataSnapshot.child("box_position").getValue(String.class));
                        //getting player id who selected the box
                        final String getPlayerId = dataSnapshot.child("player_id").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        wonEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

    }

    private void isConnected(Button stats,Button start) {
        boolean connected=false;
        connected=getIntent().getBooleanExtra(CONNECTED,true);
        if (connected){
            stats.setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // databaseReference.child("connection").removeValue();



    }

    public void reset(){
        status = "matching";
        opponentFound = false;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("connection").getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot objSnapshot: snapshot.getChildren()) {
                    connectionId = objSnapshot.getKey();
                    databaseReference.child("connection").child(connectionId).child(playerId).removeValue();
                    break;
                }
             }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("Read failed", firebaseError.getMessage());
            }
        });


    }



    private void applyPlayTurn(String playerTurn) {
        if (playerTurn.equals(playerId)) ;
    }
}