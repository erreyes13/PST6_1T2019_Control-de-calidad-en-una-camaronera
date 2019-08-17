package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {    }

    public void Ingresar (View v){
        Intent i = new Intent(this, InicioActivity.class );
        startActivity(i);
        finish();
    }
}
