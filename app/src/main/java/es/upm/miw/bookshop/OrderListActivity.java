package es.upm.miw.bookshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.bookshop.integracion.FirebaseBBDD;
import es.upm.miw.bookshop.integracion.FirebaseLogin;
import es.upm.miw.bookshop.models.BookTransfer;

public class OrderListActivity extends AppCompatActivity {

    static final String LOG_TAG = "MiW";

    private FirebaseLogin firebaseLogin;

    OrderAdapter adapter;

    private BookRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);

        firebaseLogin = FirebaseLogin.getInstance();

        ListView mListOrder = (ListView) findViewById(R.id.listaOrders);

        List<BookTransfer> libros = new ArrayList<>();
        this.adapter = new OrderAdapter(
                this,
                R.layout.item_order,
                libros
        );
        mListOrder.setAdapter(this.adapter);

        FirebaseBBDD.getInstance().loadBooks(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (!firebaseLogin.userSigned()) {
            this.finish();
        }
    }

    public void loadBookOrder(BookTransfer book) {
        this.adapter.add(book);
    }
}