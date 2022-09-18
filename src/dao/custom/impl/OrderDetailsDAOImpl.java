package dao.custom.impl;

import dao.CrudUtil;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import dto.OrderDetailDto;
import entity.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.OrderDetailsTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean add(OrderDetail orderDetailDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail orderDetailDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE orderdetail SET OrderQty=(OrderQty-?) WHERE OrderId=? AND ItemCode=?", orderDetailDto.getOrderQty(), orderDetailDto.getOrderId(), orderDetailDto.getItemCode());
    }

    @Override
    public ObservableList<OrderDetailsTM> getList() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveOrderDetails(String orderId, ArrayList<OrderDetail> items) throws SQLException, ClassNotFoundException {
        for (OrderDetail temp : items) {
            if (CrudUtil.executeUpdate("INSERT INTO orderdetail VALUES(?,?,?,?)", temp.getItemCode(), orderId, temp.getOrderQty(), temp.getDiscount())) {
                if (itemDAO.updateQty(temp.getItemCode(), temp.getOrderQty())) {
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public ObservableList<OrderDetailsTM> searchOrderIds(String value) throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetailsTM> list = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM orderdetail WHERE OrderId LIKE '%" + value + "%' GROUP BY orderId ORDER BY OrderId DESC");
        while (rst.next()) {
            list.add(new OrderDetailsTM(
                    rst.getString(1)
            ));
        }
        return list;
    }

    @Override
    public ObservableList<OrderDetailsTM> getAllOrderIds() throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetailsTM> list = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM orderdetail GROUP BY orderId ORDER BY OrderId DESC");
        while (rst.next()) {
            list.add(new OrderDetailsTM(
                    rst.getString(1)
            ));
        }
        return list;
    }
}
