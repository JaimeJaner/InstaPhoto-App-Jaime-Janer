package com.example.jaimejanersocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    private EditText txtTitulo, txtDescripcion;
    private TextView txtDuenio;
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
        txtDuenio = findViewById(R.id.txtDuenio);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("username").getValue(String.class);
                        txtDuenio.setText("Usuario: "+ username);
                    } else {
                        // El nodo del usuario no existe en la base de datos
                        txtDuenio.setText("Nombre de usuario no encontrado");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                // Obtener el ID único del usuario actualmente autenticado
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();*/

                FirebaseDatabase.getInstance().getReference("post/"+ UUID.randomUUID().toString())
                        .setValue(new Event(txtTitulo.getText().toString(), txtDescripcion.getText().toString(), txtDuenio.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    startActivity(new Intent(AddActivity.this, MediaActiviy.class));
                                }
                                else {
                                    Toast.makeText(AddActivity.this, "El post no se pudo publicar.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}