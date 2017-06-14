package com.dukhanov.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class SimpleTimer extends CordovaPlugin {

	protected static final String TAG = "simpletimer";
	private static final String ACTION_START = "start";
    private static final String ACTION_STOP = "stop";
	private static final String TIMER_EVENT_ACTION = "com.dukhanov.cordova.TIMER_ACTION";
	private static final int TIMER_INTERVAL_DEFAULT = 60 * 1000; // 60 seconds
	private static final int TIMER_INTERVAL_MINIMUM = 30 * 1000; // 30 seconds
	private static final String CONFIG_INTERVAL_NAME = "interval";

	private CallbackContext timerCallback;
	private AlarmManager mAlarmManager;
	private Context mContext;
	private PendingIntent mAlarmIntent;
	private int mTimerInterval = TIMER_INTERVAL_DEFAULT; // default value

	// adb logcat -s simpletimer
	@Override
    protected void pluginInitialize() {
		Log.v(TAG, "init");
		mAlarmManager = (AlarmManager) cordova.getActivity().getSystemService(Context.ALARM_SERVICE);
		mContext = cordova.getActivity().getApplicationContext();

		mAlarmIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(TIMER_EVENT_ACTION), 0);
    }

	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		Log.v(TAG, "execute(" + action + ")");

		boolean result = false;

		if (ACTION_START.equalsIgnoreCase(action)) {
            result = true;
			JSONObject config = data.optJSONObject(0);
			if (config != null) {
				mTimerInterval = config.optInt(CONFIG_INTERVAL_NAME, TIMER_INTERVAL_DEFAULT);
				if (mTimerInterval < TIMER_INTERVAL_MINIMUM) {
					Log.w(TAG, "startTimer, interval is less than minimum value, use minimun value instead: " + TIMER_INTERVAL_MINIMUM);
					mTimerInterval = TIMER_INTERVAL_MINIMUM;
				}
			}

            startTimer(callbackContext);
        } else if (ACTION_STOP.equals(action)) {
            result = true;
            stopTimer(callbackContext);
        }

		return result;
	}

	private void startTimer(CallbackContext cb) {
		Log.v(TAG, "startTimer, interval: " + mTimerInterval);

		mContext.registerReceiver(timerReceiver, new IntentFilter(TIMER_EVENT_ACTION));
		timerCallback = cb;
		runTimer(mTimerInterval);
	}

	private void stopTimer(CallbackContext cb) {
		Log.v(TAG, "stopTimer");

		clearReceiver();
		cb.success();
	}

	private void runTimer(long alarmInterval) {
		Calendar cal = Calendar.getInstance();
		long alarmTime = cal.getTimeInMillis() + alarmInterval;

		int ALARM_TYPE = AlarmManager.RTC_WAKEUP;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(alarmTime, mAlarmIntent);
			mAlarmManager.setAlarmClock(alarmClockInfo, mAlarmIntent);
		}else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			mAlarmManager.setExact(ALARM_TYPE, alarmTime, mAlarmIntent);
		}else {
			mAlarmManager.set(ALARM_TYPE, alarmTime, mAlarmIntent);
		}
	}

	private void clearReceiver() {
		timerCallback =  null;
		mContext.unregisterReceiver(timerReceiver);
		mAlarmManager.cancel(mAlarmIntent);
	}

	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		clearReceiver();
	}

	private BroadcastReceiver timerReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "timer tick");
			if (timerCallback != null) {
				PluginResult result = new PluginResult(PluginResult.Status.OK);
				result.setKeepCallback(true);
				timerCallback.sendPluginResult(result);
			}
			runTimer(mTimerInterval);
		}
	};
}
