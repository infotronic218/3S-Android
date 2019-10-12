package sous.etis.tech.com;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import sous.etis.tech.com.ui.dashboard.DashboardFragment;
import sous.etis.tech.com.ui.dashboard.dht11.Dht11;
import sous.etis.tech.com.ui.dashboard.dht11.Dht11Fragment;
import sous.etis.tech.com.ui.dashboard.dht11.Dht11ViewModel;
import sous.etis.tech.com.ui.dashboard.dors.DoorFragment;
import sous.etis.tech.com.ui.dashboard.fire.FireSmokeFragment;
import sous.etis.tech.com.ui.dashboard.lamps.Lamp;
import sous.etis.tech.com.ui.dashboard.lamps.LampsFragment;
import sous.etis.tech.com.ui.dashboard.lamps.LampsRecyclerAdapter;
import sous.etis.tech.com.ui.dashboard.lamps.LampsViewModel;
import sous.etis.tech.com.ui.home.HomeFragment;
import sous.etis.tech.com.ui.home.HomeViewModel;
import sous.etis.tech.com.ui.login.AccountActivity;
import sous.etis.tech.com.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeInterface,
        DashboardFragment.DashboardInterface, LampsRecyclerAdapter.ViewInterface {

    final   HomeFragment fragment1 = new HomeFragment();
    final DashboardFragment fragment2 = new DashboardFragment();
    final NotificationsFragment fragment3 = new NotificationsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    final LampsFragment lampsFragment =  new LampsFragment();
    final Dht11Fragment dht11Fragment =  new Dht11Fragment();
    final FireSmokeFragment fireSmokeFragment = new FireSmokeFragment();
    final DoorFragment  doorFragment    = new DoorFragment() ;

    Fragment active = fragment1;
    HomeViewModel homeViewModel;
    MainActivityViewModel mainViewModel ;
    LampsViewModel lampsViewModel ;
    Dht11ViewModel dht11sViewModel;
    EspClient espClient ;
    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog ;
    private Toolbar toolbar ;
    private MenuItem menuItem ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bListener);

        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, lampsFragment).hide(lampsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, dht11Fragment).hide(dht11Fragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, doorFragment).hide(doorFragment).commit();

        fm.beginTransaction().add(R.id.fragment_container, fireSmokeFragment).hide(fireSmokeFragment) .commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();
        //initiate view model
        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        lampsViewModel = ViewModelProviders.of(this).get(LampsViewModel.class);
        dht11sViewModel =ViewModelProviders.of(this).get(Dht11ViewModel.class);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        String espIP =  sharedPreferences.getString(Constants.SHARED_PREFERENCES_IP_KEY, "");

        homeViewModel.getEspIP().setValue(espIP);
        homeViewModel.getMaxTemp().setValue(sharedPreferences.getString(Constants.SHARED_PREFERENCES_MAX_TEMP_KEY, ""));
        progressDialog = new ProgressDialog(this);
        HomeFragmentCommunication();
        notificationManaging();

    }

    private void notificationManaging(){
        NotificationManager nm =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(Constants.FIREBASE_NOTIFICATION_CHANNEL_ID,
                    Constants.FIREBASE_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                  channel.setDescription(Constants.FIREBASE_NOTIFICATION_CHANNEL_DESCRIPTION);
                  channel.enableLights(true);
                  channel.setLightColor(Color.RED);
                  channel.enableVibration(true);
                  channel.setVibrationPattern(new long[] {100, 200, 300, 400, 500, 400, 300, 200, 400});

                  nm.createNotificationChannel(channel);
        }
    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof  HomeFragment){
            HomeFragment hf =(HomeFragment) fragment ;
            hf.setHomeInterface(this);
        }else if(fragment instanceof  DashboardFragment){
            DashboardFragment df = (DashboardFragment) fragment ;
            df.setDashBoardInterface(this);
        }
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        boolean openNot = intent.getBooleanExtra(Constants.OPEN_NOTIFICATION_FRAGMENT, false);
        if(openNot){
            fm.beginTransaction().hide(active).show(fragment3).commit();
            active = fragment3;
        }

    }

    private void HomeFragmentCommunication(){
        // Button start clicked



    }

    private BottomNavigationView.OnNavigationItemSelectedListener bListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.navigation_dashboard :
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.navigation_notifications:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                   return true;

            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.top_menu,menu);
        menuItem = menu.getItem(0);
        mainViewModel.getConnected().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean connected) {
                if(connected){
                    menuItem.setIcon(getResources().getDrawable(R.drawable.green_dot));
                }else{
                    menuItem.setIcon(getResources().getDrawable(R.drawable.red_dot));
                }
            }
        });

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()){
             case  R.id.menu_icon_green_dot:
                 return true;
             case R.id.menu_account:
                 Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                 startActivity(intent);
                 return true;


         }
        return  true;
    }
    private Handler socketHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            progressDialog.hide();
            Toast  toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0,0);
            switch (message.what){
                case  Constants.SOCKET_ON_OPEN:
                    toast.setText("Module connected !");
                    mainViewModel.setConnected(true);
                    toast.show();

                    break;
                case Constants.SOCKET_ON_CLOSE:
                    toast.setText("Connection closed !");
                    mainViewModel.setConnected(false);
                    toast.show();

                    break;
                case Constants.SOCKET_ON_ERROR:
                    toast.setText(" An error to connect to the module !");
                    mainViewModel.setConnected(false);
                    toast.show();
                    break;
                case Constants.SOCKET_ON_MESSAGE:
                    String msg = message.getData().getString("message");
                    handleMessageReceived(msg);
                    toast.setText(msg);




                    break;


            }

            return false;
        }
    });
    //Message received from the module
    private void handleMessageReceived(String message){
        try {
            JSONObject object =new JSONObject(message);
            String type = object.getString("type");
            Log.d("SOCKET", "Type : "+type);
            if(type.equals("Lamps")){
                JSONArray array = object.getJSONArray("data");
                for (int i=0; i<array.length(); i++){
                    JSONObject object1 = array.getJSONObject(i);
                    Lamp lamp = new Lamp(object1.getString("name"), object1.getBoolean("state"));
                    lampsViewModel.update(lamp);
                }
            }else if(type.equals("DHT11")){
                // CASE OF DHT11 Data update the view
                JSONArray array = object.getJSONArray("data");
                for (int i=0; i<array.length(); i++){
                    JSONObject object1 = array.getJSONObject(i);
                    Dht11 dht11 = new Dht11("Room 4", object1.getString("temperature"), object1.getString("humidity"));
                    dht11sViewModel.update(dht11);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Home fragment interface handling
    @Override
    public void btnStartClicked() {
          boolean connected = mainViewModel.getConnected().getValue() ;
          if(!connected){
              //CONNECT THE WEB SOCKET
              try {
                  espClient = new EspClient("http://"+sharedPreferences.getString("IP","192.168.1.106")+":81", socketHandler);

                  progressDialog.setMessage(" Connecting to the web socket");
                  progressDialog.show();
                  espClient.connect();
              } catch (URISyntaxException e) {
                  e.printStackTrace();
                  Toast.makeText(getApplicationContext(), "An error occur while trying to initiate the web socket",Toast.LENGTH_SHORT).show();
              }
          }else{
              // DISCONNECT THE WEB SOCKET
              espClient.close();
          }


    }

    @Override
    public void changeSettings(String ip, String max_temp) {
        sharedPreferences.edit().putString(Constants.SHARED_PREFERENCES_IP_KEY, ip).commit();
        homeViewModel.getEspIP().postValue(ip);
        sharedPreferences.edit().putString(Constants.SHARED_PREFERENCES_MAX_TEMP_KEY, max_temp).commit();
        homeViewModel.getMaxTemp().postValue(max_temp);
        Toast toast =   Toast.makeText(getApplicationContext(), " Settings saved successfully ! ", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



    @Override
    public void openViewFragment(String viewClass) {
        if(viewClass.equals(LampsFragment.class.toString())){
            fm.beginTransaction().hide(active).show(lampsFragment).commit();
            active = lampsFragment;
        }else if(viewClass.equals(Dht11Fragment.class.toString())){
            fm.beginTransaction().hide(active).show(dht11Fragment).commit();
            active = dht11Fragment;
            dht11sViewModel.update(new Dht11("Room 1", "20 °c", "81 %"));
            dht11sViewModel.update(new Dht11("Room 2", "25 °c", "75 %"));
            dht11sViewModel.update(new Dht11("Room 3", "23 °c", "86 %"));
        }else if(viewClass.equals(FireSmokeFragment.class.toString())){
            fm.beginTransaction().hide(active).show(fireSmokeFragment).commit();
            active = fireSmokeFragment ;
        }else if(viewClass.equals(DoorFragment.class.toString())){
            fm.beginTransaction().hide(active).show(doorFragment).commit();
            active =doorFragment ;
        }

    }

    @Override
    public void switchChange(Lamp lamp, boolean checked) {
       //Handle click from an switch button
        try {
            JSONObject object = new JSONObject();
            object.put("name",lamp.getName());
            object.put("state", checked);
            espClient.send(object.toString());
            Log.d("JSON", object.toString());
        } catch (Exception e){

        }

    }
}
