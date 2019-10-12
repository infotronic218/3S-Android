package sous.etis.tech.com.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.ui.notifications.Notification;

public class AppFirebaseInstanceIdService  extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TOKEN", s);
     SharedPreferences preferences =  getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
     preferences.edit().putString(Constants.FIREBASE_NOTIFICATION_TOKEN, s).apply();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title  = remoteMessage.getNotification().getTitle();
        String body   = remoteMessage.getNotification().getBody();

        AppNotificationManager.getInstance(getApplication())
                .displayNotification( title, body);
        saveNotification(title, body);
;    }

    private void saveNotification(String title, String body){
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String data =  preferences.getString(Constants.SHARED_PREFERENCES_NOTIFICATION_LIST, null) ;
        Gson gson = new Gson() ;
        Type type = new TypeToken<ArrayList<Notification>>(){}.getType() ;
        List<Notification> list = gson.fromJson(data, type);
        if (list== null) {
            list = new ArrayList<>();
        }
        list.add(new Notification(title, body, String.valueOf(System.currentTimeMillis())));
        data = gson.toJson(list) ;
        preferences.edit().putString(Constants.SHARED_PREFERENCES_NOTIFICATION_LIST,data ).apply();
    }
}
