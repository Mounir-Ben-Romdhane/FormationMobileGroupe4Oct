package com.example.formationgroupe4oct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TicketElectrique extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView iconMenu;
    private Button btnGetTicket;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    public static int i=0, iA=0, iB=0, iC=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_electrique);

        drawerLayout = findViewById(R.id.drawer_layout_ticket);
        navigationView = findViewById(R.id.navigation_view_ticket);
        iconMenu = findViewById(R.id.icon_ticket);
        btnGetTicket = findViewById(R.id.btnTicket);
        radioGroup = findViewById(R.id.rgTicket);

        btnGetTicket.setOnClickListener(v -> {
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioId);

            try {
                createPdf(radioButton.getText().toString());
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        });


        navigationDrawer();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.devices:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(TicketElectrique.this, HomeActivity.class));
                    break;
                case R.id.ticketElectrique:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.profile:
                    startActivity(new Intent(TicketElectrique.this, ProfilActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

    }

    private void createPdf(String nameTicket) throws FileNotFoundException {
        i++;
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "TicketElectrique"+i+".pdf");

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        
        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(5,5, 5, 5);

        Drawable d = getDrawable(R.drawable.img);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData) ;
        Image image = new Image(imageData);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER).setHeight(200);

        Paragraph title = new Paragraph("Ticket electrique").setBold().setFontSize(17).setTextAlignment(TextAlignment.CENTER);
        Paragraph welcome = new Paragraph("Welcome").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER);

        Paragraph numT = null, nameT = null;
        if (nameTicket.equals("Choix A")) {
            iA++;
            nameT = new Paragraph("Choix A").setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);
            numT = new Paragraph("A0"+iA).setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);
        } else if (nameTicket.equals("Choix B")) {
            iB++;
            nameT = new Paragraph("Choix B").setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);
            numT = new Paragraph("B0"+iB).setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);
        } else if (nameTicket.equals("Choix C")) {
            iC++;
            nameT = new Paragraph("Choix C").setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);
            numT = new Paragraph("C0"+iC).setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);
        }

        float[] width = {100f, 100f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        table.addCell(new Cell().add(new Paragraph("Date")));
        table.addCell(new Cell().add(new Paragraph(LocalDate.now().format(dateTimeFormatter))));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        table.addCell(new Cell().add(new Paragraph("Time")));
        table.addCell(new Cell().add(new Paragraph(LocalTime.now().format(timeFormatter))));

        document.add(image);
        document.add(title);
        document.add(welcome);
        document.add(nameT);
        document.add(numT);
        document.add(table);
        
        document.close();
        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TicketElectrique.this, HomeActivity.class));
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.ticketElectrique);

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