package lastone;

import com.lastone.lastoneGen.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StartActivity extends Activity {

	//variables for dealing with input fields
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private EditText editTextParticleMax;
	private EditText editTextParticleOnTouch;
	private EditText editTextParticleLifetime;
	private int particleSizeToPass;
	private int particleMaxToPass;
	private int particleOnTouchToPass;
	private int particleLifetimeToPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}
	//called everytime start button is hit, check for any input errors then launch LastOne activity
	public void startLastOneActivity(View view){
	
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
		
		if(radioButton == null){
			Toast.makeText(this, "Check a particle Size!", Toast.LENGTH_SHORT).show();
			return;
		}else if(radioButton == (RadioButton) findViewById(R.id.particle_small)){
			particleSizeToPass = 1;
		}else if(radioButton == (RadioButton) findViewById(R.id.particle_medium)){
			particleSizeToPass = 2;
			
		}else if(radioButton == (RadioButton) findViewById(R.id.particle_large)){
			particleSizeToPass = 3;
		}else if(radioButton == (RadioButton) findViewById(R.id.particle_XL)){
			particleSizeToPass = 4;
		}
		

		editTextParticleMax = (EditText) findViewById(R.id.editTextParticleMax);
		if(editTextParticleMax.getText() == null){
			Toast.makeText(this, "Check particle count value!", Toast.LENGTH_SHORT).show();
			return;
		} else if(editTextParticleMax.getText().length() > 0){
			particleMaxToPass = Integer.parseInt(editTextParticleMax.getText().toString());
			if(particleMaxToPass < 275){
				particleMaxToPass = 275;
			}
		}
		
		editTextParticleOnTouch = (EditText) findViewById(R.id.editTextParticleOnTouch);
		if(editTextParticleOnTouch.getText() == null){
			Toast.makeText(this, "Check particles on touch value", Toast.LENGTH_SHORT).show();
			return;
		}else if(editTextParticleOnTouch.getText().length() > 0){
			particleOnTouchToPass = Integer.parseInt(editTextParticleOnTouch.getText().toString());
			if ( particleOnTouchToPass < 275){
				particleOnTouchToPass = 275;
			}
		}

		
		
		editTextParticleLifetime = (EditText) findViewById(R.id.editTextParticleLifetime);
		if(editTextParticleLifetime.getText() == null){
			Toast.makeText(this, "Check particle Lifetime", Toast.LENGTH_SHORT).show();
			return;
		}else if(editTextParticleLifetime.getText().length() > 0){
			particleLifetimeToPass = Integer.parseInt(editTextParticleLifetime.getText().toString());
			particleLifetimeToPass = 15;
		}
		
		Intent intent = new Intent(this,LastOne.class);
		intent.putExtra("particleSize", particleSizeToPass);
		intent.putExtra("particleCount", particleMaxToPass);
		intent.putExtra("particleOnTouch", particleOnTouchToPass);
		intent.putExtra("particleLifetime", particleLifetimeToPass);
		startActivity(intent);
	
	}
}
