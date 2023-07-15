package com.example.appproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatsActivity extends AppCompatActivity {
    private String wins;
    private String loses;
    private String rating;
    private String name;
    Button backBtn;
    TextView winsTxt;
    TextView losesTxt;
    TextView ratingTxt;
    TextView nameTxt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        initViews();
        loadInfoFromDataBase();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this,MainActivity.class);
                intent.putExtra(MainActivity.CONNECTED,true);
                startActivity(intent);
                finish();
            }
        });


    }

    private void updateUI() {

            winsTxt.setText(wins+"");
            losesTxt.setText(loses+"");
            nameTxt.setText(name);
            ratingTxt.setText((rating));

    }

    private void initViews() {
        backBtn=findViewById(R.id.stats_BTN_back);
        winsTxt=findViewById(R.id.wins_txt);
        losesTxt=findViewById(R.id.loses_txt);
        ratingTxt=findViewById(R.id.rating_txt);
        nameTxt=findViewById(R.id.stats_name_txt);
    }

    private void loadInfoFromDataBase() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appproject-3c98f-default-rtdb.firebaseio.com/");
       DatabaseReference userRef= FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       userRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot stats : snapshot.getChildren()){
                   if (stats.getKey().equals("wins")){
                       wins=stats.getValue().toString();
                       updateUI();
                   }

               }
               for(DataSnapshot stats : snapshot.getChildren()){
                   if (stats.getKey().equals("loses")){
                       loses=stats.getValue().toString();
                       updateUI();
                   }

               }
               for(DataSnapshot stats : snapshot.getChildren()){
                   if (stats.getKey().equals("rating")){
                       rating=stats.getValue().toString();
                       updateUI();
                   }

               }
               for(DataSnapshot stats : snapshot.getChildren()){
                   if (stats.getKey().equals("name")){
                       name=stats.getValue().toString();
                       updateUI();
                   }

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}

