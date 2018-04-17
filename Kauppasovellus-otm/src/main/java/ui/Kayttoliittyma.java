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

public class Kayttoliittyma extends Application {

    @Override
    public void init() throws ClassNotFoundException {
        
    }
    
    public static void start(String[] args) {
        Application.launch(args);
    }

    @Override

    public void start(Stage ikkuna) throws ClassNotFoundException {
        
        Database database = new Database("jdbc:sqlite:kauppasovellus.db");
        KayttajaDao kayttajat = new KayttajaDao(database);
        
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
            } catch (SQLException ex) {
                Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        GridPane komponenttiryhma = new GridPane();
        komponenttiryhma.add(nimiTeksti, 0, 0);
        komponenttiryhma.add(nimiKentta, 1, 0);
        komponenttiryhma.add(saldoTeksti, 0, 1);
        komponenttiryhma.add(saldoKentta, 1, 1);
        komponenttiryhma.add(lisaaNappi, 1, 2);
        komponenttiryhma.add(listakayttajista, 2, 2);

        komponenttiryhma.setHgap(10);
        komponenttiryhma.setVgap(10);
        komponenttiryhma.setPadding(new Insets(10, 10, 10, 10));

        Scene nakyma = new Scene(komponenttiryhma);

        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
}
