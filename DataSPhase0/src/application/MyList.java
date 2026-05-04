package application;
public class MyList<T> implements Listable<T> {
	private T[] data;
	private int count = 0;

	public int getCount() {
		return count;
	}

	public MyList(int capacity) {
		data = (T[]) new Object[capacity];
	}


	private void reSize() {  //resize the list when needed by doubling the list size.
		T[] temp = (T[]) new Object[2 * data.length];
		System.arraycopy(data, 0, temp, 0, count);
		data = temp;
	}

	@Override
	public void add(T t) {  // insert the data on text area
		if (count >= data.length)
			reSize();
		data[count++] = t;
	}

	@Override
	public void delete(String name) {  //delete a country record from the List using the country name, 
		//first search for the name of country then delete it
		int index = -1;
		for (int i = 0; i < count; i++) {
			if (data[i] != null && data[i].toString().equals(name)) {
				index = i;
				break;
			}
		}
		if (index >= 0) {

			for (int i = index; i < count - 1; i++) {
				data[i] = data[i + 1];
			}
			data[count - 1] = null; 
			count--;
		}
	}



	@Override
	public T search(String name) {     // search for a specific country record by name
		for (int i = 0; i < count; i++) {
			if (data[i] != null &&  data[i].toString().equals(name)){
				return data[i];
			}
		}

		return null;
	}

	@Override
	public void display(double percentage) {   
		for (int i = 0; i < count; i++) {
			if (data[i] != null) {
				System.out.println(data[i].toString());
			}

		}
	}
}