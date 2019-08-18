package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MonitoreoActivity extends AppCompatActivity {
    private LinearLayout contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);
        contenedor= (LinearLayout) findViewById(R.id.contenedor);
        ArrayList<Boton> botones= new ArrayList<Boton>();
        for (int i=0; i<5; i++){
            botones.add(new Boton("Piscina "+String.valueOf(i),i));
        }
        for (Boton bt:botones){
            Button btn = new Button(getApplicationContext());
            btn.setText(bt.texto);
            btn.setId(bt.ID);
            btn.setTextColor(Color.BLACK);
            contenedor.addView(btn);
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
