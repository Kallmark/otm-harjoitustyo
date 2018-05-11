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

import domain.User;
import database.UserDao;
import database.PurchaseDao;
import database.ProductDao;
import domain.Logic;
import domain.Purchase;
import domain.Product;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Properties;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class UserInterface extends Application {

    private UserDao kayttajat;
    private ProductDao tuotteet;
    private PurchaseDao ostokset;
    private Long time;
    private Logic logic;

    @Override
    public void init() throws ClassNotFoundException, FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

        String databaseFile = properties.getProperty("database");
        System.out.println(databaseFile);
        this.time = Long.parseLong(properties.getProperty("time"));

        Database database = new Database(databaseFile);
        this.kayttajat = new UserDao(database);
        this.tuotteet = new ProductDao(database);
        this.ostokset = new PurchaseDao(database);
        this.logic = new Logic(kayttajat, tuotteet, ostokset);

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
        BorderPane kayttajanTietoNakyma2 = new BorderPane();
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
        GridPane stats = new GridPane();
        Scene statsView = new Scene(stats);

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

        Button statsButton = new Button("Tarkastele tilastoja");
        statsButton.setOnAction((event) -> {
            ikkuna.setScene(statsView);
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
        aloitusNakyma.add(statsButton, 4, 5);
        aloitusNakyma.setHgap(20);
        aloitusNakyma.setVgap(20);
        aloitusNakyma.setPadding(new Insets(20, 20, 20, 20));

        //Tilastonäkymä
        Label statsLabel = new Label("Käyttäjätilastoja");
        Label averageLabel = new Label();
        Label overallLabel = new Label();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Variable");
        yAxis.setLabel("Balance");
        BarChart<String, Number> statsBar = new BarChart<String, Number>(xAxis, yAxis);
        statsBar.setTitle("Database stats");
        XYChart.Series overallBalance = new XYChart.Series();
        overallBalance.setName("Overall balance");
        XYChart.Series averageBalance = new XYChart.Series();
        averageBalance.setName("Average balance");
        statsBar.getData().addAll(overallBalance, averageBalance);
        
        Button returnToStartFromStats = new Button("Palaa aloitusnäkymään");
        returnToStartFromStats.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        stats.add(statsBar, 1, 1);
        stats.add(statsLabel, 0, 1);
        stats.add(averageLabel, 1, 2);
        stats.add(overallLabel, 1, 3);
        stats.add(returnToStartFromStats, 1, 4);
        stats.setHgap(20);
        stats.setVgap(20);
        stats.setPadding(new Insets(20, 20, 20, 20));

        //Kayttajantietonäkymä
        Label kayttajaTietoja = new Label("Käyttäjän tiedot:");
        Label muutaKayttajaTietoja = new Label("Muuta käyttäjän tietoja");
        Label kayttajanOstosHistoria = new Label("Käyttäjän ostoshistoria");
        TextField kayttajanNimiMuokkaa = new TextField();
        TextField kayttajanSaldoMuokkaa = new TextField();
        TextField kayttajanIdMuokkaa = new TextField();
        Label kayttajanNimi = new Label("Nimi: ");
        Label kayttajanSaldo = new Label("Saldo: ");
        Label kayttajanId = new Label("Id :");
        Label succesfullupdate = new Label();

        Label listaKayttajista = new Label("Tarkastele asiakkaita");
        ComboBox<Object> listakayttajista = new ComboBox<>();
        listakayttajista.getItems().addAll(logic.findAllUsers()
        );
        ComboBox<User> kayttajaValinta = new ComboBox<>();
        kayttajaValinta.getItems().addAll(logic.findAllUsers()
        );

        TableColumn<Map.Entry<java.time.LocalDateTime, String>, java.time.LocalDateTime> column3 = new TableColumn<>("Milloin ostettu");
        column3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<java.time.LocalDateTime, String>, java.time.LocalDateTime>, ObservableValue<java.time.LocalDateTime>>() {

            @Override
            public ObservableValue<java.time.LocalDateTime> call(TableColumn.CellDataFeatures<Map.Entry<java.time.LocalDateTime, String>, java.time.LocalDateTime> p) {
                return new SimpleObjectProperty<>(p.getValue().getKey());
            }

        });

        TableColumn<Map.Entry<java.time.LocalDateTime, String>, String> column4 = new TableColumn<>("Tuote");
        column4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<java.time.LocalDateTime, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<java.time.LocalDateTime, String>, String> p) {
                return new SimpleObjectProperty<>(p.getValue().getValue());
            }

        });

        Button tarkasteleKayttajaa = new Button("Tarkastele");
        tarkasteleKayttajaa.setOnAction((event) -> {
            User tarkasteltava = (User) listakayttajista.getValue();
            kayttajanNimiMuokkaa.setText(tarkasteltava.getName());
            kayttajanSaldoMuokkaa.setText(Double.toString(tarkasteltava.getBalance()));
            kayttajanIdMuokkaa.setText(Integer.toString(tarkasteltava.getId()));
            Map<java.time.LocalDateTime, String> tuotehistoria = logic.findUsersPurchaseHistory(tarkasteltava.getId());
            ObservableList<Map.Entry<java.time.LocalDateTime, String>> items = FXCollections.observableArrayList(tuotehistoria.entrySet());
            final TableView<Map.Entry<java.time.LocalDateTime, String>> table = new TableView<>(items);
            table.getColumns().setAll(column4, column3);
            kayttajanTietoNakyma.add(table, 5, 0);
            ikkuna.setScene(kayttajanTiedot);
            ikkuna.show();
        });

        Button muokkaaKayttajaa = new Button("Muokkaa");
        muokkaaKayttajaa.setOnAction((event) -> {
            logic.saveOrUpdateUser(new User(Integer.parseInt(kayttajanIdMuokkaa.getText()), kayttajanNimiMuokkaa.getText(), Double.parseDouble(kayttajanSaldoMuokkaa.getText())));
            listakayttajista.getItems().clear();
            listakayttajista.getItems().addAll(logic.findAllUsers());
            kayttajaValinta.getItems().clear();
            kayttajaValinta.getItems().addAll(logic.findAllUsers());
        });

        Button palaaAloitusNakymaanKayttajaTietoNakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusNakymaanKayttajaTietoNakymasta.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        Button palaaKayttajanLisaysNakymaanTietoNakymasta = new Button("Palaa lisäysnäkymään");
        palaaKayttajanLisaysNakymaanTietoNakymasta.setOnAction((event) -> {
            ikkuna.setScene(kayttajaNakyma);
            ikkuna.show();
        });

        kayttajanTietoNakyma.add(kayttajaTietoja, 0, 0);
        kayttajanTietoNakyma.add(kayttajanNimi, 0, 1);
        kayttajanTietoNakyma.add(kayttajanSaldo, 0, 2);
        kayttajanTietoNakyma.add(kayttajanId, 0, 3);
        kayttajanTietoNakyma.add(kayttajanNimiMuokkaa, 1, 1);
        kayttajanTietoNakyma.add(kayttajanSaldoMuokkaa, 1, 2);
        kayttajanTietoNakyma.add(kayttajanIdMuokkaa, 1, 3);
        kayttajanTietoNakyma.add(muokkaaKayttajaa, 1, 4);
        kayttajanTietoNakyma.add(palaaAloitusNakymaanKayttajaTietoNakymasta, 0, 4);
        kayttajanTietoNakyma.add(palaaKayttajanLisaysNakymaanTietoNakymasta, 0, 5);
        kayttajanTietoNakyma.setHgap(10);
        kayttajanTietoNakyma.setVgap(20);
        kayttajanTietoNakyma.setPadding(new Insets(20, 20, 20, 20));

        //Käyttäjienlisäysnäkymä
        Label toiminnallisuusTekstiKayttajaNakyma = new Label("Valitse toiminnallisuus");

        Button palaaAloitusnakymaanKayttajanakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusnakymaanKayttajanakymasta.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        Label nimiTeksti = new Label("Nimi: ");
        TextField nimiKentta = new TextField();
        Label saldoTeksti = new Label("Saldo: ");
        Label naytaKayttajat = new Label("Selaa käyttäjiä");
        TextField saldoKentta = new TextField();

        Button lisaaNappi = new Button("Lisää henkilö!");
        lisaaNappi.setOnAction((event) -> {
            logic.saveOrUpdateUser(new User(-1, nimiKentta.getText(), Double.parseDouble(saldoKentta.getText())));
            listakayttajista.getItems().clear();
            listakayttajista.getItems().addAll(logic.findAllUsers());
            kayttajaValinta.getItems().clear();
            kayttajaValinta.getItems().addAll(logic.findAllUsers());
        });

        kayttajanLisaysNakyma.add(nimiTeksti, 0, 0);
        kayttajanLisaysNakyma.add(nimiKentta, 0, 1);
        kayttajanLisaysNakyma.add(saldoTeksti, 1, 0);
        kayttajanLisaysNakyma.add(saldoKentta, 1, 1);
        kayttajanLisaysNakyma.add(lisaaNappi, 2, 1);
        kayttajanLisaysNakyma.add(naytaKayttajat, 3, 0);
        kayttajanLisaysNakyma.add(listakayttajista, 3, 1);
        kayttajanLisaysNakyma.add(toiminnallisuusTekstiKayttajaNakyma, 0, 6);
        kayttajanLisaysNakyma.add(palaaAloitusnakymaanKayttajanakymasta, 0, 7);
        kayttajanLisaysNakyma.add(tarkasteleKayttajaa, 3, 2);

        kayttajanLisaysNakyma.setHgap(20);
        kayttajanLisaysNakyma.setVgap(20);
        kayttajanLisaysNakyma.setPadding(new Insets(20, 20, 20, 20));

        //Tuotteenlisäysnäkymä
        Label toiminnallisuusTekstiTuoteNakyma = new Label("Valitse toiminnallisuus");
        Label tarkasteleTuotteita = new Label("Tarkastele tuotteita");
        Button palaaAloitusNakymaanTuotenakymasta = new Button("Palaa aloitusnäkymään");
        palaaAloitusNakymaanTuotenakymasta.setOnAction((event) -> {
            ikkuna.setScene(aloitus);
            ikkuna.show();
        });

        Label listaTuotteista = new Label("Tarkastele tuotteita");

        ComboBox<Object> listatuotteista = new ComboBox<>();
        listatuotteista.getItems().addAll(logic.findAllProducts()
        );

        ComboBox<Product> tuoteValinta = new ComboBox<>();
        tuoteValinta.getItems().addAll(logic.findAllProducts()
        );

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
            logic.saveOrUpdateProduct(new Product(-1, tuotteenNimiKentta.getText(), Double.parseDouble(tuotteenHintaKentta.getText()), Integer.parseInt(tuotteenMaaraKentta.getText()), tuotteenInfoKentta.getText()));
            listatuotteista.getItems().clear();
            listatuotteista.getItems().addAll(logic.findAllProducts());
            tuoteValinta.getItems().clear();
            tuoteValinta.getItems().addAll(logic.findAllProducts());
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
            logic.saveOrUpdatePurchase(new Purchase(kayttajaValinta.getValue(), tuoteValinta.getValue(), Instant.now().getEpochSecond()));
            listakayttajista.getItems().clear();
            listakayttajista.getItems().addAll(logic.findAllUsers());
            listatuotteista.getItems().clear();
            listatuotteista.getItems().addAll(logic.findAllProducts());
            kayttajaValinta.getItems().clear();
            kayttajaValinta.getItems().addAll(logic.findAllUsers());
            tuoteValinta.getItems().clear();
            tuoteValinta.getItems().addAll(logic.findAllProducts());
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

        TableColumn<Map.Entry<User, Integer>, User> column1 = new TableColumn<>("Henkilö");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<User, Integer>, User>, ObservableValue<User>>() {

            @Override
            public ObservableValue<User> call(TableColumn.CellDataFeatures<Map.Entry<User, Integer>, User> p) {
                return new SimpleObjectProperty<>(p.getValue().getKey());
            }

        });

        TableColumn<Map.Entry<User, Integer>, Integer> column2 = new TableColumn<>("Ostosten määrä");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<User, Integer>, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<User, Integer>, Integer> p) {
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
                long sekunnitNyt = Instant.now().getEpochSecond();
                long sekunnitEnnen = sekunnitNyt - time;
                int i = 0;
                Map<User, Integer> ostosData = logic.findTopUsers(sekunnitNyt, sekunnitEnnen);
                ObservableList<Map.Entry<User, Integer>> items = FXCollections.observableArrayList(ostosData.entrySet());
                final TableView<Map.Entry<User, Integer>> table = new TableView<>(items);
                table.getColumns().setAll(column1, column2);
                table.setMaxWidth(Region.USE_PREF_SIZE);
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                aikaNakyma.add(table, 0, 1);
                averageBalance.getData().add(new XYChart.Data("Average balance", logic.calculateAverageBalance()));
                overallBalance.getData().add(new XYChart.Data<>("Overall balance", logic.calculateOverallBalance()));
                averageLabel.setText("Average balance for a user: " + logic.calculateAverageBalance());
                overallLabel.setText("Overall balance for users: " + logic.calculateOverallBalance());
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
