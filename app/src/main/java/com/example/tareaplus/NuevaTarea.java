package com.example.tareaplus;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class NuevaTarea extends AppCompatActivity {

    TextView selectedDateTextView;
    EditText tituloEditText, motivoEditText;
    Button guardarButton, horaButton;
    String horaSeleccionada;
    SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);

        // Obtener referencias de las vistas
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        tituloEditText = findViewById(R.id.tituloEditText);
        motivoEditText = findViewById(R.id.motivoEditText);
        guardarButton = findViewById(R.id.guardarButton);
        horaButton = findViewById(R.id.horaButton);

        // Inicializar el helper de base de datos
        dbHelper = new SQLiteOpenHelper(this, "TareaPlus.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                // Crear tabla si no existe
                String createTable = "CREATE TABLE IF NOT EXISTS Tareas (Titulo TEXT, Motivo TEXT, Hora TEXT, Fecha TEXT)";
                db.execSQL(createTable);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS Tareas");
                onCreate(db);
            }
        };

        // Obtener la fecha seleccionada de la actividad anterior
        String selectedDate = getIntent().getStringExtra("selectedDate");
        selectedDateTextView.setText("Fecha seleccionada: " + selectedDate);

        // Configurar el TimePicker al hacer clic en el botón de hora
        horaButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    NuevaTarea.this,
                    (TimePicker view, int hourOfDay, int minuteOfHour) -> {
                        horaSeleccionada = hourOfDay + ":" + String.format("%02d", minuteOfHour);
                        horaButton.setText("Hora seleccionada: " + horaSeleccionada);
                    },
                    hour,
                    minute,
                    true
            );
            timePickerDialog.show();
        });

        // Configurar el botón de guardar
        guardarButton.setOnClickListener(v -> {
            String titulo = tituloEditText.getText().toString();
            String motivo = motivoEditText.getText().toString();
            String fecha = selectedDate; // Obtener la fecha seleccionada

            if (titulo.isEmpty() || motivo.isEmpty() || horaSeleccionada == null) {
                Toast.makeText(NuevaTarea.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Guardar tarea en la base de datos
                guardarTarea(titulo, motivo, horaSeleccionada, fecha);
            }
        });
    }

    private void guardarTarea(String titulo, String motivo, String hora, String fecha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Titulo", titulo);
        values.put("Motivo", motivo);
        values.put("Hora", hora); // Agregar la hora, aunque no está en la tabla
        values.put("Fecha", fecha);

        long newRowId = db.insert("Tareas", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Tarea guardada exitosamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NuevaTarea.this, LeerTarea.class);
            startActivity(intent);
            finish(); // Cerrar la actividad actual
        } else {
            Toast.makeText(this, "Error al guardar la tarea", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
