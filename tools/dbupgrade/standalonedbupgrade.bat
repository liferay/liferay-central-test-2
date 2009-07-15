@echo off

set JRE_CLASSPATH=
set LIFERAY_CLASSPATH=

rem
rem Add JRE libraries
rem
set JRE_LIB_HOME="%JAVA_HOME%\jre\lib"
set JRE_CLASSPATH=%JRE_LIB_HOME%\charsets.jar;%JRE_LIB_HOME%\deploy.jar;%JRE_LIB_HOME%\javaws.jar;%JRE_LIB_HOME%\jce.jar;%JRE_LIB_HOME%\jsse.jar;%JRE_LIB_HOME%\rt.jar;%JRE_LIB_HOME%\ext\dnsns.jar;%JRE_LIB_HOME%\ext\localedata.jar;%JRE_LIB_HOME%\ext\sunjce_provider.jar;%JRE_LIB_HOME%\ext\sunpkcs11.jar;

rem
rem Add all jar files found in LIB_HOME to classpath
rem

for %%F in (lib\*.jar) do call :buildClasspath %%F

set LIFERAY_CLASSPATH=classes;%JRE_CLASSPATH%;%LIFERAY_CLASSPATH%

java -Djava.security.policy=classes\everything.policy -Xmx1024m -Xms256m -XX:MaxPermSize=128m -cp %LIFERAY_CLASSPATH% com.liferay.portal.tools.upgrade.StandaloneDBUpgrader

goto :eof

:buildClasspath
set LIFERAY_CLASSPATH=%LIFERAY_CLASSPATH%;%1

:eof
