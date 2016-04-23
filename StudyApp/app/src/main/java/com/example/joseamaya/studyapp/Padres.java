package com.example.joseamaya.studyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Padres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_padres);
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

        TextView tvCodigoMaestro = (TextView)findViewById(R.id.cod_padre);
        tvCodigoMaestro.setText(this.getIntent().getStringExtra("codigopadre"));
    }

    public void Seguimiento(View v){
        Intent intento=new Intent(this, Seguimiento.class);
        intento.putExtra("codigopadre2", this.getIntent().getStringExtra("codigopadre"));
        startActivity(intento);
    }

    public void Notas_padre(View v){
        Intent intento=new Intent(this, Notas_padre.class);
        intento.putExtra("codigopadre3", this.getIntent().getStringExtra("codigopadre"));
        startActivity(intento);
    }

}
