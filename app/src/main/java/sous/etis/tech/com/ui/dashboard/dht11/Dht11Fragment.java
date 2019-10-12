package sous.etis.tech.com.ui.dashboard.dht11;

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
import java.util.List;

import sous.etis.tech.com.R;

public class Dht11Fragment extends Fragment {

    private Dht11ViewModel mViewModel;
    private RecyclerView recyclerView ;
    private Dht11RecyclerAdapter dht11RecyclerAdapter ;

    public static Dht11Fragment newInstance() {
        return new Dht11Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dht11_fragment, container, false);
        recyclerView = view.findViewById(R.id.fire_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dht11RecyclerAdapter = new Dht11RecyclerAdapter(new ArrayList<Dht11>());
        recyclerView.setAdapter(dht11RecyclerAdapter );
        mViewModel= ViewModelProviders.of(getActivity()).get(Dht11ViewModel.class);

        mViewModel.dht11s.observe(this, new Observer<List<Dht11>>() {
            @Override
            public void onChanged(List<Dht11> dht11s) {
                dht11RecyclerAdapter.setDht11s(dht11s);
                dht11RecyclerAdapter.notifyDataSetChanged();
            }
        });

        return  view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(Dht11ViewModel.class);
        // TODO: Use the ViewModel
    }

}
