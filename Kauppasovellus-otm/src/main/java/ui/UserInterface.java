package ui;

import database.Database;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.SQLException;
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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class UserInterface extends Application {

    private UserDao users;
    private ProductDao products;
    private PurchaseDao purchases;
    private Long time;
    private Logic logic;

    /**
     * Initializes the program and enables configuration by sing config.properties file.
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    @Override
    public void init() throws ClassNotFoundException, FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

        String databaseFile = properties.getProperty("database");
        this.time = Long.parseLong(properties.getProperty("time"));

        Database database = new Database(databaseFile);
        this.users = new UserDao(database);
        this.products = new ProductDao(database);
        this.purchases = new PurchaseDao(database);
        this.logic = new Logic(users, products, purchases);

    }
    
    /**
     * Changes a scene to another.
     * @param button button where the change is called.
     * @param stage stage that the programm uses. 
     * @param scene scene that the user wants to be shown.
     */
    public void changeScene(Button button, Stage stage, Scene scene) {
        button.setOnAction((event) -> {
            stage.setScene(scene);
            stage.show();
        });
    }

    public static void start(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage window) throws ClassNotFoundException, SQLException {

        //Creates scenes and other graphic objects.
        
        window.setTitle("Kauppasovellus");
        window.setHeight(600);
        window.setWidth(800);

        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);

        Text welcomeText = new Text();
        welcomeText.setEffect(is);
        welcomeText.setX(20);
        welcomeText.setY(100);
        welcomeText.setText("Welcome to Kauppasovellus!");
        welcomeText.setFill(javafx.scene.paint.Color.PINK);
        welcomeText.setFont(Font.font(null, FontWeight.BOLD, 20));
        welcomeText.setTranslateX(30);
        welcomeText.setTranslateY(30);

        BorderPane startView = new BorderPane();
        GridPane startGrid = new GridPane();
        Scene start = new Scene(startView);
        GridPane addUsersGrid = new GridPane();
        Scene addUser = new Scene(addUsersGrid);
        GridPane addProductGrid = new GridPane();
        Scene addProduct = new Scene(addProductGrid);
        GridPane purchaseGrid = new GridPane();
        Scene addPurchase = new Scene(purchaseGrid);
        BorderPane topUsersInformationView = new BorderPane();
        GridPane topUserInformationGrid = new GridPane();
        Scene topUserInformation = new Scene(topUsersInformationView);
        BorderPane userInformationView = new BorderPane();
        GridPane userInformationGrid = new GridPane();
        Scene userInformation = new Scene(userInformationView);
        GridPane statsGrid = new GridPane();
        Scene stats = new Scene(statsGrid);

        //Start scene
        
        Label pickScene = new Label("Pick a functionality!");
        Button addUserButton = new Button("Add users!");
        this.changeScene(addUserButton, window, addUser);

        Button addProductButton = new Button("Add products!");
        this.changeScene(addProductButton, window, addProduct);

        Button addPurchaseButton = new Button("Make a purchase!");
        this.changeScene(addPurchaseButton, window, addPurchase);

        Button topUsersButton = new Button("View top users information!");
        this.changeScene(topUsersButton, window, topUserInformation);

        Button statsButton = new Button("View statistics!");
        this.changeScene(statsButton, window, stats);

        startView.setTop(welcomeText);
        startView.setAlignment(welcomeText, Pos.CENTER);
        startGrid.add(pickScene, 0, 0);
        startGrid.add(addUserButton, 0, 1);
        startGrid.add(addProductButton, 0, 2);
        startGrid.add(addPurchaseButton, 0, 3);
        startGrid.add(topUsersButton, 0, 4);
        startGrid.add(statsButton, 0, 5);
        startGrid.setHgap(20);
        startGrid.setVgap(20);
        startGrid.setPadding(new Insets(5, 5, 5, 5));
        startView.setCenter(startGrid);
        startView.setAlignment(startGrid, Pos.BOTTOM_RIGHT);

        //Stats scene
        
        Label statsLabel = new Label("Statistics:");
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

        Button returnToStartFromStats = new Button("Return to start!");
        this.changeScene(returnToStartFromStats, window, start);

        statsGrid.add(statsBar, 1, 1);
        statsGrid.add(statsLabel, 0, 1);
        statsGrid.add(averageLabel, 1, 2);
        statsGrid.add(overallLabel, 1, 3);
        statsGrid.add(returnToStartFromStats, 1, 4);
        statsGrid.setHgap(20);
        statsGrid.setVgap(20);
        statsGrid.setPadding(new Insets(20, 20, 20, 20));

        //User information scene
        
        Label userPurchaseHistory = new Label("User's purchase history:");
        Label userInformationLabel = new Label("User information:");
        TextField kayttajanNimiMuokkaa = new TextField();
        TextField kayttajanSaldoMuokkaa = new TextField();
        TextField kayttajanIdMuokkaa = new TextField();
        Label userName = new Label("Name: ");
        Label userBalance = new Label("Balance: ");
        Label userId = new Label("Id :");
        Label succesfullUpdate = new Label();

        Label showUsers = new Label("Show users");
        ComboBox<Object> listOfUsers = new ComboBox<>();
        listOfUsers.getItems().addAll(logic.findAllUsers()
        );
        ComboBox<User> pickUser = new ComboBox<>();
        pickUser.getItems().addAll(logic.findAllUsers()
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

        Button showUserInformation = new Button("Show user information!");
        showUserInformation.setOnAction((event) -> {
            if (listOfUsers.getValue() != null) {
                User tarkasteltava = (User) listOfUsers.getValue();
                kayttajanNimiMuokkaa.setText(tarkasteltava.getName());
                kayttajanSaldoMuokkaa.setText(Double.toString(tarkasteltava.getBalance()));
                kayttajanIdMuokkaa.setText(Integer.toString(tarkasteltava.getId()));
                Map<java.time.LocalDateTime, String> tuotehistoria = logic.findUsersPurchaseHistory(tarkasteltava.getId());
                ObservableList<Map.Entry<java.time.LocalDateTime, String>> items = FXCollections.observableArrayList(tuotehistoria.entrySet());
                final TableView<Map.Entry<java.time.LocalDateTime, String>> table = new TableView<>(items);
                table.getColumns().setAll(column4, column3);
                userInformationView.setCenter(table);
                window.setScene(userInformation);
                window.show();
            }
        });

        Button updateUserButton = new Button("Update!");
        updateUserButton.setOnAction((event) -> {
            logic.saveUser(kayttajanIdMuokkaa.getText(), kayttajanNimiMuokkaa.getText(), kayttajanSaldoMuokkaa.getText());
            if (logic.saveUser(kayttajanIdMuokkaa.getText(), kayttajanNimiMuokkaa.getText(), kayttajanSaldoMuokkaa.getText()) == true) {
                succesfullUpdate.setText("Update succesfull!");
            } else {
                succesfullUpdate.setText("Update not succesfull!");
            }
            listOfUsers.getItems().clear();
            listOfUsers.getItems().addAll(logic.findAllUsers());
            pickUser.getItems().clear();
            pickUser.getItems().addAll(logic.findAllUsers());
        });

        Button returnToStartFromUserInformation = new Button("Return to start!");
        this.changeScene(returnToStartFromUserInformation, window, start);

        Button returnToAddUsersFromUserInformation = new Button("Return to add users!");
        this.changeScene(returnToAddUsersFromUserInformation, window, addUser);
        
        userInformationView.setBottom(userInformationGrid);
        userInformationView.setTop(userPurchaseHistory);
        userInformationGrid.add(userInformationLabel, 0, 0);
        userInformationGrid.add(userName, 0, 1);
        userInformationGrid.add(userBalance, 0, 2);
        userInformationGrid.add(userId, 0, 3);
        userInformationGrid.add(kayttajanNimiMuokkaa, 1, 1);
        userInformationGrid.add(kayttajanSaldoMuokkaa, 1, 2);
        userInformationGrid.add(kayttajanIdMuokkaa, 1, 3);
        userInformationGrid.add(updateUserButton, 1, 4);
        userInformationGrid.add(returnToStartFromUserInformation, 0, 4);
        userInformationGrid.add(returnToAddUsersFromUserInformation, 0, 5);
        userInformationGrid.add(succesfullUpdate, 0, 6);
        userInformationGrid.setHgap(10);
        userInformationGrid.setVgap(20);
        userInformationGrid.setPadding(new Insets(20, 20, 20, 20));

        //Add users scene
        
        Button returnToStartFromAddUsers = new Button("Return to start!");
        this.changeScene(returnToStartFromAddUsers, window, start);

        Label nameLabel = new Label("Name: ");
        TextField nameField = new TextField();
        Label balanceLabel = new Label("Balance: ");
        Label showUsersLabel = new Label("Show users:");
        TextField balanceField = new TextField();
        Label saveOrUpdateUserWasSuccesful = new Label();

        Button addNewUserButton = new Button("Add new user!");
        addNewUserButton.setOnAction((event) -> {
            logic.saveUser("-1", nameField.getText(), balanceField.getText());
            if (logic.saveUser("-1", nameField.getText(), balanceField.getText()) == true) {
                saveOrUpdateUserWasSuccesful.setText("User was succesfully created or updated!");
            } else {
                saveOrUpdateUserWasSuccesful.setText("User was not succesfully created or updated!");
            }
            listOfUsers.getItems().clear();
            listOfUsers.getItems().addAll(logic.findAllUsers());
            pickUser.getItems().clear();
            pickUser.getItems().addAll(logic.findAllUsers());
        });

        addUsersGrid.add(nameLabel, 0, 0);
        addUsersGrid.add(nameField, 0, 1);
        addUsersGrid.add(balanceLabel, 1, 0);
        addUsersGrid.add(balanceField, 1, 1);
        addUsersGrid.add(addNewUserButton, 2, 1);
        addUsersGrid.add(showUsersLabel, 3, 0);
        addUsersGrid.add(listOfUsers, 3, 1);
        addUsersGrid.add(returnToStartFromAddUsers, 0, 3);
        addUsersGrid.add(showUserInformation, 3, 2);
        addUsersGrid.add(saveOrUpdateUserWasSuccesful, 0, 4);

        addUsersGrid.setHgap(20);
        addUsersGrid.setVgap(20);
        addUsersGrid.setPadding(new Insets(20, 20, 20, 20));

        //Add products scene
        
        Label showProductsLabel = new Label("Show products:");
        Button returnToStartFromAddProducts = new Button("Return to start!");
        this.changeScene(returnToStartFromAddProducts, window, start);
        Label saveOrUpdateProductWasSuccesful = new Label();

        Label listOfProductsLabel = new Label("Show products:");

        ComboBox<Object> listOfProducts = new ComboBox<>();
        listOfProducts.getItems().addAll(logic.findAllProducts()
        );

        ComboBox<Product> pickProduct = new ComboBox<>();
        pickProduct.getItems().addAll(logic.findAllProducts()
        );

        Label productNameLabel = new Label("Name: ");
        TextField productNameField = new TextField();
        Label productPriceLabel = new Label("Price: ");
        TextField productPriceField = new TextField();
        Label productAmountLabel = new Label("Amount: ");
        TextField productAmountField = new TextField();
        Label productInfoLabel = new Label("Info: ");
        TextField productInfoField = new TextField();

        Button addOrUpdateProductButton = new Button("Add a product!");
        addOrUpdateProductButton.setOnAction((event) -> {
            logic.saveProduct("-1", productNameField.getText(), productPriceField.getText(), productAmountField.getText(), productInfoField.getText());
            if (logic.saveProduct("-1", productNameField.getText(), productPriceField.getText(), productAmountField.getText(), productInfoField.getText()) == true) {
                saveOrUpdateProductWasSuccesful.setText("Product was succesfully created or updated");
            } else {
                saveOrUpdateProductWasSuccesful.setText("Product was not succesfully created or updated");
            }
            listOfProducts.getItems().clear();
            listOfProducts.getItems().addAll(logic.findAllProducts());
            pickProduct.getItems().clear();
            pickProduct.getItems().addAll(logic.findAllProducts());
        });

        addProductGrid.add(productNameLabel, 0, 0);
        addProductGrid.add(productNameField, 0, 1);
        addProductGrid.add(productPriceLabel, 1, 0);
        addProductGrid.add(productPriceField, 1, 1);
        addProductGrid.add(productAmountLabel, 2, 0);
        addProductGrid.add(productAmountField, 2, 1);
        addProductGrid.add(productInfoLabel, 3, 0);
        addProductGrid.add(productInfoField, 3, 1);
        addProductGrid.add(addOrUpdateProductButton, 4, 1);
        addProductGrid.add(listOfProducts, 0, 3);
        addProductGrid.add(showProductsLabel, 0, 2);
        addProductGrid.add(returnToStartFromAddProducts, 0, 5);
        addProductGrid.add(saveOrUpdateProductWasSuccesful, 0, 6);
        addProductGrid.setHgap(20);
        addProductGrid.setVgap(20);
        addProductGrid.setPadding(new Insets(10, 10, 10, 10));

        //Make a purchase scene
        
        Button retunToStartFromMakePurchase = new Button("Return to start!");
        this.changeScene(retunToStartFromMakePurchase, window, start);

        Label pickUserForPurcahse = new Label("Pick a user:");
        Label pickProductForPurchase = new Label("Pick a product:");
        Button purchaseButton = new Button("Purchase!");

        purchaseButton.setOnAction(e -> {
            logic.saveOrUpdatePurchase(new Purchase(pickUser.getValue(), pickProduct.getValue(), Instant.now().getEpochSecond()));
            listOfUsers.getItems().clear();
            listOfUsers.getItems().addAll(logic.findAllUsers());
            listOfProducts.getItems().clear();
            listOfProducts.getItems().addAll(logic.findAllProducts());
            pickUser.getItems().clear();
            pickUser.getItems().addAll(logic.findAllUsers());
            pickProduct.getItems().clear();
            pickProduct.getItems().addAll(logic.findAllProducts());
        });

        purchaseGrid.add(pickUserForPurcahse, 0, 0);
        purchaseGrid.add(pickProductForPurchase, 0, 1);
        purchaseGrid.add(pickUser, 1, 0);
        purchaseGrid.add(pickProduct, 1, 1);
        purchaseGrid.add(purchaseButton, 1, 2);
        purchaseGrid.add(retunToStartFromMakePurchase, 2, 1);

        purchaseGrid.setHgap(20);
        purchaseGrid.setVgap(20);
        purchaseGrid.setPadding(new Insets(10, 10, 10, 10));

        //Top userInformaiton scene
        
        Label topUsersLabel = new Label("Users with recently most purchases");
        Button returnToStartFromTopUsers = new Button("Return to start");
        this.changeScene(returnToStartFromTopUsers, window, start);

        TableColumn<Map.Entry<User, Integer>, User> column1 = new TableColumn<>("User");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<User, Integer>, User>, ObservableValue<User>>() {

            @Override
            public ObservableValue<User> call(TableColumn.CellDataFeatures<Map.Entry<User, Integer>, User> p) {
                return new SimpleObjectProperty<>(p.getValue().getKey());
            }

        });

        TableColumn<Map.Entry<User, Integer>, Integer> column2 = new TableColumn<>("Amount of purchases");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<User, Integer>, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Map.Entry<User, Integer>, Integer> p) {
                return new SimpleObjectProperty<>(p.getValue().getValue());
            }

        });

        new AnimationTimer() {
            private long timeBefore;

            @Override
            public void handle(long timeNow) {
                if (timeNow - timeBefore < 200_000_000_0L) {
                    return;
                }
                timeBefore = timeNow;
                long secondsNow = Instant.now().getEpochSecond();
                long secondsBefore = secondsNow - time;
                int i = 0;
                Map<User, Integer> ostosData = logic.findTopUsers(secondsNow, secondsBefore);
                ObservableList<Map.Entry<User, Integer>> items = FXCollections.observableArrayList(ostosData.entrySet());
                final TableView<Map.Entry<User, Integer>> table = new TableView<>(items);
                table.getColumns().setAll(column1, column2);
                table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                topUsersInformationView.setCenter(table);
                averageBalance.getData().add(new XYChart.Data("Average balance", logic.calculateAverageBalance()));
                overallBalance.getData().add(new XYChart.Data<>("Overall balance", logic.calculateOverallBalance()));
                averageLabel.setText("Average balance for a user: " + logic.calculateAverageBalance());
                overallLabel.setText("Overall balance for users: " + logic.calculateOverallBalance());
            }
        }.start();

        topUsersInformationView.setTop(topUsersLabel);
        topUsersInformationView.setBottom(topUserInformationGrid);
        topUserInformationGrid.setHgap(20);
        topUserInformationGrid.setVgap(20);
        topUserInformationGrid.add(returnToStartFromTopUsers, 0, 2);
        topUserInformationGrid.setPadding(new Insets(50, 50, 50, 50));

        //Set start scene 
        
        window.setScene(start);
        window.show();

    }

}
