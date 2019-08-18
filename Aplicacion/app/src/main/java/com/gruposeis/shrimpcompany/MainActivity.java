package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String STRING_PREFERENCES="usuario";
    private static final String USUARIO="usuario.sesion";
    private static final String CONTRASEÑA="usuario.contraseña";
    private EditText user,password;
    private String u="a", c="b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ObtenercontraseñaGuardada().equals(c) && ObtenerUsuarioGuardado().equals(u)){
            Intent i = new Intent(this, InicioActivity.class );
            startActivity(i);
            finish();
        }
        user= (EditText) findViewById(R.id.user);
        password= (EditText) findViewById(R.id.password);
    }

    @Override
    public void onBackPressed() {    }

    public void Ingresar (View v){
        if(user.getText().toString().trim().equals(u) && password.getText().toString().trim().equals(c)){
            Intent i = new Intent(this, InicioActivity.class );
            startActivity(i);
            guardarInicio();
            finish();
        }
        else {
            Toast.makeText(this, "El usuario o contraseña ingresados son incorrectos!",Toast.LENGTH_SHORT).show();
            user.setText("");
            password.setText("");
        }
    }

    public void guardarInicio(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putString(USUARIO,user.getText().toString().trim()).apply();
        preferences.edit().putString(CONTRASEÑA,password.getText().toString().trim()).apply();
    }

    public static void CerrarSesion(Context c,String users, String passwords){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putString(USUARIO,users).apply();
        preferences.edit().putString(CONTRASEÑA,passwords).apply();
    }

    public String ObtenerUsuarioGuardado(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getString(USUARIO,"");
    }
    public String ObtenercontraseñaGuardada(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getString(CONTRASEÑA,"");
    }
}
