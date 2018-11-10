package es.upm.miw.bookshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.bookshop.integracion.FirebaseBBDD;
import es.upm.miw.bookshop.integracion.FirebaseStorageBooks;
import es.upm.miw.bookshop.integracion.FirebaseLogin;
import es.upm.miw.bookshop.models.BookTransfer;

public class OrderListActivity extends AppCompatActivity {

    static final String LOG_TAG = "MiW";

    private FirebaseLogin firebaseLogin;

    OrderAdapter adapter;

    ListView mListOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);

        firebaseLogin = FirebaseLogin.getInstance();

        this.mListOrder = (ListView) findViewById(R.id.listaOrders);

        this.setAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (!firebaseLogin.userSigned()) {
            this.finish();
        }
    }

    public void setAdapter() {
        List<BookTransfer> libros = new ArrayList<>();
        this.adapter = new OrderAdapter(
                this,
                R.layout.item_order,
                libros
        );
        this.mListOrder.setAdapter(this.adapter);

        FirebaseBBDD.getInstance().loadBooks(this);
    }

    public void loadBookOrder(BookTransfer book) {
        this.adapter.add(book);
    }

    public void update(String operation) {
        if (operation.equalsIgnoreCase("add")) {
            Toast.makeText(this, R.string.okrelamacion, Toast.LENGTH_LONG).show();
        } else if (operation.equalsIgnoreCase("delete")) {
            Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_LONG).show();
        }

        this.setAdapter();
    }

    @Override
    public void onActivityResult(int requestcode, int resultCode, Intent data){
        super.onActivityResult(requestcode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Toast.makeText(this, R.string.adjutandoImagen, Toast.LENGTH_LONG).show();
            FirebaseStorageBooks.getInstance().saveImage(selectedImageUri, this, this.adapter.getLibros().get(requestcode));
        }
    }
}