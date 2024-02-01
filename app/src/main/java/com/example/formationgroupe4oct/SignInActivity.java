package com.example.formationgroupe4oct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    private TextView result;
    private Button btnPlus,btnSous,btnMult,btnDiv;
    private EditText val1,val2;
    private int intRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        result = findViewById(R.id.resultat);
        btnPlus = findViewById(R.id.btnPlus);
        btnSous = findViewById(R.id.btnSous);
        btnMult = findViewById(R.id.btnMult);
        btnDiv = findViewById(R.id.btnDiv);
        val1 = findViewById(R.id.etVal1);
        val2 = findViewById(R.id.etVal2);


        btnPlus.setOnClickListener(v -> {
            String v1 = val1.getText().toString();
            String v2 = val2.getText().toString();
            result.setText(Integer.toString(Integer.parseInt(v1)+Integer.parseInt(v2)));
        });

        btnSous.setOnClickListener(v -> {
            String v1 = val1.getText().toString();
            String v2 = val2.getText().toString();
            result.setText(Integer.toString(Integer.parseInt(v1)-Integer.parseInt(v2)));
        });

        btnMult.setOnClickListener(v -> {
            String v1 = val1.getText().toString();
            String v2 = val2.getText().toString();
            result.setText(Integer.toString(Integer.parseInt(v1)*Integer.parseInt(v2)));
        });

        btnDiv.setOnClickListener(v -> {
            String v1 = val1.getText().toString();
            String v2 = val2.getText().toString();
            result.setText(Integer.toString(Integer.parseInt(v1)/Integer.parseInt(v2)));
        });



    }
}