package application;
import javafx.beans.property.*;

public class Country {
	private final SimpleStringProperty name;
	private final SimpleDoubleProperty percentage;

	public Country(String name, double percentage) {
		this.name = new SimpleStringProperty(name);
		this.percentage = new SimpleDoubleProperty(percentage);
	}

	public String getName() {
		return name.get();
	}

	public double getPercentage() {
		return percentage.get();
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public SimpleDoubleProperty percentageProperty() {
		return percentage;
	}

	@Override
	public String toString() {
		return "Country [name=" + name.get() + ", percentage=" + percentage.get() + "]";
	}

}



