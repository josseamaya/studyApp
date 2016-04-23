package com.example.joseamaya.studyapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
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
import java.util.ArrayList;

public class IngresarNota extends AppCompatActivity {
    String IP = "http://studyaplication.esy.es";
    String INSERT_N = IP + "/insertar_nota.php";
    ObtenerWebService hiloconexion;
    Context context=null;

    String codigoAlumno, codigoAsignatura=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_nota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;

        setTitle("Ingresar Notas");

        codigoAlumno=this.getIntent().getStringExtra("codigoAlumno");
        codigoAsignatura=this.getIntent().getStringExtra("codigoAsignatura");

        TextView tvCodigoAluno = (TextView)findViewById(R.id.textIngresarNotasCodigoAlumno);
        tvCodigoAluno.setText(this.getIntent().getStringExtra("codigoAlumno"));

        TextView tvNombreAlumno = (TextView)findViewById(R.id.textIngresarNotasNombreAlumno);
        tvNombreAlumno.setText(this.getIntent().getStringExtra("nombreAlumno"));

        TextView tvCodigoAsignatura = (TextView)findViewById(R.id.textIngresarNotasCodigoAsignatura);
        tvCodigoAsignatura.setText(this.getIntent().getStringExtra("codigoAsignatura"));

        TextView tvNombreAsignatura = (TextView)findViewById(R.id.textIngresarNotasNombreAsignatura);
        tvNombreAsignatura.setText(this.getIntent().getStringExtra("nombreAsignatura"));

        obtenerNombreAsignatura("http://studyaplication.esy.es/obtener_asignatura.php");


    }
    public void onClickIngresarNotaAgregar(View v) {

        hiloconexion = new ObtenerWebService();
        EditText cajaTextoPeriodo =(EditText)findViewById(R.id.cajaTextoIngresarNotaPeriodo);
        EditText cajaTextoExamen =(EditText)findViewById(R.id.cajaTextoIngresarNotaExamen);
        EditText cajaTextoAcumulativo =(EditText)findViewById(R.id.cajaTextoIngresarNotaAcumulativo);

        hiloconexion.execute(INSERT_N, "1", cajaTextoPeriodo.getText().toString(), cajaTextoExamen.getText().toString(), cajaTextoAcumulativo.getText().toString(),
                codigoAlumno,codigoAsignatura);

        cajaTextoPeriodo.setText("");
        cajaTextoExamen.setText("");
        cajaTextoAcumulativo.setText("");
    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";

            if (params[1] == "1") {    // Ingresar maestros

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

                    jsonParam.put("periodo", params[2]);
                    jsonParam.put("examen", params[3]);
                    jsonParam.put("acumulativo", params[4]);
                    jsonParam.put("cod_alumno", params[5]);
                    jsonParam.put("cod_asignatura", params[6]);

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
                            devuelve = "Nota agregada";


                        } else if (resultJSON == "2") {
                            devuelve = "Nota no agregada";
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
            Toast toast = Toast.makeText(context, "Nota agregada correctamente", duration);
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

    private void obtenerNombreAsignatura(String url) {
        final Context context=this;
        JsonObjectRequest jor=new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray asignaturas=response.getJSONArray("asignatura");

                            for(int i=0;i<asignaturas.length();i++)
                            {
                                JSONObject asignatura = (JSONObject) asignaturas.get(i);
                                String codigoAsignatura2 = asignatura.getString("cod_asignatura");
                                String nombreAsignatura2 = asignatura.getString("nombre");
                                if (codigoAsignatura2.equals(codigoAsignatura)){
                                    TextView textView=(TextView)findViewById(R.id.textIngresarNotasNombreAsignatura);
                                    textView.setText(nombreAsignatura2);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, "error try2", duration);
                            toast.show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, "error de red por favor recargue 2", duration);
                        toast.show();
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(jor);
    }




}
