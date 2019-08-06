package main;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
	
public class TimeZonesDisplay extends Application {

		private final double WIDTH = 800;
		private final double HEIGHT = 340;
		private Label maintime, lb1, lb2, lb3;
		private ChoiceBox<String> choiceBox1, choiceBox2, choiceBox3;
		private LocalTime ltime;
		private TimeZone timeZone;
		private DateTimeFormatter timeFormatter;
		private List<Integer> choiceHashes;
		
		@Override
	    public void start(Stage stage) {
			GridPane root = new GridPane();
	        Scene scene = new Scene(root, HEIGHT, WIDTH);
	        ltime = LocalTime.now(); 
	        String timePattern = "HH:mm:ss"; //pattern of time displayed on the labels
	        timeFormatter = DateTimeFormatter.ofPattern(timePattern);
	        timeZone = TimeZone.getDefault();
	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); //points css style file
	        Insets inserts = new Insets(10, 10, 10, 10);
	        choiceHashes= new ArrayList<>();
	        
	        //sets labels of local zone and current time, done once
	        maintime = new Label("Your time zone is: "+timeZone.getID()+"\n"+"Current time is: "
	        					+timeFormatter.format(ltime).toString());
	        
	        //sets starting value of 2nd row labels and choice boxes below them
	        timeZone.setID("America/New_York");
	        ltime=LocalTime.now(ZoneId.of("America/New_York"));
	        lb1 = new Label("In time zone: "+timeZone.getID()+"\n"+"Current time is: "
					+timeFormatter.format(ltime).toString());
	        timeZone.setID("Portugal");
	        ltime=LocalTime.now(ZoneId.of("Portugal"));
	        lb2 = new Label("In time zone: "+timeZone.getID()+"\n"+"Current time is: "
					+timeFormatter.format(ltime).toString());
	        timeZone.setID("Australia/Tasmania");
	        ltime=LocalTime.now(ZoneId.of("Australia/Tasmania"));
	        lb3 = new Label("In time zone: "+timeZone.getID()+"\n"+"Current time is: "
					+timeFormatter.format(ltime).toString());
	        
	        choiceBox1 = new ChoiceBox<String>();
	        choiceBox2 = new ChoiceBox<String>();
	        choiceBox3 = new ChoiceBox<String>();
	        List<String> zonelist = new ArrayList<>(Arrays.asList("America/New_York", "Portugal", "Australia/Tasmania",
	        		"Japan", "Canada/Central", "Iceland")); //list of available zones that is converted from a simple array
	        choiceBox1.getItems().addAll(zonelist);
	        choiceBox2.getItems().addAll(zonelist); //sets available choice picks for the choice boxes
	        choiceBox3.getItems().addAll(zonelist);
	        choiceBox1.setValue(zonelist.get(0));
	        choiceBox2.setValue(zonelist.get(1)); //sets default values to be displayed on start
	        choiceBox3.setValue(zonelist.get(2));
	        choiceHashes.add(choiceBox1.hashCode());
	        choiceHashes.add(choiceBox2.hashCode()); //hashes stored on a list that helps later identify the source
	        choiceHashes.add(choiceBox3.hashCode());
	        
	        GridPane.setMargin(maintime, inserts); //cosmetic adjustments
	        GridPane.setMargin(lb1, inserts);
	        GridPane.setMargin(lb2, inserts);
	        GridPane.setMargin(lb3, inserts);
	        GridPane.setMargin(choiceBox1, inserts);
	        GridPane.setMargin(choiceBox2, inserts);
	        GridPane.setMargin(choiceBox3, inserts);
	        
	        root.add(maintime, 1, 0, 1, 1); //objects put on a gridpane and then aligned
	        root.add(lb1, 0, 1, 1, 1);
	        root.add(lb2, 1, 1, 1, 1);
	        root.add(lb3, 2, 1, 1, 1);
	        root.add(choiceBox1, 0, 2, 1, 1);
	        root.add(choiceBox2, 1, 2, 1, 1);
	        root.add(choiceBox3, 2, 2, 1, 1);
	        GridPane.setHalignment(maintime, HPos.CENTER);
	        GridPane.setHalignment(lb1, HPos.LEFT);
	        GridPane.setHalignment(lb2, HPos.CENTER);
	        GridPane.setHalignment(lb3, HPos.RIGHT);
	        GridPane.setHalignment(choiceBox1, HPos.LEFT);
	        GridPane.setHalignment(choiceBox2, HPos.CENTER);
	        GridPane.setHalignment(choiceBox3, HPos.RIGHT);
	        
	        choiceBox1.setOnAction(chboxHandler);
	        choiceBox2.setOnAction(chboxHandler); //call chboxHandler when anything is picked
	        choiceBox3.setOnAction(chboxHandler);
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.setTitle("Time Zones");
	        stage.setHeight(HEIGHT);
	        stage.setWidth(WIDTH);
	        stage.show();
	    }
		
		public void refreshLabel(String s, int x) { //takes variables: zone as string and label id as integer
			switch(x) {
			case 1: {
				timeZone.setID(s);
		        ltime=LocalTime.now(ZoneId.of(s));
		        lb1.setText("In time zone: "+timeZone.getID()+"\n"+"Current time is: "
						+timeFormatter.format(ltime).toString());
		        break;
			}
			case 2: {
				timeZone.setID(s);
		        ltime=LocalTime.now(ZoneId.of(s));
		        lb2.setText("In time zone: "+timeZone.getID()+"\n"+"Current time is: "
						+timeFormatter.format(ltime).toString());
		        break;
			}
			case 3: {
				timeZone.setID(s);
		        ltime=LocalTime.now(ZoneId.of(s));
		        lb3.setText("In time zone: "+timeZone.getID()+"\n"+"Current time is: "
						+timeFormatter.format(ltime).toString());
		        break;
			}
			default: break;
			}
		}

		EventHandler<ActionEvent> chboxHandler = new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	if(e.getSource().hashCode()==choiceHashes.get(0)) { //if hashcode matches first 2nd row label
		    		refreshLabel(choiceBox1.getSelectionModel().getSelectedItem(), 1); //action of refreshing the vaule
				}
		    	else if(e.getSource().hashCode()==choiceHashes.get(1)) {
		    		refreshLabel(choiceBox2.getSelectionModel().getSelectedItem(), 2);
				}
		    	else if(e.getSource().hashCode()==choiceHashes.get(2)) {
		    		refreshLabel(choiceBox3.getSelectionModel().getSelectedItem(), 3);
				}
		    	e.consume();
		    }
		};

	    //main class
	    public static void main(String[] args) {
	        launch(args);
	    }
	}
