package dao.custom;

import dao.CrudDAO;
import dto.OrderDetailDto;
import entity.OrderDetail;
import javafx.collections.ObservableList;
import view.tm.OrderDetailsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail, String, OrderDetailsTM> {
    boolean saveOrderDetails(String orderId, ArrayList<OrderDetail> items) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDetailsTM> searchOrderIds(String value) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDetailsTM> getAllOrderIds() throws SQLException, ClassNotFoundException;
}
