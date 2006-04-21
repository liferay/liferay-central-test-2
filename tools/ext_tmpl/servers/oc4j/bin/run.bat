@echo off

if "" == "%JAVA_HOME%" goto errorJavaHome

%JAVA_HOME%/bin/java -Xmx512m -Dfile.encoding=UTF-8 -Duser.timezone=GMT -jar ../oc4j/j2ee/home/oc4j.jar -config ../config/server.xml -userThreads

goto end

:errorJavaHome
	echo JAVA_HOME not defined.

	goto end

:end