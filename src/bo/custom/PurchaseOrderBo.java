package bo.custom;

import bo.SuperBO;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import dto.OrderDto;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseOrderBo extends SuperBO {
    boolean purchaseOrder(OrderDto orderDto) throws SQLException;

    List<String> getAllItemIds() throws SQLException, ClassNotFoundException;

    ItemDto getItem(String itemCode) throws SQLException, ClassNotFoundException;

    String getOrderId() throws SQLException, ClassNotFoundException;

    TextField searchCustomers(JFXTextField txtSearchCustomer) throws SQLException, ClassNotFoundException;
}
