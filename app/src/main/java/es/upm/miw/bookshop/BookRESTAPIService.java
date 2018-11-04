package es.upm.miw.bookshop;

import es.upm.miw.bookshop.models.Books;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@SuppressWarnings("Unused")
interface BookRESTAPIService {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    // https://www.googleapis.com/books/v1/volumes?q=flowers+inauthor:keyes&projection=lite
    @GET("/books/v1/volumes")
    Call<Books> getBooks(@Query("q") String query, @Query("projection") String projection);

}
