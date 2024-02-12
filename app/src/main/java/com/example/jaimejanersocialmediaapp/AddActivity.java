package com.example.jaimejanersocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    private EditText txtTitulo, txtDescripcion;
    private ImageView imgPost;
    private Button btnPublicar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        imgPost = findViewById(R.id.imgPost);
        btnPublicar = findViewById(R.id.btnPublicar);

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("post/"+ UUID.randomUUID().toString())
                        .setValue(new Event(txtTitulo.getText().toString(), txtDescripcion.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    startActivity(new Intent(AddActivity.this, MediaActiviy.class));

                                }
                            }
                        });
            }
        });
    }
}