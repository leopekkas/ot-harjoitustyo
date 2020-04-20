package aurinkokuntasimulaattori.ui;

import aurinkokuntasimulaattori.domain.SimulationPhysics;
import aurinkokuntasimulaattori.domain.Kappale;
import aurinkokuntasimulaattori.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.shape.Circle; 
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import javafx.geometry.Orientation;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import static javafx.application.Application.launch;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;

import javafx.scene.input.MouseButton;

import javafx.util.Duration;

public class UI extends Application {
    
    private AnimationTimer mainLoop;
    private Canvas simcanvas;
    private final SimulationPhysics simulaatio = new SimulationPhysics();
    Timeline simulationTimeline = new Timeline();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        
        Group root = new Group();
        Scene scene = new Scene(root);
        
        BorderPane mainBorderPane = new BorderPane();
        root.getChildren().add(mainBorderPane);
        
        simcanvas = createSimCanvas();
        mainBorderPane.setCenter(simcanvas);
        
        simulationRendering();
        
        Menu menu = new Menu("Info");
        Menu simMenu = new Menu("Simulation");
        Menu presets = new Menu("Presets");
        
        setUpInfoBar(menu, mainBorderPane);
        setUpSimButtons(simMenu, mainBorderPane);
        setUpPresets(presets, mainBorderPane);
        
        MenuBar mb = new MenuBar();
        mb.getMenus().addAll(menu, simMenu, presets);
        
        VBox vb = new VBox(mb);
        mainBorderPane.setTop(vb);
        
        stage.setScene(scene);
        stage.show();
        
