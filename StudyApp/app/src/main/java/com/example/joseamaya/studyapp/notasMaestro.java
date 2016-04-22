package com.example.joseamaya.studyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class notasMaestro extends AppCompatActivity {
    JSONObject matriculaJson=null;
    String codigoMaestro, nombreMaestro=null;
    ArrayList<String> arrayListCodigosAlumno;
    public static ArrayList<String> arrayListCodigosAsignatura=null;
    public static ArrayList<String> arrayListNombresAsignatura=null;

    int a=0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_maestro);
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


        codigoMaestro=this.getIntent().getStringExtra("codigoMaestro3");
        nombreMaestro=this.getIntent().getStringExtra("nombreMaestro3");

        TextView tvNotasCodigo= (TextView)findViewById(R.id.textNotasCodigo);
        tvNotasCodigo.setText(codigoMaestro);
        TextView tvNotasNombre= (TextView)findViewById(R.id.textNotasNombre);
        tvNotasNombre.setText(nombreMaestro);

        obtenerCodigosAlumnos("http://studyaplication.esy.es/obtener_matricula.php", codigoMaestro);
        //obtenerNombreAsignaturas("http://studyaplication.esy.es/obtener_asignatura.php");
        //obtenerAlumnosFiltrados("http://studyaplication.esy.es/obtener_alumno.php");

        ListView lv2 = (ListView) findViewById(R.id.listaAlumnosNotas);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView1 = (TextView) view.findViewById(R.id.textCeldaComplejaCodigoAsignatura);
                final String codigoAsignatura = (String) textView1.getText();

                TextView textView2 = (TextView) view.findViewById(R.id.textCeldaComplejaAlumnosAsignatura);
                final String nombreAsignatura = (String) textView2.getText();


                TextView textView3 = (TextView) view.findViewById(R.id.textCeldaComplejaAlumnosCodigo);
                final String codigoAlumno = (String) textView3.getText();

                TextView textView4= (TextView) view.findViewById(R.id.textCeldaComplejaAlumnosNombre);
                final String nombreAlumno = (String) textView4.getText();



                Intent intent = new Intent(context, IngresarNota.class);
                intent.putExtra("codigoAsignatura", codigoAsignatura);
                intent.putExtra("nombreAsignatura", nombreAsignatura);

                intent.putExtra("codigoAlumno", codigoAlumno);
                intent.putExtra("nombreAlumno", nombreAlumno);

                startActivity(intent);
            }
        });




    }
    private void obtenerCodigosAlumnos(String url, final String codigoMaestroComparacion) {
        final Context context=this;
        JsonObjectRequest jor=new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String matriculas;

                        try {
                            matriculas = response.getString("matricula");
                            JSONArray arregloMatriculas = new JSONArray(matriculas);

                            JSONObject matricula;

                            arrayListCodigosAlumno = new ArrayList<String>();
                            arrayListCodigosAsignatura = new ArrayList<String>();


                            for (int i=0;i<=arregloMatriculas.length()-1;i++)
                            {
                                matricula = (JSONObject) arregloMatriculas.get(i);
                                String codigoAlumno = matricula.getString("cod_alumno");
                                String codigoMaestro = matricula.getString("cod_maestro");
                                String codigoAsignatura = matricula.getString("cod_asignatura");


                                if (codigoMaestroComparacion.equals(codigoMaestro))
                                {
                                    arrayListCodigosAlumno.add(codigoAlumno);
                                    arrayListCodigosAsignatura.add(codigoAsignatura);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, "error try", duration);
                            toast.show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, "error de red por favor recargue", duration);
                        toast.show();
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(jor);

    }
   private void obtenerAlumnosFiltrados(String url) {
        final Context context=this;
        JsonObjectRequest jor=new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray alumnos=response.getJSONArray("alumno");

                            ArrayList<JSONObject> dataSourse=new ArrayList<JSONObject>();
                            for(int i=0;i<alumnos.length();i++)
                            {
                                JSONObject alumno = (JSONObject) alumnos.get(i);
                                String codigoAlumno = alumno.getString("cod_alumno");

                                for (int x=1;x<=arrayListCodigosAlumno.size();x++) {

                                    String codigoTemp=arrayListCodigosAlumno.get(x-1).toString();

                                    if (codigoAlumno.equals(codigoTemp))
                                    {
                                        dataSourse.add(alumnos.getJSONObject(i));
                                    }

                                }

                            }
                            AdaptadorNotasMaestro adapter=new AdaptadorNotasMaestro(context,0,dataSourse);
                            ((ListView)findViewById(R.id.listaAlumnosNotas)).setAdapter(adapter);
                            adapter.notifyDataSetChanged();

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

    private void obtenerNombreAsignaturas(String url) {
        final Context context=this;
        JsonObjectRequest jor=new JsonObjectRequest(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String asignaturas;

                        try {
                            asignaturas = response.getString("asignatura");
                            JSONArray arregloAsignaturas = new JSONArray(asignaturas);

                            JSONObject asignatura;


                            arrayListNombresAsignatura = new ArrayList<String>();


                            for (int i=0;i<=arregloAsignaturas.length()-1;i++)
                            {
                                asignatura = (JSONObject) arregloAsignaturas.get(i);
                                String nombreAsignatura = asignatura.getString("nombre");
                                String codigoAsignatura = asignatura.getString("cod_asignatura");

                                for (int x=1;x<=arrayListCodigosAsignatura.size();x++) {

                                    String codigoTemp=arrayListCodigosAsignatura.get(x-1).toString();

                                    if (codigoAsignatura.equals(codigoTemp))
                                    {
                                        arrayListNombresAsignatura.add(nombreAsignatura);
                                    }

                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, "error try3"+e, duration);
                            toast.show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, "error de red por favor recargue 3", duration);
                        toast.show();
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(jor);

    }

    public void onClickNotasCancelar(View v){
        obtenerNombreAsignaturas("http://studyaplication.esy.es/obtener_asignatura.php");
        obtenerAlumnosFiltrados("http://studyaplication.esy.es/obtener_alumno.php");

        /*for (int i=1;i<=arrayListCodigosAlumno.size();i++) {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, String.valueOf(arrayListCodigosAlumno.get(i)), duration);
            toast.show();

        }*/

    }

}
