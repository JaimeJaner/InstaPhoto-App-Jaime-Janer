package com.example.jaimejanersocialmediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class imgPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_perfil);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Volviendo a la App", Toast.LENGTH_SHORT).show();
    }
}