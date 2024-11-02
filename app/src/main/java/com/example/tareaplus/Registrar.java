package com.example.tareaplus;

import android.content.Intent;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Registrar extends AppCompatActivity {

    private EditText correoEditText, usuarioEditText, contrasenaEditText;
    private SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Inicializa los EditTexts y los botones
        correoEditText = findViewById(R.id.correo);
        usuarioEditText = findViewById(R.id.usuario);
        contrasenaEditText = findViewById(R.id.contrasena);
        Button registrarButton = findViewById(R.id.registrar);
        Button verButton = findViewById(R.id.ver); // Nuevo botón para ver registros

        // Inicializa el helper de la base de datos
        dbHelper = new SQLiteOpenHelper(this, "TareaPlus", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String createTable = "CREATE TABLE Registro (Correo TEXT, Usuario TEXT, Contrasena TEXT)";
                db.execSQL(createTable);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS Registro");
                onCreate(db);
            }
        };

        // Configura el listener para el botón "Registrar"
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEnBaseDeDatos();
            }
        });

        // Configura el listener para el botón "Ver"
        verButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrar.this, Leer.class);
                startActivity(intent);
            }
        });
    }

    // Método para guardar datos en la base de datos
    private void guardarEnBaseDeDatos() {
        String correo = correoEditText.getText().toString().trim();
        String usuario = usuarioEditText.getText().toString().trim();
        String contrasena = contrasenaEditText.getText().toString().trim();

        if (!correo.isEmpty() && !usuario.isEmpty() && !contrasena.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Correo", correo);
            values.put("Usuario", usuario);
            values.put("Contrasena", contrasena);

            long nuevoRegistro = db.insert("Registro", null, values);
            if (nuevoRegistro != -1) {
                Toast.makeText(this, "Registro guardado exitosamente", Toast.LENGTH_SHORT).show();
                // Limpiar campos después de guardar
                correoEditText.setText("");
                usuarioEditText.setText("");
                contrasenaEditText.setText("");
            } else {
                Toast.makeText(this, "Error al guardar el registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
