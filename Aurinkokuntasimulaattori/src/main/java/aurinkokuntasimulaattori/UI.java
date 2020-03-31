package aurinkokuntasimulaattori;

import java.util.Random;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.shape.Circle; 
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.util.Duration;

public class UI extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        Pane canvas = new Pane();
        
        Scene scene = new Scene(canvas, 600, 300);
        
        Button btn = new Button();
        btn.setText("Lisää planeetta :-)");
        
        Bounds reunat = canvas.getBoundsInLocal();
        
        Random rnd = new Random();
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int koko = rnd.nextInt(50);
                
                int nopeusx = rnd.nextInt(3) + 1;
                int nopeusy = rnd.nextInt(3) + 1;
                
                Circle circle2 = new Circle(koko);
                
                circle2.setCenterX(0);
                circle2.setCenterY(0);
                canvas.getChildren().add(circle2);
                
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(nopeusx)
                        , new KeyValue(circle2.layoutXProperty(), reunat.getMaxX()-circle2.getRadius())));
                timeline.setCycleCount(2);
                timeline.play();
        
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(nopeusy)
                        , new KeyValue(circle2.layoutYProperty(), reunat.getMaxY()-circle2.getRadius())));
                timeline2.setCycleCount(2);
                timeline2.play();
            }
        });
        
        canvas.getChildren().add(btn);
        
        // Luodaan ikkunaan ympyrä
        Circle circle = new Circle(10);
        
        canvas.getChildren().add(circle);
        
        // Merkataan ympyrän ominaisuudet
        circle.setCenterX(0.0f);
        circle.setCenterY(0.0f);
        
        //Lisätään ikkunalle otsikko
        stage.setTitle("Ympyrän muodostus ja simppeliä animointia");
        
        stage.setScene(scene);
        
        stage.show();
       
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3)
                , new KeyValue(circle.layoutXProperty(), reunat.getMaxX()-circle.getRadius())));
        timeline.setCycleCount(3);
        timeline.play();
        
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(2)
                , new KeyValue(circle.layoutYProperty(), reunat.getMaxY()-circle.getRadius())));
        timeline2.setCycleCount(5);
        timeline2.play();
    }
    
}
