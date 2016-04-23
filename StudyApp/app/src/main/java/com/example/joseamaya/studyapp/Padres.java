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

        setTitle("Bienvenido Papa");

        TextView tvCodigoMaestro = (TextView)findViewById(R.id.cod_padre);
        tvCodigoMaestro.setText(this.getIntent().getStringExtra("codigopadre"));
        TextView tv2 = (TextView)findViewById(R.id.nom_padre);
        tv2.setText(this.getIntent().getStringExtra("nombrepadre"));
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
