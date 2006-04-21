#!/bin/sh

if [ ! $JAVA_HOME ]
then
	echo JAVA_HOME not defined.
	exit
fi

$JAVA_HOME/bin/java -Xmx512m -Dfile.encoding=UTF-8 -Duser.timezone=GMT -jar ../orion/orion.jar -config ../config/server.xml -userThreads
