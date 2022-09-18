package bo.custom;

import bo.SuperBO;
import dto.CustomerDto;
import javafx.collections.ObservableList;
import view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBO {

    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean addCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    List<CustomerTM> searchCustomer(String text) throws SQLException, ClassNotFoundException;

    ObservableList<CustomerTM> getCustomerList() throws SQLException, ClassNotFoundException;

    String getCustomerId() throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String text) throws SQLException, ClassNotFoundException;
}
