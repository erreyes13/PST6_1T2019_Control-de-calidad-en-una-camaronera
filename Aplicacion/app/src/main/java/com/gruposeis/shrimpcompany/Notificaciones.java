package com.gruposeis.shrimpcompany;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notificaciones extends Service {
    private final static String CHANNEL_ID="NOTIFICACION";
    private final static int NOTIFICACION_ID=1;
    private static final String STRING_PREFERENCES="alertas";
    private static final String num="alerta.cantidad";

    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "Jc7RWjhMfk";
    private String pwdMySQL = "BPY0HGFDSZ";
    private String database = "Jc7RWjhMfk";
    private String[] datosConexion = null;

    @Override
    public void onCreate(){

    }

    /**
     *Este metodo sobreescrito permite iniciar un servicio en segundo plano con el fin de retornar una notificacion si una alerta se produce en las piscinas
     */
    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){
        if (ObtenerEstado()) {
            mostrarResultados();
            startService(new Intent(new Intent(this, Notificaciones.class)));
        }
        return START_STICKY;

    }

    /**
     * Este metodo permite detener el servicio iniciado, deteniendose en el momento que el usuario cierra sesion en el aplicativo.
     */
    @Override
    public void onDestroy(){
        SharedPreferences preferences= (SharedPreferences) getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putBoolean("servicio",false).apply();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Este metodo permite generar una notificacion del aplicativo
     */
    public void generarNotificaicon(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name ="Alerta";
            NotificationChannel notificacionChanel= new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notification =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notification.createNotificationChannel(notificacionChanel);
        }

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.alert);
        builder.setContentTitle("Alerta");
        builder.setContentText("Ha ocurrido un cambio de parametros en el agua.");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat not=NotificationManagerCompat.from(getApplicationContext());
        not.notify(NOTIFICACION_ID,builder.build());
    }

    /**
     * Este metodo permite determinar si ha sido agragada un nuevo dato en la tabla de alertas de la base de datos, generando una notificacion sobre esto.
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
                    "SELECT * FROM Alerta;"
            };
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            resultadoSQL = new AsyncQuery().execute(datosConexion).get();
            String filas=resultadoSQL[1];
            int filasanteriores=Integer.parseInt(ObtenerAlertas());
            if(Integer.parseInt(filas)>filasanteriores){
                guardarAlerta(filas);
                generarNotificaicon();
            }

        }catch(Exception ex) { }
    }

    /**
     * Este metodo permite guardar la ultima cantidad de datos guardados en la tabla de alertas
     * @param s
     */
    public void guardarAlerta(String s){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putString(num,s).apply();
    }

    /**
     *Permite guardar el estado de inicio de sesion, siendo true si ha iniciado algun usuario o false si no lo ha hecho o ha cerrado sesion
     */
    public static void guardarEstado(Context c, boolean s){
        SharedPreferences preferences=c.getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putBoolean("servicio",s).apply();
    }

    /**
     * Permtie obtener el dato de la cantidad de los ultimos datos guardados en la tabla de alertas
     * @return
     */
    public String ObtenerAlertas(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getString(num,"0");
    }

    /**
     * Permite Obtener los datos del estado de la app
     * @return
     */
    public boolean ObtenerEstado(){
        SharedPreferences preferences=getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getBoolean("servicio",false);
    }



}
