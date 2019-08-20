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
import android.widget.Toast;

import java.util.ArrayList;

public class MonitoreoActivity extends AppCompatActivity {
    private LinearLayout contenedor;
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "Jc7RWjhMfk";
    private String pwdMySQL = "BPY0HGFDSZ";
    private String database = "Jc7RWjhMfk";
    private String[] datosConexion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);
        contenedor= (LinearLayout) findViewById(R.id.contenedor);
        ArrayList<Boton> botones= new ArrayList<Boton>();
        for (int i=1; i<=(cantPiscinas()); i++){
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



    public int cantPiscinas(){
        String[] resultadoSQL = null;
        try{
            datosConexion = new String[]{
                    serverIP,
                    port,
                    database,
                    userMySQL,
                    pwdMySQL,
                    "SELECT * FROM Piscina;"
            };
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            Toast.makeText(MonitoreoActivity.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

            String resultadoConsulta = resultadoSQL[0];
            String numFilas = resultadoSQL[1];
            String numColumnas = resultadoSQL[2];
            return Integer.parseInt(numFilas);
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
            return 0;
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
