package com.example.appproject.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appproject.Logic.GameManager;
import com.example.appproject.Model.Bishop;
import com.example.appproject.Model.Blank;
import com.example.appproject.Model.DrawBoard;
import com.example.appproject.Model.King;
import com.example.appproject.Model.Knight;
import com.example.appproject.Model.Pawn;
import com.example.appproject.Model.Piece;
import com.example.appproject.Model.Point;
import com.example.appproject.Model.Queen;
import com.example.appproject.Model.Rook;
import com.example.appproject.R;
import com.example.appproject.Utillities.SignalGenerator;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class GameActivity extends Activity {
    public static final String PLAYER1 = "KEY_PLAYER1";
    public static final String PLAYER2 = "KEY_PLAYER2";
    public static final String START = "KEY_START";
    public static final String CONID = "KEY_CONID";
    public static final String COUNT="KEY_COUNT";
    String connectionId="";
    Intent prevIntent;

    TextView player1Name;
    TextView player2Name;

    final int ROWS = 8;
    ShapeableImageView selected = null;
    final int COLS = 8;
    int color;
    int srcI, srcJ, dstI, dstJ;
    ShapeableImageView[][] board;
    Button backBtn;
    GameManager gameManager = new GameManager();
    SignalGenerator signalGenerator;
    boolean start = false;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appproject-3c98f-default-rtdb.firebaseio.com/");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        prevIntent = getIntent();
        initView();
        movesListener();
        resetBackgrounds();
        drawBoard();
        waitForGameToStart();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("connection").removeValue();
                finish();

            }
        });


        //  drawView = new DrawBoard(this);
        //  drawView.setBackgroundColor(Color.WHITE);
        //  setContentView(drawView);

    }


    private void waitForGameToStart() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int playersCount = prevIntent.getIntExtra("KEY_COUNT", 0);
                if (playersCount == 1) {
                    signalGenerator.getInstance().toast("waiting for player", Toast.LENGTH_SHORT);

                    if (prevIntent.getStringExtra("KEY_PLAYER1") != null) {
                        player1Name.setText(prevIntent.getStringExtra("KEY_PLAYER1"));

                    }
                }
                if (playersCount == 2) {
                    if (prevIntent.getStringExtra("KEY_PLAYER2") != null) {
                        player2Name.setText(prevIntent.getStringExtra("KEY_PLAYER2"));

                    }
                }
            }

        }, 1000);




    }



    private void drawBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if ((gameManager.getBoard()[i][j]) instanceof Rook) {
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.BLACK.ordinal())
                        board[i][j].setImageResource(R.drawable.rook_black);
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.WHITE.ordinal())
                        board[i][j].setImageResource(R.drawable.rook_white);
                }
                if ((gameManager.getBoard()[i][j]) instanceof Knight) {
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.BLACK.ordinal())
                        board[i][j].setImageResource(R.drawable.knight_black);
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.WHITE.ordinal())
                        board[i][j].setImageResource(R.drawable.knight_white);
                }
                if ((gameManager.getBoard()[i][j]) instanceof Bishop)
                {
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.BLACK.ordinal())
                        board[i][j].setImageResource(R.drawable.bishop_black);
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.WHITE.ordinal())
                        board[i][j].setImageResource(R.drawable.bishop_white);
                }
                if ((gameManager.getBoard()[i][j]) instanceof Queen)
                {
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.BLACK.ordinal())
                        board[i][j].setImageResource(R.drawable.queen_black);
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.WHITE.ordinal())
                        board[i][j].setImageResource(R.drawable.queen_white);
                }
                if ((gameManager.getBoard()[i][j]) instanceof King)
                {
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.BLACK.ordinal())
                        board[i][j].setImageResource(R.drawable.king_black);
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.WHITE.ordinal())
                        board[i][j].setImageResource(R.drawable.king_white);
                }
                if ((gameManager.getBoard()[i][j]) instanceof Pawn)
                {
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.BLACK.ordinal())
                        board[i][j].setImageResource(R.drawable.pawn_black);
                    if (gameManager.getBoard()[i][j].getColor() == GameManager.color.WHITE.ordinal())
                        board[i][j].setImageResource(R.drawable.pawn_white);
                }

                if(gameManager.getBoard()[i][j] instanceof Blank)
                    board[i][j].setImageResource(0);
            }

        }
    }

    private void resetBackgrounds() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if((i+j)%2==0)
                    board[i][j].setBackgroundColor(getResources().getColor(R.color.green_100));
                else
                    board[i][j].setBackgroundColor(getResources().getColor(R.color.white));


            }

        }
    }


    @SuppressLint("ResourceAsColor")
        private void movesListener() {

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    int finalI = i;
                    int finalJ = j;
                    board[i][j].setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onClick(View v) {
                            waitForGameToStart();
                            if (selected==null){
                                selected=board[finalI][finalJ];
                                selected.setBackgroundColor(R.color.teal_200);
                                srcI=finalI;
                                srcJ=finalJ;
                                paintLegalMoves( new Point(srcI,srcJ));

                            }
                            else{

                             //   board[finalI][finalJ].setImageResource(R.drawable.bishop_black);

                                gameManager.move(srcI,srcJ,finalI,finalJ);
                                resetBackgrounds();
                                drawBoard();
                                selected=null;
                            }
                        }
                    });

                }

            }




            }

    private void paintLegalMoves( Point p) {

        //Set<Point> points = piece.getLegalMoves();
        for (Point point: gameManager.getLegalMoves(p)
             ) {
            board[point.getX()][point.getY()].setBackgroundColor(Color.GREEN);
            
        }

    }


    private void initView() {
        player1Name=findViewById(R.id.player1_id);
        player2Name=findViewById(R.id.player2_id);

        backBtn=findViewById(R.id.back_button);

        board=new ShapeableImageView[][]{
                {findViewById(R.id.board00),findViewById(R.id.board01),findViewById(R.id.board02),findViewById(R.id.board03),
                        findViewById(R.id.board04),findViewById(R.id.board05),findViewById(R.id.board06),findViewById(R.id.board07)


        },{
                findViewById(R.id.board10),findViewById(R.id.board11),
                findViewById(R.id.board12),findViewById(R.id.board13),
                findViewById(R.id.board14),findViewById(R.id.board15),
                findViewById(R.id.board16),findViewById(R.id.board17)

        },{

                 findViewById(R.id.board20)
                ,findViewById(R.id.board21),
                 findViewById(R.id.board22)
                ,findViewById(R.id.board23),
                 findViewById(R.id.board24)
                ,findViewById(R.id.board25),
                 findViewById(R.id.board26)
                ,findViewById(R.id.board27)


        },{
                findViewById(R.id.board30)
                ,findViewById(R.id.board31),
                findViewById(R.id.board32)
                ,findViewById(R.id.board33),
                findViewById(R.id.board34)
                ,findViewById(R.id.board35),
                findViewById(R.id.board36)
                ,findViewById(R.id.board37)


        },{
                findViewById(R.id.board40)
                ,findViewById(R.id.board41),
                findViewById(R.id.board42)
                ,findViewById(R.id.board43),
                findViewById(R.id.board44)
                ,findViewById(R.id.board45),
                findViewById(R.id.board46)
                ,findViewById(R.id.board47)

        },{
                findViewById(R.id.board50)
                ,findViewById(R.id.board51),
                findViewById(R.id.board52)
                ,findViewById(R.id.board53),
                findViewById(R.id.board54)
                ,findViewById(R.id.board55),
                findViewById(R.id.board56)
                ,findViewById(R.id.board57)

        },{
                findViewById(R.id.board60)
                ,findViewById(R.id.board61),
                findViewById(R.id.board62)
                ,findViewById(R.id.board63),
                findViewById(R.id.board64)
                ,findViewById(R.id.board65),
                findViewById(R.id.board66)
                ,findViewById(R.id.board67)

        },{
                findViewById(R.id.board70)
                ,findViewById(R.id.board71),
                findViewById(R.id.board72)
                ,findViewById(R.id.board73),
                findViewById(R.id.board74)
                ,findViewById(R.id.board75),
                findViewById(R.id.board76)
                ,findViewById(R.id.board77)

        }};





    }
}



