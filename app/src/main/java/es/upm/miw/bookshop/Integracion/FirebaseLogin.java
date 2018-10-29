package es.upm.miw.bookshop.Integracion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Observable;
import java.util.Observer;

public class FirebaseLogin {

    private static FirebaseLogin instance = null;

    static final String LOG_TAG = "MiW";

    private FirebaseAuth mAuth;

    private FirebaseUser mUser;

    private ProgressDialog mProgressDialog;

    private FirebaseLogin() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseLogin getInstance() {
        if (instance == null) {
            instance = new FirebaseLogin();
        }
        return instance;
    }

    public boolean userSigned() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser mUser = mAuth.getCurrentUser();
        return mUser != null;
    }

    public void setmUser(FirebaseUser firebaseUser) {
        this.mUser = firebaseUser;
    }

    public Task<AuthResult> signInWithCredentials(String email, String password, Activity activity) {

        // Create EmailAuthCredential with email and password
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // [START signin_with_email_and_password]
        return mAuth.signInWithCredential(credential);
    }

    public void signOut() {
        mAuth.signOut();
    }

}