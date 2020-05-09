package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.users.Employee;
import sample.users.EmployeeData;

import java.io.IOException;
import java.util.ArrayList;

public class AdminLoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<String> branch;

    @FXML
    private AnchorPane content;

    public void initialize(){
//        EmployeeData.getInstance().addEmployee(new Employee("mahdi","123","tabriz"));
//        EmployeeData.getInstance().addEmployee(new Employee("ali","123","tabriz"));
//        EmployeeData.getInstance().addEmployee(new Employee("mamad","123","tabriz"));
//        EmployeeData.getInstance().addEmployee(new Employee("hassan","435","tehran"));
        ArrayList<String> branchList = new ArrayList<>();
        for (Employee employee : EmployeeData.getInstance().getList()){
            if (!(branchList.contains(employee.getBranch()))){
                branchList.add(employee.getBranch());
            }
        }

        for (String branch : branchList){
            this.branch.getItems().add(branch);
        }
    }


    @FXML
    public void onSignInButtonClicked() throws IOException {

        String id = username.getText();
        String pass = password.getText();

        if (EmployeeData.getInstance().isPresent(id,pass,branch.getValue() )){
            EmployeeData.getInstance().addEnteredEmp(id);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
            content.getChildren().setAll(pane);

        }else{
            username.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            password.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            branch.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");


        }

    }
    @FXML
    public void setAdminChangeButton() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        content.getChildren().setAll(pane);
    }
}
