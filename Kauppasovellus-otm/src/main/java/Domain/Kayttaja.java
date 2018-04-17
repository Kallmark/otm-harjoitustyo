
package Domain;

public class Kayttaja {
    
    public String nimi;
    public Integer saldo;
    public Integer id;
    
    
    public Kayttaja(Integer id, String nimi, Integer saldo) {
        this.saldo=saldo;
        this.id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
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
    
    public Integer getSaldo(){
        return this.saldo;
    }
    
    public void setSaldo(Integer saldo){
        this.saldo=saldo;
    }
    
    @Override
    
    public String toString(){
        return this.nimi;
    }

}
