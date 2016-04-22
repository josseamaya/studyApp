package com.example.joseamaya.studyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
        final TextView horario=(TextView) celda.findViewById(R.id.texthora);
        TextView codigo=(TextView) celda.findViewById(R.id.textcod);

        JSONObject elemento=this.getItem(position);

        try {
            nombre.setText(elemento.getString("nombre"));
            horario.setText(elemento.getString("horario"));

            codigo.setText(elemento.getString("cod_asignatura"));


            /*celda.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent =new Intent(getContext(), Matricularse.class);
                    intent.putExtra("id", id);
                    return false;
                }
            });*/



        }catch(JSONException e){
            e.printStackTrace();
        }
        return celda;


    }
}
