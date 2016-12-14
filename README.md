# SocketCommunication
Code for our socket communication program
This will be the design of the system once completed...

Server will be the off board processor that is on the robot.
Clients will be the rio and the driver station. 
Rio will recieve values about targets from the camera.
Driver Station will recieve robot stats.

Protocol:
Data Sent in form "ID" : "data"
Input is split to give ID value and data.
 
Switch(ID) {
Case ID: 
New case for each type of data
Interpret and do something with data
}

Separate file with all IDs and meanings for reference