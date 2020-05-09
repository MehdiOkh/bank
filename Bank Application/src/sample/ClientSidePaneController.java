package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.users.ClientData;

public class ClientSidePaneController {
    String username = ClientData.getInstance().getRecentUser(0);
    int index = ClientData.getInstance().indexOfClient(username);
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private Label name;
    @FXML
    private Label balance;

    @FXML
    public void initialize(){

        for (int i = 0; i < ClientData.getInstance().getList().get(index).getAccList().size(); i++){
            String accountNumber = ClientData.getInstance().getList().get(index).getAccList().get(i).getAccountNumber();
            combobox.getItems().add(accountNumber);
        }
        combobox.getSelectionModel().selectFirst();
        name.setText(ClientData.getInstance().getList().get(index).getClientName());
        balance.setText(ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getBudget()+"$");


    }
    @FXML
    public void onSetComboBoxValue(){

        for (int i = 0; i < ClientData.getInstance().getList().get(index).getAccList().size(); i++){
            String accountNumber = ClientData.getInstance().getList().get(index).getAccList().get(i).getAccountNumber();
            if (accountNumber.equals(combobox.getValue())){

                ClientData.getInstance().setRecentUser(1,i+"");

            }
        }
        balance.setText(ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getBudget()+"$");


    }
}
