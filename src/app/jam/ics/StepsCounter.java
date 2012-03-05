package app.jam.ics;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class StepsCounter extends Service implements SensorEventListener, Runnable {
	private final IBinder mBinder = new MyBinder();
    
	private int steps;
	private float calorieBurn;
	private SensorManager mSensorManager;
	private Handler handler = new Handler();
	private final int sampleInterval = 100;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	public class MyBinder extends Binder {
		StepsCounter getStepsCounter()
		{
			return StepsCounter.this;
		}
	}
	
	public void onCreate() {
		super.onCreate();
		mSensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> mySensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		//Log.v("stepscounter","Num of Sensors: " + mySensors.size());
		if(mySensors.size() > 0)
		{
			mSensorManager.registerListener(this, mySensors.get(0), SensorManager.SENSOR_DELAY_FASTEST);
			//Log.i("Regiester Sensor", "Register accelerometer sensor");
		}
		handler.postDelayed(this, sampleInterval);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mSensorManager.unregisterListener(this);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		//StepsCounting();
	    handler.postDelayed(this, sampleInterval);
		
	}
	
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

    private double acc_x;
    private double acc_y;
    private double acc_z;
    private double[] gravity = new double[3];
    
	private int RingSize = 10;
	private double[] RingBuffer = new double[RingSize];
	private int RingIndex = 0;
	
	private final int derivativeBufferSize = 3;
	private double[] derivativeBuffer = new double[derivativeBufferSize];
	private int derivativeIndex = 0;	
	double currentAcc,prevAcc;
    private final double ZERO_UP = 0.001;
    private final double ZERO_DOWN =0.000001; 
    private final double ZERO = 8;
	private long prevTime, dT;
	
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
    	if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
    	{
		   dT = System.currentTimeMillis() - prevTime;
		   //1. High-path filter to eliminate gravity
		   final double alpha = 0.8;

           gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
           gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
           gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

           acc_x = event.values[0] - gravity[0];
           acc_y = event.values[1] - gravity[1];
           acc_z = event.values[2] - gravity[2];

    		/*
    		acc_x = event.values[0];
    		acc_y = event.values[1];
    		acc_z = event.values[2];
    		*/
  		 //2. Calculate magnitude
  		 double Magnitude = Math.sqrt(acc_x * acc_x + acc_y * acc_y + acc_z * acc_z);
  		 RingBuffer[RingIndex] = Magnitude;
  		 RingIndex = (RingIndex+1)%RingSize;
  		 
  			//1. Smooth the data from the ring buffer
  			int size = RingBuffer.length;
  			currentAcc = 0;
  			for(int i =0; i < size; i++)
  			{
  				currentAcc += RingBuffer[i];
  			}
  			if(size != 0)
  				currentAcc /= size;
  			else
  				currentAcc = 0;
  			
  			//2. Derivative
  			
  			double derivative = (currentAcc - prevAcc)*1000/dT;
  			//double derivative = (currentAcc - prevAcc);
  			derivativeBuffer[derivativeIndex]=derivative;
  			derivativeIndex = (derivativeIndex+1)%derivativeBufferSize;
  			
  			if(derivativeBuffer.length == derivativeBufferSize)
  			{
  				// step calculation
  				// find zero passing point from positive to negative 
  				if(derivativeBuffer[1] > ZERO && derivativeBuffer[0] < -ZERO)
  					steps++;
  			}

  			prevAcc = currentAcc;
  			prevTime = System.currentTimeMillis();
    	}   		   		
	}
    	
	public void StepsCounting()
	{		 
		//1. Smooth the data from the ring buffer
		int size = RingBuffer.length;
		currentAcc = 0;
		for(int i =0; i < size; i++)
		{
			currentAcc += RingBuffer[i];
		}
		if(size != 0)
			currentAcc /= size;
		else
			currentAcc = 0;
		
		//2. Derivative
		
		//double derivative = (currentAcc - prevAcc)*1000/dT;
		double derivative = (currentAcc - prevAcc);
		derivativeBuffer[derivativeIndex] = derivative;
		derivativeIndex = (derivativeIndex+1)%derivativeBufferSize;
		
		if(derivativeBuffer.length == derivativeBufferSize)
		{
			// step calculation
			// 3. find zero passing point from positive to negative 
			Log.v("Derivative","Derivative: "+ derivativeBuffer[0]);
			if(derivativeBuffer[0] > ZERO_UP && Math.abs(derivativeBuffer[1]) < ZERO_UP && derivativeBuffer[2] < -ZERO_UP)
			//if(derivativeBuffer[0] > ZERO_UP && derivativeBuffer[1] < -ZERO_UP)
				steps++;
		}
		prevAcc = currentAcc;
		
	}
	

	public int getSteps()
	{
		return steps;
	}
	
	final float caloriePerStep = 0.05f;
	
	public float getCalorie()
	{
		calorieBurn = steps * caloriePerStep;
		return calorieBurn;
	}
	
	
	public void Reset()
	{
		steps = 0;
		calorieBurn = 0;
	}
	
}
