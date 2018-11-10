package es.upm.miw.bookshop.integracion;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import es.upm.miw.bookshop.OrderListActivity;
import es.upm.miw.bookshop.models.BookTransfer;

public class FirebaseBBDD {

    private static FirebaseBBDD instance = null;

    static final String LOG_TAG = "MiW";

    // btb Firebase database variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBooksDatabaseReference;
    private ChildEventListener mChildEventListener;


    private FirebaseBBDD() {
        this.mFirebaseDatabase = FirebaseDatabase.getInstance();
        this.mBooksDatabaseReference = mFirebaseDatabase.getReference().child("books");
    }

    public static FirebaseBBDD getInstance() {
        if (instance == null) {
            instance = new FirebaseBBDD();
        }
        return instance;
    }

    public void sendBook(BookTransfer book) {
        this.mBooksDatabaseReference.push().setValue(book);
    }

    public boolean modifyBook(BookTransfer book) {
        Map<String, Object> booksUpdates = new HashMap<>();
        DatabaseReference mBooksDataReferenceChange = mFirebaseDatabase.getReference().child("books/" + book.getId());
        mBooksDataReferenceChange.setValue(book);
        return true;
    }

    public void loadBooks(final OrderListActivity activity) {
        if(mChildEventListener==null){
            // btb this same code is executed onCreate
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    // Deserialize data from DB into our FriendlyMessage object
                    BookTransfer book = dataSnapshot.getValue(BookTransfer.class);
                    book.setId(dataSnapshot.getKey());
                    activity.loadBookOrder(book);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            };
            mBooksDatabaseReference.addChildEventListener(mChildEventListener);
        } else {
            mChildEventListener = null;
            this.loadBooks(activity);
        }
    }

}