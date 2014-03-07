Chattyguard
===========

##Smart Thermometer with VoIP interface on Raspberry Pi
![Architecture](http://www.rafalwarno.pl/wp-content/uploads/2014/03/chattyguard-600x350.gif)

###Functionality:
- reads & monitors the actual temperature 
- receives the VoIP calls and informs about actual measured temperature 
- triggers the VoIP call alarms in case of special conditions met: exceeding lower/upper bounds of measured temperature

###Prerequisites to assemble functioning device:
- Hardware: Raspberry Pi ARM board or similar
- Sensors: Thermometer DS1820
- Software stack: Linux Raspbian distro, Asterisk for VoIP interface, Java SE 1.7 embedded
- Chattyguard middleware - Apache Camel based integration module


