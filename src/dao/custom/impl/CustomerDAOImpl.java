package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import dto.CustomerDto;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import view.tm.CustomerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(Customer customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUE (?,?,?,?,?,?,?)", customerDto.getCustId(), customerDto.getCustTitle(), customerDto.getCustName(), customerDto.getCustAddress(), customerDto.getCity(), customerDto.getProvince(), customerDto.getPostalCode());
    }

    @Override
    public boolean delete(String customerName) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE CustId=?", customerName);
    }

    @Override
    public boolean update(Customer customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET CustTitle=?, CustName=?, CustAddress=?, city=?, Province=?, PostalCode=? WHERE CustId=?", customerDto.getCustTitle(), customerDto.getCustName(), customerDto.getCustAddress(), customerDto.getCity(), customerDto.getProvince(), customerDto.getPostalCode(), customerDto.getCustId());
    }

    @Override
    public ObservableList<CustomerTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTM> list = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            list.add(new CustomerTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return list;
    }

    @Override
    public List<CustomerTM> search(String value) throws SQLException, ClassNotFoundException {
        List<CustomerTM> list = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE CONCAT(CustName,CustAddress,city,Province,PostalCode,CustId) LIKE '%" + value + "%'");
        while (rst.next()) {
            list.add(
                    new CustomerTM(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getString(6),
                            rst.getString(7)
                    )
            );
        }
        return list;
    }

    @Override
    public String getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer ORDER BY CustId DESC LIMIT 1");
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                return "C-00" + tempId;
            } else if (tempId < 100) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }
        } else {
            return "C-000";
        }
    }

    @Override
    public TextField searchCustomers(TextField value) throws SQLException, ClassNotFoundException {
        TextField field = new TextField();

        ResultSet rst = CrudUtil.executeQuery("SELECT CustTitle,CustName,CustId FROM Customer WHERE CONCAT(CustId,CustName) LIKE '%" + value.getText() + "%'");
        while (rst.next()) {
            field.setText(rst.getString(1) + " " + rst.getString(2) + "/" + rst.getString(3));
        }
        return field;
    }

}
