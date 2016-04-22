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
public class CeldaVerClases extends ArrayAdapter<JSONObject> {
    public CeldaVerClases(Context context, int textViewResourceId){
        super (context, textViewResourceId);
    }

    public CeldaVerClases(Context context, int resource, List<JSONObject> items){
        super (context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View celda=convertView;
        if(celda==null){
            LayoutInflater layoutInflater= android.view.LayoutInflater.from(getContext());
            celda=layoutInflater.inflate(R.layout.celdacompleverclases,null);

        }
        TextView codigo=(TextView) celda.findViewById(R.id.textcodn);
        TextView periodo=(TextView) celda.findViewById(R.id.textperion);
        TextView examen=(TextView) celda.findViewById(R.id.textexamn);
        TextView acumulativo=(TextView) celda.findViewById(R.id.textacun);
        TextView clase=(TextView) celda.findViewById(R.id.textasign);

        JSONObject elemento=this.getItem(position);

        try {
            codigo.setText(elemento.getString("cod_notas"));
            periodo.setText(elemento.getString("periodo"));
            examen.setText(elemento.getString("examen"));
            acumulativo.setText(elemento.getString("acumulativo"));
            clase.setText(elemento.getString("cod_asignatura"));

        }catch(JSONException e){
            e.printStackTrace();
        }
        return celda;


    }
}