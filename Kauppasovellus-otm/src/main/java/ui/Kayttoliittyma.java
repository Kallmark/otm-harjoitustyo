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

import domain.Kayttaja;
import database.KayttajaDao;
import database.OstosDao;
import database.TuoteDao;
import domain.Ostos;
import domain.Tuote;
import java.time.Instant;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class Kayttoliittyma extends Application {

    @Override
    public void init() throws ClassNotFoundException {

    }

    public static void start(String[] args) {
        Application.launch(args);
    }

    @Override

    public void start(Stage ikkuna) throws ClassNotFoundException, SQLException {

        //Luodaan näkymät ja niihin liittyvät tavarat
        ikkuna.setTitle("Kauppasovellus");
        ikkuna.setHeight(600);

        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);

        Text alkuteksti = new Text();
        alkuteksti.setEffect(is);
        alkuteksti.setX(20);
        alkuteksti.setY(100);
        alkuteksti.setText("Tervetuloa kauppasovellukseen!");
        alkuteksti.setFill(javafx.scene.paint.Color.PINK);
        alkuteksti.setFont(Font.font(null, FontWeight.BOLD, 20));
        alkuteksti.setTranslateX(30);
        alkuteksti.setTranslateY(30);

        BorderPane aloitusNakyma2 = new BorderPane();
        GridPane aloitusNakyma = new GridPane();
        Scene aloitus = new Scene(aloitusNakyma2);
        GridPane kayttajanLisaysNakyma = new GridPane();
        Scene kayttajaNakyma = new Scene(kayttajanLisaysNakyma);
        GridPane tuotteenLisaysNakyma = new GridPane();
        Scene tuoteNakyma = new Scene(tuotteenLisaysNakyma);
        GridPane ostosNakyma = new GridPane();
        Scene ostosnakyma = new Scene(ostosNakyma);
        GridPane aikaNakyma = new GridPane();
        Scene aikanakyma = new Scene(aikaNakyma);
        GridPane kayttajanTietoNakyma = new GridPane();
        Scene kayttajanTiedot = new Scene(kayttajanTietoNakyma);

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

        Button OstosNappi = new Button("Tee ostos!");
        OstosNappi.setOnAction((event) -> {
            ikkuna.setScene(ostosnakyma);
            ikkuna.show();
        });

        Button aikaNappi = new Button("Ostostilastot!");
        aikaNappi.setOnAction((event) -> {
            ikkuna.setScene(aikanakyma);
            ikkuna.show();
        });

        aloitusNakyma2.setTop(alkuteksti);
        BorderPane.setAlignment(alkuteksti, Pos.CENTER);
        aloitusNakyma2.setCenter(aloitusNakyma);
        aloitusNakyma.add(toiminnallisuusTeksti, 0, 4);
        aloitusNakyma.add(kayttajanLisaysNappi, 1, 4);
        aloitusNakyma.add(tuotteenLisaysNappi, 2, 4);
        aloitusNakyma.add(OstosNappi, 3, 4);
        aloitusNakyma.add(aikaNappi, 4, 4);
        aloitusNakyma.setHgap(20);
        aloitusNakyma.setVgap(20);
        aloitusNakyma.setPadding(new Insets(20, 20, 20, 20));

        //Käyttäjienlisäysnäkymä
        Database database = new Database("jdbc:sqlite:kauppasovellus.db");
        KayttajaDao kayttajat = new KayttajaDao(database);

        Label toiminnallisuusTekstiKayttajaNakyma = new Label("Valitse toiminnallisuus");
        Label kayttajaTiedot = new Label("Tarkastele käyttäjien tietoja");

        Button palaaAloitusnakymaanKayttajanakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusnakymaanKayttajanakymasta.setOnAction((event) -> {
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

        ComboBox<Kayttaja> kayttajaValinta = new ComboBox<>();
        try {
            kayttajaValinta.getItems().addAll(kayttajat.findAll()
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
                kayttajaValinta.getItems().clear();
                kayttajaValinta.getItems().addAll(kayttajat.findAll());
            } catch (SQLException ex) {
                Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Button tarkasteleKayttajaa = new Button("Tarkastele");
        tarkasteleKayttajaa.setOnAction((event) -> {
            ikkuna.setScene(kayttajanTiedot);
            ikkuna.show();
        });

        kayttajanLisaysNakyma.add(nimiTeksti, 0, 0);
        kayttajanLisaysNakyma.add(nimiKentta, 0, 1);
        kayttajanLisaysNakyma.add(saldoTeksti, 1, 0);
        kayttajanLisaysNakyma.add(saldoKentta, 1, 1);
        kayttajanLisaysNakyma.add(lisaaNappi, 2, 1);
        kayttajanLisaysNakyma.add(naytaKayttajat, 3, 0);
        kayttajanLisaysNakyma.add(listakayttajista, 3, 1);
        kayttajanLisaysNakyma.add(kayttajaTiedot, 0, 4);
        kayttajanLisaysNakyma.add(toiminnallisuusTekstiKayttajaNakyma, 0, 6);
        kayttajanLisaysNakyma.add(palaaAloitusnakymaanKayttajanakymasta, 0, 7);
        kayttajanLisaysNakyma.add(tarkasteleKayttajaa, 3, 2);

        kayttajanLisaysNakyma.setHgap(20);
        kayttajanLisaysNakyma.setVgap(20);
        kayttajanLisaysNakyma.setPadding(new Insets(20, 20, 20, 20));

        //Kayttajantietonäkymä
        Kayttaja tarkasteltava = (Kayttaja) listakayttajista.getValue();

        Label kayttajaTietoja = new Label("Käyttäjän tiedot");
        Label muutaKayttajaTietoja = new Label("Muuta käyttäjän tietoja");
        Label kayttajanOstosHistoria = new Label("Käyttäjän ostoshistoria");

        TableView kayttajaTaulukko = new TableView<>();
        TableColumn nimiSarake = new TableColumn("Nimi");
        TableColumn saldoSarake = new TableColumn("Saldo");
        TableColumn idSarake = new TableColumn("Id");

        if (listakayttajista.getValue() != null) {
            ObservableList<Kayttaja> kayttajaData = FXCollections.observableArrayList(
                    new Kayttaja(tarkasteltava.getId(), tarkasteltava.getNimi(), tarkasteltava.getSaldo()));

            nimiSarake.setCellValueFactory(
                    new PropertyValueFactory<>("Id")
            );
            saldoSarake.setCellValueFactory(
                    new PropertyValueFactory<>("Nimi")
            );
            idSarake.setCellValueFactory(
                    new PropertyValueFactory<>("Saldo")
            );

            kayttajaTaulukko.setItems(kayttajaData);
            kayttajaTaulukko.getColumns().addAll(idSarake, nimiSarake, saldoSarake);
            
        }

        kayttajanTietoNakyma.add(kayttajaTietoja, 0, 0);
        kayttajanTietoNakyma.add(kayttajaTaulukko, 1, 1);

        //Tuotteenlisäysnäkymä
        TuoteDao tuotteet = new TuoteDao(database);

        Label toiminnallisuusTekstiTuoteNakyma = new Label("Valitse toiminnallisuus");
        Label tarkasteleTuotteita = new Label("Tarkastele tuotteita");
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

        ComboBox<Tuote> tuoteValinta = new ComboBox<>();
        try {
            tuoteValinta.getItems().addAll(tuotteet.findAll()
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
                listatuotteista.getItems().addAll(tuotteet.findAll());
                tuoteValinta.getItems().clear();
                tuoteValinta.getItems().addAll(tuotteet.findAll());
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
        tuotteenLisaysNakyma.add(tarkasteleTuotteita, 0, 2);
        tuotteenLisaysNakyma.add(toiminnallisuusTekstiTuoteNakyma, 0, 4);
        tuotteenLisaysNakyma.add(palaaAloitusNakymaanTuotenakymasta, 0, 5);

        tuotteenLisaysNakyma.setHgap(20);
        tuotteenLisaysNakyma.setVgap(20);
        tuotteenLisaysNakyma.setPadding(new Insets(10, 10, 10, 10));

        //Ostosnäkymä
        OstosDao ostokset = new OstosDao(database);
        Label toiminallisuusTekstiOstosNakyma = new Label("Valitse toiminnallisuus");
        Button palaaAloitusNakymaanOstosNakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusNakymaanOstosNakymasta.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        Label valitseKayttaja = new Label("Valitse kayttäjä:");
        Label valitseTuote = new Label("Valitse tuote:");
        Button teeOstos = new Button("Osta");

        teeOstos.setOnAction(e -> {
            try {
                Kayttaja kayttaja = kayttajaValinta.getValue();
                Tuote tuote = tuoteValinta.getValue();
                long sekunnit = Instant.now().getEpochSecond();
                System.out.println(sekunnit);
                ostokset.saveOrUpdate(new Ostos(kayttaja, tuote, sekunnit));
                Double kayttajanUusiSaldo = kayttaja.getSaldo() - tuote.getHinta();
                kayttaja.setSaldo(kayttajanUusiSaldo);
                kayttajat.saveOrUpdate(kayttaja);
                Integer tuotteenUusiMaara = tuote.getMaara() - 1;
                tuote.setMaara(tuotteenUusiMaara);
                tuotteet.saveOrUpdate(tuote);
                listakayttajista.getItems().clear();
                listakayttajista.getItems().addAll(kayttajat.findAll());
                listatuotteista.getItems().clear();
                listatuotteista.getItems().addAll(kayttajat.findAll());
                kayttajaValinta.getItems().clear();
                kayttajaValinta.getItems().addAll(kayttajat.findAll());
                tuoteValinta.getItems().clear();
                tuoteValinta.getItems().addAll(tuotteet.findAll());

            } catch (SQLException ex) {
                Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ostosNakyma.add(valitseKayttaja, 0, 0);
        ostosNakyma.add(valitseTuote, 0, 1);
        ostosNakyma.add(kayttajaValinta, 1, 0);
        ostosNakyma.add(tuoteValinta, 1, 1);
        ostosNakyma.add(teeOstos, 1, 2);
        ostosNakyma.add(toiminallisuusTekstiOstosNakyma, 2, 0);
        ostosNakyma.add(palaaAloitusNakymaanOstosNakymasta, 2, 1);

        ostosNakyma.setHgap(20);
        ostosNakyma.setVgap(20);
        ostosNakyma.setPadding(new Insets(10, 10, 10, 10));

        //Aikanäkymä
        Button palaaAloitusNakymaanAikanakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusNakymaanAikanakymasta.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        TableColumn<Map.Entry<Kayttaja, Integer>, Kayttaja> column1 = new TableColumn<>("Henkilö");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Kayttaja, Integer>, Kayttaja>, ObservableValue<Kayttaja>>() {

            @Override
            public ObservableValue<Kayttaja> call(TableColumn.CellDataFeatures<Map.Entry<Kayttaja, Integer>, Kayttaja> p) {
                return new SimpleObjectProperty<>(p.getValue().getKey());
            }

        });

        TableColumn<Map.Entry<Kayttaja, Integer>, Integer> column2 = new TableColumn<>("Ostosten määrä");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Kayttaja, Integer>, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<Kayttaja, Integer>, Integer> p) {
                return new SimpleObjectProperty<>(p.getValue().getValue());
            }

        });

        new AnimationTimer() {
            private long edellinen;

            @Override
            public void handle(long nykyhetki) {
                if (nykyhetki - edellinen < 200_000_000_0L) {
                    return;
                }
                edellinen = nykyhetki;
                System.out.println("testi");
                long sekunnitNyt = Instant.now().getEpochSecond();
                long sekunnitEnnen = sekunnitNyt - 3600;

                try {
                    Map<Kayttaja, Integer> ostosData = kayttajat.findAllTime(sekunnitNyt, sekunnitEnnen);
                    // System.out.println(ostosData);
                    ObservableList<Map.Entry<Kayttaja, Integer>> items = FXCollections.observableArrayList(ostosData.entrySet());

                    final TableView<Map.Entry<Kayttaja, Integer>> table = new TableView<>(items);

                    table.getColumns().setAll(column1, column2);
                    table.setMaxWidth(Region.USE_PREF_SIZE);
                    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    aikaNakyma.add(table, 0, 1);
                } catch (SQLException ex) {
                    Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();

        aikaNakyma.setHgap(20);
        aikaNakyma.setVgap(20);
        aikaNakyma.setPadding(new Insets(50, 50, 50, 50));

        TableView table2 = new TableView();

        Label tilastot = new Label("Eniten ostoksia tehneet käyttäjät:");

        aikaNakyma.add(palaaAloitusNakymaanAikanakymasta, 0, 2);
        aikaNakyma.add(tilastot, 0, 0);

        //Asetetaan aloitusnäkymä aluksi näytille
        ikkuna.setScene(aloitus);
        ikkuna.show();

    }
}
