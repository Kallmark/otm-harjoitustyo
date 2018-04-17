package ui;

import Database.Database;
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

import Domain.Kayttaja;
import Database.KayttajaDao;
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
        Scene nakyma = new Scene(kayttajanLisaysNakyma);
        
        //Aloitusnäkymä
        Label label1 = new Label("Valitse toiminnallisuus");
        Button button1 = new Button("Lisää käyttäjiä");
        button1.setOnAction((event) -> {
            ikkuna.setScene(nakyma);
            ikkuna.show();
        });
        
        aloitusNakyma.add(label1, 0, 0);
        aloitusNakyma.add(button1, 0, 1);
        aloitusNakyma.setHgap(10);
        aloitusNakyma.setVgap(10);
        aloitusNakyma.setPadding(new Insets(10, 10, 10, 10));
        

        //Käyttäjienlisäysnäkymä
        Database database = new Database("jdbc:sqlite:kauppasovellus.db");
        KayttajaDao kayttajat = new KayttajaDao(database);
        
        Label label2 = new Label("Valitse toiminnallisuus");
        Button button2 = new Button("Palaa aloitusnäkymään");
        button2.setOnAction((event) -> {
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
        TextField saldoKentta = new TextField();

        Button lisaaNappi = new Button("Lisää henkilö!");

        lisaaNappi.setOnAction((event) -> {
            try {
                Kayttaja lisattava = new Kayttaja(null, nimiKentta.getText(), Integer.parseInt(saldoKentta.getText()));
                kayttajat.saveOrUpdate(lisattava);
                listakayttajista.getItems().clear();
                listakayttajista.getItems().addAll(kayttajat.findAll());
            } catch (SQLException ex) {
                Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        kayttajanLisaysNakyma.add(nimiTeksti, 0, 0);
        kayttajanLisaysNakyma.add(nimiKentta, 1, 0);
        kayttajanLisaysNakyma.add(saldoTeksti, 0, 1);
        kayttajanLisaysNakyma.add(saldoKentta, 1, 1);
        kayttajanLisaysNakyma.add(lisaaNappi, 1, 2);
        kayttajanLisaysNakyma.add(listakayttajista, 2, 2);
        kayttajanLisaysNakyma.add(listaKayttajista, 2, 1);
        kayttajanLisaysNakyma.add(label2, 3, 0);
        kayttajanLisaysNakyma.add(button2, 4, 0);

        kayttajanLisaysNakyma.setHgap(10);
        kayttajanLisaysNakyma.setVgap(10);
        kayttajanLisaysNakyma.setPadding(new Insets(10, 10, 10, 10));

        //Asetetaan aloitusnäkymä aluksi näytille
        
        ikkuna.setScene(aloitus);
        ikkuna.show();
        
        
    }
}
