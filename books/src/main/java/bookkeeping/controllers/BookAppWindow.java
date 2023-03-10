package bookkeeping.controllers;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bookkeeping.domain.QueryObject;
import bookkeeping.domain.BookInfo;
import bookkeeping.service.GetRequest;
import bookkeeping.task.DataTask;
import bookkeeping.utils.AlertUtils;
import bookkeeping.utils.LineWrapCellFactory;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BookAppWindow implements Initializable{
    public TableView<BookInfo> bookTableView;
    public TextField nameTextField;
    public TextField isbnTextField;
    public TextField languageTextField;
    public TextField authorTextField;
    public TextField subjectTextField;
    public Label statusLabel;
    private QueryObject result;
    public HBox chartsPane;
    GetRequest bookService;
    public ProgressBar progressBar;
    private BookInfo selectedBook;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            bookService = new GetRequest();
        } catch (MalformedURLException error) {
            
        } catch (ProtocolException error) {

        }

        TableColumn<BookInfo, String> titleColumn = new TableColumn<>("Name");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(LineWrapCellFactory.WRAPPING_CELL_FACTORY);
        TableColumn<BookInfo, String> isbnColumn = new TableColumn<>("ISBN10");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        TableColumn<BookInfo, String> isbn13Column = new TableColumn<>("ISBN13");
        isbn13Column.setCellValueFactory(new PropertyValueFactory<>("isbn13"));
        TableColumn<BookInfo, String> dewey_decimalColumn = new TableColumn<>("DDN");
        dewey_decimalColumn.setCellValueFactory(new PropertyValueFactory<>("dewey_decimal"));
        TableColumn<BookInfo, String> languageColumn = new TableColumn<>("Language");
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        TableColumn<BookInfo, String> authorsColumn = new TableColumn<>("Authors");
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        TableColumn<BookInfo, String> subjectsColumn = new TableColumn<>("Subjects");
        subjectsColumn.setCellValueFactory(new PropertyValueFactory<>("subjects"));

        bookTableView.getColumns().add(titleColumn);
        bookTableView.getColumns().add(isbnColumn);
        bookTableView.getColumns().add(isbn13Column);
        bookTableView.getColumns().add(dewey_decimalColumn);
        bookTableView.getColumns().add(languageColumn);
        bookTableView.getColumns().add(authorsColumn);
        bookTableView.getColumns().add(subjectsColumn);    
    }

    public void searchBook(ActionEvent event) {
        String isbnSearchString = isbnTextField.getText();
        String titleSearchString = nameTextField.getText();

        bookTableView.getItems().clear();
        statusLabel.setText("Looking through the library ;)");

        progressBar.setVisible(true);

        try {
            if (isbnSearchString.length() > 0) { 
                DataTask dataTask = new DataTask(isbnSearchString, "isbn");

                dataTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, workerStateEvent -> {
                    statusLabel.setText("Search completed");
                    progressBar.setVisible(false);
                    result = dataTask.getValue();
                    bookTableView.getItems().add(result.getBook());
                });
                new Thread(dataTask).start();
            } else if (titleSearchString.length() > 0) {
                DataTask dataTask = new DataTask(titleSearchString, "title");

                dataTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, workerStateEvent -> {
                    statusLabel.setText("Search completed");
                    progressBar.setVisible(false);
                    result = dataTask.getValue();
                    bookTableView.getItems().addAll(result.getBooks());
                });
                new Thread(dataTask).start();
            }
        } catch (Exception error) {
            // TODO Write exception catching
        }
    }

    public void filterBook(ActionEvent event) {
        String language = languageTextField.getText();
        String author = authorTextField.getText();
        String subject = subjectTextField.getText();

        List<BookInfo> bookList = result.getBooks().stream()
                                    .filter(book -> book.getLanguage().equals(language) | language.length() == 0)
                                    .filter(book -> book.getAuthors().toLowerCase().contains(author.toLowerCase()))
                                    .filter(book -> book.getSubjects().toLowerCase().contains(subject.toLowerCase()))
                                    .toList();
        bookTableView.getItems().clear();
        bookTableView.getItems().addAll(bookList);
    }

    public void alertInformation(MouseEvent event) {
        selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            return;
        }

        AlertUtils.showBookDescription(selectedBook.getTitle(),
                                        selectedBook.getIsbn(),
                                        selectedBook.getIsbn13(),
                                        selectedBook.getDewey_decimal(),
                                        selectedBook.getLanguage(),
                                        selectedBook.getSynopsis());
    }
}
