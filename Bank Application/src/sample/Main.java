package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.users.BillData;
import sample.users.ClientData;
import sample.users.EmployeeData;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 425));
        primaryStage.show();
    }

    @Override
    public void init() {
        EmployeeData.getInstance().readData();
        ClientData.getInstance().readData();
        BillData.getInstance().readData();


    }

    @Override
    public void stop() {
        EmployeeData.getInstance().saveData();
        ClientData.getInstance().saveData();
        BillData.getInstance().saveData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
