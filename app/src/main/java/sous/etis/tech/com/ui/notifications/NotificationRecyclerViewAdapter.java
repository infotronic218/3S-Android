package sous.etis.tech.com.ui.notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.R;

public class NotificationRecyclerViewAdapter  extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.CustomViewHolder> {
    private List<Notification> notifications ;
    private Context activity;
    public NotificationRecyclerViewAdapter(Context activity,List<Notification> notifications){
        this.notifications = notifications ;
        this.activity = activity ;

    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRecyclerViewAdapter.CustomViewHolder holder, final int position) {
        Notification not = notifications.get(position);
        holder.textViewTitle.setText( not.getTitle());
        holder.textViewBody.setText(not.getBody());
        Date date = new Date();
        if(not.getDate()!=null)
        date.setTime(Long.parseLong(not.getDate()));
        holder.textViewDate.setText(date.toString());
        final SharedPreferences preferences = activity.
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, activity.MODE_PRIVATE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                notifications.remove(position);
                Gson gson = new Gson() ;
                Type type  = new TypeToken<List<Notification>>(){}.getType() ;
                String data = gson.toJson(notifications);


                preferences.edit().putString(Constants.SHARED_PREFERENCES_NOTIFICATION_LIST,data ).apply();

                NotificationRecyclerViewAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewBody ;
        TextView textViewDate ;
        TextView delete ;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBody = itemView.findViewById(R.id.notification_textViewBody);
            textViewDate = itemView.findViewById(R.id.notification_textViewDate);
            textViewTitle  = itemView.findViewById(R.id.notification_textViewTitle);
            delete  = itemView.findViewById(R.id.notification_textViewDelete);
        }
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
