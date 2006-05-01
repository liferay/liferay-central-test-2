Liferay Portal @lp.version@

You can be customize LP by using this script to add your own portlets and remove
any of the standard Liferay portlets.

This script currently supports the following servers: JBoss+Jetty, JBoss+Tomcat,
Jetty, JOnAS+Jetty, JOnAS+Tomcat, OracleAS, Orion, Resin, RexIP, Tomcat,
WebLogic, and WebSphere. LP also supports JRun, Pramati, and Sun JSAS. We will
upgrade this script to support these other servers as soon as possible.

See http://www.liferay.com/web/guest/devzone/documentation for
information on installing the prerequesites, writing custom portlets, and
customizing LP.

Ant scripts are customizable if it is a script named build.xml and imports
another script named build-parent.xml from the same directory. Customizable
scripts named build.xml will NOT be updated next time you run "ant build-ext"
from /portal whereas scripts named build-parent.xml will be updated.

JBoss+Jetty:

    Download liferay-portal-jboss-jetty-@lp.version.file.name@.zip from
    http://www.liferay.com to /ext/downloads

    Type "ant install-jboss-jetty" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=jboss-jetty"

    Type "ant deploy" from /ext

    Execute /ext/servers/jboss-jetty/bin/run.bat

JBoss+Tomcat:

    Download liferay-portal-jboss-tomcat-@lp.version.file.name@.zip from
    http://www.liferay.com to /ext/downloads

    Type "ant install-jboss-tomcat" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=jboss-tomcat"

    Type "ant deploy" from /ext

    Execute /ext/servers/jboss-tomcat/bin/run.bat

Jetty:

    Download liferay-portal-jetty-@lp.version.file.name@.zip from
    http://www.liferay.com to /ext/downloads

    Type "ant install-jetty" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=jetty"

    Type "ant deploy" from /ext

    Execute /ext/servers/jetty/bin/run.bat

JOnAS+Jetty:

    Download liferay-portal-jonas-jetty-@lp.version.file.name@.zip from
    http://www.liferay.com to /ext/downloads

    Type "ant install-jonas-jetty" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=jonas-jetty"

    Type "ant deploy" from /ext

    Execute /ext/servers/jonas-jetty/bin/nt/jonas.bat

JOnAS+Tomcat:

    Download liferay-portal-jonas-tomcat-@lp.version.file.name@.zip from
    http://www.liferay.com to /ext/downloads

    Type "ant install-jonas-tomcat" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=jonas-tomcat"

    Type "ant deploy" from /ext

    Execute /ext/servers/jonas-tomcat/bin/nt/jonas.bat

OracleAS:

    Download oc4j_extended.zip from http://www.oracle.com to /ext/downloads

    Type "ant install-oc4j" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=oc4j"

    Type "ant deploy" from /ext

    Edit /ext/servers/oc4j/config/global-web-application.xml to configure the
    path for JDK 1.4

    Edit /ext/servers/oc4j/config/server.xml to configure the path for Jikes

    Execute /ext/servers/oc4j/bin/run.bat

Orion:

    Download orion2.0.7.zip from http://www.orionserver.com to /ext/downloads

    Type "ant install-orion" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=orion"

    Type "ant deploy" from /ext

    Edit /ext/servers/orion/config/server.xml to configure the path for Jikes

    Execute /ext/servers/orion/bin/run.bat

RexIP:

    Download RexIPAppServer.exe from http://www.tradecity.com and install

    Edit /ext/app.server.properties and set "app.server.type=rexip"

    Type "ant deploy" from /ext

    Edit /ext/servers/rexip/bin/run.bat to configure the path for RexIP

    Execute /ext/servers/rexip/bin/run.bat

Tomcat:

    Download liferay-portal-tomcat-@lp.version.file.name@.zip from
    http://www.liferay.com to /ext/downloads

    Type "ant install-tomcat" from /ext/servers

    Edit /ext/app.server.properties and set "app.server.type=tomcat"

    Type "ant deploy" from /ext

    Execute /ext/servers/tomcat/bin/startup.bat

WebLogic:

    Download and install BEA WebLogic Platform 8.1

    Edit /ext/app.server.properties and set "app.server.type=weblogic"

    Type "ant deploy" from /ext

    Search for all files in /ext/servers/weblogic with phrase 
    C:\Program Files\BEA" and replace it with the location of your BEA
    directory

    Search for all files in /ext/servers/weblogic with phrase 
    C:\Program Files\BEA\jrockit81sp4_142_05" and replace it with the location
    of your JRockit directory

    Execute /ext/servers/weblogic/startWebLogic.cmd