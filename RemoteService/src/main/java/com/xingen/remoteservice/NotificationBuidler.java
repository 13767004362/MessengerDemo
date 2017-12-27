package com.xingen.remoteservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by ${xingen} on 2017/12/27.
 */

public class NotificationBuidler {
    public static Notification createMessageNotifaction(Context context, String title, String content){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setContentIntent(PendingIntent.getActivity(context,0,new Intent(),0));
        builder.setAutoCancel(true);
        return builder.build();
    }
}
