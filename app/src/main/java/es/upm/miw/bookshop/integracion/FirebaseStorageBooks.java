package es.upm.miw.bookshop.integracion;

import android.app.Activity;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class FirebaseStorageBooks {

    private static FirebaseStorageBooks instance = null;

    static final String LOG_TAG = "MiW";

    // btb Firebase storage variables
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mBookPreviewsStorageReference;


    private FirebaseStorageBooks() {
        this.mFirebaseStorage = FirebaseStorage.getInstance();
        this.mBookPreviewsStorageReference = mFirebaseStorage.getReference().child("cod_book");
    }

    public static FirebaseStorageBooks getInstance() {
        if (instance == null) {
            instance = new FirebaseStorageBooks();
        }
        return instance;
    }

    public void saveImage(Uri urlBook, Activity activity)  {
        Uri selectedImageUri = urlBook;

        // Get a reference to store file at chat_photos/<FILENAME>
        StorageReference photoRef =
            mBookPreviewsStorageReference.child(selectedImageUri.getLastPathSegment());
        // Upload file to Firebase Storage
        photoRef.putFile(selectedImageUri).addOnSuccessListener(
            activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // When the image has successfully uploaded, we get its download URL
                    Task<Uri> urlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                }
            });

    }

}