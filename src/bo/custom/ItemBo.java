package bo.custom;

import bo.SuperBO;
import dto.ItemDto;
import javafx.collections.ObservableList;
import view.tm.ItemTM;

import java.sql.SQLException;

public interface ItemBo extends SuperBO {
    
    ObservableList<ItemTM> getItemList() throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDto addItemDtoFromFields) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    ObservableList<ItemTM> searchItems(String text) throws SQLException, ClassNotFoundException;
}
