package sous.etis.tech.com.ui.dashboard.dht11;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Dht11ViewModel extends ViewModel {
  MutableLiveData<List<Dht11>> dht11s ;
  public  Dht11ViewModel(){
      dht11s= new MutableLiveData<>();
      dht11s.setValue(new ArrayList<Dht11>());

  }
    public void update(Dht11 dht11){
        boolean update = false ;
        int i = 0, index=-1 ;
        for (i =  0 ; i< dht11s.getValue().size() ; i++){
            if(dht11s.getValue().get(i).getName().equals(dht11.getName())){
                update = true ;
                index = i ;
            }
        }
        if(update){
            dht11s.getValue().set(index, dht11);
            dht11s.postValue(dht11s.getValue());
        }else {
            dht11s.getValue().add(dht11);

        }
    }

}
