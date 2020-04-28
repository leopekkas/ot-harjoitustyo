package aurinkokuntasimulaattori.ui;

import aurinkokuntasimulaattori.domain.Saver;
import aurinkokuntasimulaattori.domain.Loader;
import aurinkokuntasimulaattori.domain.SimulationPhysics;
import aurinkokuntasimulaattori.domain.Planet;
import aurinkokuntasimulaattori.math.Vector2;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.IOException;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Popup;

import javafx.stage.FileChooser;

import javafx.scene.paint.Color;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import static javafx.application.Application.launch;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.util.Duration;

public class UI extends Application {
    
    private double timestep;
    private AnimationTimer mainLoop;
    private Canvas simcanvas;
    
    private final SimulationPhysics simulaatio = new SimulationPhysics();
    Timeline simulationTimeline = new Timeline();
    Loader loader = new Loader();
    Saver saver = new Saver();
    FileChooser filechooser = new FileChooser();
    
    // Tällä lasken käytyjen steppien lukumäärää
    private long stepcounter;
    private double timecounter;
    private Label stepTimeLabel;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stepcounter = 0;
        timecounter = 0;
        filechooser.setTitle("Choose the file to use");
        
        Group root = new Group();
        Scene scene = new Scene(root);
        
        BorderPane mainBorderPane = new BorderPane();
        root.getChildren().add(mainBorderPane);
        
        simcanvas = createSimCanvas();
        mainBorderPane.setCenter(simcanvas);
        
        timestep = 0.4;
        simulationRendering();
        
        VBox vb = setupMenuBar(mainBorderPane, stage);
        mainBorderPane.setTop(vb);
        
        stepTimeLabel = new Label("");
        mainBorderPane.setBottom(stepTimeLabel);
        
