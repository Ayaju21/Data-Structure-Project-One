package application;
import javafx.scene.control.cell.*;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.*;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import java.io.*;

public class Main extends Application {

	public MyList<Country> yourList = new MyList(2000);

	// for Table view
	public ObservableList<Country> tData = FXCollections.observableArrayList();
	public TableView<Country> tableView = new TableView<>(tData);

	Label names = new Label("Name");
	TextField name = new TextField();
	Label pre = new Label("%");
	TextField pres = new TextField();


	TextArea textArea = new TextArea();
	double prefWidth = 400;
	double prefHeight = 400;


	Button load = new Button("File Chooser");
	Button insert = new Button ("Insert");
	Button delete = new Button("Delete");
	Button search = new Button ("Search");
	Button display = new Button ("Display");


	@Override
	public void start(Stage primaryStage) {
		try {

			GridPane gp = new GridPane();
			gp.add(names, 0, 0);
			gp.add(name, 1, 0);
			gp.add(pre, 0, 1);
			gp.add(pres, 1, 1);
			gp.add(load, 1, 4);
			gp.add(insert, 2, 4);
			gp.add(delete, 3, 4);
			gp.add(search, 4, 4);
			gp.add(display, 5, 4);
			gp.add(textArea, 1, 9);
			gp.setAlignment(Pos.TOP_LEFT);
			gp.setPadding(new Insets(20));
			gp.setVgap(20);
			gp.setHgap(10);
			gp.setStyle("-fx-background-color: orange");


			//Country name column 
			TableColumn<Country, String> nameColumn = new TableColumn<>("Country");
			nameColumn.setMinWidth(200);
			nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

			// Country Percentage column
			TableColumn<Country, Double> percentageColumn = new TableColumn<>("Percentage");
			percentageColumn.setMinWidth(200);
			percentageColumn.setCellValueFactory(data -> data.getValue().percentageProperty().asObject());

			tableView.getColumns().addAll(nameColumn, percentageColumn);
			tableView.setItems(tData);

			// stage for Table view
			Stage stage = new Stage();
			stage.setTitle("Table View");
			VBox vbox = new VBox(tableView );
			Scene scence = new Scene(vbox, 400, 400);
			stage.setScene(scence);
			stage.show(); 


			//create Button for File chooser
			load.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv Files", "*.csv"));
				File selectedFile = fileChooser.showOpenDialog(primaryStage);

				if (selectedFile != null) {
					try {
						Scanner scanner = new Scanner(selectedFile);
						StringBuilder fileContent = new StringBuilder();
						while (scanner.hasNextLine()) {
							fileContent.append(scanner.nextLine()).append("\n");
						}
						scanner.close();
						textArea.setText(fileContent.toString());
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});

			// insert a new country record into the List. 
			insert.setOnAction(  e -> { 
				String country = name.getText();
				double percentage = Double.parseDouble(pres.getText());
				Country newRecord = new Country(country, percentage);
				yourList.add(newRecord);
				textArea.appendText(newRecord.toString() + "\n");
				name.clear();
				pres.clear();
			});


			// deleted data by name
			delete.setOnAction( e -> { 
				String countryToDelete = name.getText();
				String textInTextArea = textArea.getText();
				String[] lines = textInTextArea.split("\n");
				StringBuilder newTextInTextArea = new StringBuilder();
				for (String line : lines) {
					if (line.contains(countryToDelete)) {
						continue;
					}
					newTextInTextArea.append(line + "\n");
				}
				textArea.setText(newTextInTextArea.toString());
				name.clear();
			});

			// depends on the name 
			search.setOnAction( e -> {
				String countrySearch = name.getText();
				String textAreaContent = textArea.getText();
				String[] lines = textAreaContent.split("\n");

				StringBuilder updatedContent = new StringBuilder();
				boolean found = false;
				for (String line : lines) {
					if (line.contains(countrySearch)) {
						updatedContent.append("Country is found: ").append(line).append("\n");
						found = true;
					}
				}

				if (!found) {
					updatedContent.append("Country not found");

				}
				textArea.setText(updatedContent.toString());
			});



			display.setOnAction( e -> { //display data on Table view
				try {
					tData.clear(); 
					FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv Files", "*.csv"));
					File selectedFile = fileChooser.showOpenDialog(primaryStage);

					if (selectedFile != null) {
						try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
							String line;
							while ((line = reader.readLine()) != null) {
								String[] parts = line.split(",");
								if (parts.length == 2) {
									String countryName = parts[0].trim();
									double internetUsage = Double.parseDouble(parts[1].trim());
									tData.add(new Country(countryName, internetUsage));
								}
							}
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}


			});

			Scene scene = new Scene(gp,900,500);
			primaryStage.setTitle("Phase 0");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
