package bo.custom.impl;

import bo.custom.PurchaseOrderBo;
import com.jfoenix.controls.JFXTextField;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import db.DbConnection;
import dto.ItemDto;
import dto.OrderDto;
import entity.Order;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderBoImpl implements PurchaseOrderBo {
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public boolean purchaseOrder(OrderDto orderDto) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (orderDAO.add(new Order(orderDto.getOrderId(),orderDto.getOrderDate(),orderDto.getCustomerId()))) {
                if (orderDetailsDAO.saveOrderDetails(orderDto.getOrderId(), orderDto.getOrderDetails())) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            {
                connection.setAutoCommit(true);
            }
        }

        return false;
    }

    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemIds();
    }

    @Override
    public ItemDto getItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.getItem(itemCode);
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderId();
    }

    @Override
    public TextField searchCustomers(JFXTextField txtSearchCustomer) throws SQLException, ClassNotFoundException {
        return customerDAO.searchCustomers(txtSearchCustomer);
    }
}
