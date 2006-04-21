#!/bin/sh

if [ ! $JAVA_HOME ]
then
	echo JAVA_HOME not defined.
	exit
fi

$JAVA_HOME/bin/java -Xmx512m -Dfile.encoding=UTF-8 -Duser.timezone=GMT -jar ../oc4j/j2ee/home/oc4j.jar -config ../config/server.xml -userThreads
