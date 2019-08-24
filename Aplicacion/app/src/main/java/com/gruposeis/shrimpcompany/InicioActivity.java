package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;



public class InicioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    /**
     * metodo onClick del boton monitorear donde nos lleva a la vantana de monitoreo donde se muestras las piscinas ingresadas por el usuario
     */
    public void Monitorear (View v){
        Intent i = new Intent(this, MonitoreoActivity.class );
        startActivity(i);
    }

    /**
     * metodo onClick del boton agregar donde nos lleva a la vantana agregar piscina
     */
    public void Agregar(View v) {
        Intent i = new Intent(this, AgregarActivity.class );
        startActivity(i);
    }

    /**
     * metodo onClick del boton salir que cierra sesion del usuario llevandonos a la ventana de ingreso
     */
    public void Salir(View v){
        MainActivity.CerrarSesion(InicioActivity.this, "","");
        Intent i = new Intent(this, MainActivity.class );
        stopService(new Intent (this,Notificaciones.class));
        startActivity(i);
        finish();
    }

}
