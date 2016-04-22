package com.example.joseamaya.studyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Estudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView tvCodigoMaestro = (TextView)findViewById(R.id.cod_alumno);
        tvCodigoMaestro.setText(this.getIntent().getStringExtra("codigoalumno"));
    }

    public void Matricula(View v){
        Intent intento=new Intent(this, Matricularse.class);
        intento.putExtra("codigoalumno2", this.getIntent().getStringExtra("codigoalumno"));
        startActivity(intento);
    }

    public void Ver_notas(View v){
        Intent intento=new Intent(this, Mostrar_notas.class);
        intento.putExtra("codigoalumno2", this.getIntent().getStringExtra("codigoalumno"));
        startActivity(intento);
    }
}
