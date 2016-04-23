package com.example.joseamaya.studyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Michell on 23/04/2016.
 */
public class CeldaRecursos extends ArrayAdapter<JSONObject> {
    public CeldaRecursos(Context context, int textViewResourceId){
        super (context, textViewResourceId);
    }

    public CeldaRecursos(Context context, int resource, List<JSONObject> items){
        super (context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View celda=convertView;
        if(celda==null){
            LayoutInflater layoutInflater= android.view.LayoutInflater.from(getContext());
            celda=layoutInflater.inflate(R.layout.celdacomplejarecursos,null);

        }
        TextView codigo=(TextView) celda.findViewById(R.id.textcodR);
        TextView nombre=(TextView) celda.findViewById(R.id.texttipoR);
        TextView url=(TextView) celda.findViewById(R.id.texturlR);
        TextView descripcion=(TextView) celda.findViewById(R.id.textdesR);

        JSONObject elemento=this.getItem(position);

        try {
            codigo.setText(elemento.getString("cod_recurso"));
            nombre.setText(elemento.getString("tipo_recurso"));
            url.setText(elemento.getString("url"));
            descripcion.setText(elemento.getString("detalle"));

        }catch(JSONException e){
            e.printStackTrace();
        }
        return celda;


    }
}
