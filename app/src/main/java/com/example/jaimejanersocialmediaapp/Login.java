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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.common.subtyping.qual.Bottom;

public class Login extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button btnLogin;
    private TextView txtCrearCuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.txtLoginEmail);
        loginPassword= findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtCrearCuenta = findViewById(R.id.txtCrearCuenta);


        //Verificamos valores en campos de texto y ejecutamos.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginEmail.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Por favor, rellene todos los datos para iniciar sesión.", Toast.LENGTH_SHORT).show();
                }
                else {
                    iniciarSesion();
                }
            }
        });


        //Si no tenemos cuenta, nos registamos, se abre la ventanaa de regisstro.
        txtCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Volviendo a la App", Toast.LENGTH_SHORT).show();
    }

    private void iniciarSesion() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Ha iniciado sesión con éxito.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MediaActiviy.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Datos de acceso inválidos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}