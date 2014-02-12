#!/bin/sh

### chattyguard
if [ -f ./chattyguard.pid ];
then
echo ""
else
echo "#########################"
echo "chattyguard already stopped"
echo "#########################"
exit; 
fi
pid=0;
pid=`cat ./chattyguard.pid`  
echo "#########################"
echo "Stop chattyguard, pid:$pid"
echo "#########################"
kill $pid
rm -r ./chattyguard.pid


