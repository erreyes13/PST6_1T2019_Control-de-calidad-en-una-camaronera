package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EstadoActualActivity extends AppCompatActivity {
    private String piscina;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_actual);
        Bundle bundle = getIntent().getExtras();
        piscina="Piscina #" + bundle.getString("ID");
        name=(TextView) findViewById(R.id.title);
        name.setText(piscina);
    }


    public void Historial(View v){
        final String[] items = {"General", "Alertas"};
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("HISTORIAL").setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("General")){
                    Intent i = new Intent(EstadoActualActivity.this, HistorialActivity.class );
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(EstadoActualActivity.this, AlertasActivity.class );
                    startActivity(i);
                }
            }
        });

        builder.create().show();
    }
}
