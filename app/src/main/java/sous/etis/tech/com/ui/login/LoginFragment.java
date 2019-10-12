package sous.etis.tech.com.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.R;
import sous.etis.tech.com.models.InstallationUser;

public class LoginFragment extends Fragment {

    private AccountViewModel mViewModel;
    private LoginInter loginInter ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        Button signUpBtn = root.findViewById(R.id.lf_sign_up_btn);
        Button loginBtn  = root.findViewById(R.id.lf_login_btn);
        final EditText editTextUsername = root.findViewById(R.id.lf_username);
        final EditText editTextPassword = root.findViewById(R.id.lf_password);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInter.openSignUpView();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(Constants.INSTALLATION_COLLECTION_NAME)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                        for (int i=0; i< task.getResult().size(); i++){
                            documentSnapshots.get(i).getReference()
                                    .collection("users")
                                    .whereEqualTo("username",editTextUsername.getText().toString())
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                    if(!queryDocumentSnapshots.isEmpty()){
                                        Toast.makeText(getContext(), "Login successfully !", Toast.LENGTH_LONG).show();
                                        InstallationUser user =queryDocumentSnapshots.toObjects(InstallationUser.class).get(0);
                                        Log.d("DATA", user.getEmail());
                                        mViewModel.userMutableLiveData.postValue(user);
                                        mViewModel.connected.postValue(true);

                                    }else{
                                        Toast.makeText(getContext(), "User not found in the database", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });

                //loginInter.login(editTextEmail.getText().toString(),editTextPassword.getText().toString());
            }
        });
        /*Complete the login validation and saved user locally */


        return root ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);

        // TODO: Use the ViewModel
    }

    public  void setLoginInter(LoginInter loginInter){
        this.loginInter =  loginInter ;
    }


    public interface LoginInter{
        void openSignUpView();

    }

}
