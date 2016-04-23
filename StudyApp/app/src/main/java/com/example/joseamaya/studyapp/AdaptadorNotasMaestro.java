
package com.example.joseamaya.studyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdaptadorNotasMaestro extends ArrayAdapter<JSONObject> {

    public AdaptadorNotasMaestro(Context context, int resourse, List<JSONObject> items){
        super(context,resourse,items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View celda = convertView;
        if (celda==null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            celda= layoutInflater.inflate(R.layout.celdacomplejaalumnos,null);
        }

        TextView nombre = (TextView) celda.findViewById(R.id.textCeldaComplejaAlumnosNombre);
        TextView codigo=(TextView) celda.findViewById(R.id.textCeldaComplejaAlumnosCodigo);
        //TextView asignatura=(TextView) celda.findViewById(R.id.textCeldaComplejaAlumnosAsignatura);
        TextView codigoAsignatura=(TextView) celda.findViewById(R.id.textCeldaComplejaCodigoAsignatura);


        JSONObject elemento=this.getItem(position);

        try {

            nombre.setText(elemento.getString("nombre"));
            codigo.setText(elemento.getString("cod_alumno"));
           // asignatura.setText(notasMaestro.arrayListNombresAsignatura.get(position));
            codigoAsignatura.setText(notasMaestro.arrayListCodigosAsignatura.get(position));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return celda;
    }


}

