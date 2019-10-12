package sous.etis.tech.com.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import sous.etis.tech.com.MainActivityViewModel;
import sous.etis.tech.com.R;

public class HomeFragment extends Fragment {

    public HomeViewModel homeViewModel;;
    private TextView textView ;
    private HomeInterface  homeInterface ;
    private Button buttonSave ;
    private AlertDialog.Builder alertDialog ;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
         textView = root.findViewById(R.id.text_home);
         buttonSave = root.findViewById(R.id.hf_button_save);
         final TextView textViewIP = root.findViewById(R.id.hf_textView_IP);
         final TextView textViewMaxTemp = root.findViewById(R.id.hf_textViewMaxTemp);

        final Button btnStart = root.findViewById(R.id.hf_btn_start);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class)
                .getConnected().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean connected) {
                if(connected){
                    btnStart.setText("Disconnect");
                    btnStart.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
                }else{
                    btnStart.setText("Connect");
                    btnStart.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              homeInterface.btnStartClicked();

            }
        });
        // IP address changed
        homeViewModel.getEspIP().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewIP.setText(s);
            }
        });
        homeViewModel.getMaxTemp().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewMaxTemp.setText(s+ " ° C");
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new AlertDialog.Builder(getContext()) ;
                View alertView = LayoutInflater.from(getContext()).inflate(R.layout.setting_dialog, null, false);
                alertDialog.setView(alertView);
                alertDialog.setCancelable(false);
                Button btnSave = alertView.findViewById(R.id.settings_dialog_button_save);
                Button btnCancel = alertView.findViewById(R.id.settings_dialog_button_cancel);
                final EditText editTextIP = alertView.findViewById(R.id.settings_dialog_editText_IP);
                editTextIP.setText(homeViewModel.getEspIP().getValue());
                final EditText editTextTemp = alertView.findViewById(R.id.settings_dialog_editText_Max_temp);
                editTextTemp.setText(homeViewModel.getMaxTemp().getValue());
                final AlertDialog  dialog =  alertDialog.create();
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ip  = editTextIP.getText().toString() ;
                        // VALIDATION OF IP ADDRESS
                        String[] list = ip.split("\\.");
                        Log.d("TEST", list.length + " : "+ list.toString());
                        boolean hastSup255orInf0 = false ;
                        for(int i=0; i<list.length; i++){
                            int num = Integer.valueOf(list[i]);
                            if(num<=0 || num>=255){
                                hastSup255orInf0 = true ;
                                break;
                            }
                        }
                        if(list.length!=4 || hastSup255orInf0  ){
                         Toast toast =   Toast.makeText(getContext(), "Invalid IP address !", Toast.LENGTH_SHORT);
                         toast.setGravity(Gravity.CENTER, 0, 0);
                         toast.show();
                        }else{
                            homeInterface.changeSettings(ip,  editTextTemp.getText().toString());
                            dialog.dismiss();
                        }



                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }

        });

        return root;
    }

    public void setHomeInterface(HomeInterface homeInterface) {
        this.homeInterface = homeInterface;
    }

    public interface  HomeInterface{
      void btnStartClicked();
      void changeSettings(String ip , String max_temp);

    }
}


