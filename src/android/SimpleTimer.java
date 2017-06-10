package com.dukhanov.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class SimpleTimer extends CordovaPlugin {

	protected static final String TAG = "simpletimer";
	private static final String ACTION_START = "start";
    private static final String ACTION_STOP = "stop";

	// adb logcat -s simpletimer
	@Override
    protected void pluginInitialize() {
		Log.v(TAG, "init");
    }

	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		Log.v(TAG, "execute(" + action + ")");

		boolean result = false;

		if (ACTION_START.equalsIgnoreCase(action)) {
            result = true;
            // startTimer(callbackContext);
        } else if (ACTION_STOP.equals(action)) {
            result = true;
            // stopTimer(callbackContext);
        }

		return result;
	}
	
}
