package sous.etis.tech.com;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
   private  MutableLiveData<Boolean> connected ;

  public MainActivityViewModel(){
       connected = new MutableLiveData<>() ;
       connected.setValue(false);
   }





    public LiveData<Boolean> getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected.setValue(connected);
    }
}
