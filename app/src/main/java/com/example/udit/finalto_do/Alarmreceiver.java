package com.example.udit.finalto_do;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import static android.app.Activity.RESULT_OK;

public class Alarmreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int id = intent.getIntExtra(com.example.udit.finalto_do.intent.id,0);
        String title =intent.getStringExtra(com.example.udit.finalto_do.intent.EXPENSE_TITLE);
        long[] k={123,123,123,123,456,789,890,1000,12345};
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("My NotiFication")
                .setAutoCancel(true)
                .setVibrate(k)
                .setLights(Color.GRAY,1000,2000)
                .setContentText("Alarm!!!!"+title);
        Uri uri = Uri.parse("uri://");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.getDefaultType(uri));
        mBuilder.setSound(alarmSound);
        Intent resultIntent=new Intent(context,expenseactvity.class);
        resultIntent.putExtra("title",title);
        PendingIntent resultPendinIntent=PendingIntent.getActivity(context,id,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendinIntent);
        NotificationManager mNotificationManager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id,mBuilder.build());
        setResultCode(10);

    }
}
