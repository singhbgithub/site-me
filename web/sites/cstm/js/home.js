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
  FIXME this function belongs in another file
*/
var test = function() {
    $("#test").submit(function(evnt) {
        evnt.preventDefault();
        var uri = "/pass/admin/create";
        var user = $("#test input[name='user']").val();
        var password = $("#test input[name='p1']").val(); // TODO add pass check
        var email = $("#test input[name='email']").val();
        var data = JSON.stringify({
            "username": user,
            "password":password,
            "email":email
        });
        var callback = function(response) {
            alert(response);
            uri = "/pass/admin/delete";
            $.post(uri, data, function(response) { alert(response); } );
        };
        $.post(uri, data, callback);
    });
};
