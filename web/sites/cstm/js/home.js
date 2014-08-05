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
    validateLogin();
    registerUser();
    unregisterUser();
    updateUser();
    startSlideShow();
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
  Validate user credentials. Load content is valid.
*/
var validateLogin = function() {
    $("#login").submit(function(event) {
       var uri = "/pass/admin/login";
       var user = $("#login input[name='user']").val();
       var password = $("#login input[name='password']").val();
       var email = $("#login input[name='email']").val();
       var data = {
         "username": user,
         "password": password,
         "email": email 
       };
       var callback = function(response) {
           alert(response);
       };
       $.post(uri, data, callback);
    });
};

/*
  Register new user.
*/
var registerUser = function() {
    $("#register").submit(function(evnt) {
        evnt.preventDefault();
        var uri = "/pass/admin/create";
        var user = $("#register input[name='user']").val(); // TODO change to this instead of #register
        var password = $("#register input[name='p1']").val();
        var password2 = $("#register input[name='p2']").val();
        // passwords don't match
        if (password !== password2) {
            alert("Passwords do not match.");
        }
        else {
            var email = $("#register input[name='email']").val();
            var data = JSON.stringify({
                "username": user,
                "password": password,
                "email": email
            });
            var callback = function(response) {
                alert(response);
            };
            $.post(uri, data, callback);
        }
    });
};

/*
  Unregister an existing user.
*/
var unregisterUser = function() {
    $("#unregister").submit(function(evnt) {
        evnt.preventDefault();
        var uri = "/pass/admin/delete";
        var user = $("#unregister input[name='user']").val();
        var password = $("#unregister input[name='password']").val(); // TODO add pass check
        var email = $("#unregister input[name='email']").val();
        var data = JSON.stringify({
            "username": user,
            "password": password,
            "email": email
        });
        var callback = function(response) {
            alert(response);
        };
        $.post(uri, data, callback);
    });
};

/*
  Update user account information.
*/
var updateUser = function() {
    $("#updatelogin").submit(function(evnt) {
        evnt.preventDefault();
        var uri = "/pass/admin/update";
        var user = $("#updatelogin input[name='user']").val();
        var password = $("#updatelogin input[name='password']").val();
        var newpassword = $("#updatelogin input[name='newpassword']").val();
        var email = $("#updatelogin input[name='email']").val();
        var newemail = $("#updatelogin input[name='newemail']").val();
        var data = JSON.stringify({
            "username": user,
            "password": password,
            "email": email,
            "newpassword": newpassword,
            "newemail": newemail
        });
        var callback = function(response) {
            alert(response);
        };
        $.post(uri, data, callback);
    });
};
