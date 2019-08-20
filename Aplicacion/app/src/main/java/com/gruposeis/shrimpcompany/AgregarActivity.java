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

    public void agregar(View v){
        if (validarDatos()){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);

            builder.setMessage("¿Desea Registrar la piscina?").setTitle("Mensaje de confirmación")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(AgregarActivity.this, "Se ha registrado la piscina con exito!",Toast.LENGTH_SHORT).show();
                            alto.setText(""); ancho.setText(""); largo.setText(""); pHmax.setText(""); pHmin.setText(""); TempMax.setText(""); TempMin.setText("");
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
