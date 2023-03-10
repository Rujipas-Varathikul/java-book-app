package bookkeeping.service;

import bookkeeping.domain.QueryObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookService {

    @GET("/book/{isbn}")
    public Call<QueryObject> getBookByISBN(@Path("isbn") String isbn);

    @GET("/books/{query}")
    public Call<QueryObject> getBookByQuery(@Path("query") String query,
                                        @Query("pageSize") int pageSize,
                                        @Query("column") String column);
}
