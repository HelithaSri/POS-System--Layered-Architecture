package controller;

import bo.BoFactory;
import bo.custom.ManageOrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.OrderDetailDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.CartTM;
import view.tm.OrderDetailsTM;
import view.tm.ReturnTM;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageOrderFormController {

    public TableView<OrderDetailsTM> orderMainTbl;
    public TableColumn colOrder;
    public JFXComboBox cmbOrderIds;

    public TableView<ReturnTM> tblManage;
    public TableColumn colItemCode;
    public TableColumn colItemDescription;
    public TableColumn colItemQty;
    public TableColumn colItemDiscount;
    public TableColumn colItemTotal;
    public TableColumn colOrderId;

    static ObservableList<CartTM> observableList = FXCollections.observableArrayList();
    public AnchorPane manageOrderAnchorPane;
    public JFXTextField txtSearchOrder;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public JFXTextField txtTotal;
    public JFXTextField txtOrderId;
    public JFXTextField txtReturnedQty;
    public JFXTextField txtUnitPrice;
    public Label lblCustomerName;
    public JFXButton btnReturned;

    private ManageOrderBo manageOrderBo = (ManageOrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.MANAGE_ORDER);

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern QTYPattern = Pattern.compile("^[0-9]+$");

    private void storeValidations() {
        map.put(txtReturnedQty, QTYPattern);
    }


    public void initialize() {
        try {
            loadAllOrders();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        storeValidations();
        txtReturnedQty.setEditable(false);
        btnReturned.setDisable(true);
    }

    public void loadAllOrders() throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetailsTM> allOrderIds = manageOrderBo.getAllOrderIds();
        colOrder.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderMainTbl.setItems(allOrderIds);
    }

    public void orderTblMouseEvent(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        clear();
        lblCustomerName.setText("");
        try {
            String orderId = orderMainTbl.getSelectionModel().getSelectedItem().getOrderId();
            String customer = manageOrderBo.getCustomer(orderId); //queryDao
            if (customer.isEmpty()) {
                lblCustomerName.setText("Unknown");
            }else{
                lblCustomerName.setText(customer);
            }
            refreshOrderDetailTbl();
        } catch (Exception e) {
        }
       // String orderId = orderMainTbl.getSelectionModel().getSelectedItem().getOrderId();



    }

    private void refreshOrderDetailTbl() throws SQLException, ClassNotFoundException {
        String orderId = orderMainTbl.getSelectionModel().getSelectedItem().getOrderId();

        ObservableList<ReturnTM> list = manageOrderBo.getSelectedOrderDetails(orderId);
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colItemTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblManage.setItems(list);
    }

    public void orderDetailTblOnMouseClick(MouseEvent mouseEvent) {
        try {
            ReturnTM selectedItem = tblManage.getSelectionModel().getSelectedItem();
            txtItemCode.setText(selectedItem.getItemCode());
            txtDescription.setText(selectedItem.getDescription());
            txtQty.setText(selectedItem.getQty()+"");
            txtDiscount.setText(selectedItem.getDiscount()+"");
            txtTotal.setText(selectedItem.getTotal()+"");
            txtOrderId.setText(selectedItem.getOrderId());
            txtUnitPrice.setText(selectedItem.getUnitPrice()+"");
            txtReturnedQty.setEditable(true);
        } catch (Exception e) {
        }


    }

    private double calDiscount(int qtyForCustomer, double unitPrice, int discount) {
        double total = qtyForCustomer * unitPrice;
        double s = 100 - discount;
        double price = (s * total) / 100;
        return price;
    }

    public void returnedQtyKeyReleased(KeyEvent keyEvent) {
        ReturnTM selectedItem = tblManage.getSelectionModel().getSelectedItem();
        try {
            if (Integer.parseInt(txtQty.getText()) < Integer.parseInt(txtReturnedQty.getText()) || 0 > Integer.parseInt(txtReturnedQty.getText())) {
                txtReturnedQty.clear();
                new Alert(Alert.AlertType.ERROR,"Invalid Returned Qty").show();
            }
        } catch (NumberFormatException e) {
        }

        if (txtTotal.getText().isEmpty()) {
            return;
        }

        if (!txtReturnedQty.getText().isEmpty()) {
            try {
                double discount = calDiscount(Integer.parseInt(txtReturnedQty.getText()), selectedItem.getUnitPrice(), selectedItem.getDiscount());
                txtTotal.setText(discount + "");
            } catch (NumberFormatException e) {
            }
        } else {
            txtTotal.setText(selectedItem.getTotal() + "");
        }

        btnReturned.setDisable(true);
        Object response = ValidationUtil.validate(map,btnReturned,"Green");
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof JFXTextField){
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            }else if(response instanceof Boolean){
            }
        }
    }

    public void returnBtnMouseClick(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        OrderDetailDto orderDetailDto = new OrderDetailDto(
                txtOrderId.getText(),
                txtItemCode.getText(),
                Integer.parseInt(txtReturnedQty.getText())
        );

        if (manageOrderBo.updateOrderDetails(orderDetailDto)){
            new Alert(Alert.AlertType.CONFIRMATION,"success").show();
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/ReturnBill.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);

                String itemCode = txtItemCode.getText();
                String qty = txtReturnedQty.getText();
                String unitPrice = txtUnitPrice.getText();
                String discount = txtDiscount.getText();
                String total = txtTotal.getText();
                String orderId = txtOrderId.getText();
                String description = txtDescription.getText();

                HashMap map = new HashMap();
                map.put("itemCode", itemCode);
                map.put("Qty", qty);
                map.put("unitPrice", unitPrice);
                map.put("description", description);
                map.put("Total", total);
                map.put("Discount", discount);
                map.put("OrderId", orderId);

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JREmptyDataSource(1));
                JasperViewer.viewReport(jasperPrint, false);

            } catch (JRException e) {
                e.printStackTrace();
            }
            refreshOrderDetailTbl();
            clear();

        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Try Again").show();
        }
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetailsTM> list = manageOrderBo.searchOrderIds(txtSearchOrder.getText());
        orderMainTbl.setItems(list);
    }

    private void clear(){
        //txtSearchOrder.clear();
        txtItemCode.clear();
        txtDescription.clear();
        txtQty.clear();
        txtDiscount.clear();
        txtTotal.clear();
        txtOrderId.clear();
        txtReturnedQty.clear();
        txtUnitPrice.clear();
        txtReturnedQty.setEditable(false);
    }


    public void cancelOrderBtnMouseClick(MouseEvent mouseEvent) {
        clear();
        tblManage.getSelectionModel().clearSelection();
        orderMainTbl.getSelectionModel().clearSelection();
        txtSearchOrder.clear();

    }
}
