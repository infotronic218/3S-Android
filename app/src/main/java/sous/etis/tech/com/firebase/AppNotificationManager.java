package sous.etis.tech.com.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.MainActivity;
import sous.etis.tech.com.R;
import sous.etis.tech.com.ui.notifications.Notification;

public class AppNotificationManager {
    private static  AppNotificationManager mInstance ;
    private Context context ;
    private AppNotificationManager(Context context){
        this.context = context ;
    }

    public static synchronized AppNotificationManager getInstance (Context ctx){
       if(mInstance==null){
           mInstance = new AppNotificationManager(ctx);
       }
       return mInstance ;
    }

    public void displayNotification(String title , String body){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long [] patterns  = {100L, 200L, 300L, 400L, 200L, 300L, 400L};
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,
                        Constants.FIREBASE_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                        .setVibrate(patterns)
                        .setSound(alarmSound)
                .setContentText(body)
                .setColor(Color.RED);



        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.OPEN_NOTIFICATION_FRAGMENT, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

       NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       if(notificationManager!=null){
           notificationManager.notify(1, builder.build());
       }
    }


}
