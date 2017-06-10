var exec = require('cordova/exec');

var SimpleTimer = {
    isStarted: false,
    start: function (callback, error) {
        if (this.isStarted) {
            console.warn('SimpleTimer is already started');
        }
        this.isStarted = true;
        callback = callback || function() {};
        error = error || function() {};
        exec(callback, error, 'SimpleTimer', 'start', []);
    },
    stop: function (success, error) {
        if (!this.isStarted) {
            console.warn('SimpleTimer is already stopped');
        }
        this.isStarted = false;
        success = success || function() {};
        error = error || function() {};
        exec(success, error, 'SimpleTimer', 'stop', []);
    }
};

module.exports = SimpleTimer;