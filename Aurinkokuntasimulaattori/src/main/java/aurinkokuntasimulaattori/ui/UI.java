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
import javafx.application.Platform; 
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
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import javafx.stage.FileChooser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

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
    private double timeUnit;
    private AnimationTimer mainLoop;
    private Canvas simcanvas;
    
    private final SimulationPhysics simulaatio = new SimulationPhysics();
    Timeline simulationTimeline;
    Loader loader = new Loader();
    Saver saver = new Saver();
    FileChooser filechooser = new FileChooser();
    
    // Tällä lasken käytyjen steppien (ja ajan) kokonaisuuksia
    private long stepcounter;
    private double timecounter;
    private double yearcounter;
    private Label stepTimeLabel;
    
    // Kertoo näytetäänkö simulaatioikkunassa kappaleiden nimet
    private boolean nameDisplay = false;
    
    // Onko simulaatio käynnissä
    private boolean stopped = true;
    
    // Zoomtason suuruus
    private double scale;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        simulationTimeline = new Timeline();
        stepcounter = 0;
        timecounter = 0;
        timeUnit = 86400;
        scale = 1;
        filechooser.setTitle("Choose the file to use");
        
        Group root = new Group();
        Scene scene = new Scene(root);
        
        BorderPane mainBorderPane = new BorderPane();
        root.getChildren().add(mainBorderPane);
        
        simcanvas = createSimCanvas();
        mainBorderPane.setCenter(simcanvas);
        
        timestep = timeUnit;
        simulationRendering();
        
        VBox vb = setupMenuBar(mainBorderPane, stage);
        mainBorderPane.setTop(vb);
        
        stepTimeLabel = new Label("");
        mainBorderPane.setBottom(stepTimeLabel);
        
        stage.setScene(scene);
        stage.show();
        
    }
    
    public VBox setupMenuBar(Pane mainBorderPane, Stage stage) {
        Menu fileMenu = new Menu("File");
        Menu simMenu = new Menu("Simulation");
        Menu presets = new Menu("Presets");
        Menu custom = new Menu("Custom");
        Menu timestepmenu = new Menu("Timestep length");
        Menu timestepScale = new Menu("Timestep unit");
        Menu scalingMenu = new Menu("Scaling tool");
        //Menu overRide = new Menu("Override methods");
        
        setUpFileMenu(fileMenu, stage);
        setUpSimButtons(simMenu, stage);
        setUpPresets(presets);
        setUpStepSlider(timestepmenu); 
        setUpTimeUnit(timestepScale);
        setUpScaleSlider(scalingMenu);
        //setUpOverRide(overRide);
        setUpCustomize(custom);
        
        MenuBar mb = new MenuBar();
        mb.getMenus().addAll(fileMenu, simMenu, presets, custom, timestepmenu,
                timestepScale, scalingMenu);
        
        VBox vb = new VBox(mb);
        
        return vb;
    }
    
    private Canvas createSimCanvas() {
        Canvas canvas = new Canvas(1500, 900);
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
        Slider step = new Slider(0.0, 10.0, 1);
        step.setShowTickLabels(true);
        step.setShowTickMarks(true);
        step.setMajorTickUnit(5);
        step.valueProperty().addListener(event -> {
            timestep = step.getValue() * timeUnit;
        });
        
        return step;
    }
    
    private Slider scaleSlider() {
        Slider scaler = new Slider(0.1, 10.0, 2);
        scaler.setMinWidth(500);
        scaler.setShowTickLabels(true);
        scaler.setShowTickMarks(true);
        scaler.setMajorTickUnit(2);
        
        scaler.valueProperty().addListener(event -> {
            scale = scaler.getValue();
            drawSim();
        });
        
        return scaler;
    }
    
    private void drawSim() {
        GraphicsContext graphics = simcanvas.getGraphicsContext2D(); 
        
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
        
        for (Planet planet : simulaatio.getPlanets()) {
            if (!planet.isDeleted()) {
                drawPlanet(graphics, planet, 0, 1);
            }
        }
    }
    
    private void drawPlanet(GraphicsContext graphics, Planet kappale, int tail, double tailfactor) {
        double radius = Math.max(kappale.getRadius(), 1);
        Vector2 pos = kappale.getPos();
        // Muutetaan oikea etäisyys pikseleille vastaavaksi
        double uiXpos = pos.x/1.496E11 * 100/scale + 750;
        double uiYpos = pos.y/1.496E11 * 100/scale + 450;
        Color color = Color.WHITE;
        
        List<Vector2> oldPos = kappale.getOldPos();
        if (oldPos != null) {
            for (int i = 0; i < Math.min(tail, oldPos.size()); i++) {
                Vector2 tailPos = oldPos.get(i);
                double uiTailXpos = tailPos.x/1.496E11 * 80 + 750;
                double uiTailYpos = tailPos.y/1.496E11 * 80 + 450;
                graphics.setStroke(Color.WHITE);
                graphics.strokeLine(uiXpos, uiYpos, uiTailXpos, uiTailYpos);
                
                pos = tailPos;
            }
        }
        
        graphics.setFill(color);
        
        double pituus = 0;
        
        if (kappale.getName() != null) {
            pituus = kappale.getName().length();
        }
        graphics.fillOval(uiXpos, uiYpos, radius / scale, radius / scale);
        
        if (nameDisplay == true) {
            graphics.fillText(kappale.getName(), uiXpos - pituus,
                uiYpos + kappale.getRadius() / scale + 15);
        }
    }
    
    private void simulateStep(double step) {
        stepcounter++;
        timecounter += step/86400;
        yearcounter += (step/86400)/365;
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
                stopped = false;
                simulationTimeline.play();
            } else {
                if (!popup.isShowing()) {
                    popup.show(stage);
                }
            }
        });
        
        stop.addEventHandler(ActionEvent.ACTION, event -> {
            simulationTimeline.stop();
            stopped = true;
        });
        
        step.addEventHandler(ActionEvent.ACTION, event -> {
            if (popup.isShowing()) {
                popup.hide();
            }
            simulateStep(timestep);
            drawSim();
        });
    }
    
    private void setUpFileMenu(Menu menu, Stage stage) {
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        
        menu.getItems().addAll(save, load);
        
        save.setOnAction(event -> {
            try  {
                FileChooser chooser = new FileChooser();
                File savefile = chooser.showSaveDialog(null);
                saver.saveSimulationData(savefile, simulaatio.getPlanets());
            } catch (NullPointerException e) {
                
            }    
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
            } catch (IOException ioe) {
                
            } catch (NullPointerException e) {
                
            }
            
        });
    }
    
    public void setUpPresets(Menu menu) {
        MenuItem preset1 = new MenuItem("Empty space (clean canvas)");
        MenuItem inners = new MenuItem("Inner Planets of the Solar System");
        MenuItem af = new MenuItem("Binary star system with a distant third star orbiting");
        MenuItem solarSystem = new MenuItem("The Solar System");
        menu.getItems().addAll(preset1, solarSystem, inners, af);
        // Lisää preset4 kun saat valmiiksi
        preset1Content(preset1);
        innerContent(inners);
        alfaCentauriContent(af);
        solarSystemContent(solarSystem);
    }
    
    public void setUpStepSlider(Menu menu) {
        Slider stepper = timestepSlider();
        menu.getItems().add(new SeparatorMenuItem());
        
        CustomMenuItem stepperItem = new CustomMenuItem(stepper);
        stepperItem.setHideOnClick(false);
        menu.getItems().add(stepperItem);
    }
    
    public void setUpScaleSlider(Menu menu) {
        Slider scaler = scaleSlider();
        menu.getItems().add(new SeparatorMenuItem());
        
        CustomMenuItem scalerItem = new CustomMenuItem(scaler);
        scalerItem.setHideOnClick(false);
        menu.getItems().add(scalerItem);
    }
    
    public void setUpTimeUnit(Menu menu) {
        MenuItem sec = new MenuItem("Seconds");
        MenuItem min = new MenuItem("Minutes");
        MenuItem hours = new MenuItem("Hours");
        MenuItem days = new MenuItem("Days");
        MenuItem months = new MenuItem("Months");
        MenuItem y = new MenuItem("Years");
        sec.setOnAction(events-> {
            timeUnit = 1;
            timestep = timeUnit;
        });
        min.setOnAction(eventm-> {
            timeUnit = 60;
            timestep = timeUnit;
        });
        hours.setOnAction(eventh-> {
            timeUnit = 3600;
            timestep = timeUnit;
        });
        days.setOnAction(eventd-> {
            timeUnit = 86400;
            timestep = timeUnit;
        });
        //TODO: turhaa copypasteemista
        months.setOnAction(eventm-> {
            Stage dialogStage = new Stage();
            
            dialogStage.setTitle("Warning");
            VBox vbox = new VBox(new Text("Warning! Using large timestep values \nmight "
                    + "cause heavy instability in the orbits"));
            Button cancel = new Button("Cancel");
            Button ok = new Button("Continue");
            vbox.getChildren().addAll(cancel, ok);
            
            ok.setOnAction(event ->{
                timeUnit = 2629743.83;
                timestep = timeUnit;
                dialogStage.close();
            });
            
            cancel.setOnAction(event ->{
                dialogStage.close();
            });
            
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(15));
            vbox.setSpacing(5);

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
        });
        y.setOnAction(eventy-> {
            Stage dialogStage = new Stage();
            
            dialogStage.setTitle("Warning");
            VBox vbox = new VBox(new Text("Warning! Using a large timestep value \nwill "
                    + "cause heavy instability in the orbits"));
            Button cancel = new Button("Cancel");
            Button ok = new Button("Continue");
            vbox.getChildren().addAll(cancel, ok);
            
            ok.setOnAction(event ->{
                timeUnit = 31556926;
                timestep = timeUnit;
                dialogStage.close();
            });
            
            cancel.setOnAction(event ->{
                dialogStage.close();
            });
            
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(15));
            vbox.setSpacing(5);

            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
        });
        
        menu.getItems().addAll(sec, min, hours, days, months, y);
    }
    
    /*
    public void setUpOverRide(Menu menu) {
        MenuItem timestepOR = new MenuItem("Set a timestep");
        MenuItem scaleOR = new MenuItem("Set a scale");
        
        overrideMethod(timestepOR, "timestep");
        overrideMethod(scaleOR, "scale");
        
        menu.getItems().addAll(timestepOR, scaleOR);
    }
    
    
    
    public void overrideMethod(MenuItem item, String number) {
        item.setOnAction(event -> {
            Group root = new Group();                
            Scene textPrompt = new Scene(root, 200, 100);                
            Stage newStage = new Stage();
            GridPane gridi = new GridPane();
                
            newStage.setTitle("Insert a number");
            root.getChildren().add(gridi);

            TextField text = new TextField("Value of the " + number + " to insert");
            text.setMinWidth(300);
            
            gridi.add(text, 0, 0);
                
            Button confirm = new Button("Confirm");
            gridi.add(confirm, 0, 1);
            
            confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String value = text.getText();
                    try {
                        // Tästä tulikin ihan tyhmä mut saapi olla
                        double valueDouble = Double.parseDouble(value);
                        if (valueDouble < 0 || valueDouble > 1000) {
                            text.setText("The number is too large");
                        } else {
                            if (number == "timestep") {
                                timestep = valueDouble;
                            } else if (number == "scale") {
                                scale = valueDouble;
                            }
                        }    
                    } catch (Exception ne) {
                        text.setText("Please insert a number");
                    }
                }
            });
            newStage.setScene(textPrompt);
                
            newStage.show();
        });
    }
    */

    public void preset1Content(MenuItem preset1) {
        preset1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                simulationTimeline.stop();
                resetCounters();
                drawSim();
            }
        });
    }
    
    // TODO: ainakin aurinkokunnan planeettojen nimet 
    // jotenkin järkevästi esim erilliseen tiedostoon
    public void solarSystemContent(MenuItem preset) {
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                scale = 6.3;
                
                Planet aurinko = new Planet("Sun", new Vector2(0, 0), new Vector2(0, 0), 2E30, 13);
                
                Planet maa = new Planet("Earth", new Vector2(1.496E11, 0), new Vector2(0, 30000), 5.972E24, 4);
                Planet mars = new Planet("Mars", new Vector2(2.1641E11, 0), new Vector2(0, 24130), 6.4E23, 3);
                Planet venus = new Planet("Venus", new Vector2(1.08E11, 0), new Vector2(0, 35021), 4.87E24, 3.5);
                Planet merkurius = new Planet("Mercury", new Vector2(7.5E10, 0), new Vector2(0, 41004), 3.3E23, 1);
                
                Planet saturn = new Planet("Saturn", new Vector2(14.3E11, 0), new Vector2(0, 9680), 6.6834E26, 7.5);
                Planet jupiter = new Planet("Jupiter", new Vector2(7.79E11, 0), new Vector2(0, 13070), 1.898E27, 9);
                Planet uranus = new Planet("Uranus", new Vector2(2.871E12, 0), new Vector2(0, 6835.2), 8.686E25, 7);
                Planet neptune = new Planet("Neptune", new Vector2(4.4989E12, 0), new Vector2(0, 5477.8), 1.024E26, 7);
                Planet pluto = new Planet("Pluto", new Vector2(5.909E12, 0), new Vector2(0, 4749), 1.290E22, 1);
                
                // TODO: addAll metodi simulaatiolle
                simulaatio.add(maa);
                simulaatio.add(mars);
                simulaatio.add(venus);
                simulaatio.add(merkurius);
                
                simulaatio.add(jupiter);
                simulaatio.add(saturn);
                simulaatio.add(uranus);
                simulaatio.add(neptune);
                simulaatio.add(pluto);
                
                simulaatio.add(aurinko);
                
                resetCounters();
                drawSim();
            }
        });
    }
    
    public void innerContent(MenuItem preset) {
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                scale = 0.5;
                Planet toinen = new Planet("Sun", new Vector2(0, 0), new Vector2(0, 0), 2E30, 20.0);
        
                Planet maa = new Planet("Earth", new Vector2(1.496E11, 0), new Vector2(0, 30000), 5.972E24, 10);
                Planet kolmas = new Planet("Mars", new Vector2(2.1641E11, 0), new Vector2(0, 24130), 6.4E23, 6);
                Planet neljas = new Planet("Venus", new Vector2(1.08E11, 0), new Vector2(0, 35021), 4.87E24, 9);
                Planet viides = new Planet("Mercury", new Vector2(6.5E10, 0), new Vector2(0, 42004), 3.3E23, 4);
                
                simulaatio.add(maa);
                simulaatio.add(toinen);
                simulaatio.add(kolmas);
                simulaatio.add(neljas);
                simulaatio.add(viides);
                
                resetCounters();
                drawSim();
            }
        });
    }
    
    public void alfaCentauriContent(MenuItem preset) {
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulaatio.clear();
                scale = 5;
                Planet a = new Planet("A", new Vector2(-1.7E12, 0), new Vector2(0, 2700), 2.2E30, 30);
                Planet b = new Planet("B", new Vector2(1.7E12, 0), new Vector2(0, -1100), 1.8E30, 25);
                // Planet distant = new Planet("I'm smol", new Vector2(750, 100), new Vector2(15, 0), 10000, 15);

                simulaatio.add(a);
                simulaatio.add(b);
                //simulaatio.add(distant);
                resetCounters();
                drawSim();
            }
        });
    }
    
    public void resetCounters() {
        stepcounter = 0;
        timecounter = 0;
        yearcounter = 0;
    }
    
    public void setUpCustomize(Menu menu) {
        MenuItem add = new MenuItem("Add an object");
        MenuItem delete = new MenuItem("Clear an object");
        MenuItem disp = new MenuItem("Show planet names");
        menu.getItems().addAll(add, delete, disp);
        userDeletePlanet(delete);
        userAddPlanet(add);
        userDisplayNames(disp);
    }
    
    public void userDeletePlanet(MenuItem delete) {
        delete.setOnAction(event -> {
            Stage newStage = new Stage();
            GridPane gridi = new GridPane();
            gridi.setPadding(new Insets(5));
            gridi.setHgap(5);
            gridi.setVgap(5);
            
            newStage.setTitle("Click to clear a planet");
            
            int rowIndex = 0;
            int columnIndex = 0;
            
            for (Planet p : simulaatio.getPlanets()) {
                Button deleteButton = new Button(p.getName() + ", Mass: " + p.getMass()
                        + ", Radius: " + p.getRadius());
                deleteButton.setMinWidth(400);
                deleteButton.setMaxWidth(400);
                deleteButton.setMinHeight(25);
                deleteButton.setMaxHeight(25);
                gridi.add(deleteButton, 2, rowIndex++);
                deleteButton.setOnAction(e -> {
                    p.delete();
                    drawSim();
                });
            }
            
            ScrollPane sp = new ScrollPane(gridi);
            sp.setMaxHeight(300);
            sp.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
            sp.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            
            newStage.setScene(new Scene(sp));
            newStage.show();
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
    
    public void userDisplayNames(MenuItem disp) {
        disp.setOnAction(event -> {
            if (nameDisplay == true) {
                nameDisplay = false;
                disp.setText("Show planet names");
                drawSim();
            } else {
                nameDisplay = true;
                disp.setText("Hide planet names");
                drawSim();
            }
        });
    }
    
    private void updateStepCounter() {
        String s = String.valueOf(stepcounter);
        long days = 0;
        long years = 0;
        String d = String.valueOf((long)timecounter);
        String y = String.valueOf((int)yearcounter);
        
        stepTimeLabel.setText("Elapsed timesteps: " + s + "        Elapsed days: " + d 
        + "        Elapsed years: " + y);
    }
    
    // Vähän tynkä metodi
    private File chooseLoadFile(Stage stage) {
        File toLoad = filechooser.showOpenDialog(stage);
        return toLoad;
    }
    
    
}
