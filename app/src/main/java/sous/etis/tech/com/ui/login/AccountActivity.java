package sous.etis.tech.com.ui.login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.R;
import sous.etis.tech.com.models.InstallationUser;


public class AccountActivity extends AppCompatActivity
        implements LoginFragment.LoginInter, SignInFragment.SignInInterface , AccoutViewFragment.AccountViewInterface {

    final FragmentManager fm = getSupportFragmentManager() ;
    final  LoginFragment loginFragment = LoginFragment.newInstance();
    final SignInFragment signInFragment = SignInFragment.newInstance() ;
    final AccoutViewFragment viewFragment = new AccoutViewFragment() ;
    Fragment active = viewFragment ;
    AccountViewModel viewModel ;
    SharedPreferences preferences ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        loginFragment.setLoginInter(this);
        signInFragment.setSignInInterface(this);
        viewFragment.setViewInterface(this);
        fm.beginTransaction().add(R.id.account_container,loginFragment).show(loginFragment).commit();
        fm.beginTransaction().add(R.id.account_container,signInFragment).hide(signInFragment).commit();
        fm.beginTransaction().add(R.id.account_container,viewFragment).hide(viewFragment).commit();
        active = loginFragment ;
        viewModel=  ViewModelProviders.of(this).get(AccountViewModel.class);
        InstallationUser user = getUser() ;
        if(user!=null){
            fm.beginTransaction().hide(active).show(viewFragment).commit();
            viewModel.userMutableLiveData.postValue(user);
            active = viewFragment ;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.userMutableLiveData.observe(this, new Observer<InstallationUser>() {
            @Override
            public void onChanged(InstallationUser user) {

                if(user!=null){
                    Log.d("USER", user.getEmail());
                    //Save the user locally
                    Gson gson = new Gson();
                    String data = gson.toJson(user);
                    preferences.edit().putString(Constants.LOGUED_USER, data).apply();
                }
            }
        });

        viewModel.connected.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean connected) {
                if(connected){
                    fm.beginTransaction().hide(active).show(viewFragment).commit();
                    active = viewFragment ;
                }
            }
        });



    }

    private  InstallationUser getUser(){
        Gson gson = new Gson();
        Type type = new TypeToken<InstallationUser>(){}.getType();
        String data = preferences.getString(Constants.LOGUED_USER, null);
        if(data!=null)
        Log.d("USER", data);
        return gson.fromJson(data, type);
    }
    @Override
    public void openSignUpView() {
        fm.beginTransaction().hide(active).show(signInFragment).commit();
        active = signInFragment ;
    }



    @Override
    public void accountSaved() {
        fm.beginTransaction().hide(active).show(loginFragment).commit();
        active = loginFragment ;
    }

    @Override
    public void logOutClick() {
        preferences.edit().putString(Constants.LOGUED_USER, null).apply();
        fm.beginTransaction().hide(active).show(loginFragment).commit();
        active = loginFragment ;
        Toast.makeText(getApplicationContext(), "Logout successfully !", Toast.LENGTH_SHORT).show();
    }
}
