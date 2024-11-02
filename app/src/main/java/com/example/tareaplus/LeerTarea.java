package com.example.tareaplus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LeerTarea extends AppCompatActivity {
    private ListView lstTareas;
    private ArrayList<String> tareas = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_tarea);

        lstTareas = findViewById(R.id.lstTareas);

        // Inicializar la base de datos
        SQLiteDatabase db = openOrCreateDatabase("TareaPlus.db", Context.MODE_PRIVATE, null);

        // Configurar el adaptador
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas);
        lstTareas.setAdapter(arrayAdapter);

        cargarTareas(db);

        lstTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Editar tarea
                String tareaSeleccionada = tareas.get(position);
                Intent intent = new Intent(LeerTarea.this, EditarTarea.class);
                intent.putExtra("tarea", tareaSeleccionada);
                startActivity(intent);
            }
        });
    }

    private void cargarTareas(SQLiteDatabase db) {
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM Tareas", null);
            tareas.clear(); // Limpiar la lista antes de agregar nuevas tareas

            // Comprobar si el cursor tiene resultados
            if (cursor.moveToFirst()) {
                int tituloIndex = cursor.getColumnIndex("Titulo");
                int motivoIndex = cursor.getColumnIndex("Motivo");
                int horaIndex = cursor.getColumnIndex("Hora");
                int fechaIndex = cursor.getColumnIndex("Fecha");

                do {
                    String tarea = cursor.getString(tituloIndex) + " - " +
                            cursor.getString(motivoIndex) + " - " +
                            cursor.getString(horaIndex) + " - " +
                            cursor.getString(fechaIndex);
                    tareas.add(tarea);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No hay tareas disponibles", Toast.LENGTH_SHORT).show();
            }
            arrayAdapter.notifyDataSetChanged(); // Notificar al adaptador sobre los cambios
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al cargar tareas: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar tareas cada vez que se regresa a esta actividad
        SQLiteDatabase db = openOrCreateDatabase("TareaPlus.db", Context.MODE_PRIVATE, null);
        cargarTareas(db);
    }
}
