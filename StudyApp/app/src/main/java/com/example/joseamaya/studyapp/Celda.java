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

/**
 * Created by Michell on 21/04/2016.
 */
public class Celda  extends ArrayAdapter<JSONObject> {
    public Celda(Context context, int textViewResourceId){
        super (context, textViewResourceId);
    }

    public Celda(Context context, int resource, List<JSONObject> items){
        super (context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View celda=convertView;
        if(celda==null){
            LayoutInflater layoutInflater= android.view.LayoutInflater.from(getContext());
            celda=layoutInflater.inflate(R.layout.celdacompleja,null);

        }
        TextView nombre=(TextView) celda.findViewById(R.id.textnombre);
        TextView horario=(TextView) celda.findViewById(R.id.texthora);

        JSONObject elemento=this.getItem(position);

        try {
            nombre.setText(elemento.getString("nombre"));
            horario.setText(elemento.getString("horario"));

        }catch(JSONException e){
            e.printStackTrace();
        }
        return celda;


    }
}