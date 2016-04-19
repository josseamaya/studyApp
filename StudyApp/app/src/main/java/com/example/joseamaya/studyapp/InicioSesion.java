package com.example.joseamaya.studyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Objects;

public class InicioSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
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


    }
    public void onClickInicioAceptar(View v){
        RadioButton radioButtonEstudiante = (RadioButton)findViewById(R.id.inicioRadioEstudiante);
        RadioButton radioButtonMaestro = (RadioButton)findViewById(R.id.inicioRadioMaestro);
        RadioButton radioButtonPadre = (RadioButton)findViewById(R.id.inicioRadioPadre);
        if (radioButtonEstudiante.isChecked()==true){

            obtenerJson("http://studyaplication.esy.es/obtener_alumno.php","alumno");

        }
        if (radioButtonMaestro.isChecked()==true){

            obtenerJson("http://studyaplication.esy.es/obtener_maestro.php","maestro");
        }
        if (radioButtonPadre.isChecked()==true){

            obtenerJson("http://studyaplication.esy.es/obtener_padre.php","padre");
        }

    }
    public void onClickInicioCancelar(View v){
        TextView tvInicioTextoCorreo = (TextView)findViewById(R.id.inicioTextoCorreo);
        tvInicioTextoCorreo.setText("");

        TextView tvInicioTextoContrasena = (TextView)findViewById(R.id.inicioTextoContrasena);
        tvInicioTextoContrasena.setText("");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void obtenerJson(String c, final String i){
        JsonObjectRequest jor= new JsonObjectRequest(
                c,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String estudiantes = null;

                        try {
                            estudiantes = response.getString(i);
                            JSONArray arregloAlunos = new JSONArray(estudiantes);

                            JSONObject estudiante=null;

                            TextView tvCorreo=(TextView)findViewById(R.id.inicioTextoCorreo);

                            for (int i=0;i<=3;i++)
                            {
                                 estudiante = (JSONObject) arregloAlunos.get(i);
                                 String correo = estudiante.getString("email");
                                if (correo.equals(tvCorreo.getText().toString()))
                                {
                                    TextView tvTitulo = (TextView) findViewById(R.id.inicioTextTitulo);
                                    tvTitulo.setText("correo encontrado");
                                 break;
                                }
                            }

                            String contrasena = estudiante.getString("password");

                            TextView tvContrasena= (TextView)findViewById(R.id.inicioTextoContrasena);

                            TextView tvTitulo = (TextView) findViewById(R.id.inicioTextTitulo);


                           if (contrasena.equals(tvContrasena.getText().toString())){

                                tvTitulo.setText("todo bien");
                            }
                                else
                                {
                                    tvTitulo.setText("Correo o contraseña incorrecto");
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            TextView tvTitulo = (TextView) findViewById(R.id.inicioTextTitulo);
                            tvTitulo.setText("error try (for)");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error en el response
                    }
                }
        );
        MySingleton.getInstance(this).addToRequestQueue(jor);
    }

}

