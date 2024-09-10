package com.example.tareaplus;

import android.content.Intent;
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

            if (titulo.isEmpty() || motivo.isEmpty() || horaSeleccionada == null) {
                Toast.makeText(NuevaTarea.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Enviar los datos a la actividad ListaTareas
                Intent intent = new Intent(NuevaTarea.this, ListaTareas.class);
                intent.putExtra("titulo", titulo);
                intent.putExtra("motivo", motivo);
                intent.putExtra("fecha", selectedDate);
                intent.putExtra("hora", horaSeleccionada);
                startActivity(intent);
            }
        });
    }
}
