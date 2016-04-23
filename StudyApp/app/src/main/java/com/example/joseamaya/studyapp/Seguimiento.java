package com.example.joseamaya.studyapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class Seguimiento extends AppCompatActivity implements View.OnClickListener {

    EditText id_buscarAlumno;
    TextView txt_nombre;
    String ingresar_cod_padre;
    Button btn_seguir;
    ImageButton btn_buscar;

    // IP de mi Url
    String IP = "http://studyaplication.esy.es";
    // Rutas de los Web Services
    String GET_BY_ID = IP + "/obtener_alumno_por_id.php";
    String INSERT = IP + "/insertar_seguimiento_alumno.php";

    ObtenerWebService hiloconexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);

        //Enlace con los elementos
        id_buscarAlumno = (EditText) findViewById(R.id.id_buscarAlumno);
        txt_nombre = (TextView) findViewById(R.id.txt_nombre);
        btn_seguir=(Button)findViewById(R.id.btn_seguir);
        btn_buscar=(ImageButton)findViewById(R.id.btn_buscar);

        setTitle("Seguimiento de Alumno");

        //Listener de los botones
        btn_buscar.setOnClickListener(this);
        btn_seguir.setOnClickListener(this);

       // TextView tvCodigoMaestro = (TextView)findViewById(R.id.e_cod_padre);
        ingresar_cod_padre=(this.getIntent().getStringExtra("codigopadre2"));
      //  tvCodigoMaestro.setText(this.getIntent().getStringExtra("codigopadre2"));

    }

    @Override
    public void onClick(View v) {
        final Context context=this;

        switch (v.getId()){

            case R.id.btn_buscar:
                hiloconexion = new ObtenerWebService();
                String cadenallamada = GET_BY_ID + "?cod_alumno=" + id_buscarAlumno.getText().toString();
                hiloconexion.execute(cadenallamada, "2");   // Parámetros que recibe doInBackground

                break;
            case R.id.btn_seguir:
                if (id_buscarAlumno.getText().toString().isEmpty())
                {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Complete el campo de busqueda", duration);
                    toast.show();
                }
                else {
                    hiloconexion = new ObtenerWebService();
                    hiloconexion.execute(INSERT, "3", ingresar_cod_padre, id_buscarAlumno.getText().toString());   // Parámetros que recibe doInBackground

                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Seguimiento realizado correctamente", duration);
                    toast.show();
                }
                break;
            default:

                break;
        }
    }

    public class ObtenerWebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";
            String devuelve2 ="";



            if(params[1]=="1"){    // Consulta de todos los Alumnos

            }
            else if(params[1]=="2"){    // consulta por id

                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


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

                        if (resultJSON=="1"){      // hay un alumno que mostrar
                            devuelve = devuelve + respuestaJSON.getJSONObject("alumno").getString("nombre") + " " +
                                    respuestaJSON.getJSONObject("alumno").getString("apellido");
                        }
                        else if (resultJSON=="2"){
                            devuelve = "No hay Alumnos";
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
            else if(params[1]=="3"){    // insert
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
                    jsonParam.put("cod_padre", params[2]);
                    jsonParam.put("cod_alumno", params[3]);
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
                        BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line=br.readLine()) != null) {
                            result.append(line);
                            //response+=line;
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON == "1") {      // hay un seguimiento que mostrar
                        //    devuelve2 = "Seguimiento realizado correctamente";

                        } else if (resultJSON == "2") {
                            devuelve2 = "El Seguimiento no pudo realizarse";
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve2;


            }
            else if(params[1]=="4"){    // update

            }
            else if(params[1]=="5") {    // delete

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            txt_nombre.setText(s);
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
