package sous.etis.tech.com.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.R;
import sous.etis.tech.com.models.InstallationUser;

public class AccoutViewFragment extends Fragment {

    private AccountViewModel mViewModel;
    AccountViewInterface viewInterface ;

    public static AccoutViewFragment newInstance() {
        return new AccoutViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.accout_view_fragment, container, false);
        Button logOut = root.findViewById(R.id.accountViewBtnLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewInterface.logOutClick();
            }
        });

        final EditText editTextName = root.findViewById(R.id.account_view_editTextName);
        final EditText editTextUserName = root.findViewById(R.id.account_view_editTextUsername);
        final EditText editTextEmail = root.findViewById(R.id.account_view_editTextEmail);
        final EditText editTextID = root.findViewById(R.id.account_view_editTextID);
        final String instID = getActivity().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(Constants.INSTALLATION_ID, "Not installed ");

        ViewModelProviders.of(getActivity()).get(AccountViewModel.class)
                .userMutableLiveData.observe(this, new Observer<InstallationUser>() {
            @Override
            public void onChanged(InstallationUser user) {
                if(user!=null){
                    editTextName.setText(user.getName());
                    editTextEmail.setText(user.getEmail());
                    editTextUserName.setText(user.getUsername());
                    editTextID.setText(instID);
                }
            }
        });

       return root ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }

    public void setViewInterface(AccountViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    public  interface AccountViewInterface{
        void logOutClick();
    }
}
