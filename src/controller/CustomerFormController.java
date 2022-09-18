package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.ValidationUtil;
import view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {

    public JFXTextField txtCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXComboBox  cmbTitle;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public JFXButton btnUpdate;
    public JFXButton btnAdd;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCusId;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public JFXTextField txtSearch;
    public JFXButton btnClear;

    private CustomerBo customerBo = (CustomerBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    //Pattern customerIdPattern = Pattern.compile("^(C)[-]?[0-9]{2}$");
    Pattern customerNamePattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern cityPattern = Pattern.compile("^[A-z ]{5,20}$");
    Pattern provincePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern postalCodePattern = Pattern.compile("^[0-9]{5}$");


    private void storeValidations() {
        //map.put(txtCustomerId,customerIdPattern);
        map.put(txtName,customerNamePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtCity,cityPattern);
        map.put(txtProvince,provincePattern);
        map.put(txtPostalCode,postalCodePattern);
    }

    public void btnUpdateAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        CustomerDto customerDto = new CustomerDto(
                txtCustomerId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
        if (customerBo.updateCustomer(customerDto)){
            showAllCustomersOnTable();
            clearField();
            new Alert(Alert.AlertType.CONFIRMATION,"Update CustomerDto").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void btnAddAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        CustomerDto customerDto = new CustomerDto(
                txtCustomerId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );

        if (customerBo.addCustomer(customerDto)){
            clearField();
            showAllCustomersOnTable();
            setCustomerId();

            new Alert(Alert.AlertType.CONFIRMATION,"Saved New CustomerDto").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void btnClearAction(MouseEvent mouseEvent) {
        clearField();
        btnUpdate.setDisable(true);
        try {
            setCustomerId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void searchKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<CustomerTM> list = customerBo.searchCustomer(txtSearch.getText());
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
        for (CustomerTM customer : list){
            customerTMS.add(new CustomerTM(
                    customer.getCustomerId(),
                    customer.getCustomerTitle(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            ));
        }
        tblCustomer.getItems().setAll(customerTMS);

    }

    public void showAllCustomersOnTable() throws SQLException, ClassNotFoundException {

        ObservableList<CustomerTM> list = customerBo.getCustomerList();

        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        tblCustomer.setItems(list);
    }

    public void initialize(){
        btnUpdate.setDisable(true);
        btnAdd.setDisable(true);
        try {
            showAllCustomersOnTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbTitle.getItems().addAll("Mr.", "Mrs.", "Miss.");

        try {
            setCustomerId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storeValidations();
    }

    public void tableMouseClick(MouseEvent mouseEvent) {
        try {
            CustomerTM item = tblCustomer.getSelectionModel().getSelectedItem();
            txtCustomerId.setText(item.getCustomerId());
            cmbTitle.setValue(item.getCustomerTitle());
            txtName.setText(item.getCustomerName());
            txtAddress.setText(item.getCustomerAddress());
            txtCity.setText(item.getCity());
            txtProvince.setText(item.getProvince());
            txtPostalCode.setText(item.getPostalCode());
            btnUpdate.setDisable(false);
        } catch (Exception e) {
            //new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }

    }

    public void setCustomerId() throws SQLException, ClassNotFoundException {
        txtCustomerId.setText(customerBo.getCustomerId());
    }

    public void custTableKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode()== KeyCode.DELETE) {
            if (customerBo.deleteCustomer(txtCustomerId.getText())) {
                showAllCustomersOnTable();
                clearField();
                new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
    }

    private void clearField() {
        txtCustomerId.clear();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        tblCustomer.getSelectionModel().clearSelection();
        cmbTitle.getSelectionModel().clearSelection();
        txtSearch.clear();

    }

    public void validationKeyReleased(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        Object response = ValidationUtil.validate(map,btnAdd,"Green");
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof JFXTextField){
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            }else if(response instanceof Boolean){
            }
        }
    }
}
