package com.example.jaimejanersocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btnSignUp;
    private TextView txtIniciaSesion;
    private EditText txtUser, txtPassword, txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = findViewById(R.id.btnSignUp);
        txtIniciaSesion = findViewById(R.id.txtIniciaSesion);
        txtUser= findViewById(R.id.txtUser);
        txtEmail= findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtEmail.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty() || txtUser.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Por favor, rellene todos los campos para poder registrarse.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    IniciarRegistro();
                }
            }
        });

        txtIniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }

    /*
     * Este método inicia sesión creando un nuevo usuario en Firebase Authentication
     * con la dirección de correo electrónico y contraseña proporcionadas en un formulario.
     * Después de la creación del usuario, se almacenan los detalles del usuario en la base de datos Firebase Realtime Database.
     * Se crea un objeto de la clase User con la información del formulario y se envía a la base de datos.
     * Si la operación es exitosa, no se toma ninguna acción adicional.
     * Si hay algún error durante el proceso, se muestra un mensaje de error.
     */

    private void IniciarRegistro() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtEmail.getText().toString(),txtPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(new User(txtEmail.getText().toString(), txtPassword.getText().toString(), txtUser.getText().toString()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                            }
                                            else{
                                                Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            /*Después de iniciar sesión queremos ir de una actividad a otra. O sea, de una pantalla a otra.
                            * Para eso, es la siguiente línea de código, que toma como referencia la actividad que estamos realizando,
                            * y pone en segundo parámetro a la actividad a abrir.*/

                            startActivity(new Intent(MainActivity.this, MediaActiviy.class));

                            Toast.makeText(MainActivity.this, "Iniciaste sesión correctamente.", Toast.LENGTH_SHORT).show();
                            finish(); /*Debido a que salió bien la operación, finalizamos procesos.*/
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}