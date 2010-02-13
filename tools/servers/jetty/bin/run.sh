#!/bin/sh

if [ ! $JAVA_HOME ]
then
	echo JAVA_HOME not defined.
	exit
fi

JAVA_OPTS="-Dfile.encoding=UTF8 -DSTART=../etc/start.config -Duser.timezone=GMT -Xmx1024m -XX:MaxPermSize=256m"

$JAVA_HOME/bin/java $JAVA_OPTS -jar ../start.jar ../etc/jetty.xml