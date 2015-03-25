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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Test2 extends Application {
    Stage    primaryStage;
    FlowPane rootLayout;
    VBox     servicesBox;
    Customer customer;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        customer = new Customer();
        
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
        date.setValue(LocalDate.now());
        dateHBox.getChildren().add(new Label("Date: "));
        dateHBox.getChildren().add(date);
        
        hbox.getChildren().add(nameHBox);
        hbox.getChildren().add(dateHBox);
        
        
    }
    private void insertServices(Pane parent) {
        servicesBox = new VBox();
        servicesBox.setSpacing(10);
        parent.getChildren().add(servicesBox);
        // Get the services list and insert each into the FlowPane
        Garage.getServices().forEach((service) -> {
            CheckBox box = new CheckBox();
            box.setText(service + " - $" + Garage.getPrice(service));
            box.setId(service.replaceAll("\\s", ""));
            servicesBox.getChildren().add(box);
        });
    }
    private void insertButtons(Pane parent) {
        Button addService = new Button();
        Button viewData   = new Button();
        HBox buttonBox    = new HBox(addService, viewData);
        buttonBox.setSpacing(10);
        
        addService.setText("Submit Order");
        addService.onActionProperty().set((event) -> {
            TextField idField = (TextField)rootLayout.lookup("#customerID");
            String id = idField.getText();
            idField.setText("");
            
            DatePicker dateField = (DatePicker)rootLayout.lookup("#date");
            LocalDate date = dateField.getValue();
            dateField.setValue(LocalDate.now());
            
            final IntegerProperty accum = new SimpleIntegerProperty();
            servicesBox.getChildren().forEach((child) -> {
                CheckBox box = (CheckBox)child;
                if (box.isSelected()) {
                    String[] temp = box.getText().split("-");
                    accum.setValue(Integer.parseInt(
                            Garage.getPrice(temp[0].trim())));
                }
            });
            String cost = Integer.toString(accum.get());
            
            customer.addOrder(id, date, cost);
        });
        
        viewData.setText("View Data");
        viewData.setOnAction((event) -> {
            String customerData = "";
            String[][] orders = customer.getOrders();
            for (int i = 0; i < orders.length; i++) {
                customerData += orders[i][0] + "\t\t";
                customerData += orders[i][1] + "\t\t";
                customerData += "$" + orders[i][2] + "\n";
            }
            Alert alert = new Alert(AlertType.INFORMATION, customerData);
            alert.setTitle("Orders");
            alert.showAndWait();
        });
        
        AnchorPane pane = new AnchorPane(buttonBox);
        AnchorPane.setBottomAnchor(buttonBox, 0d);
        AnchorPane.setLeftAnchor(buttonBox, 0d);
        parent.getChildren().add(pane);
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
        services.put("Oil Change", "26");
        services.put("Lube Job", "18");
        services.put("Radiator Flush", "30");
        services.put("Transmission flush", "80");
        services.put("Inspection", "15");
        services.put("Muffler Replacement", "100");
        services.put("Tire Rotation", "20");
    }
    
    public static Set<String> getServices() { return services.keySet(); }
    public static String getPrice(String service) {
        return services.get(service);
    }
    //end static
    public void addOrder(String id, LocalDateTime time, String price) {
        ArrayList<String> order = new ArrayList<>();
    }
}
// As per instructions by Mr. Cacase, the customer class uses a fixed size
// Array. Therefore, if you attempt to add more than five entries, it will\
// simply overwrite the first entry.
class Customer {
    private int numOfOrders = 0;
    private final String[][] orders = new String[5][3];
    public String[][] getOrders() { return orders.clone(); }
    public void addOrder(String id, LocalDate date, String cost) {
        int index = numOfOrders % 5;
        orders[index][0] = id;
        orders[index][1] = date.toString();
        orders[index][2] = cost;
        numOfOrders += 1;
    }
}