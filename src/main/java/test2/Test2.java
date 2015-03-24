/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 2 (Garage Class)
 *
 *  
 *****************************************************************************/

package test2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.scene.*;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Test2 extends Application {
    Stage primaryStage;
    FlowPane rootLayout;
    Garage garage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        garage = new Garage();
        
        initRootLayout();
        insertMetadataInput(rootLayout);
        insertServices(rootLayout);
        insertButtons(rootLayout);
        
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    
    private void initRootLayout() {
        rootLayout = new FlowPane();
        primaryStage.setScene(new Scene(new AnchorPane(rootLayout)));
    }
    private void insertMetadataInput(Pane parent) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        parent.getChildren().add(hbox);
        
        HBox nameHBox = new HBox();
        TextField id = new TextField();
        id.setId("customerID");
        nameHBox.getChildren().add(new Label("Customer ID: "));
        nameHBox.getChildren().add(id);
        
        HBox dateHBox = new HBox();
        DatePicker date = new DatePicker();
        date.setId("date");
        dateHBox.getChildren().add(new Label("Date: "));
        dateHBox.getChildren().add(date);
        
        hbox.getChildren().add(nameHBox);
        hbox.getChildren().add(dateHBox);
        
        
    }
    private void insertServices(Pane parent) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        parent.getChildren().add(vbox);
        // Get the services list and insert each into the FlowPane
        Garage.getServices().forEach((service) -> {
            CheckBox box = new CheckBox();
            box.setText(service + " - $" + Garage.getPrice(service));
            box.setId(service.replaceAll("\\s", ""));
            vbox.getChildren().add(box);
        });
    }
    private void insertButtons(Pane parent) {
        Button addService = new Button();
        addService.setText("Submit Order");
        addService.onActionProperty().set((event) -> {
            TextField idField = (TextField)rootLayout.lookup("#customerID");
            String id = idField.getText();
            idField.setText("");
            
            TextField dateField = (TextField)rootLayout.lookup("#date");
            String date = dateField.getText();
            dateField.setText("");
            
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
class Garage {
    //static
    private static final ArrayList<ArrayList<String>> orders = new ArrayList<>();
    private static final HashMap<String, String> services =
            new HashMap<>();
    static {
        services.put("Lube Job", "19.99");
        services.put("Tire Rotation", "48.00");
        services.put("Random other service", "102.80");
    }
    
    public static Set<String> getServices() { return services.keySet(); }
    public static String     getPrice(String service) {
        String temp = services.getOrDefault(service, "");
        System.out.println("Getting "  + service + " from hash map.");
        System.out.println("Returned " + temp);
        return temp;
    }
    //end static
    public void addOrder(String id, LocalDateTime time, String price) {
        ArrayList<String> order = new ArrayList<>();
    }
}
