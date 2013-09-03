package com.monkeydriver.uxpaper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((Button)findViewById(R.id.button_submit)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validateForm()) {
					launchPaperActivity();
				} else {
					Toast.makeText(getBaseContext(), "Error placeholder", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private boolean validateForm() {
		boolean isValid = true;
		
		EditText eProject = (EditText)findViewById(R.id.e_project);
		EditText ePin = (EditText)findViewById(R.id.e_pin);
		
		String tProject = eProject.getText().toString();
		String tPin = ePin.getText().toString();
		
		if(tProject.length() < 1) {
			isValid = false;
		}
		if(tPin.length() < 1) {
			isValid = false;
		}
		
		return isValid;
	}
	
	private void launchPaperActivity() {
		Intent intent = new Intent(this, PaperActivity.class);
		intent.putExtra("url", "http://www.duckduckgo.com");
		startActivity(intent);
	}

}
