@ECHO OFF

@REM WARNING: This file is created by the Configuration Wizard.
@REM Any changes to this script may be lost when adding extensions to this configuration.

SETLOCAL

@REM *************************************************************************
@REM This script is used to start WebLogic Server for the domain in the
@REM current working directory.  This script simply sets the SERVER_NAME
@REM variable and starts server.
@REM 
@REM To create your own start script for your domain, all you need to set is
@REM SERVER_NAME, then starts the server.
@REM 
@REM Other variables that startWLS takes are:
@REM 
@REM WLS_USER     - cleartext user for server startup
@REM WLS_PW       - cleartext password for server startup
@REM PRODUCTION_MODE    - true for production mode servers, false for
@REM                development mode
@REM JAVA_OPTIONS - Java command-line options for running the server. (These
@REM                will be tagged on to the end of the JAVA_VM and MEM_ARGS)
@REM JAVA_VM      - The java arg specifying the VM to run.  (i.e. -server,
@REM                -hotspot, etc.)
@REM MEM_ARGS     - The variable to override the standard memory arguments
@REM                passed to java
@REM 
@REM For additional information, refer to the WebLogic Server Administration
@REM Console Online Help(http:\\e-docs.bea.com\wls\docs81\ConsoleHelp\startstop.html)
@REM *************************************************************************

@REM Initialize the common environment.

set WL_HOME=C:\Program Files\BEA\weblogic81
for %%i in ("%WL_HOME%") do set WL_HOME=%%~fsi

set PRODUCTION_MODE=

set JAVA_VENDOR=BEA

set JAVA_HOME=C:\Program Files\BEA\jrockit81sp4_142_05
for %%i in ("%JAVA_HOME%") do set JAVA_HOME=%%~fsi

@REM Call commEnv here AFTER setting the java_vendor to get common environmental settings.

call "%WL_HOME%\common\bin\commEnv.cmd"

@REM Set SERVER_NAME to the name of the server you wish to start up.

set SERVER_NAME=myserver

set CLASSPATH=%WEBLOGIC_CLASSPATH%;%POINTBASE_CLASSPATH%;%JAVA_HOME%\jre\lib\rt.jar;%WL_HOME%\server\lib\webservices.jar;%CLASSPATH%

set LOCALCLASSPATH=
for %%i in ("lib\*.jar") do call "lcp.bat" %%i
set CLASSPATH=%CLASSPATH%;%LOCALCLASSPATH%

set MEM_ARGS=-Xmx512m

@REM Call WebLogic Server

echo .
echo CLASSPATH=%CLASSPATH%
echo .
echo PATH=%PATH%
echo .
echo ***************************************************
echo *  To start WebLogic Server, use a username and   *
echo *  password assigned to an admin-level user.  For *
echo *  server administration, use the WebLogic Server *
echo *  console at http:\\[hostname]:[port]\console    *
echo ***************************************************

%JAVA_HOME%\bin\java %JAVA_VM% %MEM_ARGS% %JAVA_OPTIONS% -Dweblogic.Name=%SERVER_NAME% -Dweblogic.ProductionModeEnabled=%PRODUCTION_MODE% -Djava.security.policy="%WL_HOME%\server\lib\weblogic.policy" weblogic.Server



ENDLOCAL