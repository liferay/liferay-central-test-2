#!/bin/sh
export JRE_CLASSPATH=
export LIFERAY_CLASSPATH=

# Adding JRE libraries
export JRE_LIB_HOME=${JAVA_HOME}/jre/lib
export JRE_CLASSPATH=${JRE_LIB_HOME}/charsets.jar:${JRE_LIB_HOME}/deploy.jar:${JRE_LIB_HOME}/javaws.jar:${JRE_LIB_HOME}/jce.jar:${JRE_LIB_HOME}/jsse.jar:${JRE_LIB_HOME}/rt.jar:${JRE_LIB_HOME}/ext/dnsns.jar:${JRE_LIB_HOME}/ext/localedata.jar
:${JRE_LIB_HOME}/ext/sunjce_provider.jar:${JRE_LIB_HOME}/ext/sunpkcs11.jar:

for filename in lib/*.jar
do
export LIFERAY_CLASSPATH=${LIFERAY_CLASSPATH}:$filename
done

export LIFERAY_CLASSPATH=classes:${JRE_CLASSPATH}:${LIFERAY_CLASSPATH}:

java -Djava.security.policy=classes/everything.policy -Xmx1024m -Xms256m -XX:MaxPermSize=128m -cp %LIFERAY_CLASSPATH% com.liferay.portal.tools.upgrade.StandaloneDBUpgrader
