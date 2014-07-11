/*
  This script provides features for the home page.
  TODO more explanation
*/

// CONSTANTS
var MILLIS_TO_SECS = 1000;

"use strict";

/*
  Main start script
*/
$(document).ready(function() {
    startSlideShow();
    test();
});

/* 
  start slideshow animation
*/
var startSlideShow = function() {
    var slideshow = $("#slideshow img");
    var properties = {
        "margin-left": "+=16px"
    };
    var options = {
        duration: 600,
        complete: function() {
            slideshow.animate({ "margin-left": "-=16px", duration:600 });
            setTimeout(function() {
                startSlideShow();
            }, 5 * MILLIS_TO_SECS);
        }
    };
    slideshow.animate(properties, options);
};

/*
  Show the spotify music player with some swag
*/
var showMusicWithSwag = function() {

};

/*
  Test button FIXME remove
*/
var test = function() {
    $("#test").submit(function(evnt) {
        evnt.preventDefault();
        var uri = "/pass/admin/reset";
        var data = JSON.stringify({
            "hi": "ho"
        });
        var callback = function(response) {
            alert(response);
        };
        $.post(uri, data, callback);
    });
};
