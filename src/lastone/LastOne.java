package lastone;

import com.lastone.lastoneGen.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;
import android.hardware.Sensor;


public class LastOne extends Activity implements SensorEventListener{
	
	/*Surfaceview and renderer, renderer will need
	 * context so it can get correct sensor and touch input*/
	private GLSurfaceView glSurfaceView;
	private boolean rendererSet = false;
	private LastOneRenderer lastOneRenderer;
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	private int particleSize;
	private int particleOnTouch;
	private int particleCount;
	private int particleLifetime;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		glSurfaceView = new GLSurfaceView(this);
		//get user variables for simulation
		particleCount = getIntent().getIntExtra("particleCount", 275);
		particleSize = getIntent().getIntExtra("particleSize", 4);
		particleOnTouch = getIntent().getIntExtra("particleOnTouch", 275);
		particleLifetime = getIntent().getIntExtra("particleLifetime", 20);
		
		lastOneRenderer = new LastOneRenderer(this, particleCount, particleSize, particleOnTouch, particleLifetime);
		
		
		//hide status and action bar
		if(Build.VERSION.SDK_INT < 16){
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else{
		 
		
		
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		}
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		setContentView(R.layout.activity_last_one);
		
		final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
		
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
		

		if(supportsEs2){
			glSurfaceView.setEGLContextClientVersion(2);
			
			glSurfaceView.setRenderer(lastOneRenderer);
			rendererSet = true;
		} else{
			Toast.makeText(this, "This device does not support openGLES 2", Toast.LENGTH_LONG).show();
			return;
		}
		
	
		setContentView(glSurfaceView);
		//code for initializing gyroscope have to acquire world becuase it
		//sometimes causes null pointer exception when sensor attempts to access world
		//before it has been defined
		lastOneRenderer.getWorld();
		try{
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensor, SENSOR_DELAY_NORMAL);
		}finally{
			lastOneRenderer.releaseWorld();
		}
		
	}
	
	static{
		System.loadLibrary("liquidfun");
		System.loadLibrary("liquidfun_jni");
		
	}
	
	public void onSensorChanged(SensorEvent event){
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
		float axisX = event.values[0];
		float axisY = event.values[1];
		lastOneRenderer.handleRotation(axisX, axisY);
		}
	}
	
	protected void onPause(){
		super.onPause();
		mSensorManager.unregisterListener(this);
		if(rendererSet){
			glSurfaceView.onPause();
			lastOneRenderer.endSimulation();
		}
	}
	
	protected void onResume(){
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SENSOR_DELAY_NORMAL);

		if(rendererSet){
			glSurfaceView.onResume();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public boolean onTouchEvent(MotionEvent e){
		float x = e.getX();
		float y = e.getY();
		
		return lastOneRenderer.handleTouchEvent(x,y);
	}
}
