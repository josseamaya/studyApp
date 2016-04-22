package com.example.joseamaya.studyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Maestro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maestro);
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

        TextView tvCodigoMaestro = (TextView)findViewById(R.id.textCodigoMaestro);
        tvCodigoMaestro.setText(this.getIntent().getStringExtra("codigoMaestro"));
        TextView tvNombreMaestro = (TextView)findViewById(R.id.textNombreMaestro);
        tvNombreMaestro.setText(this.getIntent().getStringExtra("nombreMaestro"));
    }
    public void onClickMaestroClases(View v) {
        Intent intent=new Intent(this, clases.class);
        intent.putExtra("codigoMaestro2", this.getIntent().getStringExtra("codigoMaestro"));
        intent.putExtra("nombreMaestro2", this.getIntent().getStringExtra("nombreMaestro"));
        startActivity(intent);
    }
    public void onClickMaestroNotas(View v) {
        Intent intent=new Intent(this, notasMaestro.class);
        intent.putExtra("codigoMaestro3", this.getIntent().getStringExtra("codigoMaestro"));
        intent.putExtra("nombreMaestro3", this.getIntent().getStringExtra("nombreMaestro"));
        startActivity(intent);
    }

}
