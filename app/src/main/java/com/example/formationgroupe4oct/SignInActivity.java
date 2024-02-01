package com.example.formationgroupe4oct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    private TextView goToSignUp,goToForgetPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

      goToSignUp = findViewById(R.id.goToSignUp);
      goToForgetPass = findViewById(R.id.goToForgetPassword);

      goToSignUp.setOnClickListener(v -> {
          startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
      });

      goToForgetPass.setOnClickListener(v -> {
          startActivity(new Intent(SignInActivity.this,ForgetPasswordActivity.class));
      });



    }
}