#!/bin/sh

### chattyguard plugin
if [ -f ./chattyguard.pid ];
then
echo "#########################"
echo "chattyguard already started"
echo "#########################"
exit; 
fi
nohup java -Dlog4j.configuration=file:../cfg/log4j.xml -Dorg.apache.camel.jmx.rmiConnector.registryPort=1899 -jar ../lib/chattyguard-plugin-1.0.0.jar  > /dev/null &
PID=$!
echo $PID > "./chattyguard.pid"


