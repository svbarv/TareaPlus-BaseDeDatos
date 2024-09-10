package com.example.tareaplus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarTarea extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);

        calendarView = findViewById(R.id.calendarView);

        // Configura el listener para capturar la fecha seleccionada
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Crear un mensaje con la fecha seleccionada
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(AgregarTarea.this, "Fecha seleccionada: " + selectedDate, Toast.LENGTH_SHORT).show();

            // Abre la actividad para añadir una tarea
            Intent intent = new Intent(AgregarTarea.this, NuevaTarea.class);
            intent.putExtra("selectedDate", selectedDate);  // Envía la fecha seleccionada
            startActivity(intent);
        });
    }
}
