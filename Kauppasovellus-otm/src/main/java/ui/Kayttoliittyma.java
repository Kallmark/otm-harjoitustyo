package ui;

import database.Database;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import domain.Kayttaja;
import database.KayttajaDao;
import database.TuoteDao;
import domain.Tuote;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class Kayttoliittyma extends Application {

    @Override
    public void init() throws ClassNotFoundException {

    }

    public static void start(String[] args) {
        Application.launch(args);
    }

    @Override

    public void start(Stage ikkuna) throws ClassNotFoundException {

        //Luodaan näkymät ja niihin liittyvät tavarat
        
        GridPane aloitusNakyma = new GridPane();
        Scene aloitus = new Scene(aloitusNakyma);
        GridPane kayttajanLisaysNakyma = new GridPane();
        Scene kayttajaNakyma = new Scene(kayttajanLisaysNakyma);
        GridPane tuotteenLisaysNakyma = new GridPane();
        Scene tuoteNakyma = new Scene(tuotteenLisaysNakyma);
        
        //Aloitusnäkymä
        Label toiminnallisuusTeksti = new Label("Valitse toiminnallisuus");
        Button kayttajanLisaysNappi = new Button("Lisää käyttäjiä!");
        kayttajanLisaysNappi.setOnAction((event) -> {
            ikkuna.setScene(kayttajaNakyma);
            ikkuna.show();
        });
        
        Button tuotteenLisaysNappi = new Button("Lisää tuotteita!");
        tuotteenLisaysNappi.setOnAction((event) -> {
            ikkuna.setScene(tuoteNakyma);
            ikkuna.show();
        });
        
        aloitusNakyma.add(toiminnallisuusTeksti, 0, 0);
        aloitusNakyma.add(kayttajanLisaysNappi, 1, 0);
        aloitusNakyma.add(tuotteenLisaysNappi, 2, 0);
        aloitusNakyma.setHgap(10);
        aloitusNakyma.setVgap(10);
        aloitusNakyma.setPadding(new Insets(10, 10, 10, 10));
        

        //Käyttäjienlisäysnäkymä
        Database database = new Database("jdbc:sqlite:kauppasovellus.db");
        KayttajaDao kayttajat = new KayttajaDao(database);
        
        Label toiminnallisuusTekstiKayttajaNakyma = new Label("Valitse toiminnallisuus");
        Button palaaAloitusNakymaanKayttajanakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusNakymaanKayttajanakymasta.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        Label listaKayttajista = new Label("Tarkastele asiakkaita");
        ComboBox<Object> listakayttajista = new ComboBox<>();
        try {
            listakayttajista.getItems().addAll(kayttajat.findAll()
            );
        } catch (SQLException ex) {
            Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
        }

        Label nimiTeksti = new Label("Nimi: ");
        TextField nimiKentta = new TextField();
        Label saldoTeksti = new Label("Saldo: ");
        Label naytaKayttajat = new Label("Selaa käyttäjiä");
        TextField saldoKentta = new TextField();

        Button lisaaNappi = new Button("Lisää henkilö!");

        lisaaNappi.setOnAction((event) -> {
            try {
                Kayttaja lisattava = new Kayttaja(null, nimiKentta.getText(), Double.parseDouble(saldoKentta.getText()));
                kayttajat.saveOrUpdate(lisattava);
                listakayttajista.getItems().clear();
                listakayttajista.getItems().addAll(kayttajat.findAll());
            } catch (SQLException ex) {
                Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        kayttajanLisaysNakyma.add(nimiTeksti, 0, 0);
        kayttajanLisaysNakyma.add(nimiKentta, 0, 1);
        kayttajanLisaysNakyma.add(saldoTeksti, 1, 0);
        kayttajanLisaysNakyma.add(saldoKentta, 1, 1);
        kayttajanLisaysNakyma.add(lisaaNappi, 2, 1);
        kayttajanLisaysNakyma.add(naytaKayttajat, 3, 0);
        kayttajanLisaysNakyma.add(listakayttajista, 3, 1);
        kayttajanLisaysNakyma.add(toiminnallisuusTekstiKayttajaNakyma, 4, 0);
        kayttajanLisaysNakyma.add(palaaAloitusNakymaanKayttajanakymasta, 4, 1);

        kayttajanLisaysNakyma.setHgap(10);
        kayttajanLisaysNakyma.setVgap(10);
        kayttajanLisaysNakyma.setPadding(new Insets(10, 10, 10, 10));
        
        //Tuotteenlisäysnäkymä
        
        TuoteDao tuotteet = new TuoteDao(database);
        
        Label toiminnallisuusTekstiTuoteNakyma = new Label("Valitse toiminnallisuus");
        Button palaaAloitusNakymaanTuotenakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusNakymaanTuotenakymasta.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        Label listaTuotteista = new Label("Tarkastele tuotteita");
        
        ComboBox<Object> listatuotteista = new ComboBox<>();
        try {
            listatuotteista.getItems().addAll(tuotteet.findAll()
            );
        } catch (SQLException ex) {
            Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
        }

        Label tuotteenNimi = new Label("Nimi: ");
        TextField tuotteenNimiKentta = new TextField();
        Label tuotteenHinta = new Label("Hinta: ");
        TextField tuotteenHintaKentta = new TextField();
        Label tuotteenMaara = new Label("Maara: ");
        TextField tuotteenMaaraKentta = new TextField();
        Label tuotteenInfo = new Label("Info: ");
        TextField tuotteenInfoKentta = new TextField();

        Button lisaaTuote = new Button("Lisää tuote!");

        lisaaTuote.setOnAction((event) -> {
            try {
                Tuote lisattavaTuote = new Tuote(null, tuotteenNimiKentta.getText(), Double.parseDouble(tuotteenHintaKentta.getText()), Integer.parseInt(tuotteenMaaraKentta.getText()), tuotteenInfoKentta.getText());
                tuotteet.saveOrUpdate(lisattavaTuote);
                listatuotteista.getItems().clear();
                listatuotteista.getItems().addAll(kayttajat.findAll());
            } catch (SQLException ex) {
                Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tuotteenLisaysNakyma.add(tuotteenNimi, 0, 0);
        tuotteenLisaysNakyma.add(tuotteenNimiKentta, 0, 1);
        tuotteenLisaysNakyma.add(tuotteenHinta, 1, 0);
        tuotteenLisaysNakyma.add(tuotteenHintaKentta, 1, 1);
        tuotteenLisaysNakyma.add(tuotteenMaara, 2, 0);
        tuotteenLisaysNakyma.add(tuotteenMaaraKentta, 2, 1);
        tuotteenLisaysNakyma.add(tuotteenInfo, 3, 0);
        tuotteenLisaysNakyma.add(tuotteenInfoKentta, 3, 1);
        tuotteenLisaysNakyma.add(lisaaTuote, 4, 1);
        tuotteenLisaysNakyma.add(listatuotteista, 0, 3);
        tuotteenLisaysNakyma.add(toiminnallisuusTekstiTuoteNakyma, 0, 4);
        tuotteenLisaysNakyma.add(palaaAloitusNakymaanTuotenakymasta, 1, 4);

        tuotteenLisaysNakyma.setHgap(10);
        tuotteenLisaysNakyma.setVgap(10);
        tuotteenLisaysNakyma.setPadding(new Insets(10, 10, 10, 10));

        //Asetetaan aloitusnäkymä aluksi näytille
        
        ikkuna.setScene(aloitus);
        ikkuna.show();
        
        
    }
}
