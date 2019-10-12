package sous.etis.tech.com.ui.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import sous.etis.tech.com.Constants;
import sous.etis.tech.com.R;
import sous.etis.tech.com.models.InstallationUser;

public class SignInFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AccountViewModel mViewModel;
    private SignInInterface signInInterface;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.sign_fragment, container, false);
        final EditText email = root.findViewById(R.id.sf_email);
        final EditText username = root.findViewById(R.id.sf_username);
        final EditText name = root.findViewById(R.id.sf_name);
        final EditText password  = root.findViewById(R.id.sf_password);
        final EditText installationId = root.findViewById(R.id.sf_intallationID);
        Button signUpBtn = root.findViewById(R.id.sf_sign_up_btn);
        final SharedPreferences preferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, getActivity().MODE_PRIVATE);
        final String token = preferences.getString(Constants.FIREBASE_NOTIFICATION_TOKEN, null);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final InstallationUser user = new InstallationUser();
               user.setEmail(email.getText().toString());
               user.setName(name.getText().toString());
               user.setUsername(username.getText().toString());
               user.setPassword(password.getText().toString());
               user.setToken(token);
                db.collection(Constants.INSTALLATION_COLLECTION_NAME).document(installationId.getText().toString()).
                        get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(!documentSnapshot.exists()){
                            Toast.makeText(getContext(), "The installation ID is not correct. Please check it and try again.", Toast.LENGTH_LONG).show();
                        }else{

                            documentSnapshot.getReference().collection("users").document(user.getUsername())
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), "Saved successfully ! You can now login to your account", Toast.LENGTH_SHORT).show();
                                    preferences.edit().putString(Constants.INSTALLATION_ID, installationId.getText().toString()).apply();
                                    signInInterface.accountSaved();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                   Toast toast = Toast.makeText(getContext(), "Error for registering your account", Toast.LENGTH_LONG);
                                   toast.setGravity(Gravity.CENTER, 0, 0);
                                   toast.show();
                                }
                            });
                        }
                    }
                });


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

    public void setSignInInterface(SignInInterface signInInterface) {
        this.signInInterface = signInInterface;
    }

    public interface SignInInterface {
        void accountSaved();

    }
}
