package sous.etis.tech.com.ui.dashboard.fire;

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

public class FireSmokeFragment extends Fragment {

    private FireSmokeViewModel mViewModel;

    public static FireSmokeFragment newInstance() {
        return new FireSmokeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =        inflater.inflate(R.layout.fire_smoke_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fire_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Fire>  list = new ArrayList<>();
        list.add(new Fire());
        list.add(new Fire());
        list.add(new Fire());
        list.add(new Fire());
        FireSmokeRecyclerAdapter adapter = new FireSmokeRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);


        return  view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FireSmokeViewModel.class);
        // TODO: Use the ViewModel
    }

}
