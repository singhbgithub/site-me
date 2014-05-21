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
    centerDOM();
    startSlideShow();
});

var centerDOM = function() {
    $(".center").css({
       "margin-left":"auto",
        "margin-right":"auto",
        "color":"blue"
    });
};

var startSlideShow = function() {
    var slideshow = $("#slideshow p");
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
