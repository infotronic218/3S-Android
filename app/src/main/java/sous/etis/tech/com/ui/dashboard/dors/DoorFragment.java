package sous.etis.tech.com.ui.dashboard.dors;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sous.etis.tech.com.R;

public class DoorFragment extends Fragment {

    private DoorViewModel mViewModel;

    public static DoorFragment newInstance() {
        return new DoorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View root =  inflater.inflate(R.layout.door_fragment, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.door_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Door> doors = new ArrayList<>();
        doors.add(new Door("Door 1", "", true));
        doors.add(new Door("Window 1", "", false));
        doors.add(new Door("Windows 2", "", true));
        DoorRecyclerViewAdapter adapter = new DoorRecyclerViewAdapter(doors);
        recyclerView.setAdapter(adapter);
       return root ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DoorViewModel.class);
        // TODO: Use the ViewModel
    }

}
