package com.example.jaimejanersocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MediaActiviy extends AppCompatActivity {

    private TextView txtLogout, txtPost;
    private ImageView imgProfile;
    private RecyclerView postRecView;
    private PostAdapter postAdapter;

    //Array para almacenar los datos de los posts. (Eventos)
    private ArrayList<Event> events = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_activiy);

        txtLogout = findViewById(R.id.txtLogout);
        txtPost = findViewById(R.id.txtPost);
        imgProfile= findViewById(R.id.imgProfile);
        postRecView = findViewById(R.id.postRecview);


        //Abre el espacio para crear un nuevo post.
        txtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MediaActiviy.this, AddActivity.class));

            }
        });

        //Cierra sesión.
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
                //Vamos a implementar la lógica para ver el perfil del usuario.
                startActivity(new Intent(MediaActiviy.this, imgPerfil.class));
            }
        });

        //En el OnCreate se ejecuta constantemente la actualización de los nuevos datos.
        fetchUserEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Volviendo a la App", Toast.LENGTH_SHORT).show();
    }

    private void fetchUserEvents() {
        // Verificar si hay un usuario autenticado
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "No hay usuario autenticado", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
            finish(); // Finalizar la actividad actual para evitar que el usuario vuelva a la pantalla anterior
            return;
        }

/*        // Obtenemos el UID del usuario.
        String userId = currentUser.getUid();*/

        // Obtenemos la dirección de los posts que han hecho todos los usuarios.
        DatabaseReference userPostsRef = FirebaseDatabase.getInstance().getReference("post/");

        // Escuchamos los cambios en los datos de cada usuario.
        userPostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Verificar si hay datos disponibles en el snapshot
                if (!snapshot.exists()) {
                    Toast.makeText(MediaActiviy.this, "No hay posts en estos momentos.", Toast.LENGTH_SHORT).show();
                    return; //Salimos del método para no procesar lo demás.
                }

                // Limpiar la lista de eventos antes de agregar nuevos eventos.
                events.clear();

                // Iterar sobre los posts
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Convertir cada post en un objeto Event y agregarlo a la lista
                    events.add(postSnapshot.getValue(Event.class));
                }

                //Creamos un post adapter.
                postAdapter = new PostAdapter(MediaActiviy.this, events);
                postRecView.setLayoutManager(new LinearLayoutManager(MediaActiviy.this));
                postRecView.setAdapter(postAdapter);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar el error, si es necesario
                Toast.makeText(MediaActiviy.this, "Error al obtener los post" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}