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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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

public class Matricularse extends AppCompatActivity implements View.OnClickListener{
    EditText buscarid;
    EditText nombre;
    EditText codigo_a;
    Button buscar;
    Button matricula;

    // IP de mi Url
    String IP = "http://studyaplication.esy.es";
    // Rutas de los Web Services
    String GET_BY_ID = IP + "/obtener_maestro_por_id.php";
    String INSERT_M = IP + "/insertar_matricula.php";

    ObtenerWebService hiloconexion;

    String codigoalumno;
    String cod_asig;

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
        //asignatura=(ListView)findViewById(R.id.listasignatura);
        matricula=(Button)findViewById(R.id.buttonmatric);
        codigo_a=(EditText)findViewById(R.id.editcodA);

        //Listener de los botones
        buscar.setOnClickListener(this);
        matricula.setOnClickListener(this);


        codigoalumno=this.getIntent().getStringExtra("codigoalumno2");

        TextView cod_alumno = (TextView) findViewById(R.id.cod_alumno2);
        cod_alumno.setText(codigoalumno);

    }

    @Override
    public void onClick(View v) {
        final Context context=this;
        if (v.getId() == R.id.buttonbus) {
            hiloconexion = new ObtenerWebService();
            String cadenallamada = GET_BY_ID + "?cod_maestro=" + buscarid.getText().toString();
            hiloconexion.execute(cadenallamada,"1");   // Parámetros que recibe doInBackground
            obtenerJson();

        }else if(v.getId()==R.id.buttonmatric){
            /*insertar id
            cod_asig = this.getIntent().getStringExtra("id");
            TextView id= (TextView) findViewById(R.id.textresul);
            id.setText(cod_asig);*/
            if(codigo_a.getText().toString().isEmpty() || buscarid.getText().toString().isEmpty()){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "Ingrese todos los datos", duration);
                toast.show();
            }else{
                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(INSERT_M, "2", buscarid.getText().toString(),codigoalumno , codigo_a.getText().toString());   // Parámetros que recibe doInBackground
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "Matricula Ingresada", duration);
                toast.show();
            }

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
                            devuelve = devuelve + respuestaJSON.getJSONObject("maestro").getString("nombre");

                        } else if (resultJSON == "2") {
                            devuelve = "No hay Maestros";
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

                    jsonParam.put("cod_maestro", params[2]);
                    jsonParam.put("cod_alumno", params[3]);
                    jsonParam.put("cod_asignatura", params[4]);
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

                        if (resultJSON == "1") {      // hay un padre que mostrar
                            devuelve = "Matricula realizada correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "No se pudo realizar la matricula";
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
            nombre.setText(s);
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

    public void obtenerJson(){
        final Context context=this;
        JsonObjectRequest jor= new JsonObjectRequest(
                "http://studyaplication.esy.es/obtener_asignatura.php",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.getAnonymousLogger().log(Level.INFO, response.toString());

                        try {
                            JSONArray asig = response.getJSONArray("asignatura");
                            final ArrayList<JSONObject> dataSource = new ArrayList<JSONObject>();
                            JSONObject codmaestro=null;
                            for (int i = 0; i < asig.length(); i++) {
                                codmaestro = (JSONObject) asig.get(i);
                                String codigo = codmaestro.getString("cod_maestro");
                                if(codigo.equals(buscarid.getText().toString())){
                                    dataSource.add(asig.getJSONObject(i));
                                }

                            }
                            final Celda adapter = new Celda(context, 0, dataSource);
                            ((ListView) findViewById(R.id.listasignatura)).setAdapter(adapter);


                            //seleccionar un dato de la lista
                            final ListView lista=(ListView)findViewById(R.id.listasignatura);
                            final TextView resul=(TextView)findViewById(R.id.textresul);

                            /*assert lista != null;
                            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                             public void onItemClick(AdapterView<?> customerAdapter, View footer, int selectedInt, long selectedLong) {
                                                                 String seleccion = (String) lista.getItemAtPosition(selectedInt);
                                                                 resul.setText(seleccion);
                                                             }
                                                         }

                                );*/


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
