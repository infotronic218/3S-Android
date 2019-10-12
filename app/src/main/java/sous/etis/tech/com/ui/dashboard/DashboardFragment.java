package sous.etis.tech.com.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import sous.etis.tech.com.R;
import sous.etis.tech.com.ui.dashboard.dht11.Dht11;
import sous.etis.tech.com.ui.dashboard.dht11.Dht11Fragment;
import sous.etis.tech.com.ui.dashboard.dors.DoorFragment;
import sous.etis.tech.com.ui.dashboard.fire.FireSmokeFragment;
import sous.etis.tech.com.ui.dashboard.lamps.LampsFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private GridView gridView ;
    private  GridViewAdapter adapter ;
    private DashboardInterface dashbordInterface ;
    private List<ViewType> viewTypes ;
    String [] data = {"Lamps" ,"Temperatures", "Fire", "Windows "};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        gridView = root.findViewById(R.id.df_gridView);
        final TextView textView = root.findViewById(R.id.text_dashboard);
         viewTypes = new ArrayList<>();
         viewTypes.add(new ViewType("Lamps", LampsFragment.class.toString(), R.drawable.lamp));
          viewTypes.add(new ViewType("Temperature", Dht11Fragment.class.toString(), R.drawable.temperature_humidity));
        viewTypes.add(new ViewType("Fire/Smoke", FireSmokeFragment.class.toString(), R.drawable.fire_icon));

        viewTypes.add(new ViewType("Door/Windows", DoorFragment.class.toString(), R.drawable.door_windows_icon));

        adapter = new GridViewAdapter(getContext(), viewTypes);
         gridView.setAdapter(adapter);

         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                dashbordInterface.openViewFragment(viewTypes.get(i).getViewClass());
             }
         });

        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public interface DashboardInterface {
        public void openViewFragment(String classFragment);
    }

    public void setDashBoardInterface(DashboardInterface dashbordInterface) {
        this.dashbordInterface = dashbordInterface;
    }
}