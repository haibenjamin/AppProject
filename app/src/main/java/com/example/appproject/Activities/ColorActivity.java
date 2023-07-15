package com.example.appproject.Activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appproject.R;


public class ColorActivity extends AppCompatActivity {
    Button chooseBtn;
    ImageView imgWhite;
    ImageView imgBlack;
    boolean isWhiteSelected=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        initViews();
imgBlack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        imgWhite.setBackgroundColor(0);
        imgBlack.setBackgroundColor(getResources().getColor(R.color.blue_200));
        isWhiteSelected=false;
    }
});
        imgWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWhite.setBackgroundColor(getResources().getColor(R.color.blue_200));
                imgBlack.setBackgroundColor(0);
                isWhiteSelected=true;
            }
        });
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ColorActivity.this,GameActivity.class);
                intent.putExtra(GameActivity.COLOR,isWhiteSelected);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initViews() {
        chooseBtn=findViewById(R.id.colorChooseBtn);
        imgBlack=findViewById(R.id.color_king_black);
        imgWhite=findViewById(R.id.color_king_white);
    }
}
