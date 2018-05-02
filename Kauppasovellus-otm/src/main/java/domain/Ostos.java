package domain;

public class Ostos {
    
    private Kayttaja kayttaja;
    private Tuote tuote;
    private long aika;
    

    public Ostos(Kayttaja kayttaja, Tuote tuote, long aika) {
        this.kayttaja = kayttaja;
        this.tuote = tuote;
        this.aika = aika;
    }

    public Kayttaja getKayttaja() {
        return this.kayttaja;
    }

    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
    }

    public Tuote getTuote() {
        return this.tuote;
    }

    public void setTuote(Tuote tuote) {
        this.tuote = tuote;
    }
    
    public long getAika() {
        return this.aika;
    }
    
    public void setAika(long aika) {
        this.aika =  aika;
    }

    @Override

    public String toString() {
        return "Käyttäjä: " + this.kayttaja.getNimi() + ", tuote: " + this.tuote.getNimi() + ", aika: " + this.aika;
    }

}
