package sous.etis.tech.com.ui.dashboard.lamps;

import androidx.lifecycle.Observer;
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

import sous.etis.tech.com.MainActivity;
import sous.etis.tech.com.R;

public class LampsFragment extends Fragment {

    private LampsViewModel mViewModel;
    RecyclerView recyclerView;
    LampsRecyclerAdapter  recyclerAdapter;

    public static LampsFragment newInstance() {
        return new LampsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lamps_fragment, container, false);
        recyclerView = root.findViewById(R.id.lamps_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new LampsRecyclerAdapter(new ArrayList<Lamp>());
        recyclerView.setAdapter(recyclerAdapter);
        mViewModel = ViewModelProviders.of(getActivity()).get(LampsViewModel.class);

        mViewModel.getLamps().observe(this, new Observer<ArrayList<Lamp>>() {
            @Override
            public void onChanged(ArrayList<Lamp> lamps) {
                recyclerAdapter.setLamps(lamps);
                recyclerAdapter.notifyDataSetChanged();

            }
        });
        recyclerAdapter.setViewInterface((MainActivity) getActivity());
        return root;
    }

}