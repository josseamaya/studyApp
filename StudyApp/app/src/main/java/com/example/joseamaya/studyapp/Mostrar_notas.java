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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mostrar_notas extends AppCompatActivity implements View.OnClickListener{

    // IP de mi Url
    String IP = "http://studyaplication.esy.es";
    // Rutas de los Web Services
    String GET_BY_ID = IP + "/obtener_nota_por_id.php";

    ObtenerWebService hiloconexion;

    String codigoA_ver_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_notas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Tus Calificaciones");


        codigoA_ver_n=this.getIntent().getStringExtra("codigoalumno2");
        TextView cod_alumno = (TextView) findViewById(R.id.textcodigoAVer);
        cod_alumno.setText(codigoA_ver_n);

        hiloconexion = new ObtenerWebService();
        String cadenallamada = GET_BY_ID + "?cod_alumno=" + codigoA_ver_n;
        hiloconexion.execute(cadenallamada,"1");   // Parámetros que recibe doInBackground
        obtenerJson();
    }

    @Override
    public void onClick(View v) {
        final Context context=this;
        if (v.getId() == R.id.buttonbus) {
            /*hiloconexion = new ObtenerWebService();
            String cadenallamada = GET_BY_ID + "?cod_alumno=" + codigoA_ver_n;
            hiloconexion.execute(cadenallamada,"1");   // Parámetros que recibe doInBackground
            obtenerJson();*/

        }

    }

    public class ObtenerWebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";


            if (params[1] == "1") {    // Consulta por id

                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON == "1") {      // hay un Maestro que mostrar
                            devuelve = devuelve + respuestaJSON.getJSONObject("notas").getString("periodo");
                        } else if (resultJSON == "2") {
                            devuelve = "No hay Notas";
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


            } else if (params[1] == "2") {    // guardar matricula

            } else if (params[1] == "3") {    // insert

            } else if (params[1] == "4") {    // update

            } else if (params[1] == "5") {    // delete

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //nombre.setText(s);
            //super.onPostExecute(s);
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

    public void obtenerJson(){
        final Context context=this;
        JsonObjectRequest jor= new JsonObjectRequest(
                "http://studyaplication.esy.es/obtener_nota.php",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.getAnonymousLogger().log(Level.INFO, response.toString());

                        try {
                            JSONArray asig = response.getJSONArray("notas");
                            final ArrayList<JSONObject> dataSource = new ArrayList<JSONObject>();
                            JSONObject codalumno=null;
                            for (int i = 0; i < asig.length(); i++) {
                                codalumno = (JSONObject) asig.get(i);
                                String codigo = codalumno.getString("cod_alumno");
                                if(codigo.equals(codigoA_ver_n)) {
                                    dataSource.add(asig.getJSONObject(i));
                                }

                            }
                            final CeldaVerClases adapter = new CeldaVerClases(context, 0, dataSource);
                            ((ListView) findViewById(R.id.listView_vernotas)).setAdapter(adapter);

                        }catch (JSONException e) {
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
