"use strict";
var $window = $(window);
var $dependent = $(".dependent");
$(document).ready(function() {
 init();
 setWindowDependents();
});

var init = function() {
 setWindowDependentSize();
};

var setWindowDependents = function() {
 $window.resize(function(event) {
  setWindowDependentSize();
 });
};

/*  set window resize */
var setWindowDependentSize = function() {
 var widthRatio = $(window).width() / 800;
 var adjHeight = 252 * widthRatio;
 $dependent.css({
  "height": adjHeight,
  "width": $window.width()
 });
};
