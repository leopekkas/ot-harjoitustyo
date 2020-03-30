package aurinkokuntasimulaattori;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.shape.Circle; 

public class Main extends Application {
    
    Kappale keskusta;
    
    @Override
    public void start(Stage stage) {
        // Luodaan ikkunaan ympyrä
        Circle circle = new Circle();
        
        // Lisätään keskuskappale
        Kappale keskusta = new Kappale("Aurinko", 100000, 100);
        
        // Merkataan ympyrän ominaisuudet
        circle.setCenterX(300.0f);
        circle.setCenterY(135.0f);
        circle.setRadius(keskusta.getRadius());
        
        Group root = new Group(circle);
        
        Scene scene = new Scene(root, 900, 700);
        
        //Lisätään ikkunalle otsikko
        stage.setTitle("Ympyrän muodostus");
        
        stage.setScene(scene);
        
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
