package entities;

public class Customer {
    private Integer id;
    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Customer(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) {this.name = name; }
    public void setEmail(String email) {this.email = email; }
    public void setAddress(String address) {this.address = address; }

    @Override
    public String toString() {
     return "Customer{" +
             "id =" + id +
             ", name ='" + name + '\'' +
             ", email ='" + email + '\'' +
             ", address ='" + address + '\'' +
             '}';
    }



}
