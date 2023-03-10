package bookkeeping.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryObject {
    private BookInfo book;
    private int total;
    private List<BookInfo> books;
}
