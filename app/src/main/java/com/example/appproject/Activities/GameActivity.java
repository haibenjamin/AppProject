package com.example.appproject.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appproject.Logic.GameManager;
import com.example.appproject.Model.Bishop;
import com.example.appproject.Model.Blank;
import com.example.appproject.Model.ChessBot;
import com.example.appproject.Model.DrawBoard;
import com.example.appproject.Model.King;
import com.example.appproject.Model.Knight;
import com.example.appproject.Model.Move;
import com.example.appproject.Model.Pawn;
import com.example.appproject.Model.Piece;
import com.example.appproject.Model.Point;
import com.example.appproject.Model.Queen;
import com.example.appproject.Model.Rook;
import com.example.appproject.R;
import com.example.appproject.Utillities.SignalGenerator;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class GameActivity extends Activity {
    public static final String PLAYER_NAME="KEY_PLAYER";
    public static final String PLAYER1 = "KEY_PLAYER1";
    public static final String PLAYER2 = "KEY_PLAYER2";
    public static final String START = "KEY_START";
    public static final String CONID = "KEY_CONID";
    public static final String COUNT="KEY_COUNT";
    public static final String COLOR="KEY_COLOR";

    public int turnColor;
    private boolean flagLoses=false;
    private boolean flagRating=false;
    String connectionId="";
    String playerName;
    String opponentName;
    String rating;
    Intent prevIntent;

    TextView player1Name;
    TextView player2Name;
    TextView player1Rating;
    ChessBot bot;

    final int ROWS = 8;
    ShapeableImageView selected = null;
    final int COLS = 8;
    int color;
    int srcI, srcJ, dstI, dstJ;
    ShapeableImageView[][] board;
     GameManager.color myColor;
    public static boolean myTurn=false;
    Button resignBtn;
    GameManager gameManager = new GameManager();
    SignalGenerator signalGenerator;
    boolean start = false;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appproject-3c98f-default-rtdb.firebaseio.com/");
    DatabaseReference userRef= FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        prevIntent = getIntent();
        initView();
        resetBackgrounds(); //resets tiles colors on board
        resetCastlingRight();
        drawBoard();
        setName();
        determineFirstPlayer();
        waitForGameToStart();
        movesListener();


        resignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignalGenerator.getInstance().toast("you lost",Toast.LENGTH_SHORT);
                gameManager.gameOver();
                gameOver();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(MainActivity.CONID,connectionId);
                intent.putExtra(MainActivity.CONNECTED,true);
                myTurn=false;
                finish();
                startActivity(intent);
            }
        });
    }

    private void resetCastlingRight() {
    }

    public void gameOver(){
        flagLoses=true;
        flagRating=true;
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int loses=0;
                        int rating=0;

                        for (DataSnapshot value : snapshot.getChildren()){
                            if (value.getKey().equals("loses")&&flagLoses) {
                                loses = Integer.parseInt(value.getValue().toString());
                                value.getRef().setValue(loses+1+"");
                                flagLoses=false;
                            }

                        }
                        for (DataSnapshot value : snapshot.getChildren()){
                            if (value.getKey().equals("rating")&&flagRating) {
                                rating = Integer.parseInt(value.getValue().toString());
                                value.getRef().setValue(rating-3+"");
                                flagRating=false;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
    private void setName() {
      //  playerName=prevIntent.getStringExtra(PLAYER_NAME);
      //  player1Name.setText(playerName);
        getNameFromDataBase();
        getRatingFromDataBase();




    }

    private void getNameFromDataBase() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot stats : snapshot.getChildren()){
                    if (stats.getKey().equals("rating")){
                        rating=stats.getValue().toString();
                        player1Rating.setText(rating);
                    }

                }
                for(DataSnapshot stats : snapshot.getChildren()){
                    if (stats.getKey().equals("name")){
                        playerName=stats.getValue().toString();
                        player1Name.setText(playerName);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getRatingFromDataBase() {
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("rating").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot stats : snapshot.getChildren()){
                            if (stats.getKey().equals("rating")){
                                rating=stats.getValue().toString();
                                player1Rating.setText(rating);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void determineFirstPlayer() {
        if (prevIntent.getBooleanExtra("KEY_COLOR", false)) {
            myColor = GameManager.color.WHITE;
            myTurn=true;


        } else {
            myColor = GameManager.color.BLACK;
            gameManager.flipBoard();
            drawBoard();

        }
        gameManager.getBoard();
    }


    private void waitForGameToStart() {
        bot = new ChessBot();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
             /*   int playersCount = prevIntent.getIntExtra("KEY_COUNT", 0);
                if (playersCount == 2) {
                    if (prevIntent.getStringExtra("KEY_PLAYER2") != null) {
                        player2Name.setText(prevIntent.getStringExtra("KEY_PLAYER2"));

                    }
                    if (prevIntent.getStringExtra("KEY_PLAYER1") != null) {
                        player1Name.setText(prevIntent.getStringExtra("KEY_PLAYER1"));

                    }
                }*/
                if (!myTurn)
                    firstMove();
                drawBoard();
            }

        }, 1000);




    }

    private void changeTurn() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myTurn=true;


            }
        },10);

    }

    private void firstMove() {
        ArrayList moves=bot.getMoves().poll();
        int srcI,srcJ,dstI,dstJ;
        if (moves!=null){
        srcI=(int)moves.get(0);
        srcJ=(int)moves.get(1);
        dstI=(int)moves.get(2);
        dstJ=(int)moves.get(3);
        if (!(gameManager.getBoard()[srcI][srcJ] instanceof Blank)) {
            makeMove(srcI, srcJ, dstI, dstJ, player2Name.getText().toString());
            changeTurn();
        }


        }
        else{
            makeRandomMove();
            changeTurn();
        }

    }

    private void makeRandomMove() {
        ArrayList<Piece> myPieces=null;
        Set<Point> legalMoves=null;
        Piece chosenPiece=null;
        Point chosenMove=null;

        if (myColor==GameManager.color.BLACK){
            myPieces= gameManager.getWhitePieces();
        }
        else{
            myPieces=gameManager.getBlackPieces();
        }

        if (myPieces!=null){
        Random rnd = new Random();
            do{
                int randomIndex = rnd.nextInt(myPieces.size());
                chosenPiece = myPieces.get(randomIndex);
                legalMoves = gameManager.getLegalMoves(chosenPiece.getCurrPosition());
            }while(legalMoves.size()==0);
            chosenMove = (Point) legalMoves.toArray()[0];
        makeMove( chosenPiece.getCurrPosition().getX(),chosenPiece.getCurrPosition().getY()
                ,chosenMove.getX(),chosenMove.getY(),player2Name.getText().toString());

        }

    }

    private void makeMove(int srcI, int srcJ, int dstI, int dstJ,String name) {
        gameManager.move(srcI,srcJ,dstI,dstJ);
        updateServerMove(srcI,srcJ,dstI,dstJ,name);
        updateIsMoved(srcI,srcJ);
        gameManager.setPiecePosition();
        myTurn=false;
        resetBackgrounds();
        drawBoard();

    }

    private void updateIsMoved(int x, int y) {
        Piece piece = gameManager.getPiece(x,y);
        if (piece instanceof Rook){
            ((Rook) piece).setMoved(true);
        }
        if (piece instanceof King){
            ((King) piece).setMoved(true);
        }
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
                            if (selected==null){//no piece is selected on board
                                selected=board[finalI][finalJ];
                                selected.setBackgroundColor(R.color.green_100);
                                srcI=finalI;
                                srcJ=finalJ;
                                paintLegalMoves( new Point(srcI,srcJ));

                            }
                            else{ //piece is allready selected
                                if (selected!=board[finalI][finalJ]) { // if different tile is selected
                                    if (legalMove(srcI, srcJ, finalI, finalJ)) { // is it legal move ?
                                        if (!(gameManager.getPiece(srcI,srcJ) instanceof King)) {
                                            makeMove(srcI, srcJ, finalI, finalJ, playerName);
                                            firstMove();
                                            selected = null;

                                        }else{ //special case for king castling
                                            if (srcJ==finalJ-2){
                                                castlingShort(srcI,srcJ);
                                                selected=null;
                                                firstMove();
                                            }
                                            else{
                                                makeMove(srcI,srcJ,finalI,finalJ,playerName);
                                                firstMove();
                                                selected = null;
                                            }
                                        }
                                    } else if (gameManager.getBoard()[finalI][finalJ].getColor() == myColor.ordinal()) {
                                        unPaintLegalMoves(new Point(srcI, srcJ));
                                        selected = board[finalI][finalJ];
                                        selected.setBackgroundColor(R.color.green_100);
                                        srcI = finalI;
                                        srcJ = finalJ;
                                        resetBackgrounds();
                                        paintLegalMoves(new Point(srcI, srcJ));
                                    } else if (legalMove(srcI, srcJ, finalI, finalJ)) {
                                        makeMove(srcI, srcJ, finalI, finalJ, playerName);
                                        firstMove();
                                        selected = null;
                                    }
                                }
                                else{
                                    resetBackgrounds();
                                    unPaintLegalMoves(new Point(srcI,srcJ));
                                    selected=null;

                                }
                            }
                        }
                    });

                }

            }




            }

    private void castlingShort(int x,int y) {
        King piece = (King)gameManager.getPiece(x,y);
        makeMove(x,y,x,y+2,playerName);
        makeMove(x,y+3,x,y+1,playerName);
        piece.setMoved(true);
    }

    private boolean legalMove(int srcI, int srcJ, int dstI, int dstJ) {
        Point target = new Point(dstI, dstJ);
        Point src = new Point(srcI, srcJ);
        Set<Point> legalMoves = gameManager.getLegalMoves(src);
        for (Point point : legalMoves) {
            if (point.getX() == target.getX()) {
                if (point.getY() == target.getY()) {
                    return true;
                }
            }

        }
        return false;
    }


    private void updateServerMove(int srcI, int srcJ, int dstI, int dstJ,String name) {
      //  Move move = new Move(srcI,srcJ,dstI,dstJ);
       DatabaseReference movesRef= databaseReference.child("connection").child("moves").push();
                movesRef.child(name).setValue(new Move(srcI,srcJ,dstI,dstJ).getList());
        //movesRef.child(playerName).setValue(srcJ+"");
        //movesRef.child(playerName).setValue(dstI+"");
        //movesRef.child(playerName).setValue(dstJ+"");
    }

    private void paintLegalMoves( Point p) {

        //Set<Point> points = piece.getLegalMoves();
        for (Point point: gameManager.getLegalMoves(p)
             ) {
           // board[point.getX()][point.getY()].setBackgroundColor(Color.GREEN);
            if (gameManager.getBoard()[point.getX()][point.getY()] instanceof Blank)
                board[point.getX()][point.getY()].setImageResource(R.drawable.circle);
            else{
                board[point.getX()][point.getY()].setBackgroundColor(getResources().getColor(R.color.teal_200));
            }
        }

    }

    private void shrinkImage(ShapeableImageView img) {
        img.setAlpha(100);

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);

// Set the desired width and height for the scaled image
        int desiredWidth = 200;
        int desiredHeight = 200;

// Create a scaled version of the original image
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);

// Set the scaled bitmap to an ImageView
        img.setImageBitmap(scaledBitmap);

    }

    private void unPaintLegalMoves( Point p) {

        //Set<Point> points = piece.getLegalMoves();
        for (Point point: gameManager.getLegalMoves(p)
        ) {
            // board[point.getX()][point.getY()].setBackgroundColor(Color.GREEN);
          //
            if (gameManager.getBoard()[point.getX()][point.getY()] instanceof Blank)
                board[point.getX()][point.getY()].setImageResource(0);
            else
                resetBackgrounds();

        }

    }


    private void initView() {
        turnColor= getResources().getColor(R.color.teal_200);
        player1Name=findViewById(R.id.player1_id);
        player2Name=findViewById(R.id.player2_id);
        player1Rating = findViewById(R.id.player1_rating);
        resignBtn=findViewById(R.id.resign_btn);
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



