package com.monkeydriver.uxpaper;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.monkeydriver.APIHelpers.APIHelper;
import com.monkeydriver.uxpaper.helpers.JSONUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private final String API_URL = "http://www.monkeydriver.com/uxpaper/index.php";
	private final String FILTER_NAME = this.getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((Button)findViewById(R.id.button_submit)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validateForm()) {
//					attemptLogin();
					launchPaperActivity("{\"status\":\"success\", \"url\":\"http://www.duckduckgo.com\"}");
				} else {
					Toast.makeText(getBaseContext(), "Error placeholder", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(FILTER_NAME));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
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
	
	private void attemptLogin() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("project_name", ((EditText)findViewById(R.id.e_project)).getText().toString()));
		params.add(new BasicNameValuePair("pin", ((EditText)findViewById(R.id.e_pin)).getText().toString()));
		
		APIHelper.apiPost(this, FILTER_NAME, API_URL, params, false);
	}
	
	private void launchPaperActivity(String jsonString) {
		JSONObject jsonObject = JSONUtils.getJsonFromString(jsonString);
		
		Intent intent = new Intent(this, PaperActivity.class);
//		intent.putExtra("url", "http://www.duckduckgo.com");
		intent.putExtra("url", JSONUtils.getStringFromJSON(jsonObject, "url"));
		startActivity(intent);
	}
	
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String msg = intent.getStringExtra(APIHelper.EXTRA_MESSAGE);
			
			if(msg.equals(APIHelper.MSG_SUCCESS)) {
				Log.d(getLocalClassName(), "! Success");
				launchPaperActivity(APIHelper.EXTRA_JSON);
			} else if(msg.equals(APIHelper.MSG_NO_CONNECTIVITY)) {
				Log.d(getLocalClassName(), "! No connectivity");
			} else if(msg.equals(APIHelper.MSG_HTTP_FAILURE)){
				Log.d(getLocalClassName(), "! HTTP failure");
			} else if(msg.equals(APIHelper.MSG_API_FAILURE)) {
				Log.d(getLocalClassName(), "! API failure");
			} else if(msg.equals(APIHelper.MSG_JSON_FAILURE)) {
				Log.d(getLocalClassName(), "! JSON failure");
			}
		}
		
	};

}
