if not "%JAVA_HOME%" == "" (
	set JAVA_HOME=
)

set JRE_HOME=%CATALINA_HOME%/jre@java.version@/win

set "JAVA_OPTS=%JAVA_OPTS% -Dfile.encoding=UTF8 -Djava.security.auth.login.config=%CATALINA_HOME%/conf/jaas.config -Dorg.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false -Duser.timezone=GMT -Xmx1024m -XX:MaxPermSize=256m"