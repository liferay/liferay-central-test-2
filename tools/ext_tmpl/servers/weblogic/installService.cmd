@rem *************************************************************************
@rem This script is used to install WebLogic Server as a service for the 
@rem domain in the current working directory.  
@rem
@rem To create your own domain script, all you need to set is 
@rem SERVER_NAME, then call %WL_HOME%\server\bin\installSvc.cmd
@rem
@rem Other variables that installService takes are:
@rem
@rem WLS_USER     - cleartext user for server startup
@rem WLS_PW       - cleartext password for server startup
@rem PRODUCTION_MODE    - true for production mode servers, false for 
@rem                development mode
@rem JAVA_OPTIONS - Java command-line options for running the server. (These
@rem                will be tagged on to the end of the JAVA_VM and MEM_ARGS)
@rem JAVA_VM      - The java arg specifying the VM to run.  (i.e. -server, 
@rem                -hotspot, etc.)
@rem MEM_ARGS     - The variable to override the standard memory arguments
@rem                passed to java
@rem
@rem For additional information, refer to the WebLogic Server Administration 
@rem Console Online Help(http://e-docs.bea.com/wls/docs81/ConsoleHelp/startstop.html)
@rem *************************************************************************

echo off
SETLOCAL

set WL_HOME=C:\Program Files\BEA\weblogic81

@rem Set Production Mode.  When this is set to true, the server starts up in
@rem production mode.  When set to false, the server starts up in development
@rem mode.  If it is not set, it will default to false.
set PRODUCTION_MODE=

@rem Set JAVA_VENDOR to java virtual machine you want to run on server side.
set JAVA_VENDOR=BEA

@rem Set JAVA_HOME to java virtual machine you want to run on server side.
set JAVA_HOME=C:\Program Files\BEA\jrockit81sp2_141_05

call "%WL_HOME%\common\bin\commEnv.cmd"


@rem USERDOMAIN_HOME is preset to the domain directory.
set USERDOMAIN_HOME=D:\CVSROOT\liferay\ext\servers\bea\mydomain

@rem Set SERVER_NAME to the name of the server you wish to start up.
set SERVER_NAME=myserver

@rem Set DOMAIN_NAME to the name of the server you wish to start up.
set DOMAIN_NAME=mydomain

@rem Set WLS_USER equal to your system username and WLS_PW equal  
@rem to your system password for no username and password prompt 
@rem during server startup.  Both are required to bypass the startup
@rem prompt.
set WLS_USER=system
set WLS_PW=

if NOT "%1" == "" set WLS_USER=%1
if NOT "%2" == "" set WLS_PW=%2

if "%WLS_USER%" == "" goto usage
if "%WLS_PW%" == ""  goto usage
goto continue

:usage
echo Need to set WLS_USER and WLS_PW environment variables or specify
echo them in command line:
echo Usage: installService.cmd [WLS_USER] [WLS_PW]
echo for example:
echo installService.cmd user password
goto finish

:continue

@rem Set JAVA_OPTIONS to the java flags you want to pass to the vm. i.e.: 
@rem set JAVA_OPTIONS=-Dweblogic.attribute=value -Djava.attribute=value
set JAVA_OPTIONS=

@rem Set JAVA_VM to the java virtual machine you want to run.  For instance:
@rem set JAVA_VM=-server
@rem set JAVA_VM=

@rem Set MEM_ARGS to the memory args you want to pass to java.  For instance:
@rem set MEM_ARGS=-Xms32m -Xmx200m
@rem set MEM_ARGS=


@rem Check that the WebLogic classes are where we expect them to be
:checkWLS
if exist "%WL_HOME%\server\lib\weblogic.jar" goto checkJava
echo The WebLogic Server wasn't found in directory %WL_HOME%\server.
echo Please edit your script so that the WL_HOME variable points 
echo to the WebLogic installation directory.
goto finish

@rem Check that java is where we expect it to be
:checkJava
if exist "%JAVA_HOME%\bin\java.exe" goto runWebLogic
echo The JDK wasn't found in directory %JAVA_HOME%.
echo Please edit your script so that the JAVA_HOME variable 
echo points to the location of your JDK.
goto finish

:runWebLogic

@echo on

set CLASSPATH=%WEBLOGIC_CLASSPATH%;%CLASSPATH%

@echo ***************************************************
@echo *  To start WebLogic Server, use the password     *
@echo *  assigned to the system user.  The system       *
@echo *  username and password must also be used to     *
@echo *  access the WebLogic Server console from a web  *
@echo *  browser.                                       *
@echo ***************************************************

rem *** Set Command Line for service to execute within created JVM

@echo off

if "%ADMIN_URL%" == "" goto runAdmin
@echo on
set CMDLINE="%JAVA_VM% %MEM_ARGS% %JAVA_OPTIONS% -classpath \"%CLASSPATH%\" -Dweblogic.Name=%SERVER_NAME% -Dweblogic.management.username=%WLS_USER% -Dweblogic.management.server=\"%ADMIN_URL%\" -Dweblogic.ProductionModeEnabled=%PRODUCTION_MODE% -Djava.security.policy=\"%WL_HOME%\server\lib\weblogic.policy\" weblogic.Server"
goto installSvc

:runAdmin
@echo on
set CMDLINE="%JAVA_VM% %MEM_ARGS% %JAVA_OPTIONS% -classpath \"%CLASSPATH%\" -Dweblogic.Name=%SERVER_NAME% -Dweblogic.management.username=%WLS_USER% -Dweblogic.ProductionModeEnabled=%PRODUCTION_MODE% -Djava.security.policy=\"%WL_HOME%\server\lib\weblogic.policy\" weblogic.Server"

:installSvc
rem *** Set up extrapath for win32 and win64 platform separately
if not "%WL_USE_64BITDLL%" == "true" set EXTRAPATH=%WL_HOME%\server\bin;%JAVA_HOME%\jre\bin;%JAVA_HOME%\bin;%WL_HOME%\server\bin\oci920_8

if "%WL_USE_64BITDLL%" == "true" set EXTRAPATH=%WL_HOME%\server\bin\win64;%WL_HOME%\server\bin;%JAVA_HOME%\jre\bin;%JAVA_HOME%\bin;%WL_HOME%\server\bin\win64\oci920_8

rem *** Install the service
"%WL_HOME%\server\bin\beasvc" -install -svcname:"beasvc %DOMAIN_NAME%_%SERVER_NAME%" -javahome:"%JAVA_HOME%" -execdir:"%USERDOMAIN_HOME%" -extrapath:"%EXTRAPATH%" -password:"%WLS_PW%" -cmdline:%CMDLINE%

:finish
ENDLOCAL
