
package domain;

public class Kayttaja {
    
    private String nimi;
    private Double saldo;
    private Integer id;
    
    
    public Kayttaja(Integer id, String nimi, Double saldo) {
        this.saldo = saldo;
        this.id = id;
        this.nimi = nimi;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public double getSaldo() {
        return this.saldo;
    }
    
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
    @Override
    
    public String toString() {
        return this.nimi + ", id: " + this.getId() + ", saldo: " + this.getSaldo();
    }

}
