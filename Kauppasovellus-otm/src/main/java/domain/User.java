
package domain;

public class User {
    
    private String name;
    private Double balance;
    private Integer id;
    
    
    public User(Integer id, String name, Double balance) {
        this.balance = balance;
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public double getBalance() {
        return this.balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    
    @Override
    
    public String toString() {
        return this.name + ", id: " + this.getId() + ", saldo: " + this.getBalance();
    }

}
