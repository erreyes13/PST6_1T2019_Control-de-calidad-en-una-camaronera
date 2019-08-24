package com.gruposeis.shrimpcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private static final String STRING_PREFERENCES="usuario";
    private static final String USUARIO="usuario.sesion";
    private static final String CONTRASEÑA="usuario.contraseña";
    private EditText user,password;
    private String u="ccoloma", c="1234";

    /**
     * En este método sobreescrito se inicializan las variables a usar y que se encuentran definidas en el archivo XML
     * Ademas nos permite revisar en la memoria de la app si aulgun usuario a iniciado sesion antes para iniciar con la siguiente ventana, caso contrario pide que ingrese su usuario y cotnraseña
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ObtenercontraseñaGuardada().equals(c) && ObtenerUsuarioGuardado().equals(u)){
            Intent i = new Intent(this, InicioActivity.class );
            startActivity(i);
            Notificaciones.guardarEstado(MainActivity.this,true);
            startService(new Intent (this,Notificaciones.class));
            finish();
        }

        user= (EditText) findViewById(R.id.user);
        password= (EditText) findViewById(R.id.password);
    }

    /**
     * Este metodo valida el usuario y contraseña ingresado, de ser correctos los dirige a la siguiente ventana, caso contrario indica que los datos ingresados son incorrectos.
     */
    public void Ingresar (View v) {
        String usuario=user.getText().toString().trim();
        String contrasena=password.getText().toString().trim();

        if(usuario.equals(u) && contrasena.equals(c)){
            Intent i = new Intent(this, InicioActivity.class );
            startActivity(i);
            guardarInicio();
            Notificaciones.guardarEstado(MainActivity.this,true);
            startService(new Intent (this,Notificaciones.class));
            finish();
        }
        else {
            Toast.makeText(this, "El usuario o contraseña ingresados son incorrectos!",Toast.LENGTH_SHORT).show();
            user.setText("");
            password.setText("");
        }

    }

    /**
     * Este metodo permite guardar la informacion del usuario con el fin de que este no tenga q iniciar sesion si llegara a cerrar la app
     */
    public void guardarInicio(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putString(USUARIO,user.getText().toString().trim()).apply();
        preferences.edit().putString(CONTRASEÑA,password.getText().toString().trim()).apply();
    }

    /**
     * Este metodo elimina los datos del usuario cuando este cierra sesion en el aplicativo.

     */
    public static void CerrarSesion(Context c,String users, String passwords){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putString(USUARIO,users).apply();
        preferences.edit().putString(CONTRASEÑA,passwords).apply();
    }

    /**
     * Obtiene al nombre del usurio que inicia sesion desde la memoria del aplicativo
     */
    public String ObtenerUsuarioGuardado(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getString(USUARIO,"");
    }

    /**
     * Retorna la contraseña del usurio que inicia sesion guardada en la memoria del aplicativo
     */
    public String ObtenercontraseñaGuardada(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getString(CONTRASEÑA,"");
    }
}
