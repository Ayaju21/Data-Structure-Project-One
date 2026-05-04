package application;

public interface Listable <Country>{   // Abstract method

	void add(Country t);

	void delete(String name );

	Country search (String name);

	void display(double percentage);

}
