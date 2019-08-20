package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AlertasActivity extends AppCompatActivity {
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "Jc7RWjhMfk";
    private String pwdMySQL = "BPY0HGFDSZ";
    private String database = "Jc7RWjhMfk";
    private String[] datosConexion = null;
    private TextView consulta;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas);
        Bundle bundle = getIntent().getExtras();
        ID= bundle.getString("IDpiscina");
        consulta= (TextView) findViewById(R.id.alertas);
        mostrarResultados();
    }

    public void mostrarResultados()
    {
        String[] resultadoSQL = null;
        try{
            datosConexion = new String[]{
                    serverIP,
                    port,
                    database,
                    userMySQL,
                    pwdMySQL,
                    "SELECT * FROM Alerta WHERE idpiscina="+ID+";"
            };
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            Toast.makeText(AlertasActivity.this,"Conexión Establecida", Toast.LENGTH_LONG).show();

            String resultadoConsulta = resultadoSQL[0];
            consulta.setText("ID, Descripcion, Fecha, #Piscina\n"+resultadoConsulta );
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
