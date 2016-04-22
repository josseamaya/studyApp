package com.example.joseamaya.studyapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class clases extends AppCompatActivity {
    String IP = "http://studyaplication.esy.es";
    String INSERT_C = IP + "/insertar_asignatura.php";
    ObtenerWebService hiloconexion;
    Context context=null;

    String codigoMaestro=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView tvCodigoMaestroClase = (TextView) findViewById(R.id.textCodigoMaestroClase);
        tvCodigoMaestroClase.setText(this.getIntent().getStringExtra("codigoMaestro2"));
        TextView tvNombreMaestroClase = (TextView) findViewById(R.id.textNombreMaestroClase);
        tvNombreMaestroClase.setText(this.getIntent().getStringExtra("nombreMaestro2"));

    }
    public void onClickClasesAgregar(View v){
        EditText cajaTextoClaseCodigo =(EditText)findViewById(R.id.cajaTextoClasesCodigo);
        EditText cajaTextoClaseNombre =(EditText)findViewById(R.id.cajaTextoClasesNombre);
        EditText cajaTextoClaseHoario =(EditText)findViewById(R.id.cajaTextoClasesHoario);

        hiloconexion.execute(INSERT_C, "3", cajaTextoClaseCodigo.getText().toString(), cajaTextoClaseNombre.getText().toString(), cajaTextoClaseHoario.getText().toString(),
                this.getIntent().getStringExtra("codigoMaestro2"));

    }
    public void onClickClasesCancelar(View v){
        /*EditText textoClase =(EditText)findViewById(R.id.cajaTextoClases);
        textoClase.setText("");*/

    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";

            if (params[1] == "3") {    // Ingresar maestros

                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();

                    jsonParam.put("cod_asignatura", params[2]);
                    jsonParam.put("nombre", params[3]);
                    jsonParam.put("horario", params[4]);
                    jsonParam.put("cod_maestro", params[5]);

                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                            //response+=line;
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                        if (resultJSON == "1") {      // hay un Maestro que mostrar
                            devuelve = "Clase agregada";

                        } else if (resultJSON == "2") {
                            devuelve = "La clase no pude agregarse";
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;


            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, s, duration);
            toast.show();
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}

