# Watchdog Nodejs Client 
Watchdog is a simple loging project  
you'll need nodejs for this project aswell as linux to run logger.sh

## logger
this component is made of two parts

### log.sh
creates new lines in 'log.log' with a delay  
We can use this file to emulate some program that creates logs

### index.js
this program get updates from log.log and makes an (axios) http post request to localhost 8080

This file requires packages
- axios
- tailing-stream
to install run ```npm i``` 
to start run ```node index```