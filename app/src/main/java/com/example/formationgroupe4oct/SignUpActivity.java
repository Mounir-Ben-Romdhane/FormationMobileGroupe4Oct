package com.example.formationgroupe4oct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    
    private TextView goToSignIn;
    private Button btnSignUp;
    private EditText fullName,email,cin,phone,password;
    private String fullNameS,emailS,cinS,phoneS,passwordS;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        goToSignIn = findViewById(R.id.goToSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        fullName = findViewById(R.id.fullNameSignUp);
        email = findViewById(R.id.emailSignUp);
        cin = findViewById(R.id.cinSignUp);
        phone = findViewById(R.id.phoneSignUp);
        password = findViewById(R.id.passwordSignUp);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnSignUp.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait...!");
            progressDialog.show();
            if(validate()){
                firebaseAuth.createUserWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Registration done !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                            progressDialog.dismiss();
                        }else {
                            Toast.makeText(SignUpActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });

        goToSignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            Toast.makeText(this, "go to Sign in activity ", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validate() {
        boolean res = false;
        fullNameS = fullName.getText().toString().trim();
        emailS = email.getText().toString().trim();
        cinS = cin.getText().toString().trim();
        phoneS = phone.getText().toString().trim();
        passwordS = password.getText().toString().trim();
        
        if (fullNameS.isEmpty() || fullNameS.length()<7){
            fullName.setError("Full name is invalid !");
        } else if (!isValidEmail(emailS)) {
            email.setError("Email is invalid!");
        }else if (cinS.length() != 8) {
            cin.setError("CIN is invalid!");
        }else if (phoneS.length() != 8) {
            phone.setError("Phone is invalid!");
        }else if (passwordS.isEmpty() || passwordS.length()<6) { 
            password.setError("Password is invalid!");
        }else {
            res = true;
        }

        return res;
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}