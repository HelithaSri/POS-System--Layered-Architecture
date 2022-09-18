package dao.custom;

import dao.SuperDAO;
import dto.ItemDto;
import javafx.collections.ObservableList;
import view.tm.ReturnTM;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {
    ObservableList<ItemDto> getMostPopulerItemsToday() throws SQLException, ClassNotFoundException;

    ObservableList<ItemDto> getMostPopulerItemsMonth() throws SQLException, ClassNotFoundException;

    ObservableList<ItemDto> getMostPopulerItemsYear() throws SQLException, ClassNotFoundException;

    ObservableList<ReturnTM> getSelectedOrderDetails(String oid) throws SQLException, ClassNotFoundException;

    String getCustomer(String oid) throws SQLException, ClassNotFoundException;

}
