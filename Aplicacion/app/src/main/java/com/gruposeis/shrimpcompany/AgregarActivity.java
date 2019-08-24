package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarActivity extends AppCompatActivity {
    private EditText alto, ancho, largo, pHmax, pHmin, TempMax, TempMin;
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "Jc7RWjhMfk";
    private String pwdMySQL = "BPY0HGFDSZ";
    private String database = "Jc7RWjhMfk";
    private String[] datosConexion = null;

    /**
     * En este método sobreescrito se inicializan las variables a usar y que se encuentran definidas en el archivo XML
     */
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        alto=(EditText) findViewById(R.id.alto);
        ancho=(EditText) findViewById(R.id.ancho);
        largo=(EditText) findViewById(R.id.largo);
        pHmax=(EditText) findViewById(R.id.phmax);
        pHmin=(EditText) findViewById(R.id.phmin);
        TempMax=(EditText) findViewById(R.id.tempmax);
        TempMin=(EditText) findViewById(R.id.tempmin);
    }

    /**
     * Este metodo permite agregar en la base de datos una nueva piscina de la camaronera, enviando los datos ingresados en la activity
     * a la base de datos del proyecto, validando todos los datos ingresados.
     *
     */
    public void agregar(View v){
        if (validarDatos()){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);

            builder.setMessage("¿Desea Registrar la piscina?").setTitle("Mensaje de confirmación")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String[] resultadoSQL = null;
                            try{
                                datosConexion = new String[]{
                                        serverIP,
                                        port,
                                        database,
                                        userMySQL,
                                        pwdMySQL,
                                        "INSERT INTO Piscina (ID, largo, ancho, profundidad, phMax, phmin, tmax, tmin, idusuario) VALUES (NULL,"+
                                                largo.getText().toString()+","+ancho.getText().toString() +","+alto.getText().toString()+","+pHmax.getText().toString()
                                                +","+pHmin.getText().toString()+","+TempMax.getText().toString()+","+TempMin.getText().toString()+", 1);"
                                };
                                String driver = "com.mysql.jdbc.Driver";
                                Class.forName(driver).newInstance();
                                resultadoSQL = new AsyncQuery().execute(datosConexion).get();
                                Toast.makeText(AgregarActivity.this, "Se ha registrado la piscina con exito!",Toast.LENGTH_SHORT).show();
                                alto.setText(""); ancho.setText(""); largo.setText(""); pHmax.setText(""); pHmin.setText(""); TempMax.setText(""); TempMin.setText("");

                            }catch(Exception ex)
                            {
                                Toast.makeText(AgregarActivity.this, "Error al agregar la piscina.",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).setNegativeButton("NO",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
            });
            builder.create().show();
        }

        else{
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);

            builder.setMessage("La información ingresada no es valida.")
                    .setTitle("Error")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.create().show();
        }
    }

    /**
     *  Esta funcion se encarga de validar que todos los datos sean de tipo float, ademas que el valor maximo de algun parametro sea siempre mayor que el minimo,
     *  usando la funcion de try--catch donde si logra transformarlo a float todos los valores inresados, entonces retorna true, y de caso contrario retorna false.
     *
     * @return
     */
    public Boolean validarDatos(){
        try{
            Float.parseFloat(alto.getText().toString().trim());
            Float.parseFloat(ancho.getText().toString().trim());
            Float.parseFloat(largo.getText().toString().trim());
            Float phM=Float.parseFloat(pHmax.getText().toString().trim());
            Float phm=Float.parseFloat(pHmin.getText().toString().trim());
            Float TM=Float.parseFloat(TempMax.getText().toString().trim());
            Float Tm=Float.parseFloat(TempMin.getText().toString().trim());
            if ((phm<phM)&&(phm>0)&&(phM<14)&&(TM>Tm)){
                return true;
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }
}
