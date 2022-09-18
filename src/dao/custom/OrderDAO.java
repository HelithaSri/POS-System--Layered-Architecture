package dao.custom;

import dao.CrudDAO;
import dto.OrderDto;
import entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order, String, Object> {
    String getOrderId() throws SQLException, ClassNotFoundException;

}
