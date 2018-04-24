/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author kallmark
 */
public class Tuote {
    
    public String nimi;
    public Double hinta;
    public Integer id;
    public Integer maara;
    public String info;
    
    
    public Tuote(Integer id, String nimi, Double hinta, Integer maara, String info) {
        this.hinta = hinta;
        this.id = id;
        this.nimi = nimi;
        this.maara = maara;
        this.info = info;
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
    
    public Double getHinta() {
        return this.hinta;
    }
    
    public void setHinta(Double saldo) {
        this.hinta = saldo;
    }
    
    public Integer getMaara() {
        return this.maara;
    }
    
    public void setMaara(Integer maara) {
        this.maara = maara;
    }
    
    public String getInfo() {
        return this.info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
    
    
    @Override
    
    public String toString() {
        return this.nimi + ", id: " + this.getId() + ", hinta: " + this.getHinta() + ", määrä: " + this.getMaara() + ", info: " + this.getInfo();
    }
}
