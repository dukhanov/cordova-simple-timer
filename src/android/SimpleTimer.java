package com.dukhanov.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class SimpleTimer extends CordovaPlugin {

	protected static final String TAG = "simpletimer";
	private static final String ACTION_START = "start";
    private static final String ACTION_STOP = "stop";

	IntentFilter mTimerIntentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
	private CallbackContext timerCallback;

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
            startTimer(callbackContext);
        } else if (ACTION_STOP.equals(action)) {
            result = true;
            stopTimer(callbackContext);
        }

		return result;
	}

	private void startTimer(CallbackContext cb) {
		Log.v(TAG, "startTimer");

		timerCallback = cb;
		PluginResult result = new PluginResult(PluginResult.Status.OK);
		result.setKeepCallback(true);
		cb.sendPluginResult(result);

        cordova.getActivity().getApplicationContext().registerReceiver(timerReceiver, mTimerIntentFilter);
	}

	private void stopTimer(CallbackContext cb) {
		Log.v(TAG, "stopTimer");

		clearReceiver();
		cb.success();
	}

	private void clearReceiver() {
		timerCallback =  null;
		cordova.getActivity().getApplicationContext().unregisterReceiver(timerReceiver);
	}

	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		clearReceiver();
	}

	BroadcastReceiver timerReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "******** timer tick **********");
			if (timerCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK);
				result.setKeepCallback(true);
				timerCallback.sendPluginResult(result);
			}
		}
	};
}
