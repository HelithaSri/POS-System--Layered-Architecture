package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.ReturnTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ObservableList<ItemDto> getMostPopulerItemsToday() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDto> list = FXCollections.observableArrayList();
        ResultSet set = CrudUtil.executeQuery("SELECT i.Description, SUM(od.OrderQty) AS `Total Qty` FROM `order` o INNER JOIN orderdetail od ON o.orderId=od.OrderId INNER JOIN item i ON i.ItemCode=od.ItemCode WHERE orderDate= CURDATE() GROUP BY od.ItemCode ORDER BY SUM(od.OrderQty) DESC LIMIT 5;");
        while (set.next()) {
            list.add(new ItemDto(
                    set.getString(1),
                    set.getInt(2)
            ));
        }
        return list;
    }

    @Override
    public ObservableList<ItemDto> getMostPopulerItemsMonth() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDto> list = FXCollections.observableArrayList();
        ResultSet set = CrudUtil.executeQuery("SELECT i.Description, SUM(od.OrderQty) AS `Total Qty` FROM `order` o INNER JOIN orderdetail od ON o.orderId=od.OrderId INNER JOIN item i ON i.ItemCode=od.ItemCode WHERE MONTH(orderDate)= MONTH(CURDATE()) GROUP BY od.ItemCode ORDER BY SUM(od.OrderQty) DESC LIMIT 10");
        while (set.next()) {
            list.add(new ItemDto(
                    set.getString(1),
                    set.getInt(2)
            ));
        }
        return list;
    }

    @Override
    public ObservableList<ItemDto> getMostPopulerItemsYear() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDto> list = FXCollections.observableArrayList();
        ResultSet set = CrudUtil.executeQuery("SELECT i.Description, SUM(od.OrderQty) AS `Total Qty` FROM `order` o INNER JOIN orderdetail od ON o.orderId=od.OrderId INNER JOIN item i ON i.ItemCode=od.ItemCode WHERE YEAR(orderDate)= YEAR(CURDATE()) GROUP BY od.ItemCode ORDER BY SUM(od.OrderQty) DESC LIMIT 20");
        while (set.next()) {
            list.add(new ItemDto(
                    set.getString(1),
                    set.getInt(2)
            ));
        }
        return list;
    }

    @Override
    public ObservableList<ReturnTM> getSelectedOrderDetails(String oid) throws SQLException, ClassNotFoundException {
        ObservableList<ReturnTM> list = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT od.*,i.Description, round(((100-od.Discount)*(i.UnitPrice*od.OrderQty))/100,2) AS Total, i.unitPrice FROM orderdetail od INNER JOIN item i ON od.ItemCode=i.ItemCode WHERE od.orderId=?", oid);
        while (rst.next()) {
            list.add(new ReturnTM(
                    rst.getString(2),
                    rst.getString(1),
                    rst.getString(5),
                    rst.getInt(3),
                    rst.getInt(4),
                    rst.getDouble(6),
                    rst.getDouble(7)
            ));
        }
        return list;
    }

    @Override
    public String getCustomer(String oid) throws SQLException, ClassNotFoundException {
        ObservableList<ReturnTM> list = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT c.CustName,c.CustTitle From `order` o INNER JOIN customer c ON c.custId=o.cId WHERE o.orderId=?", oid);
        if (rst.next()) {
            return rst.getString(2) + " " + rst.getString(1);
        }
        return "";
    }
}
