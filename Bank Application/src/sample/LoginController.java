package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.users.ClientData;

import java.io.IOException;


public class LoginController  {


    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @FXML
    private AnchorPane content;

    public void initialize(){
//        Account account1 =new Account("639287894343","321","12");
//        account1.deposit("100","bank employee");
//        account1.withdraw("50","mahdi");
//        Client client = new Client("ehsan","1362886343");
//        Account account2 =new Account("26724390876","456","33");
//        client.addAccount(account2);
//        account2.deposit("10","emp");
//        ClientData.getInstance().addClient(client);
//         Client ehsan = ClientData.getInstance().getList().get(0);
//         ehsan.getAccList().get(1).deposit("50","employee");
//        Client client = new Client("sadra","789", new Account("34567291878","2","5"));
//        ClientData.getInstance().addClient(client);

    }

    @FXML
    public void onSignInButtonClicked() throws IOException {

        String id = username.getText();
        String pass = password.getText();

        if (ClientData.getInstance().isPresent(id,pass)){
            ClientData.getInstance().setRecentUser(0,id);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("ClientPage.fxml"));
            content.getChildren().setAll(pane);
        }else{
            password.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            username.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

        }

    }

    @FXML
    public void setAdminChangeButton() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        content.getChildren().setAll(pane);
    }



}
