package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EstadoActualActivity extends AppCompatActivity {
    private String piscina,ID;
    private TextView name;
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "Jc7RWjhMfk";
    private String pwdMySQL = "BPY0HGFDSZ";
    private String database = "Jc7RWjhMfk";
    private String[] datosConexion = null;
    private TextView consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_actual);
        Bundle bundle = getIntent().getExtras();
        ID= bundle.getString("ID");
        piscina="Piscina #" + ID;
        name=(TextView) findViewById(R.id.title);
        consulta=(TextView) findViewById(R.id.historial);
        name.setText(piscina);
        MostrarDatos();
    }


    public void Historial(View v){
        final String[] items = {"General", "Alertas"};
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("HISTORIAL").setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("General")){
                    Intent i = new Intent(EstadoActualActivity.this, HistorialActivity.class );
                    i.putExtra("IDpiscina",String.valueOf(ID));
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(EstadoActualActivity.this, AlertasActivity.class );
                    i.putExtra("IDpiscina",String.valueOf(ID));
                    startActivity(i);
                }
            }
        });

        builder.create().show();
    }

    public void MostrarDatos(){
        String[] resultadoSQL = null;
        try{
            datosConexion = new String[]{
                    serverIP,
                    port,
                    database,
                    userMySQL,
                    pwdMySQL,
                    "SELECT * FROM Piscina WHERE ID=" +ID+";"
            };
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            Toast.makeText(EstadoActualActivity.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

            String[] resultadoConsulta = resultadoSQL[0].split(",");
            consulta.setText("Datos:\n"+"Largo= "+resultadoConsulta[1] + "\nAncho= " +resultadoConsulta[2] + "\nProfundidad= " +
                    resultadoConsulta[3] + "\n\nDatos de Control:\npH maximo= " +resultadoConsulta[4] + "\npH minimo= " +resultadoConsulta[5]
                    + "\nTemperatura Maxima= " +resultadoConsulta[6] + " ºC\nTemperatura minima= " +resultadoConsulta[7]+" ºC");
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
