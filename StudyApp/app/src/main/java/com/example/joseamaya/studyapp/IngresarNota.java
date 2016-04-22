package com.example.joseamaya.studyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class IngresarNota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_nota);
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

        TextView tvCodigoAsinatura = (TextView)findViewById(R.id.textIngresarNotasCodigoAlumno);
        tvCodigoAsinatura.setText(this.getIntent().getStringExtra("codigoAsignatura"));

        TextView tvNombreAsinatura = (TextView)findViewById(R.id.textIngresarNotasNombreAlumno);
        tvNombreAsinatura.setText(this.getIntent().getStringExtra("nombreAsignatura"));

        TextView tvCodigoAlumno = (TextView)findViewById(R.id.textIngresarNotasCodigoAsignatura);
        tvCodigoAlumno.setText(this.getIntent().getStringExtra("codigoAlumno"));

        TextView tvNombreAlumno = (TextView)findViewById(R.id.textIngresarNotasNombreAsignatura);
        tvNombreAlumno.setText(this.getIntent().getStringExtra("nombreAlumno"));


    }

}
