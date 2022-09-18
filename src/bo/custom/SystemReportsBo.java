package bo.custom;

import bo.SuperBO;
import dto.ItemDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface SystemReportsBo extends SuperBO {
    ObservableList<ItemDto> getMostPopulerItemsToday() throws SQLException, ClassNotFoundException;

    ObservableList<ItemDto> getMostPopulerItemsMonth() throws SQLException, ClassNotFoundException;

    ObservableList<ItemDto> getMostPopulerItemsYear() throws SQLException, ClassNotFoundException;
}
