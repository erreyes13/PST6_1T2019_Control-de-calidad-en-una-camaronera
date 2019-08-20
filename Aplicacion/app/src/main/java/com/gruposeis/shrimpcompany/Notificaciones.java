package com.gruposeis.shrimpcompany;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notificaciones extends Service {
    private final static String CHANNEL_ID="NOTIFICACION";
    private final static int NOTIFICACION_ID=1;
    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){
        int i=0;
        while (i<10000000){
          i++;


        }

        generarNotificaicon();

        return START_STICKY;

    }

    @Override
    public void onDestroy(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

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
        builder.setContentText("");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat not=NotificationManagerCompat.from(getApplicationContext());
        not.notify(NOTIFICACION_ID,builder.build());
    }
}
