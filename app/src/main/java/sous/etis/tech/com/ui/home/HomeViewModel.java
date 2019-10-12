package sous.etis.tech.com.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> btnStartClicked;
    private MutableLiveData<Boolean> btnCloseClicked;
    private MutableLiveData<String> espIP ;
    private MutableLiveData<String> maxTemp ;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        btnStartClicked = new MutableLiveData<>();
        btnCloseClicked = new MutableLiveData<>();
        espIP = new MutableLiveData<>();
        mText.setValue("Settings");
        maxTemp = new MutableLiveData<>() ;


    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String text ){
        mText.setValue(text);
    }

    public LiveData<Boolean> getBtnStartClicked(){
        return btnStartClicked;
    }
    public void setBtnStartClicked(){
        btnStartClicked.setValue(!btnStartClicked.getValue());
    }


    public MutableLiveData<Boolean> getBtnCloseClicked() {
        return btnCloseClicked;
    }

    public MutableLiveData<String> getEspIP() {
        return espIP;
    }

    public void setBtnCloseClicked(){
        btnCloseClicked.setValue(true);
    }

    public MutableLiveData<String> getMaxTemp() {
        return maxTemp;
    }
}

