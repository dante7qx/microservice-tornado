#!/bin/bash

SIGNAL=0
while [[ $SIGNAL != 1 ]]
do
	STATUS=`curl -I -m 10 -o /dev/null -s -w %{http_code} configserver:8888`
	echo "Config STATUS=$STATUS"
if [ "$STATUS" != "000" ]; then
		SIGNAL=1
		echo "Config server start ok!"
		java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar
	else
		echo "wait for 5s, please waiting....."
		sleep 5s 
	fi
done