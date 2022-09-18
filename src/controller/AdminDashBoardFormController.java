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

public class AdminDashBoardFormController{

    public AnchorPane anchor;
    public AnchorPane mainPaneAddmin;
    public JFXDrawer drawer;
    public JFXButton systemReportsBtn;
    public JFXButton manageItemsBtn;
    public JFXButton logoutBtn;
    public JFXHamburger hamburger;

    public void systemReportsBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SystemReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainPaneAddmin.getChildren().clear();
        mainPaneAddmin.getChildren().add(load);
    }

    public void manageItemsBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainPaneAddmin.getChildren().clear();
        mainPaneAddmin.getChildren().add(load);
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LogingForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.mainPaneAddmin.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void initialize() {
        systemReportsBtn.fire();
        //drawer.open();
        drawer.toggle();
        drawer.setVisible(false);
        try {
            VBox vBox = FXMLLoader.load(getClass().getResource("../view/SidePaneAdminForm.fxml"));
            drawer.setSidePane(vBox);

            for (Node node : vBox.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        try {
                            switch (node.getAccessibleText()) {
                                case "sideSystemReportsBtn":

                                    URL resource = getClass().getResource("../view/SystemReportsForm.fxml");
                                    Parent load = FXMLLoader.load(resource);
                                    mainPaneAddmin.getChildren().clear();
                                    mainPaneAddmin.getChildren().add(load);
                                    break;
                                case "sideManageItemsBtn":

                                    URL resource1 = getClass().getResource("../view/ManageItemsForm.fxml");
                                    Parent load1 = FXMLLoader.load(resource1);
                                    mainPaneAddmin.getChildren().clear();
                                    mainPaneAddmin.getChildren().add(load1);
                                    break;

                                case "sideAddUser":
                                    URL resource2 = getClass().getResource("../view/UserForm.fxml");
                                    Parent load2 = FXMLLoader.load(resource2);
                                    mainPaneAddmin.getChildren().clear();
                                    mainPaneAddmin.getChildren().add(load2);
                                    break;

                                case "sideLogOutBtn":
                                    Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LogingForm.fxml"));
                                    Scene scene = new Scene(parent);
                                    Stage primaryStage = (Stage) this.mainPaneAddmin.getScene().getWindow();
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

    public void userBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/UserForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainPaneAddmin.getChildren().clear();
        mainPaneAddmin.getChildren().add(load);
    }
}
