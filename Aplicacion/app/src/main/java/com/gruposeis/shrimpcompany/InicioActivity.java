package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void Monitorear (View v){
        Intent i = new Intent(this, MonitoreoActivity.class );
        startActivity(i);
    }

    public void Agregar(View v){
        Intent i = new Intent(this, AgregarActivity.class );
        startActivity(i);
    }

    public void Salir(View v){
        MainActivity.CerrarSesion(InicioActivity.this, "","");
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
        finish();
    }

}
