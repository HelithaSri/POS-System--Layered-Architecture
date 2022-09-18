package bo.custom.impl;

import bo.custom.ManageOrderBo;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailsDAO;
import dao.custom.QueryDAO;
import db.DbConnection;
import dto.OrderDetailDto;
import entity.OrderDetail;
import javafx.collections.ObservableList;
import view.tm.OrderDetailsTM;
import view.tm.ReturnTM;

import java.sql.Connection;
import java.sql.SQLException;

public class ManageOrderBoImpl implements ManageOrderBo {
    private final OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ObservableList<OrderDetailsTM> getAllOrderIds() throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.getAllOrderIds();
    }

    @Override
    public String getCustomer(String orderId) throws SQLException, ClassNotFoundException {
        return queryDAO.getCustomer(orderId);
    }

    @Override
    public ObservableList<ReturnTM> getSelectedOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        return queryDAO.getSelectedOrderDetails(orderId);
    }

    @Override
    public boolean updateOrderDetails(OrderDetailDto oD) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (orderDetailsDAO.update(new OrderDetail(oD.getItemCode(),oD.getOrderId(),oD.getOrderQty(),oD.getDiscount()))) {
                if (itemDAO.updateReturnQty(oD.getItemCode(), oD.getOrderQty())) {
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
    public ObservableList<OrderDetailsTM> searchOrderIds(String text) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.searchOrderIds(text);
    }
}
