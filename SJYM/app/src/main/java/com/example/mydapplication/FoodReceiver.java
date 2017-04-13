package com.example.mydapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;

import org.json.JSONObject;

/**
 * Created by 44265 on 2016/7/18.
 */
public class FoodReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        try {
            if (intent.getAction().equals("UPDATE_STATUS")) {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
                final String message = json.getString("alert");
                Intent resultIntent = new Intent(AVOSCloud.applicationContext, ManageActivity.class);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(AVOSCloud.applicationContext, 0, resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                long[] vibrates={0,1000,1000};
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(AVOSCloud.applicationContext)
                                .setSmallIcon(R.drawable.detail)
                                .setContentTitle(
                                        AVOSCloud.applicationContext.getResources().getString(R.string.app_name))
                                .setContentText(message)
                                .setTicker(message).setVibrate(vibrates);
                mBuilder.setContentIntent(pendingIntent);
                mBuilder.setAutoCancel(true);
                Notification notification=mBuilder.build();
                notification.defaults=Notification.DEFAULT_SOUND;
                int mNotificationId = 10086;
                NotificationManager mNotifyMgr =
                        (NotificationManager) AVOSCloud.applicationContext
                                .getSystemService(
                                        Context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, notification);
            }
        } catch (Exception e) {
            Log.d("FoodReceiver", "JSONException: " + e.getMessage());
        }
    }
}
