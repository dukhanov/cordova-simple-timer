var exec = require('cordova/exec');

function SimpleTimer() {
}

SimpleTimer.prototype.start = function (callback, success, error) {
    if (this.isStarted) {
        console.warn('SimpleTimer is already started');
    }
    this.isStarted = true;
    callback = callback || function() {};
    success = success || function() {};
    error = error || function() {};
    exec(success, error, 'SimpleTimer', 'start', []);
};

SimpleTimer.prototype.stop = function (success, error) {
    if (!this.isStarted) {
        console.warn('SimpleTimer is already stopped');
    }
    this.isStarted = false;
    success = success || function() {};
    error = error || function() {};
    exec(success, error, 'SimpleTimer', 'stop', []);
};

module.exports = SimpleTimer;