#!/bin/bash

# Exit on error
set -e

# This script starts the application server,
# the mysql server, and nginx server.
# TODO is pgrep portable? ps aux | grep instead? 
USAGE="Usage: $0 start|stop"



# Subroutine to run a process if it is not already running.
# Misuse of this subroutine are caught silently.
# $1: the name of the process to run
# $2: the command to run the process
start() {
  if [ $# -eq 2 ]; then
    # pgrep exit 0, process is running
    if pgrep -q $1; then
      echo "$1 is already running."
    # pgrep exit 1, process is not running
    elif [ $? -eq 1 ]; then
      $2 > /dev/null # run $2 command
      echo "$1 started."
   # pgrep error occurred
    else 
      echo "Error starting: $1"
    fi
  fi
}

# Subroutine to stop a process if it is running.
# Misuse of this subroutine are caught silently.
# $1: the name of the process to stop
# $2: the command to stop the process
stop() {
  if [ $# -eq 2 ]; then
    # pgrep exit 0, process is running
    if pgrep -q $1; then
      $2 > /dev/null
      echo "$1 stopped."
    # pgrep exit 1, process is not running
    elif [ $? -eq 1 ]; then
      echo "$1 is not running."
   # pgrep error occurred
    else 
      echo "Error starting: $1"
    fi
  fi
}



# TODO Are the start and stop subroutines more readable as two subroutines, or
# should they be abstracted via parameterization?



# require command line args
if [ $# -lt 1 ]; then
  echo $USAGE
  exit 0
fi

# run the required processes
if [ $1 = "start" ]; then
  start mysql "sudo mysql.server start"
  start nginx "sudo nginx"
  #run application.jar "sudo start this thing" TODO
# stop the required processes
elif [ $1 = "stop" ]; then
  stop mysql "sudo mysql.server stop"
  stop nginx "sudo nginx -s stop"
  #stop application.jar "sudo killall application.jar" TODO
else
  echo "$1 is not a valid option."
  echo $USAGE
fi

