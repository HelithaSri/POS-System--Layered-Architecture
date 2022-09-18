package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.ValidationUtil;
import view.tm.UserTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UserFormController {

    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXTextField txtName;
    public JFXComboBox cmbRole;
    public JFXButton btnAdd;

    public TableView<UserTM> tblUser;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TableColumn colName;
    public TableColumn colRole;

    private final UserBo userBo = (UserBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.USER);

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern userNamePattern = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern passwordPattern = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");


    private void storeValidations() {
        map.put(txtUserName, userNamePattern);
        map.put(txtPassword, passwordPattern);
        map.put(txtName, namePattern);

    }

    public void initialize(){
        cmbRole.getItems().addAll("Admin","UserDto");
        try {
            showAllUsersOnTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnAdd.setDisable(true);
        storeValidations();
    }


    public void addBtnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        UserDto userDto = new UserDto(
                txtUserName.getText(),
                txtPassword.getText(),
                txtName.getText(),
                cmbRole.getValue().toString()
        );

        if (userBo.addUser(userDto)){
            showAllUsersOnTable();
            clearField();
            new Alert(Alert.AlertType.CONFIRMATION,"Saved New UserDto").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    private void showAllUsersOnTable() throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> list = userBo.getUserList();
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tblUser.setItems(list);

    }

    public void updateBtnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        UserDto userDto = new UserDto(
                txtUserName.getText(),
                txtPassword.getText(),
                txtName.getText(),
                cmbRole.getValue().toString()
        );

        if (userBo.updateUser(userDto)){
            showAllUsersOnTable();
            clearField();
            new Alert(Alert.AlertType.CONFIRMATION,"Update UserDto").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void deleteBtnAction(MouseEvent mouseEvent) {
        clearField();
    }

    private void clearField() {
        txtUserName.clear();
        txtPassword.clear();
        txtName.clear();
        cmbRole.getSelectionModel().clearSelection();
        tblUser.getSelectionModel().clearSelection();
        txtUserName.setEditable(true);
    }


    public void userTableClick(MouseEvent mouseEvent) {
        txtUserName.setEditable(false);
        try {
            UserTM user = tblUser.getSelectionModel().getSelectedItem();
            txtUserName.setText(user.getUserName());
            cmbRole.setValue(user.getRole());
            txtName.setText(user.getName());
            txtPassword.setText(user.getPassword());
        } catch (Exception e) {
            //new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }

    }

    public void deleteKey(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode()== KeyCode.DELETE) {
            if (userBo.deleteUser(txtUserName.getText())) {
                showAllUsersOnTable();
                clearField();
                new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
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
