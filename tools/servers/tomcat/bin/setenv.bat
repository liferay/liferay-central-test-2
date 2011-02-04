if exist "%CATALINA_HOME%/jre@java.version@/win" (
	if not "%JAVA_HOME%" == "" (
		set JAVA_HOME=
	)

	set "JRE_HOME=%CATALINA_HOME%/jre@java.version@/win"
)

set "JAVA_OPTS=%JAVA_OPTS% -Dfile.encoding=UTF8 -Djava.net.preferIPv4Stack=true -Duser.timezone=GMT -Xmx1024m -XX:MaxPermSize=256m -Dexternal-properties=portal-tcat.properties"
