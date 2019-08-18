package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AgregarActivity extends AppCompatActivity {
    private EditText alto, ancho, largo, pHmax, pHmin, TempMax, TempMin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
    }
}
