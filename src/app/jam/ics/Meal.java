package app.jam.ics;

public class Meal{
	
	private long id;
	private String name;
	private float calorie;
	private float price;
	private String place;
	
	public Meal(long i, String n, float c, float pr, String p){
		id = i;
		name = n;
		calorie = c;
		price = pr;
		place = p;
	}
	
	public float getPrice() {
		return price;
	}
	
	public String getPlace() {
		return place;
	}

	public String getName() {
		return name;
	}

	public float getCalorie() {
		return calorie;
	}
		
}