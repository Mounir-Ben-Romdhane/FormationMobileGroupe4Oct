package com.example.formationgroupe4oct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText fullName, email, cin, phone;
    private Button btnEdit, btnLogOut;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private DatabaseReference reference;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView iconMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        fullName = findViewById(R.id.fullNameProfil);
        email = findViewById(R.id.emailProfil);
        cin = findViewById(R.id.cinProfil);
        phone = findViewById(R.id.phoneProfil);
        btnEdit = findViewById(R.id.btnEditProfil);
        btnLogOut = findViewById(R.id.btnLogOut);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();
        reference = firebaseDatabase.getReference().child("Users").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullNameS = snapshot.child("fullName").getValue().toString();
                String emailS = snapshot.child("email").getValue().toString();
                String cinS = snapshot.child("cin").getValue().toString();
                String phoneS = snapshot.child("phone").getValue().toString();
                fullName.setText(fullNameS);
                email.setText(emailS);
                cin.setText(cinS);
                phone.setText(phoneS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilActivity.this, "Error !", Toast.LENGTH_SHORT).show();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout_profile);
        navigationView = findViewById(R.id.navigation_view_profile);
        iconMenu = findViewById(R.id.icon_profile);

        navigationDrawer();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.devices:
                    startActivity(new Intent(ProfilActivity.this, HomeActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.ticketElectrique:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(ProfilActivity.this, TicketElectrique.class));
                    break;
                case R.id.profile:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

        btnLogOut.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("remember", false);
            editor.apply();
            firebaseAuth.signOut();
            startActivity(new Intent(ProfilActivity.this, SignInActivity.class));
            Toast.makeText(this, "Log out successfully !!", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnEdit.setOnClickListener(v -> {
            String editFullName = fullName.getText().toString().trim();
            String editCin = cin.getText().toString().trim();
            String editPhone = phone.getText().toString().trim();
            reference.child("fullName").setValue(editFullName);
            reference.child("cin").setValue(editCin);
            reference.child("phone").setValue(editPhone);
            Toast.makeText(this, "Your data has been changed successfully !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfilActivity.this, ProfilActivity.class));
        });
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.profile);

        iconMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }else  drawerLayout.openDrawer(GravityCompat.START);
        });
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorApp));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

}