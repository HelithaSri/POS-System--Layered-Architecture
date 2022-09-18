package bo.custom.impl;

import bo.custom.CustomerBo;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDto;
import entity.Customer;
import javafx.collections.ObservableList;
import view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    CustomerDAO customerDAOa = new CustomerDAOImpl();

    @Override
    public boolean updateCustomer(CustomerDto c) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(c.getCustomerId(),c.getCustomerTitle(),c.getCustomerName(),c.getCustomerAddress(),c.getCity(),c.getProvince(),c.getPostalCode()));
    }

    @Override
    public boolean addCustomer(CustomerDto c) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(c.getCustomerId(),c.getCustomerTitle(),c.getCustomerName(),c.getCustomerAddress(),c.getCity(),c.getProvince(),c.getPostalCode()));
    }

    @Override
    public List<CustomerTM> searchCustomer(String text) throws SQLException, ClassNotFoundException {
        return customerDAO.search(text);
    }

    @Override
    public ObservableList<CustomerTM> getCustomerList() throws SQLException, ClassNotFoundException {
        return customerDAO.getList();
    }

    @Override
    public String getCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerId();
    }

    @Override
    public boolean deleteCustomer(String text) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(text);
    }
}
