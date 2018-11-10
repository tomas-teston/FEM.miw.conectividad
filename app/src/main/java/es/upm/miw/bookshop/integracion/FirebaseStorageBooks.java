package es.upm.miw.bookshop.integracion;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import es.upm.miw.bookshop.BooksListActivity;
import es.upm.miw.bookshop.OrderListActivity;
import es.upm.miw.bookshop.enums.BookState;
import es.upm.miw.bookshop.models.BookTransfer;


public class FirebaseStorageBooks {

    private static FirebaseStorageBooks instance = null;

    static final String LOG_TAG = "MiW";

    // btb Firebase storage variables
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mBookPreviewsStorageReference;


    private FirebaseStorageBooks() {
        this.mFirebaseStorage = FirebaseStorage.getInstance();
        this.mBookPreviewsStorageReference = mFirebaseStorage.getReference().child("foto_queja");
    }

    public static FirebaseStorageBooks getInstance() {
        if (instance == null) {
            instance = new FirebaseStorageBooks();
        }
        return instance;
    }

    public void saveImage(Uri urlBook, final Activity activity, final BookTransfer bookTransfer)  {

        // Get a reference to store file at chat_photos/<FILENAME>
        StorageReference photoRef =
            mBookPreviewsStorageReference.child(urlBook.getLastPathSegment());
        // Upload file to Firebase Storage
        photoRef.putFile(urlBook).addOnSuccessListener(
            activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // When the image has successfully uploaded, we get its download URL
                    Task<Uri> urlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                    bookTransfer.setUrlFotoQueja(downloadUrl.toString());
                    bookTransfer.setEstado(BookState.CHECKING_ORDER);
                    if (es.upm.miw.bookshop.integracion.FirebaseBBDD.getInstance().modifyBook(bookTransfer)) {
                        ((OrderListActivity) activity).update("add");
                    }
                }
            });
    }

    public void deleteImage(Uri urlBook, final Activity activity, final BookTransfer bookTransfer) {
        // Get a reference to store file at chat_photos/<FILENAME>
        StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(urlBook.toString());

        // Upload file to Firebase Storage
        photoRef.delete().addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                bookTransfer.setUrlFotoQueja(null);
                if (es.upm.miw.bookshop.integracion.FirebaseBBDD.getInstance().modifyBook(bookTransfer)) {
                    ((OrderListActivity) activity).update("delete");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(((OrderListActivity) activity), "Error al eliminar", Toast.LENGTH_LONG).show();
            }
        });
    }

}