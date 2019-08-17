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

    public void Monitorear (View v){
        Intent i = new Intent(this, MonitoreoActivity.class );
        startActivity(i);
    }

    public void Salir(View v){
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {    }
}
