package com.example.tareaplus;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Leer extends AppCompatActivity {

    private ListView lstUsuarios;
    private ArrayList<String> listaUsuarios;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer);

        // Inicializa el ListView y la lista de usuarios
        lstUsuarios = findViewById(R.id.lst1);
        listaUsuarios = new ArrayList<>();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaUsuarios);
        lstUsuarios.setAdapter(adaptador);

        // Cargar los registros de usuarios
        mostrarRegistros();
    }

    private void mostrarRegistros() {
        // Abre la base de datos en modo lectura
        SQLiteDatabase db = openOrCreateDatabase("TareaPlus", MODE_PRIVATE, null);

        // Consulta los registros de la tabla Registro
        Cursor cursor = db.rawQuery("SELECT Correo, Usuario, Contrasena FROM Registro", null);

        // Verifica si hay registros y los agrega a la lista
        if (cursor.moveToFirst()) {
            do {
                String correo = cursor.getString(0);
                String usuario = cursor.getString(1);
                String contrasena = cursor.getString(2);
                listaUsuarios.add("Correo: " + correo + "\nUsuario: " + usuario + "\nContraseña: " + contrasena);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No hay registros disponibles", Toast.LENGTH_SHORT).show();
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();

        // Notifica al adaptador que los datos han cambiado
        adaptador.notifyDataSetChanged();
    }
}
