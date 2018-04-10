
import javafx.stage.Stage;

import javafx.application.Application;
public class Paaohjelma {
    public static void main(String[] args) throws ClassNotFoundException {
        Stage ikkuna = new Stage();
        
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        kayttoliittyma.start(ikkuna);
    }
}
