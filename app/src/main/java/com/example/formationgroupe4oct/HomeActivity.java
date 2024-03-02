package com.example.formationgroupe4oct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView iconMenu;

    private EditText name, value;
    private Button btnAddDevice;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    private ListView listDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout_home);
        navigationView = findViewById(R.id.navigation_view_home);
        iconMenu = findViewById(R.id.icon_home);
        name = findViewById(R.id.deviceName);
        value = findViewById(R.id.deviceValue);
        btnAddDevice = findViewById(R.id.btnAddDevice);
        listDevices = findViewById(R.id.listDevices);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        btnAddDevice.setOnClickListener(v -> {
            if (name.getText().toString().trim().isEmpty()) {
                name.setError("Device name should not be empty !");
            } else if (value.getText().toString().trim().isEmpty()) {
                value.setError("Device value should not be empty !");
            }else {
                addDevice(name.getText().toString().trim(), value.getText().toString().trim());
            }
        });

        navigationDrawer();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.devices:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.ticketElectrique:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(HomeActivity.this, TicketElectrique.class));
                    break;
                case R.id.profile:
                    startActivity(new Intent(HomeActivity.this, ProfilActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

        ArrayList<String> devicesArrayList = new ArrayList<>();
        ArrayAdapter<String> devicesAdapter = new ArrayAdapter(this,R.layout.list_item ,devicesArrayList);
        listDevices.setAdapter(devicesAdapter);


        DatabaseReference devicesReference = firebaseDatabase.getReference().child("Devices");

        devicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                devicesArrayList.clear();
                for (DataSnapshot deviceSnapShot: snapshot.getChildren()) {
                    String name = deviceSnapShot.child("name").getValue().toString();
                    String value = deviceSnapShot.child("value").getValue().toString();

                    devicesArrayList.add(name + " : "+value);
                }
                devicesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDevice(String nameDevice, String valueDevice) {
        HashMap<String, String> deviceMap = new HashMap<>();
        deviceMap.put("name", nameDevice);
        deviceMap.put("value", valueDevice);
        reference.child("Devices").push().setValue(deviceMap);

        name.setText("");
        value.setText("");
        name.clearFocus();
        value.clearFocus();
        Toast.makeText(this, "New device added successfully !", Toast.LENGTH_SHORT).show();
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.devices);

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
    }
}