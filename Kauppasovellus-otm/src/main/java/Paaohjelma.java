
import javafx.stage.Stage;


public class Paaohjelma {
    public static void main(String[] args) {
        Stage ikkuna = new Stage();
        
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        kayttoliittyma.start(ikkuna);
    }
}
