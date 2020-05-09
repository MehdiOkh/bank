package sample.users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Account {
    private String accountNumber;
    private String secPass;
    private String budget;
    private ObservableList<ClientTransaction> list = FXCollections.observableArrayList();

    public Account(String accountNumber, String secPass, String budget) {
        this.accountNumber = accountNumber;
        this.secPass = secPass;
        this.budget = budget;
    }


    public void deposit(String amount , String accNum){
        if (Double.parseDouble(amount)>0){

            double budget = Double.parseDouble(this.budget);
            budget += Double.parseDouble(amount);
            this.budget = budget+"";

            list.add(new ClientTransaction(accNum,"+"+amount));
        }
    }
    public void withdraw(String amount , String accNum){
        if (Double.parseDouble(amount)<Double.parseDouble(this.budget)){

            double budget = Double.parseDouble(this.budget);
            budget -= Double.parseDouble(amount);
            this.budget = budget+"";

            list.add(new ClientTransaction(accNum,"-"+amount));
        }
    }

    public void addTransaction(String dest,String amount,String date){
        list.add(new ClientTransaction(dest,amount,date));
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getSecPass() {
        return secPass;
    }

    public String getBudget() {
        return budget;
    }

    public ObservableList<ClientTransaction> getList() {
        return list;
    }

}
