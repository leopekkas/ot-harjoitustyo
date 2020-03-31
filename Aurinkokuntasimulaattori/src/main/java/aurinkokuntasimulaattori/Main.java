package aurinkokuntasimulaattori;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.shape.Circle; 


import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.util.Duration;


public class Main extends Application {
    
    Kappale keskusta;
    
    // Testailen aluksi vaan javafx animation toimintoja ennen minkään
    // käytännöllisen toiminnallisuuden lisäilemistä
    
    @Override
    public void start(Stage stage) {
        Pane canvas = new Pane();
        
        Scene scene = new Scene(canvas, 500, 300);
        
        // Luodaan ikkunaan ympyrä
        Circle circle = new Circle();
        
        canvas.getChildren().add(circle);
        
        // Lisätään keskuskappale
        Kappale keskusta = new Kappale("Aurinko", 100000, 10);
        
        // Merkataan ympyrän ominaisuudet
        circle.setCenterX(100.0f);
        circle.setCenterY(35.0f);
        circle.setRadius(keskusta.getRadius());
        
        //Lisätään ikkunalle otsikko
        stage.setTitle("Ympyrän muodostus ja simppeliä animointia");
        
        stage.setScene(scene);
        
        stage.show();
        
        Bounds reunat = canvas.getBoundsInLocal();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3)
                , new KeyValue(circle.layoutXProperty(), reunat.getMaxX()-circle.getRadius())));
        timeline.setCycleCount(2);
        timeline.play();

    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