        stage.setScene(scene);
        stage.show();
       
    }
    
    public Group informationText() {
        Text otsikko = new Text();
        Text text = new Text();
        Text text2 = new Text();
        
        otsikko.setText("Informaatiota kontrolleista");
        otsikko.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        otsikko.setUnderline(true);
        
        text.setText("Valmiita simulaatiomalleja voit tarkastella napin 'Presets' alta.");
        
        text2.setText("Tarkemmat ohjeet simulaation käyttöön löydät " +
                "GitHub -repositoriosta,\n tähän ei saakkaan helposti " +
                "paljoa tekstiä");
        
        otsikko.setX(30);
        otsikko.setY(50);
        
        text.setX(30);
        text.setY(80);
        
        text2.setX(30);
        text2.setY(110);
        
        Group root = new Group(otsikko, text, text2);
        
        return root;
    }
    
    public VBox setupMenuBar(Pane mainBorderPane, Stage stage) {
        Menu fileMenu = new Menu("File");
        Menu menu = new Menu("Info");
        Menu simMenu = new Menu("Simulation");
        Menu presets = new Menu("Presets");
        Menu custom = new Menu("Custom");
        Menu timestepmenu = new Menu("Timestep length");
        
        setUpFileMenu(fileMenu, stage);
        setUpInfoBar(menu);
        setUpSimButtons(simMenu, stage);
        setUpPresets(presets);
        setUpStepSlider(timestepmenu); 
        setUpCustomize(custom);
        
        MenuBar mb = new MenuBar();
        mb.getMenus().addAll(fileMenu, menu, simMenu, presets, custom, timestepmenu);
        
        VBox vb = new VBox(mb);
        
        return vb;
    }
    
    public Group physicsText() {
        Text otsikko = new Text();
        Text text = new Text();
        Text text2 = new Text();
        Text text3 = new Text();
        
        otsikko.setText("Physics behind the simulation");
        otsikko.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        otsikko.setUnderline(true);
        
        text.setText("Newton kehitti gravitaatioteorian, " +
                "jota noudatetaan tänäkin päivänä!!");
        
        text2.setText("Tarkemmat kaavat ja matemaattiset perusteet \n " +
                "tulen sijoittamaan GitHub kansioon I think");
        
        text3.setText("Graphic design is my passion");
        
        otsikko.setX(30);
        otsikko.setY(50);
        
        text.setX(30);
        text.setY(80);
        
        text2.setX(30);
        text2.setY(110);
        
        text3.setX(30);
        text3.setY(150);
        
        Group root = new Group(otsikko, text, text2, text3);
        
        return root;
    }
    
    private Canvas createSimCanvas() {
        Canvas canvas = new Canvas(1500, 950);
        return canvas;
    }
    
    private void simulationRendering() {
        drawSim();                
        
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulateStep(timestep);
                drawSim();
            }
        }));
    }
    
    private Slider timestepSlider() {
        Slider step = new Slider(0.0, 1.0, 1);
        step.setShowTickLabels(true);
        step.setShowTickMarks(true);
        step.setMajorTickUnit(0.1);
        
        step.valueProperty().addListener(event -> {
            timestep = step.getValue();
        });
        
        return step;
    }
    
    private void drawSim() {
        GraphicsContext graphics = simcanvas.getGraphicsContext2D(); 
        
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
        
        for (Planet planet : simulaatio.getPlanets()) {
            drawPlanet(graphics, planet, 0, 1);
        }
    }
    
    private void drawPlanet(GraphicsContext graphics, Planet kappale, int tail, double tailfactor) {
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
    
    private void simulateStep(double step) {
        stepcounter++;
        timecounter += step;
        updateStepCounter();
        simulaatio.step(step, 0);        
    }
    
    public void setUpSimButtons(Menu menu, Stage stage) {
        Popup popup = new Popup();
        Label popupLabel = new Label("Please select a preset or add planets " +
                "before starting up the simulation");
        popupLabel.setTextFill(Color.WHITE); 
        MenuItem play = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        MenuItem step = new MenuItem("Step");
        
        menu.getItems().addAll(play, stop, step);
        popup.getContent().add(popupLabel);
        
        play.addEventHandler(ActionEvent.ACTION, event -> {
            if (simulaatio.getPlanets().size() > 0) {
                popup.hide();
                simulationTimeline.play();
            } else {
                if (!popup.isShowing()) {
                    popup.show(stage);
                }
            }
        });
        
        stop.addEventHandler(ActionEvent.ACTION, event -> {
            simulationTimeline.stop();
        });
        
        step.addEventHandler(ActionEvent.ACTION, event -> {
            if (popup.isShowing()) {
                popup.hide();
            }
            simulateStep(timestep);
            drawSim();
        });
    }
    
    public void setUpInfoBar(Menu menu) {
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
    
    private void setUpFileMenu(Menu menu, Stage stage) {
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        
        menu.getItems().addAll(save, load);
        
        save.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            File savefile = chooser.showSaveDialog(null);
            saver.saveSimulationData(savefile, simulaatio.getPlanets());
        });
        
        load.setOnAction(event -> {
            try {
                File toLoad = chooseLoadFile(stage);
                simulaatio.clear();
                ArrayList<Planet> filelist = loader.readPlanetsFromFile(toLoad);
                for (int a = 0; a < filelist.size(); a++) {
                    simulaatio.add(filelist.get(a));
                }
                stepcounter = 0;
                timecounter = 0;
                drawSim();
            } catch (IOException e) {
                System.out.println("Error");
            }
            
        });
    }
    
    public void setUpPresets(Menu menu) {
        MenuItem preset1 = new MenuItem("Empty space (clean canvas)");
        MenuItem preset2 = new MenuItem("Inner Planets of the Solar System");
        MenuItem preset3 = new MenuItem("Binary star system with a distant third star orbiting");
        MenuItem preset4 = new MenuItem("Gas giant with a few moons");
        MenuItem random10 = new MenuItem("10 random objects");
        MenuItem random100 = new MenuItem("100 random objects");
        MenuItem random200 = new MenuItem("200 random objects :-)");
        menu.getItems().addAll(preset1, preset2, preset3, random10, random100, random200);
        // Lisää preset4 kun saat valmiiksi
        preset1Content(preset1);
        preset2Content(preset2);
        preset3Content(preset3);
        preset4Content(preset4);
        presetRandomContent(random10, 10);
        presetRandomContent(random100, 100);
        presetRandomContent(random200, 200);
    }
    
    public void setUpStepSlider(Menu menu) {
        Slider stepper = timestepSlider();
        menu.getItems().add(new SeparatorMenuItem());
        
        CustomMenuItem stepperItem = new CustomMenuItem(stepper);
        stepperItem.setHideOnClick(false);
        menu.getItems().add(stepperItem);
    }
    
    public void preset1Content(MenuItem preset1) {
        preset1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                stepcounter = 0;
                timecounter = 0;
                drawSim();
            }
        });
    }
    
    public void preset2Content(MenuItem preset) {
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                Planet toinen = new Planet("Aurinko", new Vector2(750, 500), new Vector2(0, 0), 400000, 30);
        
                Planet maa = new Planet("Maa", new Vector2(760, 400), new Vector2(20, 0), 150, 15);
                Planet kolmas = new Planet("Mars", new Vector2(760, 900), new Vector2(-10, 0), 100, 10);
                Planet neljas = new Planet("Merkurius", new Vector2(800, 513), new Vector2(0, 26.3), 10, 5);

                simulaatio.add(maa);
                simulaatio.add(toinen);
                simulaatio.add(kolmas);
                simulaatio.add(neljas);
                stepcounter = 0;
                timecounter = 0;
                drawSim();
            }
        });
    }
    
    public void preset3Content(MenuItem preset) {
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                Planet toinen = new Planet("Star1", new Vector2(750, 450), new Vector2(20.5, 0), 400000, 30);
                Planet kolmas = new Planet("Star2", new Vector2(750, 500), new Vector2(-20.5, 0), 400000, 30);
                Planet distant = new Planet("Star3", new Vector2(750, 100), new Vector2(15, 0), 10000, 15);

                simulaatio.add(toinen);
                simulaatio.add(kolmas);
                simulaatio.add(distant);
                stepcounter = 0;
                timecounter = 0;
                drawSim();
            }
        });
    }
    
    public void preset4Content(MenuItem preset) {
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                Planet toinen = new Planet("Star", new Vector2(750, 450), new Vector2(0, 0), 4000000, 50);
                Planet kolmas = new Planet("Gas giant", new Vector2(200, 450), new Vector2(0, 25), 80000, 20);
                Planet distant = new Planet("Moon", new Vector2(210, 440), new Vector2(-10.5, 17), 10, 5);

                simulaatio.add(toinen);
                simulaatio.add(kolmas);
                simulaatio.add(distant);
                stepcounter = 0;
                timecounter = 0;
                drawSim();
            }
        });
    }
    
    public void presetRandomContent(MenuItem preset, int n) {
        
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Random rndm = new Random();
                simulaatio.clear();
                for (int i = 0; i < n; i++) {
                    Vector2 pos = new Vector2(100 + rndm.nextInt(1300), 100 + rndm.nextInt(800));
                    
                    // Randomisoidaan suunta (- vai +)
                    double vx = rndm.nextDouble() * 5 * (rndm.nextBoolean() ? -1 : 1);
                    double vy = rndm.nextDouble() * 5 * (rndm.nextBoolean() ? -1 : 1);
                    Vector2 vel = new Vector2(vx, vy);
                    Planet lisattava = new Planet(pos, vel, 100 + rndm.nextInt(400000), 2 + rndm.nextInt(30));
                    simulaatio.add(lisattava);
                }
                stepcounter = 0;
                timecounter = 0;
                drawSim();
            }
        });
    }
    
    public void setUpCustomize(Menu menu) {
        MenuItem add = new MenuItem("Add an object");
        MenuItem delete = new MenuItem("Clear a single object");
        menu.getItems().addAll(add, delete);
        userDeletePlanet(delete);
        userAddPlanet(add);
    }
    
    public void userDeletePlanet(MenuItem delete) {
        delete.setOnAction(event -> {
            Group root = new Group();
            Scene deleteScene = new Scene(root, 500, 200);
            Stage newStage = new Stage();
            GridPane gridi = new GridPane();
            
            newStage.setTitle("Clear a planet");
            
            int rowIndex = 0;
            int columnIndex = 0;
        });
    }
    
    public void userAddPlanet(MenuItem add) {
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Group root = new Group();                
                Scene newPlanet = new Scene(root, 500, 200);                
                Stage newStage = new Stage();
                GridPane gridi = new GridPane();
                Planet uusi = new Planet(new Vector2(0, 0), new Vector2(0, 0), 10, 10);
                
                newStage.setTitle("Add a new object");
                root.getChildren().add(gridi);
                
                int rowIndex = 0;
                int columnIndex = 0;
                TextField nimi = new TextField("Name the object (Press enter to confirm)");
                EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
                    @Override
                    public void handle(ActionEvent e) { 
                        uusi.setName(nimi.getText());               
                    } 
                }; 
                nimi.setOnAction(event2);
                gridi.add(nimi, rowIndex, columnIndex);
                rowIndex++;
                
                Button confirm = new Button("Add the planet");
                
                confirm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        simulaatio.add(uusi);
                        drawSim();
                    }
                });
                
                Text teksti = new Text("Toiminnallisuus on vielä työn alla,\n"
                        + "pahoittelut tästä!");
                
                gridi.add(confirm, columnIndex, rowIndex);
                rowIndex++;
                gridi.add(teksti, columnIndex, rowIndex + 2);
                
                newStage.setScene(newPlanet);
                
                newStage.show();
            }
        });
    }
    
    private void updateStepCounter() {
        String s = String.valueOf(stepcounter);
        String d = String.valueOf((int)timecounter);
        stepTimeLabel.setText("Elapsed timesteps: " + s + "        Elapsed time: " + d);
    }
    
    private File chooseLoadFile(Stage stage) {
        File toLoad = filechooser.showOpenDialog(stage);
        return toLoad;
    }
    
}
