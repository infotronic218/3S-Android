package sous.etis.tech.com.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    List<Notification> list ;
    NotificationRecyclerViewAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        RecyclerView recyclerView = root.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getNotificationList();

        adapter = new NotificationRecyclerViewAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
    public void getNotificationList(){
    SharedPreferences preferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    String data =  preferences.getString(Constants.SHARED_PREFERENCES_NOTIFICATION_LIST, null) ;
    Gson gson = new Gson() ;
    Type type = new TypeToken<ArrayList<Notification>>(){}.getType() ;
     list = gson.fromJson(data, type);
     if (list== null) {list = new ArrayList<>();}
     Collections.sort(list, new Notification.ShortNotification());
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotificationList();
        if(adapter!=null){
            adapter.setNotifications(list);
            adapter.notifyDataSetChanged();
        }



    }
}