package sample;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.users.*;

import java.io.IOException;

public class ClientPageController {


    @FXML
    private JFXHamburger ham;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private TableColumn<ClientTransaction,String> destination;
    @FXML
    private TableColumn<ClientTransaction,String> amount;
    @FXML
    private TableColumn<ClientTransaction,String> date;
    @FXML
    private TableView<ClientTransaction> tableView;
    @FXML
    private AnchorPane moneyTransferPage;
    @FXML
    private JFXTextField cardNumber;
    @FXML
    private JFXTextField transferAmount;
    @FXML
    private JFXPasswordField secondPass;
    @FXML
    private JFXButton cdcSubmit;
    @FXML
    private AnchorPane content;
    @FXML
    private Label message;
    @FXML
    private AnchorPane billPage;
    @FXML
    private JFXTextField billNum;
    @FXML
    private Label billDebt;
    @FXML
    private JFXPasswordField billPass;

    private Boolean validCardNumber = false;

    String username = ClientData.getInstance().getRecentUser(0);
    int index = ClientData.getInstance().indexOfClient(username);


    public void initialize(){
        try {
            VBox sidePane =  FXMLLoader.load(getClass().getResource("ClientSidePane.fxml"));
            drawer.setSidePane(sidePane);
            HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(ham);
            transition.setRate(-1);
            ham.addEventHandler(MouseEvent.MOUSE_PRESSED,(e) -> {
                message.setText("");
                transition.setRate(transition.getRate()*-1);
                transition.play();
                if (drawer.isClosed()){
                    moneyTransferPage.setVisible(false);
                    tableView.setVisible(false);
                    drawer.open();
                }else {
                    drawer.close();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setVisible(false);
        moneyTransferPage.setVisible(false);
        cdcSubmit.setDisable(false);
        billPage.setVisible(false);


        cardNumber.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) {
                for (Client client : ClientData.getInstance().getList()){
                    for (Account account : client.getAccList()){
                        if (account.getAccountNumber().equals(cardNumber.getText()) && !(ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getAccountNumber().equals(cardNumber.getText()))){
                            cardNumber.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                            validCardNumber = true;
                        }
                    }
                }
                if (!validCardNumber){
                    cardNumber.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                }
                validCardNumber = false;
            }
        });

        double accountBudget = Double.parseDouble(ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getBudget());
        transferAmount.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                if(!(transferAmount.getText().trim().isEmpty())){
                    if (accountBudget <= Double.parseDouble(transferAmount.getText())){
                        transferAmount.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

                    }else {
                        transferAmount.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                    }
                }else {
                    transferAmount.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                }

            }
        });

        String secPass = ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getSecPass();
        secondPass.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                if (!(secPass.equals(secondPass.getText()))){
                    secondPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

                }else {
                    secondPass.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                }
            }
        });

        billPass.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                if (!(secPass.equals(billPass.getText()))){
                    billPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");

                }else {
                    billPass.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                }
            }
        });

        billNum.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal){
                for (BillData.Bill bill: BillData.getInstance().getBillList()){
                    if (bill.getBillNumber().equals(billNum.getText())){
                        billNum.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                        billDebt.setText("Amount of Debt : "+bill.getDebt()+"$");
                        break;
                    }else {
                        billNum.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                        billDebt.setText("Amount of Debt : ");
                    }
                }

            }
        });




    }
    public void onRecentTransactionClicked() {
        message.setText("");
        moneyTransferPage.setVisible(false);
        billPage.setVisible(false);
        tableView.setVisible(true);


        ObservableList<ClientTransaction> transaction = ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getList();


        tableView.setItems(transaction);
//        transactionBut.setDisable(true);
    }
    public void onCardToCardClicked(){
        message.setText("");
        tableView.setVisible(false);
        billPage.setVisible(false);
        moneyTransferPage.setVisible(true);
    }
    public void onTransferSubmit(){
        Account fromAccount = ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1)));
        transferAmount.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
        cardNumber.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");
        secondPass.setStyle(" -jfx-focus-color: #4059a9; -jfx-unfocus-color: #4d4d4d;");

        if(!(transferAmount.getText().trim().isEmpty())){
            if(!(cardNumber.getText().trim().isEmpty())){
                if(!(secondPass.getText().trim().isEmpty())){
                    for (int i=0; i < ClientData.getInstance().getList().size();i++){
                        for (Account toAccount : ClientData.getInstance().getList().get(i).getAccList()){
                            if (toAccount.getAccountNumber().equals(cardNumber.getText()) && fromAccount.getSecPass().equals(secondPass.getText()) && (Double.parseDouble(fromAccount.getBudget()) > Double.parseDouble(transferAmount.getText())) && !(ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getAccountNumber().equals(cardNumber.getText())) ){

                                fromAccount.withdraw(transferAmount.getText(),cardNumber.getText());
                                toAccount.deposit(transferAmount.getText(),ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getAccountNumber());
                                cardNumber.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                                transferAmount.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                                secondPass.setStyle(" -jfx-focus-color: #31b838; -jfx-unfocus-color: #31b838;");
                                cardNumber.clear();
                                transferAmount.clear();
                                secondPass.clear();
                                message.setText("Transfer was successful");
                                message.setStyle("-fx-text-fill:green");
                            }
                        }

                    }
                }else {
                    secondPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                }
            }else {
                cardNumber.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                if (secondPass.getText().trim().isEmpty()){
                    secondPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
                }
            }

        }else {
            transferAmount.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            if (cardNumber.getText().trim().isEmpty()){
                cardNumber.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            }
            if (secondPass.getText().trim().isEmpty()){
                secondPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            }
        }

//        cardNumber.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");


    }

    public void onBillPaymentClicked(){
        message.setText("");
        tableView.setVisible(false);
        moneyTransferPage.setVisible(false);
        billPage.setVisible(true);
    }
    public void onBillPaymentSubmit(){
        int result = BillData.getInstance().billPayment(billNum.getText(),index,billPass.getText());
        if (result == 1){
            message.setText("bill is paid successfully");
            message.setStyle("-fx-text-fill:green");
            billNum.setText("");
            billPass.setText("");
            billDebt.setText("Amount of Debt :");
        }else if (result == -1){
            message.setText("you have not enough money");
            message.setStyle("-fx-text-fill:red");
        }else if (result == -2){
            billPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
        }else{
            billNum.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            if (billPass.getText().trim().isEmpty()){
                billPass.setStyle(" -jfx-focus-color: red; -jfx-unfocus-color: red;");
            }
        }
    }

    public void onQuitClicked() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        content.getChildren().setAll(pane);
    }

}
