package app.jam.ics;

import java.io.Serializable;

import android.location.Location;

public class MyLocation extends Location implements Serializable{

	public MyLocation(Location l) {
		super(l);
		// TODO Auto-generated constructor stub
	}

	double longitude, latitude;
}
