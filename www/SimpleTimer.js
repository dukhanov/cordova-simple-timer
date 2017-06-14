var exec = require('cordova/exec');

var SimpleTimer = {
    isStarted: false,
    start: function (callback, error, config) {
        if (this.isStarted) {
            console.warn('SimpleTimer is already started');
        }
        this.isStarted = true;
        callback = callback || function() {};
        error = error || function() {};
        exec(callback, error, 'SimpleTimer', 'start', [config]);
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