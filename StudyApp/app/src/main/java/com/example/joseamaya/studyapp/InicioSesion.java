package com.example.joseamaya.studyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

        setTitle("Iniciar Sesión");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);




    }
    public void onClickInicioAceptar(View v) {
        TextView tv = (TextView) findViewById(R.id.inicioTextoCorreo);
        TextView tv2 = (TextView) findViewById(R.id.inicioTextoContrasena);
        if (tv.getText() == "" || tv2.getText() == "") {

            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "Ingrese información", duration);
            toast.show();

        } else {

            RadioButton radioButtonEstudiante = (RadioButton) findViewById(R.id.inicioRadioEstudiante);
            RadioButton radioButtonMaestro = (RadioButton) findViewById(R.id.inicioRadioMaestro);
            RadioButton radioButtonPadre = (RadioButton) findViewById(R.id.inicioRadioPadre);
            if (radioButtonEstudiante.isChecked() == true) {

                obtenerJson("http://studyaplication.esy.es/obtener_alumno.php", "alumno");

            }
            if (radioButtonMaestro.isChecked() == true) {

                obtenerJson("http://studyaplication.esy.es/obtener_maestro.php", "maestro");
            }
            if (radioButtonPadre.isChecked() == true) {

                obtenerJson("http://studyaplication.esy.es/obtener_padre.php", "padre");
            }
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
        final Context context=this;
        final String individuo=i;
        JsonObjectRequest jor= new JsonObjectRequest(
                c,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String personas = null;

                        try {
                            personas = response.getString(i);
                            JSONArray arregloAlunos = new JSONArray(personas);

                            JSONObject persona=null;

                            TextView tvCorreo=(TextView)findViewById(R.id.inicioTextoCorreo);

                            String contrasena=null;
                            boolean verificador=false;

                            for (int i=0;i<=arregloAlunos.length()-1;i++)
                            {
                                persona = (JSONObject) arregloAlunos.get(i);
                                String correo = persona.getString("email");


                                if (correo.equals(tvCorreo.getText().toString()))
                                {
                                    contrasena = persona.getString("password");

                                    verificador=true;

                                    break;
                                }
                                else
                                {
                                    verificador=false;
                                }

                            }

                            if (verificador==false){
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, "Usuario no registrado", duration);
                                toast.show();
                            }
                            else {

                                TextView tvContrasena = (TextView) findViewById(R.id.inicioTextoContrasena);

                                if (contrasena.equals(tvContrasena.getText().toString())) {

                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, "Logging Correcto", duration);
                                    toast.show();

                                    if (i=="alumno"){
                                        Intent intent=new Intent(context, Estudiante.class);
                                        intent.putExtra("codigoalumno", persona.getString("cod_alumno"));
                                        startActivity(intent);
                                    }
                                    if (i=="maestro"){
                                        Intent intent=new Intent(context, Maestro.class);
                                        intent.putExtra("codigoMaestro", persona.getString("cod_maestro"));
                                        intent.putExtra("nombreMaestro", persona.getString("nombre"));
                                        startActivity(intent);

                                    }
                                    if (i=="padre"){
                                        Intent intent=new Intent(context, Padres.class);
                                        intent.putExtra("codigopadre", persona.getString("cod_padre"));
                                        startActivity(intent);

                                    }
                                } else {
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, "Contraseña Incorrecta", duration);
                                    toast.show();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

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

