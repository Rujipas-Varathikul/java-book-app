package bookkeeping.task;

import bookkeeping.domain.QueryObject;
import bookkeeping.service.GetRequest;
import javafx.concurrent.Task;

public class DataTask extends Task<QueryObject> {

    private String searchText;
    private String fieldName;

    public DataTask(String searchText, String fieldName) {
        this.searchText = searchText;
        this.fieldName = fieldName;
    }

    @Override
    protected QueryObject call() throws Exception {
        GetRequest bookService = new GetRequest();
        if (fieldName.equals("title")) {
            return bookService.getBookByTitle(searchText);
        } else {
            return bookService.getBookByISBN(searchText);
        }
    }
}
