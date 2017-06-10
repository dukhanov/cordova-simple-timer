# cordova-simple-timer

Simple timer plugin which uses Android Broadcast action: ACTION_TIME_TICK

## Installation
```
$ cordova plugin add https://www.github.com/dukhanov/cordova-simple-timer
```
### Android

cordova build android

## Plugin API

It has been currently stripped to the minimum needed from a Javascript app.

The following functions are available:

* `SimpleTimer.start(listener, error)`. Start timer with 1 minut interval
  * listener: called on timer tick
  * error: called on function error
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

// Start
GpsStatus.start(onTimerTick, errorStart);

// Stop
GpsStatus.stop(onStopped)
```
