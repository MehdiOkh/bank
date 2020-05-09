package sample;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.users.Account;
import sample.users.Client;
import sample.users.ClientData;
import sample.users.EmployeeData;

import java.io.IOException;


public class AdminPageController {
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField fund;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private Label accNum;
    @FXML
    private JFXPasswordField secPass;
    @FXML
    private AnchorPane newClientPage;
    @FXML
    private AnchorPane depositPage;
    @FXML
    private JFXTextField dfAN;
    @FXML
    private JFXTextField dfF;
    @FXML
    private AnchorPane withdrawPage;
    @FXML
    private JFXTextField wAN;
    @FXML
    private JFXTextField wF;
    @FXML
    private AnchorPane addAccountPage;
    @FXML
    private JFXTextField addAccountIdNum;
    @FXML
    private Label addAccNum;
    @FXML
    private JFXPasswordField addAccSecPass;
    @FXML
    private JFXTextField addAccFund;

    @FXML
    private Label empName;
    @FXML
    private Label branch;
    @FXML
    private Label title;
    @FXML
    private AnchorPane content;
    @FXML
    private JFXTextField name;
    @FXML
    private Label message;

    private Boolean validCardNumber = false;





    String accNumberGenerator = "";
    public void initialize(){

        newClientPage.setVisible(false);
        addAccountPage.setVisible(false);
        depositPage.setVisible(false);
        withdrawPage.setVisible(false);

        empName.setText("User : "+EmployeeData.getInstance().getEnteredEmp().getName());
        branch.setText("Branch : "+EmployeeData.getInstance().getEnteredEmp().getBranch());

        dfAN.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) {
                for (Client client : ClientData.getInstance().getList()){
                    for (Account account : client.getAccList()){
                        if (account.getAccountNumber().equals(dfAN.getText())){
                            dfAN.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                            validCardNumber = true;
                        }
                    }
                }
                if (!validCardNumber){
                    dfAN.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                }
                validCardNumber = false;
            }
        });
        
        wAN.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) {
                for (Client client : ClientData.getInstance().getList()){
                    for (Account account : client.getAccList()){
                        if (account.getAccountNumber().equals(wAN.getText())){
                            wAN.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                            validCardNumber = true;
                        }
                    }
                }
                if (!validCardNumber){
                    wAN.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                }
                validCardNumber = false;
            }
        });

        addAccountIdNum.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) {
                for (Client client : ClientData.getInstance().getList()){
                        if (client.getName().equals(addAccountIdNum.getText())){
                            addAccountIdNum.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                            validCardNumber = true;
                        }
                }if (!validCardNumber){
                    addAccountIdNum.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                }
                validCardNumber = false;
            }
        });


    }

    public void onNewClientSubmit (){
        if (!(username.getText().trim().isEmpty())&&!(pass.getText().trim().isEmpty())&&!(secPass.getText().trim().isEmpty())&&!(fund.getText().trim().isEmpty())&&!(name.getText().trim().isEmpty())){
            if (Double.parseDouble(fund.getText()) > 0){
                Client client = new Client(username.getText(),pass.getText());
                Account account = new Account(accNumberGenerator,secPass.getText(),fund.getText());
                client.setClientName(name.getText());
                client.addAccount(account);
                ClientData.getInstance().addClient(client);
                username.setText("");
                pass.setText("");
                secPass.setText("");
                fund.setText("");
                name.setText("");
                message.setText("Client is added successfully ");
                message.setStyle("-fx-text-fill:green");
            }else {

                username.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");                pass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
                pass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
                secPass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
                name.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
                fund.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            }

        }else {
            
            fund.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
            username.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");                pass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
            pass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
            secPass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
            name.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");


            if (username.getText().trim().isEmpty()){
                username.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

            }
            if (pass.getText().trim().isEmpty()){
                pass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

            }
            if (secPass.getText().trim().isEmpty()){
                secPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

            }
            if (name.getText().trim().isEmpty()){
                name.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

            }
            if (fund.getText().trim().isEmpty()){
                fund.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

            }


        }

    }

    public void onNewClientClicked(){
        message.setText("");
        accNumberGenerator = ((int)(Math.random()*100000000))+"";
        accNum.setText("Account Number : "+accNumberGenerator);
        title.setText("New Client");
        addAccountPage.setVisible(false);
        depositPage.setVisible(false);
        withdrawPage.setVisible(false);
        newClientPage.setVisible(true);
    }

    public void onDepositFundClicked(){
        message.setText("");
        title.setText("Deposit Funds");
        addAccountPage.setVisible(false);
        newClientPage.setVisible(false);
        withdrawPage.setVisible(false);
        depositPage.setVisible(true);
    }
    public void onDFSubmit(){
        for (Client client : ClientData.getInstance().getList()){
            for (Account account : client.getAccList()){
                if (account.getAccountNumber().equals(dfAN.getText())){
                    if (!(dfF.getText().trim().isEmpty())){
                        if (Double.parseDouble(dfF.getText()) > 0){
                            account.deposit(dfF.getText(),"Bank Employee");
                            dfAN.setText("");
                            dfF.setText("");
                            message.setText("transfer was successful");
                            message.setStyle("-fx-text-fill:green");
                            dfF.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");

                        }else {
                            dfF.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                        }
                    }else {
                        dfF.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                    }

                }
            }
        }
    }

    public void onWithdrawFundClicked(){
        message.setText("");
        title.setText("Withdraw Funds");
        addAccountPage.setVisible(false);
        newClientPage.setVisible(false);
        depositPage.setVisible(false);
        withdrawPage.setVisible(true);
    }

    public void  onWithdrawSubmit(){

        for (Client client : ClientData.getInstance().getList()){
            for (Account account : client.getAccList()){
                if (account.getAccountNumber().equals(wAN.getText())){
                    if ( !(wF.getText().trim().isEmpty())){

                        if ( (Double.parseDouble(wF.getText()) > 0) && (Double.parseDouble(account.getBudget()) > Double.parseDouble(wF.getText()))){
                            account.withdraw(wF.getText(),"Bank Employee");
                            wF.setText("");
                            wAN.setText("");
                            message.setText("transfer was successful");
                            message.setStyle("-fx-text-fill:green");
                            wF.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                        }else {
                            wF.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                        }
                    }else {
                        wF.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

                    }
                }
            }
        }
    }

    public void onAddAccountClicked(){
        message.setText("");
        accNumberGenerator = ((int)(Math.random()*100000000))+"";
        addAccNum.setText("Account Number : "+accNumberGenerator);
        title.setText("Add New Account For Client");
        withdrawPage.setVisible(false);
        newClientPage.setVisible(false);
        depositPage.setVisible(false);
        addAccountPage.setVisible(true);
    }

    public void onAddAccountSubmit(){
        for (Client client : ClientData.getInstance().getList()){
            if (client.getName().equals(addAccountIdNum.getText())){
                if (!(addAccFund.getText().trim().isEmpty()) && !(addAccSecPass.getText().trim().isEmpty())){
                    if (Double.parseDouble(addAccFund.getText()) > 0){
                        Account account = new Account(accNumberGenerator,addAccSecPass.getText(),addAccFund.getText());
                        client.addAccount(account);
                        addAccountIdNum.setText("");
                        addAccSecPass.setText("");
                        addAccFund.setText("");
                        message.setText("New account added successfully");
                        message.setStyle("-fx-text-fill:green");
                    }else {
                        addAccFund.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                    }

                }else {
                    addAccFund.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
                    addAccSecPass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
                    if (!(addAccFund.getText().trim().isEmpty()) && Double.parseDouble(addAccFund.getText()) < 0){
                        addAccFund.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                    }
                    if (addAccSecPass.getText().trim().isEmpty()){
                        addAccSecPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                    }
                }

            }
        }
    }

    public void onQuitClicked() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        content.getChildren().setAll(pane);
    }
}
