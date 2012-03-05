package app.jam.ics;

import java.io.Serializable;
import java.util.ArrayList;

import android.location.Location;

public class Restaurant implements Serializable{

	private long id;
	private String name;
	private ArrayList<Meal> menu = new ArrayList<Meal>();	
//	private Location loc;
	private double longitude;
	private double latitude;
	
	
	public Restaurant(long id, String name, double la, double lo) {		
		this.id = id;
		this.name = name;
		longitude = lo;
		latitude = lo;		
	}	
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {//TODO for what?
		return name;
	}

	public long getId() {
		return id;
	}	

	public ArrayList<Meal> getMenu() {
		return menu;
	}	
	
	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getName() {
		return name;
	}
}
