package es.upm.miw.bookshop;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import es.upm.miw.bookshop.Integracion.FirebaseLogin;
import es.upm.miw.bookshop.models.Books;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksListActivity extends AppCompatActivity implements View.OnClickListener {

    static final String LOG_TAG = "MiW";

    private static final String API_BASE_URL = "https://www.googleapis.com";

    private FirebaseLogin firebaseLogin;

    private EditText tituloEdit;
    private EditText autorEdit;

    private Button botonBuscar;
    private Button botonLogout;

    private ListView listaLibros;

    private int backpress = 0;

    private BookRESTAPIService apiService;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(BookRESTAPIService.class);
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

    private void actualizaListaLibros(Books books) {
        Log.e(LOG_TAG, "Adaptando");
        BookAdapter adapter = new BookAdapter(
                this,
                R.layout.item_book,
                books.getItems()
        );
        Log.e(LOG_TAG, "Set adaptador");
        this.listaLibros.setAdapter(adapter);
    }

    private void buscarLibros() {
        Toast.makeText(this, "Buscar libros", Toast.LENGTH_LONG).show();
        final StringBuilder query = new StringBuilder();
        final StringBuilder respuesta = new StringBuilder();

        query.append(this.tituloEdit.getText()).append("+inauthor:").append(this.autorEdit.getText());
        Call<Books> call_async = apiService.getBooks(query.toString(), "lite");

        // Asíncrona
        call_async.enqueue(new Callback<Books>() {

            @Override
            public void onResponse(Call<Books> call, Response<Books> response) {
                Log.e(LOG_TAG, "Actualizando libros...");
                actualizaListaLibros(response.body());
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            @Override
            public void onFailure(Call<Books> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
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