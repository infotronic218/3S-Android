package sous.etis.tech.com.ui.dashboard.dht11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sous.etis.tech.com.R;

public class Dht11RecyclerAdapter extends RecyclerView.Adapter <Dht11RecyclerAdapter.Dht11CustomViewHolder>{
 private List<Dht11> dht11s ;
   public Dht11RecyclerAdapter(List<Dht11> dht11s){
        this.dht11s = dht11s ;
    }

    @NonNull
    @Override
    public Dht11CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dht11_item, parent, false);

        return new Dht11CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Dht11CustomViewHolder holder, int position) {
          Dht11 dht11 = dht11s.get(position);
          holder.textViewTemperature.setText(dht11.getTemperature());
          holder.textViewHumidity.setText(dht11.getHumidity());
          holder.textViewName.setText(dht11.getName());
    }

    @Override
    public int getItemCount() {
        return dht11s.size();
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

    public List<Dht11> getDht11s() {
        return dht11s;
    }

    public void setDht11s(List<Dht11> dht11s) {
        this.dht11s = dht11s;
    }
}
