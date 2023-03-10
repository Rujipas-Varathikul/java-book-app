package bookkeeping.domain;

import java.util.List;

import org.jsoup.Jsoup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo {
    private String title;
    private String isbn;
    private String isbn13;
    private List<String> authors;
    public String language;
    private String dewey_decimal;
    private List<String> subjects;
    private String synopsis;

    public String getAuthors() {
        if (authors != null) {
            return String.join("\n", authors);
        } else {
            return "";
        }
    }

    public String getSubjects() {
        if (subjects != null) {
            return String.join("\n", subjects);
        } else {
            return "";
        }
    }

    public String getSynopsis() {
        if (synopsis == null) {
            return "";
        }

        return Jsoup.parse(synopsis).text();
    }
}
