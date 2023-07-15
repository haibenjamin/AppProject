package com.example.appproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.appproject.Model.User;
import com.example.appproject.R;
import com.example.appproject.Utillities.FullScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    AppCompatImageView singup_IMG_background;
    private EditText singup_ETXT_nameUser,singup_ETXT_password,singup_ETXT_email;
    private MaterialButton singup_BTN_Login ,singup_BTN_back;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        findView();
        initViews();


//        Glide
//                .with(Activity_SingUp.this)
//                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPQtbWlOYDJoXbQIozPZZCDpm7PhEMuu9Osw&usqp=CAU")
//                .into(singup_IMG_background);

    }

    private void findView() {
        singup_IMG_background= findViewById(R.id.singup_IMG_background);
        singup_ETXT_nameUser= findViewById(R.id.singup_ETXT_nameUser);
        singup_ETXT_password= findViewById(R.id.singup_ETXT_password);
        singup_ETXT_email = findViewById(R.id.singup_ETXT_email);
        singup_BTN_Login = findViewById(R.id.singup_BTN_Login);
        singup_BTN_back= findViewById(R.id.singup_BTN_back);

    }

    private void initViews() {
        singup_BTN_Login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp();

            }
        }));
        singup_BTN_back.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        }));
    }
    private void signUp() {

        String name = singup_ETXT_nameUser.getText().toString().trim();
        String password = singup_ETXT_password.getText().toString().trim();
        String email = singup_ETXT_email.getText().toString().trim();


        if (name.isEmpty()) {
            singup_ETXT_nameUser.setError("Please enter your name");
            singup_ETXT_nameUser.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            singup_ETXT_password.setError("Please enter your password");
            singup_ETXT_password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            singup_ETXT_password.setError("The password need to be minimum 6 digits ");
            singup_ETXT_password.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            singup_ETXT_email.setError("Please enter your phone number");
            singup_ETXT_email.requestFocus();
            return;
        }
        if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            singup_ETXT_email.setError("Please provide valid email!");
            singup_ETXT_email.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name,email);


                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wins").setValue("0");
                                                FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("rating").setValue("1000");
                                                FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("loses").setValue("0");
                                                Toast.makeText(SignUpActivity.this,"Signed up successful",Toast.LENGTH_LONG).show();
                                                openLogin();
                                            }else{
                                                Toast.makeText(SignUpActivity.this,"Failed to signed up  Try again!",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        }
                    }
                });



    }
    private void openLogin() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void signOut() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullScreen.hideSystemUI(this);
        }
    }
}