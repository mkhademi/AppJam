package app.jam.ics;

import android.location.Location;

/**
 * 
 * @author Maryam
 *
 */
public class Person {
	
	long id;

	String gender;
	int age;
	int feet;
	
	int inches;
	double weight;
	
	Location location;
	int number_of_steps = 0;
	int burnt_calories = 0;	
	
	public Person(long id, String gender, int age, int feet, int inches,
			double weight) {
		super();
		this.id = id;
		this.gender = gender;
		this.age = age;
		this.feet = feet;
		this.inches = inches;
		this.weight = weight;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getFeet() {
		return feet;
	}

	public int getInches() {
		return inches;
	}
	
	public long getId() {
		return id;
	}
	
	public String getGender() {
		return gender;
	}
	
	public int getAge() {
		return age;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public int getNumber_of_steps() {
		return number_of_steps;
	}
	public void setNumber_of_steps(int number_of_steps) {
		this.number_of_steps = number_of_steps;
	}
	public int getBurnt_calories() {
		return burnt_calories;
	}
	public void setBurnt_calories(int burnt_calories) {
		this.burnt_calories = burnt_calories;
	}
}
