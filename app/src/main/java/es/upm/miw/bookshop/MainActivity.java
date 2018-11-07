/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.upm.miw.bookshop;

// https://github.com/firebase/quickstart-android/blob/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/java/EmailPasswordActivity.java

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import es.upm.miw.bookshop.Integracion.FirebaseBBDD;
import es.upm.miw.bookshop.Integracion.FirebaseLogin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String LOG_TAG = "MiW";

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseLogin firebaseLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fields
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        // Click listeners
        findViewById(R.id.buttonSignIn).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        firebaseLogin = FirebaseLogin.getInstance();
        // [END initialize_auth]
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        this.firebaseLogin.signOut();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSignIn) {
            signInWithCredentials();
        } else if (i == R.id.buttonExit) {
            exit();
        }
    }

    private boolean validateLinkForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.field_required));
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(getString(R.string.field_required));
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void signInWithCredentials() {
        if (!validateLinkForm()) {
            return;
        }

        // Get email and password from form
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        this.firebaseLogin.signInWithCredentials(email, password, this).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i(LOG_TAG, "signInWithCredentials:success");
                    FirebaseLogin.getInstance().userSigned();
                    Intent intent = new Intent(getApplicationContext(), BooksListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.logKo, Toast.LENGTH_LONG).show();
                    Log.w(LOG_TAG, "signInWithCredentials:failure", task.getException());
                }
            }
        });
    }

    private void signOut() {
        this.firebaseLogin.signOut();
    }

    private void exit() {
        signOut();
        this.finish();
    }
}