package com.example.tareaplus;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarTarea extends AppCompatActivity {

    private EditText ed_titulo, ed_motivo, ed_hora, ed_id;
    private Button b_actualizar, b_eliminar, b_volver;

    private int tareaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        ed_id = findViewById(R.id.id); // Asegúrate de tener un EditText para ID
        ed_titulo = findViewById(R.id.tituloEditText);
        ed_motivo = findViewById(R.id.motivoEditText);
        ed_hora = findViewById(R.id.horaEditText);

        b_actualizar = findViewById(R.id.actualizarButton);
        b_eliminar = findViewById(R.id.eliminarButton);
        b_volver = findViewById(R.id.volverButton); // Asumiendo que tienes un botón de volver

        // Obtener el ID de la tarea desde el Intent
        Intent i = getIntent();
        tareaId = i.getIntExtra("tareaId", -1);
        if (tareaId != -1) {
            cargarTarea(tareaId);
        }

        b_actualizar.setOnClickListener(view -> editar());
        b_eliminar.setOnClickListener(view -> eliminar());
        b_volver.setOnClickListener(view -> volver());
    }

    private void cargarTarea(int tareaId) {
        // Aquí iría el código para cargar la tarea desde la base de datos.
        // Asegúrate de cargar los valores en ed_id, ed_titulo, ed_motivo, ed_hora
    }

    public void eliminar() {
        try {
            String id = String.valueOf(tareaId);
            SQLiteDatabase db = openOrCreateDatabase("DB_TareaPlus.db", Context.MODE_PRIVATE, null);
            String sql = "DELETE FROM Tarea WHERE Id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, id);
            statement.execute();

            Toast.makeText(this, "Tarea eliminada de la base de datos.", Toast.LENGTH_LONG).show();
            volver();
        } catch (Exception ex) {
            Toast.makeText(this, "Error: no se pudieron eliminar los datos.", Toast.LENGTH_LONG).show();
        }
    }

    public void editar() {
        try {
            String titulo = ed_titulo.getText().toString();
            String motivo = ed_motivo.getText().toString();
            String hora = ed_hora.getText().toString();
            String id = String.valueOf(tareaId);

            SQLiteDatabase db = openOrCreateDatabase("DB_TareaPlus.db", Context.MODE_PRIVATE, null);
            String sql = "UPDATE Tarea SET Titulo = ?, Motivo = ?, Hora = ? WHERE Id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, titulo);
            statement.bindString(2, motivo);
            statement.bindString(3, hora);
            statement.bindString(4, id);
            statement.execute();

            Toast.makeText(this, "Tarea actualizada satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();
            volver();
        } catch (Exception ex) {
            Toast.makeText(this, "Error: no se pudieron actualizar los datos.", Toast.LENGTH_LONG).show();
        }
    }

    private void volver() {
        Intent i = new Intent(getApplicationContext(), LeerTarea.class);
        startActivity(i);
        finish();
    }
}
