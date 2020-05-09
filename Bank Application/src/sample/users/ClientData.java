package sample.users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ClientData {

    private static ClientData instance = new ClientData();
    private ObservableList<Client> clientList ;
    private ArrayList<String> username = new ArrayList<>();

    private ClientData() {
        this.clientList = FXCollections.observableArrayList();
        username.add("");
        username.add("0");
    }

    public static ClientData getInstance(){
        return instance;
    }

    public void addClient(Client client){
        clientList.add(client);
    }
    public  ObservableList<Client> getList(){
        return clientList;
    }
    public boolean isPresent(String id,String pass ){
        for (Client client : clientList) {
            if (client.getName().equals(id)) {
                if (client.getPassword().equals(pass)) {
                    return true;
                }
            }

        }
        return false;
    }
    public int indexOfClient(String id){
        for (int i = 0 ; i < clientList.size();i++){
            if (clientList.get(i).getName().equals(id)){
                return i;
            }
        }
        return -1;
    }

    public void setRecentUser(int index,String username){
        this.username.set(index,username);

    }
    public String getRecentUser(int i){
        return username.get(i);
    }
    public void saveData(){
        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("ClientInfo");
            doc.appendChild(root);




            for (Client emp : clientList) {

                Element client = doc.createElement("Client");
                root.appendChild(client);

                Element username = doc.createElement("Username");
                username.appendChild(doc.createTextNode(String.valueOf(emp.getName())));
                client.appendChild(username);

                Element cn = doc.createElement("Name");
                cn.appendChild(doc.createTextNode(String.valueOf(emp.getClientName())));
                client.appendChild(cn);


                Element pass = doc.createElement("Pass");
                pass.appendChild(doc.createTextNode(String.valueOf(emp.getPassword())));
                client.appendChild(pass);



                for (Account account : emp.getAccList()  ){

                    Element acc = doc.createElement("Account");
                    client.appendChild(acc);

                    Element accNumber = doc.createElement("AccountNumber");
                    accNumber.appendChild(doc.createTextNode(String.valueOf(account.getAccountNumber())));
                    acc.appendChild(accNumber);

                    Element secPass = doc.createElement("SecondPass");
                    secPass.appendChild(doc.createTextNode(String.valueOf(account.getSecPass())));
                    acc.appendChild(secPass);

                    Element budget = doc.createElement("Budget");
                    budget.appendChild(doc.createTextNode(String.valueOf(account.getBudget())));
                    acc.appendChild(budget);



                    for (ClientTransaction report : account.getList()){
                        Element transaction = doc.createElement("Transaction");
                        acc.appendChild(transaction);

                        Element destination = doc.createElement("Destination");
                        destination.appendChild(doc.createTextNode(String.valueOf(report.getDestination())));
                        transaction.appendChild(destination);

                        Element amount = doc.createElement("Amount");
                        amount.appendChild(doc.createTextNode(String.valueOf(report.getAmount())));
                        transaction.appendChild(amount);

                        Element date = doc.createElement("Date");
                        date.appendChild(doc.createTextNode(String.valueOf(report.getDate())));
                        transaction.appendChild(date);
                    }
                }


            }


            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            try {
                FileWriter fos = new FileWriter("./clientData.xml");
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);

            } catch (IOException e) {

                e.printStackTrace();
            }

        } catch (TransformerException ex) {
            System.out.println("Error outputting document");

        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }
    }

    public void readData(){
//        try {
//            File file = new File("clientData.xml");
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
//                    .newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document document = documentBuilder.parse(file);
//            NodeList nList = document.getElementsByTagName("Client");
//            for (int temp = 0; temp < nList.getLength(); temp++) {
//                Node node = nList.item(temp);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//
//                    Element eElement = (Element) node;
//                    String username = eElement.getElementsByTagName("Username").item(0).getTextContent();
//                    String password = eElement.getElementsByTagName("Pass").item(0).getTextContent();
//                    Client newClient = new Client(username,password);
//
//
//                    NodeList accountList = document.getElementsByTagName("Account");
//                    for (int i = 0; i < accountList.getLength(); i++){
//
//                            String accnum = eElement.getElementsByTagName("AccountNumber").item(0).getTextContent();
//                            String secpass = eElement.getElementsByTagName("SecondPass").item(0).getTextContent();
//                            String budget = eElement.getElementsByTagName("Budget").item(0).getTextContent();
//                            Account newAccount =new Account(accnum,secpass,budget);
//
//
//                            NodeList transactionList = document.getElementsByTagName("Transaction");
//                            for (int j = 0; j <transactionList.getLength(); j++){
//
//                                    String destination = eElement.getElementsByTagName("Destination").item(0).getTextContent();
//                                    String amount = eElement.getElementsByTagName("Amount").item(0).getTextContent();
//                                    String date = eElement.getElementsByTagName("Date").item(0).getTextContent();
//                                    newAccount.addTransaction(destination,amount,date);
//
//
//                            }
//                            newClient.addAccount(newAccount);
//
//
//                    }
//
//
//
//                    ClientData.getInstance().addClient(newClient);
//
//
//                }
//            }
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }

        try {
            File file = new File("clientData.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList clientList = document.getElementsByTagName("Client");


            for (int i = 0; i<clientList.getLength(); i++){

                Node clientNode = clientList.item(i);
                Element clientSub = (Element) clientNode;

                String name = clientSub.getElementsByTagName("Username").item(0).getTextContent();
                String cn = clientSub.getElementsByTagName("Name").item(0).getTextContent();
                String pwd = clientSub.getElementsByTagName("Pass").item(0).getTextContent();
                Client client = new Client(name,pwd);

                client.setClientName(cn);

                NodeList accountList = clientSub.getElementsByTagName("Account");
                for (int j = 0; j<accountList.getLength(); j++){

                    Node accNode = accountList.item(j);
                    Element accSub = (Element) accNode;

                    String an = accSub.getElementsByTagName("AccountNumber").item(0).getTextContent();
                    String sn = accSub.getElementsByTagName("SecondPass").item(0).getTextContent();
                    String budget = accSub.getElementsByTagName("Budget").item(0).getTextContent();

                    Account account = new Account(an,sn,budget);


                    NodeList transactionList = accSub.getElementsByTagName("Transaction");
                    for (int k = 0; k < transactionList.getLength(); k++){

                        Node transactionNode = transactionList.item(k);
                        Element transactionSub = (Element) transactionNode;

                        String destination = transactionSub.getElementsByTagName("Destination").item(0).getTextContent();
                        String amount = transactionSub.getElementsByTagName("Amount").item(0).getTextContent();
                        String date = transactionSub.getElementsByTagName("Date").item(0).getTextContent();

                        account.addTransaction(destination,amount,date);

                    }

                    client.addAccount(account);


                }
                ClientData.getInstance().addClient(client);
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }


    }
}
