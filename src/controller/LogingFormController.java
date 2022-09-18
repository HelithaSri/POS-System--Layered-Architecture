package controller;

import bo.BoFactory;
import bo.custom.LoginBo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LogingFormController {

    public JFXTextField txtUserName;
    public JFXPasswordField txtUserPassword;
    public AnchorPane logingPane;

    private LoginBo loginBo = (LoginBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.LOGIN);

    public void logingBtnOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        UserDto login = new UserDto(txtUserName.getText(), txtUserPassword.getText());
        String userType = loginBo.loginUser(login);

        if (userType.equals("User")){
            Stage window = (Stage) logingPane.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierDashBoardForm.fxml"))));
            window.centerOnScreen();
        }else if (userType.equals("Admin")){
            Stage window = (Stage) logingPane.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"))));
            window.centerOnScreen();
        }else{
            System.out.println("Invalid User Name");
        }
    }

    public void initialize() {
        txtUserName.requestFocus();
    }
}
