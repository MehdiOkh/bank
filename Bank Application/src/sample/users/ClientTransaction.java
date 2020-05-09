package sample.users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientTransaction {

    private String destination;
    private String date;
    private String amount;

    public ClientTransaction ( String destination, String amount) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        this.destination = destination;
        this.date = df.format(date);
        this.amount = amount;
    }
    public ClientTransaction ( String destination, String amount,String date) {


        this.destination = destination;
        this.date = date;
        this.amount = amount;
    }

//    public ObservableList<ClientTransaction> newTransaction(){
//        TransactionData.getInstance().addTransaction(new ClientTransaction(this.destination,this.amount));
//        return TransactionData.getInstance().getList();
//    }



    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

}
