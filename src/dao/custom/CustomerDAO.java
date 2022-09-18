package dao.custom;

import dao.CrudDAO;
import entity.Customer;
import javafx.scene.control.TextField;
import view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, String, CustomerTM> {
    String getCustomerId() throws SQLException, ClassNotFoundException;

    TextField searchCustomers(TextField value) throws SQLException, ClassNotFoundException;

    List<CustomerTM> search(String id) throws SQLException, ClassNotFoundException;
}
