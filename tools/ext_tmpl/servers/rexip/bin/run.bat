@echo off

set REXIP_HOME=C:\Progra~1\RexIP
set REXIP_DOMAIN_HOME=.
set REXIP_SERVER=liferayServer

call %REXIP_HOME%\bin\rexipenv.bat

%JAVA_HOME%\bin\java.exe -classpath %REXIP_HOME%\lib\tcc.jar;%REXIP_HOME%\bin;%REXIP_HOME%\lib\tcc_lib.jar;%JAVA_HOME%\lib\tools.jar -Drexip.home=%REXIP_HOME% -Drexip.server=%REXIP_SERVER% -Drexip.domain.home=%REXIP_DOMAIN_HOME% com.tcc.Main