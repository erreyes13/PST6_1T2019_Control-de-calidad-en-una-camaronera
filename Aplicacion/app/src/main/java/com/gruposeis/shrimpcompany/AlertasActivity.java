package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AlertasActivity extends AppCompatActivity {
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "Jc7RWjhMfk";
    private String pwdMySQL = "BPY0HGFDSZ";
    private String database = "Jc7RWjhMfk";
    private String[] datosConexion = null;
    private String ID;
    private TableLayout table;

    /**
     * En este método sobreescrito se inicializan las variables a usar y que se encuentran definidas en el archivo XML, o gruadados en extras
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas);
        Bundle bundle = getIntent().getExtras();
        ID= bundle.getString("IDpiscina");
        table = (TableLayout) findViewById(R.id.tablealertas);
        mostrarResultados();
    }

    /**
     * Este metodo permite mostrar los resultados de las alertas registradas en la bases de datos.
     */
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
            String[] cabecera={"\n ,Fecha y Hora,Descripcion, ","1","4"};
            Tablas(cabecera);
            Tablas(resultadoSQL);
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Este metodo nos permite colocar los datos obtenidos de la base de datos y colocarlos dentro de un Table Layout, con el fin de mejorar la presentacion de dicha infromación
     * @param resultado
     */
    public void Tablas(String[] resultado) {

        String resultadoConsulta = resultado[0];
        int NUM_ROW = Integer.parseInt(resultado[1]);
        int NUM_COLS = Integer.parseInt(resultado[2]);
        String[] filas = resultadoConsulta.split("\n");
        for (int i = 1; i < (NUM_ROW+1); i++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            tableRow.setBackgroundColor(Color.LTGRAY);
            String[] columnas = filas[i].split(",");

            for (int j = 1; j < (NUM_COLS-1); j++) {
                TextView valor = new TextView(getApplicationContext());
                valor.setText(columnas[j] + "  ");
                valor.setTextColor(Color.BLACK);
                tableRow.addView(valor);
            }
        }
    }
}
