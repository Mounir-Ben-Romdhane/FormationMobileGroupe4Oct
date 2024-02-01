package com.example.formationgroupe4oct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button goToSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        goToSignIn = findViewById(R.id.goToSignInFromForget);

        goToSignIn.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPasswordActivity.this,SignInActivity.class));
        });

    }
}