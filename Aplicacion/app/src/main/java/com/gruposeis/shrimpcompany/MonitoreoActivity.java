package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MonitoreoActivity extends AppCompatActivity {
    private LinearLayout contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);
        contenedor= (LinearLayout) findViewById(R.id.contenedor);
        ArrayList<Boton> botones= new ArrayList<Boton>();
        for (int i=1; i<6; i++){
            botones.add(new Boton("Piscina "+String.valueOf(i),i));
        }
        for (final Boton bt:botones){
            Button btn = new Button(this);
            btn.setText(bt.texto);
            btn.setId(bt.ID);
            btn.setTextSize(18);
            btn.setBackgroundColor(Color.parseColor("#FFE794"));
            btn.setTextColor(Color.BLACK);
            contenedor.addView(btn);
            contenedor.addView(new TextView(this));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(MonitoreoActivity.this,EstadoActualActivity.class );
                    i.putExtra("ID",String.valueOf(bt.ID));
                    startActivity(i);
                }
            });
        }
    }



    class Boton{
        String texto;
        int ID;

        public Boton(String texto, int ID) {
            this.texto = texto;
            this.ID = ID;
        }
    }
}
