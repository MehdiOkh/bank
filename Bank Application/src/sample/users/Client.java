package sample.users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client  {

    private String name;
    private String clientName;
    private String password;
//    private Account account;
    private ObservableList<Account> accList = FXCollections.observableArrayList();



//    public Client(String name, String password, Account account) {
//        this.name = name;
//        this.password = password;
//        this.account = account;
//        addAccount(account);
//    }
    public Client(String name,String password) {
//        this.username = username;
        this.clientName = "m";
        this.name = name;
        this.password = password;

    }

    public void addAccount(Account account){
        accList.add(account);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
    //    public String getUsername() {
//        return username;
//    }

    //    public Account getAccount() {
//        return account;
//    }
    public ObservableList<Account> getAccList() {
        return accList;
    }

}
