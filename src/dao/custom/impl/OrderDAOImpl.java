package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dto.OrderDto;
import entity.Order;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    private final OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

    @Override
    public boolean add(Order orderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` Values(?,?,?)", orderDto.getOrderId(), orderDto.getOrderDate(), orderDto.getcId());

    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Order orderDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ObservableList<Object> getList() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` ORDER BY `orderId` DESC LIMIT 1");
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                return "O-00" + tempId;
            } else if (tempId < 100) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-000";
        }
    }
}
