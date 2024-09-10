package com.example.tareaplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }
    public void presion(View v) {
        Intent intent = new Intent(MainActivity.this, MenuInteracion.class);
        startActivity(intent);
    }

    public void presionRegistar(View b){
        Intent intent = new Intent(MainActivity.this, Registrar.class);
        startActivity(intent);

    }
}
