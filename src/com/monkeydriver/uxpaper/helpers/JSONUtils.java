package com.monkeydriver.uxpaper.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONUtils {
	
	/**
	 * Converts and returns JSONObject from String.
	 * @param str
	 * @return
	 */
	public static JSONObject getJsonFromString(String str) {
		
		JSONObject jsonObject = null;
		
		try {
			jsonObject = new JSONObject(str);
			return jsonObject;
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns the string value of a property in the top level of a JSON object
	 * @param jsonData
	 * @param prop
	 * @return
	 */
	public static String getStringFromJSON(JSONObject jsonData, String prop) {
		//Log.d(LOG_TAG, "# getStringFromJSON : "+prop);
		//Log.d(LOG_TAG, "@ jsonData = "+jsonData);
		
		String str = null;
		
		if(jsonData.has(prop)) {
			try {
				str = jsonData.getString(prop);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		
		return str;
	}
	
	/**
	 * Returns the string value of a property in a nested JSON object
	 * @param jsonData
	 * @param prop
	 * @param nestName
	 * @return
	 */
	public static String getStringFromJSON(JSONObject jsonData, String prop, String nestName) {
		String str = null;
		JSONObject nestJson = null;
		
		try {
			nestJson = jsonData.getJSONObject(nestName);
			str = getStringFromJSON(nestJson, prop);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return str;
	}
}
