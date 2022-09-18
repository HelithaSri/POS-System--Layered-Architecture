package dao;

import javafx.collections.ObservableList;
import view.tm.ItemTM;

import java.sql.SQLException;

public interface CrudDAO<T, ID, TM> extends SuperDAO{
    boolean add(T t) throws SQLException, ClassNotFoundException;

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException, ClassNotFoundException;

    ObservableList<TM> getList() throws SQLException, ClassNotFoundException;

}
