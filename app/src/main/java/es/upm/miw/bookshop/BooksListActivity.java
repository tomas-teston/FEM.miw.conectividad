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

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import es.upm.miw.bookshop.Integracion.FirebaseLogin;

public class BooksListActivity extends AppCompatActivity implements View.OnClickListener {

    static final String LOG_TAG = "MiW";

    private FirebaseLogin firebaseLogin;

    private EditText tituloEdit;
    private EditText autorEdit;

    private Button botonBuscar;
    private Button botonLogout;

    private ListView listaLibros;

    private int backpress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_list);

        // Fields
        this.tituloEdit = (EditText) findViewById(R.id.editTitulo);
        this.autorEdit = (EditText) findViewById(R.id.editAutor);

        this.botonBuscar = (Button) findViewById(R.id.buscarButton);
        this.botonLogout = (Button) findViewById(R.id.logoutButton);

        this.listaLibros = (ListView) findViewById(R.id.listaLibros);

        // Click listeners
        this.botonBuscar.setOnClickListener(this);
        this.botonLogout.setOnClickListener(this);

        firebaseLogin = FirebaseLogin.getInstance();

        this.listaLibros.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (!firebaseLogin.userSigned()) {
            this.finish();
        }
    }

    private void logout() {
        this.firebaseLogin.signOut();
        this.finish();
    }

    @Override
    public void onBackPressed(){
        backpress++;    // La primera vez es 1 (aviso), la segunda es 2 (finish)

        if (backpress > 1) {
            this.finishAffinity();
        } else {
            Toast.makeText(getApplicationContext(), "Pulse de nuevo para salir.", 3000).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backpress = 0;
            }
        }, 3000);
    }

    private void buscarLibros() {
        Toast.makeText(this, "Buscar libros", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buscarButton) {
            this.buscarLibros();
        } else if (v.getId() == R.id.logoutButton) {
            this.logout();
        }
    }
}