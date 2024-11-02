package com.example.tareaplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MenuInteracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_interacion);
    }

    public void agregarTarea(View x){
        Intent intent = new Intent(MenuInteracion.this, AgregarTarea.class);
        startActivity(intent);
    }

    public void LeerTarea(View view) {
        Intent intent = new Intent(MenuInteracion.this, LeerTarea.class); // Asegúrate de que ListaTareas sea la clase de tu actividad
        startActivity(intent);
    }

}