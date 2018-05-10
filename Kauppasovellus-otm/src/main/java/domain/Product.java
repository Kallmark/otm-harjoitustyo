
package domain;

public class Product {
    
    private String name;
    private Double price;
    private Integer id;
    private Integer amount;
    private String info;
    
    
    public Product(Integer id, String name, Double price, Integer amount, String info) {
        this.price = price;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nimi) {
        this.name = nimi;
    }
    
    public double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double saldo) {
        this.price = saldo;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(Integer maara) {
        this.amount = maara;
    }
    
    public String getInfo() {
        return this.info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
    
    
    @Override
    
    public String toString() {
        return this.name + ", id: " + this.getId() + ", hinta: " + this.getPrice() + ", määrä: " + this.getAmount() + ", info: " + this.getInfo();
    }
}
