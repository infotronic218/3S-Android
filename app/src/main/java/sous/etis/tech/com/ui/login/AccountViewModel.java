package sous.etis.tech.com.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sous.etis.tech.com.models.InstallationUser;

public class AccountViewModel extends ViewModel {
    MutableLiveData<InstallationUser> userMutableLiveData ;
    MutableLiveData<Boolean> connected ;


    public AccountViewModel(){
        userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData.setValue(null);
        connected = new MutableLiveData<>() ;
        connected.setValue(false);


    }
    // TODO: Implement the ViewModel
}
