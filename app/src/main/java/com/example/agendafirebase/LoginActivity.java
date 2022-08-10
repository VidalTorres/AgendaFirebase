package com.example.agendafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {

    Button btnIngresar, btnCerrar;
    EditText txtCorreo, txtContraseña;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Auth = FirebaseAuth.getInstance();

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtContraseña = (EditText) findViewById(R.id.txtCorreo);
        btnIngresar = (Button) findViewById(R.id.btnIngresa);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtCorreo.getText().toString().trim();
                String contraseña = txtContraseña.getText().toString().trim();

                if (correo.isEmpty() && contraseña.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                }
                else{
                    loginUser(correo, contraseña);
                }
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder confirmar = new AlertDialog.Builder(LoginActivity.this);
                confirmar.setTitle("¿Borrar contacto?");
                confirmar.setMessage("Se descartara toda la informacion. ");
                confirmar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                confirmar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nada
                    }
                }); confirmar.show();
            }
        });
    }

    private void loginUser(String correo, String contraseña) {
        Auth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Error de la autentificación", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error al inciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}