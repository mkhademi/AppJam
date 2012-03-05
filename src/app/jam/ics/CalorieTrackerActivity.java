package app.jam.ics;


import java.util.ArrayList;
import java.util.List;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalorieTrackerActivity extends MapActivity implements OnClickListener
{
	private MapView mMapView;
	private myLocationOverlay mPosition;
	private StepsCounter sc;  //instance to the steps counter
	
	private EditText mSteps;
	private EditText mCalories;
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.calorietracker);
		
		Button filterButton = (Button)findViewById(R.id.filterbuttonincalorietracker);
		filterButton.setOnClickListener(this);
		
		Button restaurantsButton = (Button)findViewById(R.id.restaurantsButton);
		restaurantsButton.setOnClickListener(this);
		
		Button myinfoButton = (Button)findViewById(R.id.myinfobutton);
		myinfoButton.setOnClickListener(this);
		
		Button findRecommendationButton = (Button)findViewById(R.id.findRecommendations);
		findRecommendationButton.setOnClickListener(this);
		
		mSteps = (EditText)findViewById(R.id.steps);
		mCalories = (EditText)findViewById(R.id.calories);
		
		//bind calorie calculation service now
		doBindService();
		
		// process Google map related service
        mMapView = (MapView)findViewById(R.id.MapView01);
        mMapView.setTraffic(true);
        mMapView.setEnabled(true);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setClickable(true); 
        mMapView.setHapticFeedbackEnabled(true);
                
        mPosition = new myLocationOverlay();
        List<Overlay> overlays = mMapView.getOverlays();
        overlays.add(mPosition);
        
        ///////////////////////////////////////////
        // deal with GPS related service
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        
        LocationManager mLocManager;
        mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        String provider = mLocManager.getBestProvider(criteria, true); 
        
        Location currLocation = mLocManager.getLastKnownLocation(provider);
        
        updateWithNewLocation(currLocation);
        
        mLocManager.requestLocationUpdates(provider, 3000, 0, mLocListener);
        
        	
	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
    private void updateWithNewLocation(Location location)
    {
    	if(location != null)
    	{
    		mPosition.setLocation(location);
    	}
    }
	
    
    private final LocationListener  mLocListener = new LocationListener()
    {

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			updateWithNewLocation(null);
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    
    
    class myLocationOverlay extends Overlay
    {
    	Location mLocation;
    	
    	public void setLocation(Location loc)
    	{
    		mLocation = loc;
    	}
    	
    	@Override
    	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
    	{
    	    super.draw(canvas, mapView, shadow);
    	    Point myScreenCoords = new Point();
    	    GeoPoint tmpGeoPoint = new GeoPoint((int)(mLocation.getLatitude()*1E6),(int)(mLocation.getLongitude()*1E6));
    	    mapView.getProjection().toPixels(tmpGeoPoint, myScreenCoords);
    	    Paint mPaint = new Paint();
    	    mPaint.setStrokeWidth(1);
    	    mPaint.setARGB(255, 255, 0, 0);
    	    mPaint.setStyle(Paint.Style.STROKE);
    	    Bitmap mbmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
    	    canvas.drawBitmap(mbmp, myScreenCoords.x, myScreenCoords.y, mPaint);
			return true;
    	}
    }
	
	

	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case(R.id.filterbuttonincalorietracker)://maryam
			Intent intent = new Intent(this, Filter.class);
			Location location = mPosition.mLocation;
			intent.putExtra("app.jam.ics.position", location);
			startActivity(intent);
			break;
		case(R.id.restaurantsButton):
			startActivity(new Intent(this, RestaurantList.class));
			break;
		case(R.id.myinfobutton):
			startActivity(new Intent(this,CaloriesActivity.class));
			break;
		case (R.id.findRecommendations):
			getStepsCalories(v);
			
			//maryam
	        getRestaurantRecommendation();
	        
			break;
		default:
			break;
		}		
	}
	
	/////////////////////////////////////
	// Function: Service Connection/////
	///////////////////////////////////

	private ServiceConnection mConnection = new ServiceConnection(){

		public void onServiceConnected(ComponentName className, IBinder binder) {
			// TODO Auto-generated method stub
			sc = ((StepsCounter.MyBinder)binder).getStepsCounter();
			Toast.makeText(CalorieTrackerActivity.this, "Service Connected",
					Toast.LENGTH_SHORT).show();
	
		}
	
		public void onServiceDisconnected(ComponentName className) {
			// TODO Auto-generated method stub
			sc = null;
		} 	
    };

	/////////////////////////////////////
	// Function: Service bind/////
	///////////////////////////////////
	private void doBindService(){
	     	bindService(new Intent(this,StepsCounter.class),mConnection,Context.BIND_AUTO_CREATE);
	}
	
	
    private void getStepsCalories(View view)
    {
    	if(sc != null)
    	{
    		int steps = sc.getSteps();
    		float calorie = sc.getCalorie();
    		mSteps.setText(""+steps);
    		mCalories.setText(""+calorie);
    	}
    }
    
    /**
	 * Restaurant Recommendation Part
	 * Maryam 
	 */	
	List<Restaurant> getRestaurantRecommendation(){
	
		System.out.println("calorieTracker.getRestRecom");
		List<Restaurant> restList = new ArrayList<Restaurant>();		
		
		System.out.println("sc.getCalorie : " +sc);
		
		List<Meal> mealList = new ArrayList<Meal>();
		
		MenuDataSource menuDataSource = new MenuDataSource(this);
		menuDataSource.open();
		mealList = menuDataSource.getMealRecommendation(600);//TODO @Mingming this is the place 500 should be replaced with calorie
		menuDataSource.close();
		
		RestaurantDataSource restaurantDataSource = new RestaurantDataSource(this);
		restaurantDataSource.open();
		restList = restaurantDataSource.getRestaurantRecommendation(mealList);
		restaurantDataSource.close();
		
		return restList;
	}	
}
