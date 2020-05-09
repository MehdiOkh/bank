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

public class EmployeeData {

    private static EmployeeData instance = new EmployeeData();
    private ObservableList<Employee> employeeList ;
    private ArrayList<Employee> enteredEmployee = new ArrayList<>();

    private EmployeeData() {
        this.employeeList = FXCollections.observableArrayList();
    }

    public static EmployeeData getInstance(){
        return instance;
    }

    public void addEmployee(Employee employee){
        employeeList.add(employee);
    }

    public  ObservableList<Employee> getList(){
        return employeeList;
    }

    public boolean isPresent(String id,String pass ,String branch){
        for (int i = 0 ; i < employeeList.size();i++){
            if (employeeList.get(i).getName().equals(id)){
                if (employeeList.get(i).getBranch().equals(branch)){
                    if (employeeList.get(i).getPassword().equals(pass)){
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public void addEnteredEmp(String username){
        for (Employee employee : EmployeeData.getInstance().getList()){
            if (employee.getName().equals(username)){
                enteredEmployee.add(employee);


            }
        }
    }
    public Employee getEnteredEmp(){
        return enteredEmployee.get(0);
    }


    public void saveData(){
        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("EmployeeInfo");
            doc.appendChild(root);




            for (Employee emp : employeeList) {

                Element Employee = doc.createElement("Employee");
                root.appendChild(Employee);

                Element username = doc.createElement("Username");
                username.appendChild(doc.createTextNode(String.valueOf(emp.getName())));
                Employee.appendChild(username);

                Element pass = doc.createElement("Pass");
                pass.appendChild(doc.createTextNode(String.valueOf(emp.getPassword())));
                Employee.appendChild(pass);

                Element branch = doc.createElement("Branch");
                branch.appendChild(doc.createTextNode(String.valueOf(emp.getBranch())));
                Employee.appendChild(branch);

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
                FileWriter fos = new FileWriter("./employeeData.xml");
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
            File file = new File("employeeData.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList nList = document.getElementsByTagName("Employee");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    String username = eElement.getElementsByTagName("Username").item(0).getTextContent();
                    String password = eElement.getElementsByTagName("Pass").item(0).getTextContent();
                    String branch =eElement.getElementsByTagName("Branch").item(0).getTextContent();

                    EmployeeData.getInstance().addEmployee(new Employee(username,password,branch));


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
