package com.example.formationgroupe4oct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button goToSignIn,btnForget;
    private EditText email;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private String emailS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        btnForget = findViewById(R.id.btnForget);
        email = findViewById(R.id.emailForget);
        goToSignIn = findViewById(R.id.goToSignInFromForget);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        goToSignIn.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPasswordActivity.this,SignInActivity.class));
        });

        btnForget.setOnClickListener(v -> {

            emailS = email.getText().toString().trim();
            if (!isValidEmail(emailS)){
                email.setError("Email is invalid!!");
            }else {
                progressDialog.setMessage("Please wait...!");
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(emailS).addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                       finish();
                       startActivity(new Intent(ForgetPasswordActivity.this,SignInActivity.class));
                       progressDialog.dismiss();
                   }else {
                       Toast.makeText(this, "Failed !", Toast.LENGTH_SHORT).show();
                       progressDialog.dismiss();
                   }
                });
            }
        });

    }

    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}