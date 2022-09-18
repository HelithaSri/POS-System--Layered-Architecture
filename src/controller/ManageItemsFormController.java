package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import util.ValidationUtil;
import view.tm.ItemTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageItemsFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtDiscount;
    public TableView<ItemTM> ItemTbl;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDiscount;
    public JFXButton addItemBtn;
    public JFXButton updateBtn;
    public JFXButton deleteItemBtn;
    public JFXTextField txtSearch;
    int SelectedRow=-1;

    private ItemBo itemBo = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern itemCodePattern = Pattern.compile("^(I)[-]?[0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern packSizePattern = Pattern.compile("^[0-9]{1,4}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{2,4}$");
    Pattern QTYPattern = Pattern.compile("^[0-9]{1,10}$");
    Pattern discountPattern = Pattern.compile("^[0-9]{1,2}$");

    private void storeValidations() {
        map.put(txtItemCode,itemCodePattern);
        map.put(txtDescription,descriptionPattern);
        map.put(txtPackSize,packSizePattern);
        map.put(txtUnitPrice,unitPricePattern);
        map.put(txtQtyOnHand,QTYPattern);
        map.put(txtDiscount,discountPattern);
    }


    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy");
        lblDate.setText(f.format(date));

        // load Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /*  private void loadAllItems() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM ItemDto";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            obList.add(new ItemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getInt(6)
            ));
        }
        ItemTbl.setItems(obList);


    }
*/

    public void addItemBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        String itemcode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int discount = Integer.parseInt(txtDiscount.getText());

        /*  show items in tabale    */
        /*ItemTM showTbl = new ItemTM(itemcode, description, packSize, unitPrice, qtyOnHand, discount);
        itemBo.getItemList().add(showTbl);
        */

        /*  Add items to databases  */
        ItemDto addItemDtoFromFields = new ItemDto(itemcode, description, packSize, unitPrice, qtyOnHand, discount);
        if (itemBo.addItem(addItemDtoFromFields)) {
            new Alert(Alert.AlertType.CONFIRMATION, "ItemDto Add To DataBase").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

        showItemsOnTabele();
        clearField();
    }

    private void clearField() {
        txtItemCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtPackSize.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
        ItemTbl.refresh();
        ItemTbl.getSelectionModel().clearSelection();
        txtItemCode.requestFocus();

        addItemBtn.setDisable(false);
        txtItemCode.setEditable(true);
        deleteItemBtn.setDisable(true);
        updateBtn.setDisable(true);
    }

    public void clearFieldsOnAction(ActionEvent actionEvent) {
        clearField();
    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        /*  Get ItemDto Code from TableView   */
        ItemTM item = ItemTbl.getSelectionModel().getSelectedItem();
        String itemCode = item.getItemCode();

        if (itemBo.deleteItem(itemCode)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Delete Selected ItemDto").show();
            showItemsOnTabele();
            //ItemTbl.refresh();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        clearField();
    }

    public void showItemsOnTabele() throws SQLException, ClassNotFoundException {

        ObservableList<ItemTM> list = itemBo.getItemList();

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        ItemTbl.setItems(list);
    }

/*    public ObservableList<ItemTM> getItemList() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();

        Connection connection = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM ItemDto";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            obList.add(new ItemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getInt(6)
            ));
        }
        return obList;
    }*/

    public void updateItemsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDto itemDto = new ItemDto(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()),
                Integer.parseInt(txtDiscount.getText())
        );

        if (itemBo.updateItem(itemDto)){
            new  Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
            showItemsOnTabele();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }

    }

    public void initialize() {

        /*  Show Data On TableView   */
        try {
            showItemsOnTabele();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*   Display Date And Time  */
        loadDateAndTime();

        /*  Btn Visibility switch   */
        updateBtn.setDisable(true);
        deleteItemBtn.setDisable(true);

        ItemTbl.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            SelectedRow = (int) newValue;
            if (SelectedRow == -1) {
                addItemBtn.setDisable(false);
                updateBtn.setDisable(true);
                deleteItemBtn.setDisable(true);
                txtItemCode.setEditable(true);
            }else{



                addItemBtn.setDisable(true);
                updateBtn.setDisable(false);
                deleteItemBtn.setDisable(false);
                txtItemCode.setEditable(false);
            }
        });
        storeValidations();
        addItemBtn.setDisable(true);
    }


    public void tableRowSelectOnAction(MouseEvent mouseEvent) {

        ItemTM itemTM=null;
        try {
            itemTM = ItemTbl.getSelectionModel().getSelectedItem();
            txtItemCode.setText(itemTM.getItemCode());
            txtDescription.setText(itemTM.getDescription());
            txtPackSize.setText(itemTM.getPackSize());
            txtUnitPrice.setText(""+(itemTM.getUnitPrice()));
            txtQtyOnHand.setText(""+(itemTM.getQtyOnHand()));
            txtDiscount.setText(""+(itemTM.getDiscount()));
        } catch (Exception e) {

        }
    }

    public void SearchKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM> list = itemBo.searchItems(txtSearch.getText());
        ItemTbl.setItems(list);
    }

    public void validationKeyReleased(KeyEvent keyEvent) {
        addItemBtn.setDisable(true);
        Object response = ValidationUtil.validate(map,addItemBtn,"Green");
        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof JFXTextField){
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            }else if(response instanceof Boolean){
            }
        }
    }
}
