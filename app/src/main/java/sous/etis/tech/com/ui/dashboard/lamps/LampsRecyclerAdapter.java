package sous.etis.tech.com.ui.dashboard.lamps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sous.etis.tech.com.R;
import sous.etis.tech.com.ui.dashboard.dors.Door;

public class LampsRecyclerAdapter extends RecyclerView.Adapter<LampsRecyclerAdapter.CustomViewHolder> {
    public ArrayList<Lamp> lamps ;
    public ViewInterface viewInterface;

    public   LampsRecyclerAdapter(ArrayList<Lamp> lamps){
        this.lamps = lamps ;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lamp_item, parent, false );

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
       final Lamp lamp = lamps.get(position);
       holder.textViewLampTitle.setText(lamp.getName());
       holder.switchLamp.setChecked(lamp.isState());
       holder.switchLamp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               boolean checked = holder.switchLamp.isChecked();
               holder.switchLamp.setChecked(!checked);
               viewInterface.switchChange(lamp, checked);

           }
       });
    }

    public void setViewInterface(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    public interface ViewInterface{
        void switchChange(Lamp lamp, boolean checked);
    }

    @Override
    public int getItemCount() {
        return lamps.size();
    }

    public  class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewLampTitle;
        public Switch switchLamp ;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLampTitle = itemView.findViewById(R.id.lamp_item_lamp_name);
            switchLamp  = itemView.findViewById(R.id.lamp_item_lamp_switch) ;

        }
    }

    public void setLamps(ArrayList<Lamp> lamps) {
        this.lamps = lamps;
    }
}