        /*
        ArrayList<Kappale> lista = new ArrayList<>();
        
        Pane maincanvas = new Pane();
        Pane canvas = new Pane();
        
        Scene mainMenu = new Scene(maincanvas, 500, 500); 
        
        Bounds reunat = canvas.getBoundsInLocal();        
        
        Menu m = new Menu("Edit");
        
        setUpMenuBar(m, canvas);
        
        Button kaynnistysnappi = new Button();
        
        MenuBar mb = new MenuBar();
        
        mb.getMenus().add(m);
        
        VBox vb = new VBox(mb);
        
        kaynnistysnappi.setText("Käynnistä simulaatio!");
        
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
        maincanvas.getChildren().add(kaynnistysnappi);
        
        //Lisätään ikkunalle otsikko
        stage.setTitle("Ympyrän muodostus ja simppeliä animointia");
        
        //stage.setScene(mainMenu);
        
        //stage.show();
        
        long startTime = System.currentTimeMillis();
        Label timerLabel = new Label();
        
        mainLoop = new AnimationTimer() {
            
            @Override
            public void handle(long now) {
                Scene animaatioscene = stage.getScene();                
            }
            
        };
        mainLoop.start();
        
        */
       
    }
    
    public Group informationText() {
        Text otsikko = new Text();
        Text text = new Text();
        Text text2 = new Text();
        
        otsikko.setText("Informaatiota kontrolleista");
        otsikko.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        otsikko.setUnderline(true);
        
        text.setText("Infoteksti on vanhentunut, korjailen pian!");
        
        text2.setText("");
        
        otsikko.setX(50);
        otsikko.setY(50);
        
        text.setX(50);
        text.setY(80);
        
        text2.setX(50);
        text2.setY(120);
        
        Group root = new Group(otsikko, text, text2);
        
        return root;
    }
    
    public Group physicsText() {
        Text otsikko = new Text();
        Text text = new Text();
        Text text2 = new Text();
        
        otsikko.setText("Physics behind the simulation");
        otsikko.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        otsikko.setUnderline(true);
        
        text.setText("Newton kehitti gravitaatioteorian");
        
        text2.setText(", jota noudatetaan " +
                "tänäkin päivänä 1!!!");
        
        otsikko.setX(50);
        otsikko.setY(50);
        
        text.setX(50);
        text.setY(80);
        
        text2.setX(50);
        text2.setY(120);
        
        Group root = new Group(otsikko, text, text2);
        
        return root;
    }
    
    public void planeetanLisays(Pane canvas, ActionEvent event) {
        Random rnd = new Random();
                
        int koko = rnd.nextInt(50);
                
        int nopeusx = rnd.nextInt(3) + 1;
        int nopeusy = rnd.nextInt(3) + 1;
                
        Circle circle = new Circle(koko);
                
        circle.setCenterX(0);
        circle.setCenterY(0);
        canvas.getChildren().add(circle);
    }
    
    private Canvas createSimCanvas() {
        Canvas canvas = new Canvas(1500, 1000);
        return canvas;
    }
    
    private void simulationRendering() {
        drawSim();                
        
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulateStep();
                drawSim();
            }
        }));
    }
    
    private void drawSim() {
        GraphicsContext graphics = simcanvas.getGraphicsContext2D(); 
        
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
        
        for (Kappale planet : simulaatio.getPlanets()) {
            drawPlanet(graphics, planet, 0, 1);
        }
    }
    
    private void drawPlanet(GraphicsContext graphics, Kappale kappale, int tail, double tailfactor) {
        double radius = Math.max(kappale.getRadius(), 1);
        Vector2 pos = kappale.getPos();
        Color color = Color.WHITE;
        
        List<Vector2> oldPos = kappale.getOldPos();
        if (oldPos != null) {
            for (int i = 0; i < Math.min(tail, oldPos.size()); i++) {
                Vector2 tailPos = oldPos.get(i);
                graphics.setStroke(Color.WHITE);
                graphics.strokeLine(pos.x, pos.y, tailPos.x, tailPos.y);
                
                pos = tailPos;
            }
        }
        
        graphics.setFill(color);
        
        graphics.fillOval(pos.x, pos.y, radius, radius);
    }
    
    private void simulateStep() {
        simulaatio.step(0.4, 0);
        
    }
    
    public void setUpSimButtons(Menu menu, Pane canvas) {
        MenuItem play = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        MenuItem step = new MenuItem("Step");
        
        menu.getItems().addAll(play, stop, step);
        
        play.addEventHandler(ActionEvent.ACTION, event -> {
            simulationTimeline.play();
        });
        
        stop.addEventHandler(ActionEvent.ACTION, event -> {
            simulationTimeline.stop();
        });
        
        step.addEventHandler(ActionEvent.ACTION, event -> {
            simulateStep();
            drawSim();
        });
    }
    
    public void setUpInfoBar(Menu menu, Pane canvas) {
        MenuItem info = new MenuItem("Info");
        MenuItem physics = new MenuItem("Physics behind the simulation");
        menu.getItems().addAll(info, physics);
        
        // Infonapin toiminnallisuus
        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Group root = informationText();                
                Scene info = new Scene(root, 500, 200);                
                Stage infostage = new Stage();
                
                infostage.setTitle("Information about the simulation");
                
                infostage.setScene(info);
                
                infostage.show();
            }
        });
        
        physics.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Group root = physicsText();                
                Scene info = new Scene(root, 500, 200);                
                Stage info2stage = new Stage();
                
                info2stage.setTitle("The physics behind the simulation");
                
                info2stage.setScene(info);
                
                info2stage.show();
            }
        });
    }
    
    public void setUpPresets(Menu menu, Pane canvas) {
        MenuItem preset1 = new MenuItem("Inner Planets of the Solar System");
        menu.getItems().add(preset1);
        
        preset1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                Kappale toinen = new Kappale("Aurinko", new Vector2(750, 500), new Vector2(0, 0), 400000, 30);
        
                Kappale maa = new Kappale("Maa", new Vector2(760, 400), new Vector2(20, 0), 150, 15);
                Kappale kolmas = new Kappale("Mars", new Vector2(760, 900), new Vector2(-10, 0), 100, 10);
                Kappale neljas = new Kappale("Merkurius", new Vector2(800, 513), new Vector2(0, 26.3), 10, 5);

                simulaatio.add(maa);
                simulaatio.add(toinen);
                simulaatio.add(kolmas);
                simulaatio.add(neljas);
                drawSim();
            }
        });
    }
    
}
