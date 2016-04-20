package com.example.joseamaya.studyapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Matricularse extends AppCompatActivity implements View.OnClickListener{
    EditText buscarid;
    EditText nombre;
    Button buscar;
    ExpandableListView asignatura;
    Button matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricularse);
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

        //Enlace con los elementos
        buscarid = (EditText) findViewById(R.id.editbuscar);
        nombre = (EditText) findViewById(R.id.editnombre);
        buscar=(Button) findViewById(R.id.buttonbus);
        asignatura=(ExpandableListView)findViewById(R.id.asignatura);
        matricula=(Button)findViewById(R.id.buttonmatric);

        //Listener de los botones
        buscarid.setOnClickListener(this);
        nombre.setOnClickListener(this);
        buscar.setOnClickListener(this);
        asignatura.setOnClickListener(this);
        matricula.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
