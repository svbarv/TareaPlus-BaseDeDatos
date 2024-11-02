package com.example.tareaplus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsuario, editTextPassword;
    private Button buttonIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsuario = findViewById(R.id.editTextText);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        buttonIngresar = findViewById(R.id.button);
        findViewById(R.id.txtRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irARegistro();
            }
        });

        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario();
            }
        });
    }

    private void validarUsuario() {
        String usuario = editTextUsuario.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Abre la base de datos en modo lectura
        SQLiteDatabase db = openOrCreateDatabase("TareaPlus", Context.MODE_PRIVATE, null);

        // Consulta los registros de la tabla Registro
        Cursor cursor = db.rawQuery("SELECT * FROM Registro WHERE Usuario = ? AND Contrasena = ?", new String[]{usuario, password});

        if (cursor.moveToFirst()) {
            // Usuario válido, redirige a MenuInteraccion
            Intent intent = new Intent(MainActivity.this, MenuInteracion.class);
            startActivity(intent);
            finish(); // Finaliza MainActivity para evitar que el usuario regrese a ella con el botón atrás
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();
    }

    private void irARegistro() {
        Intent intent = new Intent(MainActivity.this, Registrar.class);
        startActivity(intent);
    }
}
