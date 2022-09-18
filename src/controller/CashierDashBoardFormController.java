package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;

public class CashierDashBoardFormController {

    public AnchorPane anchor;
    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public JFXButton makeOrderBtn;
    public JFXButton manageOrderBtn;
    public JFXButton logoutBtn;
    public AnchorPane mainPane;
    public JFXButton customer;


    public void initialize(){
        makeOrderBtn.fire();
        drawer.toggle();
        drawer.setVisible(false);
        try {
            VBox vBox = FXMLLoader.load(getClass().getResource("../view/SidePaneCashierForml.fxml"));
            drawer.setSidePane(vBox);

            for (Node node : vBox.getChildren()){
                if (node.getAccessibleText()!=null){
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                        try {
                            switch (node.getAccessibleText()) {
                                case "sideOrderBtn":
                                    URL resource = getClass().getResource("../view/MakeOrderForm.fxml");
                                    Parent load = FXMLLoader.load(resource);
                                    mainPane.getChildren().clear();
                                    mainPane.getChildren().add(load);
                                    break;
                                case "sideManageOrderBtn":
                                    URL resource1 = getClass().getResource("../view/ManageOrderForm.fxml");
                                    Parent loadParent = FXMLLoader.load(resource1);
                                    mainPane.getChildren().clear();
                                    mainPane.getChildren().add(loadParent);
                                    break;
                                case "sideCustomerBtn":
                                    URL resource2 = getClass().getResource("../view/CustomerForm.fxml");
                                    Parent loadParent1 = FXMLLoader.load(resource2);
                                    mainPane.getChildren().clear();
                                    mainPane.getChildren().add(loadParent1);
                                    break;
                                case "sideLogOutBtn":
                                    Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LogingForm.fxml"));
                                    Scene scene = new Scene(parent);
                                    Stage primaryStage = (Stage) this.mainPane.getScene().getWindow();
                                    primaryStage.setScene(scene);
                                    primaryStage.centerOnScreen();
                                    break;
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        HamburgerBasicCloseTransition transition  = new HamburgerBasicCloseTransition(hamburger);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            transition.setRate(transition.getRate()*-1);
            transition.play();

            if(drawer.isOpened()) {
                drawer.setVisible(false);
                drawer.close();
            }
            else {
                drawer.setVisible(true);
                drawer.open();
            }
            });





    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LogingForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.mainPane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void makeOrderBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);

    }


    public void manageOrderBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
    }


    public void customerBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(load);
    }
}
