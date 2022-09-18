package bo.custom;

import bo.SuperBO;
import javafx.collections.ObservableList;
import dto.OrderDetailDto;
import view.tm.OrderDetailsTM;
import view.tm.ReturnTM;

import java.sql.SQLException;

public interface ManageOrderBo extends SuperBO {

    ObservableList<OrderDetailsTM> getAllOrderIds() throws SQLException, ClassNotFoundException;

    String getCustomer(String orderId) throws SQLException, ClassNotFoundException;

    ObservableList<ReturnTM> getSelectedOrderDetails(String orderId) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetails(OrderDetailDto orderDetailDto) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDetailsTM> searchOrderIds(String text) throws SQLException, ClassNotFoundException;
}
