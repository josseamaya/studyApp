package com.example.joseamaya.studyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Michell on 22/04/2016.
 */
public class CeldaNotas_Padre extends ArrayAdapter<JSONObject> {
    public CeldaNotas_Padre(Context context, int textViewResourceId){
        super (context, textViewResourceId);
    }

    public CeldaNotas_Padre(Context context, int resource, List<JSONObject> items){
        super (context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View celda=convertView;
        if(celda==null){
            LayoutInflater layoutInflater= LayoutInflater.from(getContext());
            celda=layoutInflater.inflate(R.layout.celdacomplejapadres,null);

        }
        TextView codigo=(TextView) celda.findViewById(R.id.periodo);
        TextView periodo=(TextView) celda.findViewById(R.id.examen);
        TextView examen=(TextView) celda.findViewById(R.id.asignatura);
        TextView acumulativo=(TextView) celda.findViewById(R.id.acumulativo);

        JSONObject elemento=this.getItem(position);

        try {
            codigo.setText(elemento.getString("cod_padre"));
            periodo.setText(elemento.getString("cod_alumno"));

        }catch(JSONException e){
            e.printStackTrace();
        }
        return celda;


    }
}