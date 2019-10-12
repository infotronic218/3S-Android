package sous.etis.tech.com.ui.dashboard.dors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import sous.etis.tech.com.R;


public class DoorRecyclerViewAdapter  extends RecyclerView.Adapter<DoorRecyclerViewAdapter.DoorViewHolder> {
    List<Door> doors ;
    DoorRecyclerViewAdapter(List<Door> doors){
        this.doors = doors ;
    }
    @NonNull
    @Override
    public DoorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.door_item, parent, false);
        return new DoorViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull DoorViewHolder holder, int position) {
        Door door = doors.get(position);
        holder.textViewName.setText(door.getName());
        String state = door.isOpen()? "Open": "Closed";
        holder.textViewState.setText(state);

    }

    @Override
    public int getItemCount() {
        return doors.size();
    }

    public class  DoorViewHolder extends RecyclerView.ViewHolder{
         TextView textViewName ;
         TextView textViewState ;
        public DoorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.door_item_textView_name);
            textViewState = itemView.findViewById(R.id.door_item_textView_State);
        }
    }
}
