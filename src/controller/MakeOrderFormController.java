package controller;

import bo.BoFactory;
import bo.custom.PurchaseOrderBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import dto.OrderDetailDto;
import dto.OrderDto;
import entity.OrderDetail;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class MakeOrderFormController {

    public Label lblDate;
    public Label lblTime;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public TableView<CartTM> orderTbl;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colPrice;
    public TableColumn colTotalPrice;
    public Label orderId;

    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public Label lblTotal;
    public JFXButton orderBtn;
    public JFXTextField txtSearchCustomer;

    public static ObservableList<CartTM> cartObList = FXCollections.observableArrayList();
    public JFXButton btnAddToCart;

    private int cartSelectedRowForRemove = -1;

    private final PurchaseOrderBo purchaseOrderBo = (PurchaseOrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASE_ORDER);

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern QTYPattern = Pattern.compile("^[0-9]+$");
    private void storeValidations() {
        map.put(txtQty, QTYPattern);
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = purchaseOrderBo.getAllItemIds();
        cmbItemCode.getItems().addAll(itemIds);
    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDto itemDto = purchaseOrderBo.getItem(itemCode);
        if (itemDto == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(itemDto.getDescription());
            txtUnitPrice.setText(String.valueOf(itemDto.getUnitPrice()));
            txtPackSize.setText(itemDto.getPackSize());
            txtQtyOnHand.setText(String.valueOf(itemDto.getQtyOnHand()));
            txtDiscount.setText(String.valueOf(itemDto.getDiscount()));
        }
    }

    private double calDiscount(int qtyForCustomer, double unitPrice, int discount) {
        double total = qtyForCustomer * unitPrice;
        double s = 100 - discount;
        double price = (s * total) / 100;
        return price;
    }

    public void addToCartBtnOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();
        String packetSize = txtPackSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        int discount = Integer.parseInt(txtDiscount.getText());
        double price = calDiscount(qtyForCustomer, unitPrice, discount);
        double totalPrice = (qtyForCustomer * unitPrice);

        /*Check Selected QTY is equal or lower than OnHandQty*/
        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        CartTM tm = new CartTM(
                cmbItemCode.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                discount,
                price,
                totalPrice
        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {
            cartObList.add(tm);
        } else {
            CartTM temp = cartObList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getCode(),
                    temp.getDescription(),
                    temp.getQty()+ qtyForCustomer,
                    unitPrice,
                    temp.getDiscount(),
                    price + temp.getPrice(),
                    temp.getTotalPrice() + totalPrice
            );

            if (qtyOnHand < temp.getQty()+qtyForCustomer) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }

            cartObList.remove(rowNumber);
            cartObList.add(newTm);
        }

        //orderTbl.setItems(cartObList);
        calculateCost();
        clearField();
    }

    private int isExists(CartTM tm) {
        for (int i = 0; i < cartObList.size(); i++) {
            if (tm.getCode().equals(cartObList.get(i).getCode())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTM tm : cartObList) {
            ttl += tm.getPrice();
        }

        /*  Get only two decimal    */
        String formatTotal = new DecimalFormat("0.00").format(ttl);
        lblTotal.setText(formatTotal + " /=");
    }

    public void deleteItemOnCartBtnOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
           lblTotal.setText("");
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            cartObList.remove(cartSelectedRowForRemove);
            calculateCost();
            orderTbl.refresh();
        }
        clearField();
    }

    private void clearField() {
        txtQty.clear();
        txtDescription.clear();
        txtDiscount.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        cmbItemCode.setValue("");
        cmbItemCode.requestFocus();
        orderTbl.getSelectionModel().clearSelection();
    }

    public void clearOrderBtnOnAction(ActionEvent actionEvent) {
        clearField();
        lblTotal.setText("");
        cartObList.clear();
        txtSearchCustomer.clear();
        txtCustomerName.clear();
        txtCustomerId.clear();
    }

    private void setOrderId(){
        try {
            orderId.setText(purchaseOrderBo.getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void initialize() {
        calculateCost();
        setOrderId();
        loadDateAndTime();
        try {
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        /*  Row Selecting   */
        orderTbl.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
        orderTbl.setItems(cartObList);
        btnAddToCart.setDisable(true);
        storeValidations();
    }

    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        String cusId;
        if (txtCustomerId.getText().isEmpty()) {
            cusId=null;
        }else{
            cusId=txtCustomerId.getText();
        }

        ArrayList<OrderDetail> orderDetailDtoDtos = new ArrayList<>();
        for (CartTM tempTm : cartObList){
            orderDetailDtoDtos.add(
                    new OrderDetail(
                            tempTm.getCode(),
                            orderId.getText(),
                            tempTm.getQty(),
                            tempTm.getDiscount()
                    ));
        }
        OrderDto orderDto = new OrderDto(
                orderId.getText(),
                lblDate.getText(),
                //"2021-10-29",
                //txtCustomerId.getText(),
                cusId,
                orderDetailDtoDtos
        );
        if (purchaseOrderBo.purchaseOrder(orderDto)){

            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/Bill.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                ObservableList<CartTM> items = orderTbl.getItems();

                String oId = orderId.getText();
                String subTotal = lblTotal.getText();
                String cusID = txtCustomerId.getText();
                String cusName = txtCustomerName.getText();

                HashMap map = new HashMap();
                map.put("OrderId", oId);
                map.put("SubTotal", subTotal);
                map.put("customerID", cusID);
                map.put("customerName", cusName);

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map , new JRBeanArrayDataSource(items.toArray()));
                JasperViewer.viewReport(jasperPrint, false);
            }catch (JRException e) {
                e.printStackTrace();
            }

            new Alert(Alert.AlertType.CONFIRMATION,"success").show();
            setOrderId();
            clearField();
            lblTotal.setText("0.00 /=");
            cartObList.clear();
            txtSearchCustomer.clear();
            txtCustomerName.clear();
            txtCustomerId.clear();


        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Try Again").show();
        }
        //Stage stage = (Stage) conformOrderBtn.getScene().getWindow(); stage.close();

    }

    public void searchCustomer(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        TextField field = purchaseOrderBo.searchCustomers(txtSearchCustomer);
        txtCustomerName.setText(field.getText().split("/")[0]);
        if (txtCustomerName.getText().isEmpty()) {
            txtCustomerId.clear();
        }else {
            txtCustomerId.setText(field.getText().split("/")[1]);
        }

        if (txtSearchCustomer.getText().isEmpty()){
            txtCustomerName.clear();
            txtCustomerId.clear();
        }
    }

    public void validationKeyReleased(KeyEvent keyEvent) {
        btnAddToCart.setDisable(true);
        Object response = ValidationUtil.validate(map,btnAddToCart,"Green");
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof JFXTextField){
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            }else if(response instanceof Boolean){
            }
        }
    }
}