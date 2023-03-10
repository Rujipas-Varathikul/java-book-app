package bookkeeping.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import bookkeeping.domain.QueryObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRequest {
    private BookService service;
    
    public GetRequest() throws MalformedURLException,
            ProtocolException {
  
            OkHttpClient.Builder defaultHttpClient = new OkHttpClient.Builder();
            defaultHttpClient.interceptors().clear();
            defaultHttpClient.addInterceptor(
                chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "49392_6bf54c8363a145be09080e40b11c9d39")
                        .build();
                    return chain.proceed(request);
                }
            );
            
            Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.pro.isbndb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient.build())
            .build();

            service = retrofit.create(BookService.class);
    }

    public QueryObject getBookByISBN(String isbn) throws IOException {
        Call<QueryObject> callSync = service.getBookByISBN(isbn);

        Response<QueryObject> response = callSync.execute();
        QueryObject book = response.body();
        return book;
    }

    public QueryObject getBookByTitle(String query) throws IOException {
        Call<QueryObject> callSync = service.getBookByQuery(query, 100, "title");

        Response<QueryObject> response = callSync.execute();
        QueryObject bookList = response.body();
        return bookList;
    }
}
