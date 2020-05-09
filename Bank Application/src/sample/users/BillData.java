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

public class BillData {
    public class Bill{
        private String billNumber;
        private String debt;

        public Bill(String billNumber, String debt) {
            this.billNumber = billNumber;
            this.debt = debt;
        }

        public String getBillNumber() {
            return billNumber;
        }

        public String getDebt() {
            return debt;
        }
    }
    private static BillData instance = new BillData();
    private ObservableList<Bill> billList ;

    public int billPayment(String bn ,int index,String secPass){
        int returnAmount = 0;
        for (Bill bill:billList){
            if (bill.getBillNumber().equals(bn)) {

                if (Double.parseDouble(ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getBudget()) > Double.parseDouble(bill.getDebt())){
                    if (ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).getSecPass().equals(secPass)){
                        ClientData.getInstance().getList().get(index).getAccList().get(Integer.parseInt(ClientData.getInstance().getRecentUser(1))).withdraw(bill.getDebt(),"Bill :" +bill.getBillNumber() );
                        BillData.getInstance().billList.remove(bill);
                        returnAmount = 1;
                        break;
                    }else{
                        returnAmount=-2;
                        break;
                    }

                }else {
                    returnAmount =-1;
                    break;
                }
            }
        }
        return returnAmount;
    }
    private BillData() {
        this.billList = FXCollections.observableArrayList();
    }

    public static BillData getInstance(){
        return instance;
    }

    public ObservableList<Bill> getBillList() {
        return billList;
    }

    public void saveData(){
        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("BillData");
            doc.appendChild(root);

            for (Bill bill: billList){

                Element Bill = doc.createElement("Bill");
                root.appendChild(Bill);

                Element BillNumber = doc.createElement("BillNumber");
                BillNumber.appendChild(doc.createTextNode(String.valueOf(bill.getBillNumber())));
                Bill.appendChild(BillNumber);

                Element Debt = doc.createElement("Debt");
                Debt.appendChild(doc.createTextNode(String.valueOf(bill.getDebt())));
                Bill.appendChild(Debt);
            }




            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            aTransformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            try {
                // location and name of XML file you can change as per need
                FileWriter fos = new FileWriter("./billsData.xml");
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
        try {
            File file = new File("billsData.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList nList = document.getElementsByTagName("Bill");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    String BillNum = eElement.getElementsByTagName("BillNumber").item(0).getTextContent();
                    String Debt = eElement.getElementsByTagName("Debt").item(0).getTextContent();

                    BillData.getInstance().billList.add(new Bill(BillNum,Debt));


                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
