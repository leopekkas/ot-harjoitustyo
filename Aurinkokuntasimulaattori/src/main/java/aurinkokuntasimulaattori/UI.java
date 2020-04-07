package aurinkokuntasimulaattori;

import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.shape.Circle; 
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.text.Text;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight; 

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.input.MouseButton;
import javafx.collections.ObservableList;

import javafx.util.Duration;

public class UI extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        Random rnd = new Random();
        
        Pane maincanvas = new Pane();
        Pane canvas = new Pane();
        
        Scene mainMenu = new Scene(maincanvas, 500, 500); 
        
        Bounds reunat = canvas.getBoundsInLocal();        
        
        Menu m = new Menu("Edit");
        
        MenuItem m1 = new MenuItem("Lisää planeetta");
        MenuItem m2 = new MenuItem("Info");
        
        m.getItems().add(m1);
        m.getItems().add(m2);
        
        MenuBar mb = new MenuBar();
        
        mb.getMenus().add(m);
        
        VBox vb = new VBox(mb);
        
        Button menubtn = new Button();
        
        menubtn.setText("Käynnistä simulaatio!");
        
        m1.setOnAction(new EventHandler<ActionEvent>() {
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
                        , new KeyValue(circle2.layoutXProperty(), 600 - circle2.getRadius())));
                timeline.setCycleCount(2);
                timeline.play();
        
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(nopeusy)
                        , new KeyValue(circle2.layoutYProperty(), 300 - circle2.getRadius())));
                timeline2.setCycleCount(2);
                timeline2.play();
            }
        });
        
        m2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Group root = informationText();
                
                Scene info = new Scene(root, 500, 200);
                
                Stage infostage = new Stage();
                
                infostage.setTitle("Infoa simulaatiosta");
                
                infostage.setScene(info);
                
                infostage.show();
            }
        });
        
        canvas.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double x = e.getSceneX();
                double y = e.getSceneY();
                Circle circle = new Circle(x, y, 10);
                canvas.getChildren().add(circle);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                Node klikattu = e.getPickResult().getIntersectedNode();
                if (klikattu instanceof Circle) {
                    canvas.getChildren().remove(klikattu);
                }         
            }   
        });        
        
        canvas.getChildren().add(vb);
        maincanvas.getChildren().add(menubtn);
        
        // Luodaan ikkunaan ympyrä
        Circle circle = new Circle(10);
        
        canvas.getChildren().add(circle);
        
        // Merkataan ympyrän ominaisuudet
        circle.setCenterX(0.0f);
        circle.setCenterY(0.0f);
        
        //Lisätään ikkunalle otsikko
        stage.setTitle("Ympyrän muodostus ja simppeliä animointia");
        
        stage.setScene(mainMenu);
        
        stage.show();
        
        menubtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Scene scene = new Scene(canvas, 600, 300);
                
                Stage newWindow = new Stage();
                newWindow.setScene(scene);
                
                newWindow.show();
                
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3)
                        , new KeyValue(circle.layoutXProperty(), 600 - circle.getRadius())));
                timeline.setCycleCount(3);
                timeline.play();

                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(2)
                        , new KeyValue(circle.layoutYProperty(), 300 - circle.getRadius())));
                timeline2.setCycleCount(5);
                timeline2.play();
                
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }

        });
       
    }
    
    public Group informationText() {
        Text otsikko = new Text();
        Text text = new Text();
        Text text2 = new Text();
        
        otsikko.setText("Informaatiota kontrolleista");
        otsikko.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        otsikko.setUnderline(true);
        
        text.setText("Planeettoja voit lisätä joko edit-menunapin alta löytyvästä \n"
                + "Lisää planeetta -napista tai hiiresi vasemmalla napilla.");
        
        text2.setText("Lisäämiäsi kappaleita voit poistaa hiiren oikealla \n"
                + "klikkauksella poistettavan kappaleen kohdalla.");
        
        otsikko.setX(50);
        otsikko.setY(50);
        
        text.setX(50);
        text.setY(80);
        
        text2.setX(50);
        text2.setY(120);
        
        Group root = new Group(otsikko, text, text2);
        
        return root;
    }   
    
}
