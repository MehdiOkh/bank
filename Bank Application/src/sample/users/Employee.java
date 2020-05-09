package sample.users;

public class Employee {

    private String name;
    private String password;
    private String branch;

    public Employee(String name, String password,String branch) {
        this.name = name;
        this.password = password;
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getBranch() {
        return branch;
    }

//    public void newClient(Client newOne){
//        ClientData.getInstance().addClient(newOne);
//    }
}
