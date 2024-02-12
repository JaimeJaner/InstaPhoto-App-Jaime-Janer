package com.example.jaimejanersocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MediaActiviy extends AppCompatActivity {

    private TextView txtLogout, txtPost;
    private ImageView imgProfile;
    private RecyclerView postRecView;
    private PostAdapter postAdapter;

    private ArrayList<Event> events = new ArrayList<Event>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_activiy);

        txtLogout = findViewById(R.id.txtLogout);
        txtPost = findViewById(R.id.txtPost);
        imgProfile= findViewById(R.id.imgProfile);
        postRecView = findViewById(R.id.postRecview);


        txtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MediaActiviy.this, AddActivity.class));

            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MediaActiviy.this, Login.class));
                Toast.makeText(MediaActiviy.this, "Has cerrado sesión correctamente.", Toast.LENGTH_SHORT).show();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fetchEvents();
    }

    private void fetchEvents() {

        /*Recuperamos toda la información que hay de cada post de cada persona en Firebase*/
        FirebaseDatabase.getInstance().getReference("post/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*Iteramos la información obtenida y la transformamos en un objeto de la clase Event para poder añadirla a la lista Events que previamente creamos.
                * y así poder utilizarla en el PostAdapter, que necesita información de una lista.*/
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    events.add(snapshot1.getValue(Event.class));
                }
                postAdapter = new PostAdapter(MediaActiviy.this, events);
                postRecView.setLayoutManager(new LinearLayoutManager(MediaActiviy.this));
                postRecView.setAdapter(postAdapter);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                /*Nada acá.*/
            }
        });
    }
}