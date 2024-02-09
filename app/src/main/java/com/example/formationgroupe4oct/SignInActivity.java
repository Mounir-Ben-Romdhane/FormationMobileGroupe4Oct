package com.example.formationgroupe4oct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private TextView goToSignUp,goToForgetPass;
    private EditText email,password;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String emailS,passwordS;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

      goToSignUp = findViewById(R.id.goToSignUp);
      goToForgetPass = findViewById(R.id.goToForgetPassword);
      email = findViewById(R.id.emailSignIn);
      password = findViewById(R.id.passwordSignIn);
      btnSignIn = findViewById(R.id.btnSignIn);

      firebaseAuth = FirebaseAuth.getInstance();
      progressDialog = new ProgressDialog(this);

      goToSignUp.setOnClickListener(v -> {
          startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
      });

      goToForgetPass.setOnClickListener(v -> {
          startActivity(new Intent(SignInActivity.this,ForgetPasswordActivity.class));
      });

        btnSignIn.setOnClickListener(v -> {
            emailS = email.getText().toString().trim();
            passwordS = password.getText().toString().trim();
            if (!isValidEmail(emailS)){
                email.setError("Email is invalid!!");
            } else if (passwordS.length() < 4) {
                password.setError("Password is invalid");
            }else {
                login(emailS,passwordS);
            }
        });

    }

    private void login(String emailS, String passwordS) {
        progressDialog.setMessage("Please wait...!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailS,passwordS).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               checkEmailVerification();
           } else {
               Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show();
               progressDialog.dismiss();
           }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();
        if (loggedUser != null) {
            if (loggedUser.isEmailVerified()){
                finish();
                startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                progressDialog.dismiss();
            }else {
                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                progressDialog.dismiss();
            }
        }
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}