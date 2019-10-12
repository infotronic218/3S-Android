package sous.etis.tech.com.ui.dashboard.lamps;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class LampsViewModel extends ViewModel {
    MutableLiveData<ArrayList<Lamp>> lamps ;

    public LampsViewModel() {
        lamps = new MutableLiveData<>();
        lamps.setValue(new ArrayList<Lamp>());
    }

    public MutableLiveData<ArrayList<Lamp>> getLamps() {
        return lamps;
    }

    public void update(Lamp lamp){
        boolean update = false ;
        int i = 0, index=-1 ;
       for (i =  0 ; i< lamps.getValue().size() ; i++){
           if(lamps.getValue().get(i).getName().equals(lamp.getName())){
               update = true ;
               index = i ;
           }
       }
       if(update){
           lamps.getValue().set(index, lamp);
           ArrayList<Lamp> lamps1 = lamps.getValue();
           lamps.postValue(lamps1);
       }else {
           lamps.getValue().add(lamp);

       }
    }

    public  void AddLamps(Lamp lamp){
         lamps.getValue().add(lamp);
    }
}
