package bookkeeping.utils;

import bookkeeping.domain.BookInfo;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class LineWrapCellFactory {
    public static final Callback<TableColumn<BookInfo,String>, TableCell<BookInfo,String>> WRAPPING_CELL_FACTORY = 
        new Callback<TableColumn<BookInfo,String>, TableCell<BookInfo,String>>() {
        
        @Override public TableCell<BookInfo,String> call(TableColumn<BookInfo,String> param) {
            TableCell<BookInfo,String> tableCell = new TableCell<BookInfo,String>() {
                @Override protected void updateItem(String item, boolean empty) {
                    if (item == getItem()) return;

                    super.updateItem(item, empty);

                    if (item == null) {
                        super.setText(null);
                        super.setGraphic(null);
                    } else {
                        super.setText(null);
                        Label l = new Label(item);
                        l.setWrapText(true);
                        VBox box = new VBox(l);
                        l.heightProperty().addListener((observable,oldValue,newValue)-> {
                            box.setPrefHeight(newValue.doubleValue()+7);
                            Platform.runLater(()->this.getTableRow().requestLayout());
                        });
                        super.setGraphic(box);
                    }
                }
            };
        return tableCell;
        }
        };
}
