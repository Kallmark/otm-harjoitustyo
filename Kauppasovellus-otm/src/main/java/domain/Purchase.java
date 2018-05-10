package domain;

public class Purchase {
    
    private User user;
    private Product product;
    private long time;
    

    public Purchase(User user, Product product, long time) {
        this.user = user;
        this.product = product;
        this.time = time;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public void setTime(long time) {
        this.time =  time;
    }

    @Override

    public String toString() {
        return "Käyttäjä: " + this.user.getName() + ", tuote: " + this.product.getName() + ", aika: " + this.time;
    }

}
