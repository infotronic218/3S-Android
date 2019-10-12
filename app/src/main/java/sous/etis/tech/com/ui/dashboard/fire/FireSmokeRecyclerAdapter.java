package sous.etis.tech.com.ui.dashboard.fire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sous.etis.tech.com.R;

public class FireSmokeRecyclerAdapter extends RecyclerView.Adapter <FireSmokeRecyclerAdapter.Dht11CustomViewHolder>{
 private List<Fire> fires ;
   public FireSmokeRecyclerAdapter(List<Fire> fires){
        this.fires = fires ;
    }

    @NonNull
    @Override
    public Dht11CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fire_smoke_item
                , parent, false);

        return new Dht11CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Dht11CustomViewHolder holder, int position) {
          Fire fire = fires.get(position);

    }

    @Override
    public int getItemCount() {
        return fires.size();
    }

    public class Dht11CustomViewHolder extends RecyclerView.ViewHolder {
         TextView textViewTemperature;
         TextView textViewHumidity;
         TextView textViewName ;

        public Dht11CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHumidity = itemView.findViewById(R.id.fire_smoke_item_textView_State);
            textViewTemperature = itemView.findViewById(R.id.dht11_item_textView_Temperature);
            textViewName = itemView.findViewById(R.id.fire_smoke_item_textView_name);
        }
    }

    public List<Fire> getDht11s() {
        return fires;
    }

    public void setDht11s(List<Fire> fires) {
        this.fires = fires;
    }
}
