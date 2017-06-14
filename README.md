# cordova-simple-timer

Simple timer plugin which uses Android AlarmManager

## Installation
```
$ cordova plugin add https://www.github.com/dukhanov/cordova-simple-timer
```
### Android

cordova build android

## Plugin API

It has been currently stripped to the minimum needed from a Javascript app.

The following functions are available:

* `SimpleTimer.start(listener, error, config)`. Start timer with 1 minute interval (by default)
  * listener: called on timer tick
  * error: called on function error
  * config: plugin config
  * config.interval: timer interval in milliseconds (minimum is 30000)
* `SimpleTimer.stop(success, error)`. Stop timer
  * success: called on function success
  * error: called on function error

## Sample usage code
```Javascript
function onTimerTick() {
    console.log('timer tick');
}

function errorStart(message) {
    console.log('timer start failed: ' + message);
}

function onStopped() {
    console.log('timer is stopped');
}

var config = {
    interval: 60000 // 60 seconds
}

// Start
SimpleTimer.start(onTimerTick, errorStart, config);

// Stop
SimpleTimer.stop(onStopped)
```
